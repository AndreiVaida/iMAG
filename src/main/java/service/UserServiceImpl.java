package service;

import converter.UserConverter;
import domain.User;
import dto.UserDto;
import dto.UserLoginRequestDto;
import dto.UserLoginResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;
import javax.xml.bind.ValidationException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private final static long HOUR = 3600 * 1000; // 1 hour in milliseconds
    private final static String SECRET_KEY = "abc";
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(UserDto userDto) {
        final User user = UserConverter.toEntity(userDto);
        try {
            userRepository.save(user);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("This user already exists.");
        }
        return user;
    }

    @Override
    public UserLoginResponseDto login(final UserLoginRequestDto userLoginRequestDto) throws AuthenticationException {
        // get the user by email
        final User user = userRepository.getByEmail(userLoginRequestDto.getEmail());
        if (user == null) {
            throw new EntityNotFoundException();
        }
        // check the password
        if (!user.getPassword().equals(userLoginRequestDto.getPassword())) {
            throw new AuthenticationException();
        }

        // return a token
        final String token = generateToken(user);
        return new UserLoginResponseDto(token, user.getId());
    }

    private String generateToken(final User user) {
        final Date expirationDate = new Date(new Date().getTime() + 12 * HOUR);

        return Jwts.builder()
                .claim("userId", user.getId())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    @Override
    public UserDto get(final Integer id) {
        final User user = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return UserConverter.toDto(user);
    }

    @Override
    public Integer validate(final String token) throws ValidationException, UnsupportedEncodingException {
        // check secret key
        final Jws<Claims> claims = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes("UTF-8"))
                .parseClaimsJws(token);

        // check expiration date
        final Date expirationDate = claims.getBody().getExpiration();
        if (expirationDate.before(new Date())) {
            throw new ValidationException("Sesiunea a expirat.");
        }

        // check claims
        final Integer userId = (Integer) claims.getBody().get("userId");
        if (userId == null) {
            throw new ValidationException("Token invalid.");
        }

        return userId;
    }

}

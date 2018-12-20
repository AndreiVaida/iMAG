package service;

import converter.UserConverter;
import domain.User;
import dto.UserDto;
import dto.UserLoginRequestDto;
import dto.UserLoginResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserDto userDto) {
        final User user = UserConverter.toEntity(userDto);
        userRepository.save(user);
    }

    @Override
    public UserLoginResponseDto login(final UserLoginRequestDto userLoginRequestDto) throws AuthenticationException {
        // get the user by email
        final User user = userRepository.getByEmail(userLoginRequestDto.getEmail());
        if (user == null) {
            throw new EntityNotFoundException();
        }
        // check the pasword
        if (!user.getPassword().equals(userLoginRequestDto.getPassword())) {
            throw new AuthenticationException();
        }

        // return a token
        final String token = sha256(user.getId() + "," + user.getEmail() + "," + user.getPassword());
        return new UserLoginResponseDto(token);
    }

    @Override
    public UserDto get(final Integer id) {
        final User user = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return UserConverter.toDto(user);
    }

    private String sha256(final String base) {
        final String salt = "mySecretPassword";
        final String stringToEncode = base + "." + salt;
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(stringToEncode.getBytes("UTF-8"));
            final StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
    }
}

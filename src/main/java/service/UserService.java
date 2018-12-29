package service;

import domain.User;
import dto.UserDto;
import dto.UserLoginRequestDto;
import dto.UserLoginResponseDto;

import javax.naming.AuthenticationException;
import javax.xml.bind.ValidationException;
import java.io.UnsupportedEncodingException;

public interface UserService {
    User createUser(final UserDto userDto);

    UserLoginResponseDto login(final UserLoginRequestDto userLoginRequestDto) throws AuthenticationException;

    UserDto get(final Integer id);

    /**
     * @return user id stored in token
     */
    Integer validate(final String token) throws ValidationException, UnsupportedEncodingException;
}

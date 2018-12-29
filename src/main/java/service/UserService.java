package service;

import domain.User;
import dto.UserDto;
import dto.UserLoginRequestDto;
import dto.UserLoginResponseDto;

import javax.naming.AuthenticationException;

public interface UserService {
    User createUser(final UserDto userDto);

    UserLoginResponseDto login(final UserLoginRequestDto userLoginRequestDto) throws AuthenticationException;

    UserDto get(final Integer id);
}

package converter;

import domain.User;
import dto.UserDto;

public class UserConverter {
    private UserConverter() {}

    public static UserDto toDto(final User user) {
        final UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    public static User toEntity(final UserDto userDto) {
        final User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        return user;
    }
}

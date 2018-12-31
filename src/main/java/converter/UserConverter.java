package converter;

import domain.User;
import dto.UserDto;

import java.time.LocalDate;

public class UserConverter {
    private UserConverter() {}

    public static UserDto toDto(final User user) {
        final UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        if (userDto.getBirthDay() != null) {
            userDto.setBirthDay(localDateToString(user.getBirthDay()));
        }
        return userDto;
    }

    public static User toEntity(final UserDto userDto) {
        final User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        if (userDto.getBirthDay() != null) {
            user.setBirthDay(stringToLocalDate(userDto.getBirthDay()));
        }
        return user;
    }

    private static String localDateToString(final LocalDate localDate) {
        return localDate.getYear() + "." + localDate.getMonth() + "." + localDate.getDayOfMonth();
    }

    private static LocalDate stringToLocalDate(final String stringDate) {
        try {
            final String[] dateParts = stringDate.split("\\.");
            final int year = Integer.parseInt(dateParts[0]);
            final int month = Integer.parseInt(dateParts[1]);
            final int day = Integer.parseInt(dateParts[2]);
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            return null;
        }
    }
}

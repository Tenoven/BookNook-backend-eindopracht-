package nl.tenoven.BookNook.Mappers;

import nl.tenoven.BookNook.Dtos.UserDtos.UserDto;
import nl.tenoven.BookNook.Models.User;

public class UserMappers {
    public static UserDto toUserDto(User user) {

        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.setEmail(user.getEmail());
        dto.authorities = user.getAuthorities();
        dto.picture = user.getPicture();

        return dto;
    }

    public static User toUser(UserDto userDto) {

        var user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
//        user.setAuthorities(userDto.getAuthorities());

        return user;
    }
}

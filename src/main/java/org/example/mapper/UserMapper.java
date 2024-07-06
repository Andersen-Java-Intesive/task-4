package org.example.mapper;

import org.example.dto.UserDto;
import org.example.model.User;

import javax.servlet.http.HttpServletRequest;

public interface UserMapper {

    User mapUserDtoToUser(UserDto userDto);

    UserDto mapRequestToUserDto(HttpServletRequest httpServletRequest);

    void updateUserFromDto(User user, UserDto userDto);

}

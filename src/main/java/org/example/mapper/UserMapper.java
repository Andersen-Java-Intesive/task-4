package org.example.mapper;

import org.example.dto.UserDto;
import org.example.model.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

public interface UserMapper {

    User mapUserDtoToUser (UserDto userDto);
    User mapResultSetToUser (ResultSet resultSet);
    UserDto mapRequestToUserDto(HttpServletRequest httpServletRequest);

}

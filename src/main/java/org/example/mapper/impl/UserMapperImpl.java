package org.example.mapper.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserDto;
import org.example.mapper.UserMapper;
import org.example.model.enums.Team;
import org.example.model.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class UserMapperImpl implements UserMapper {

    private static UserMapper instance;

    private UserMapperImpl() {
    }

    public static synchronized UserMapper getInstance() {
        if (instance == null) {
            instance = new UserMapperImpl();
        }
        return instance;
    }

    @Override
    public User mapUserDtoToUser(UserDto userDto) {
        return new User(userDto.getId(), userDto.getFirstName(), userDto.getSecondName(), userDto.getAge(), userDto.getTeam());
    }

    @Override
    public User mapResultSetToUser(ResultSet resultSet) {
        User user;
        try {
            user = User.builder()
                    .id(resultSet.getInt("id"))
                    .firstName(resultSet.getString("first_name"))
                    .secondName(resultSet.getString("second_name"))
                    .age(resultSet.getInt("age"))
                    .team(Team.valueOf(resultSet.getString("team")))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    @Override
    public UserDto mapRequestToUserDto(HttpServletRequest httpServletRequest) {
        UserDto user;
        try {
            String id = httpServletRequest.getParameter("id");
            user = UserDto.builder()
                    .id(id == null ? null : Integer.parseInt(id))
                    .firstName(httpServletRequest.getParameter("firstName"))
                    .secondName(httpServletRequest.getParameter("secondName"))
                    .age(Integer.parseInt(httpServletRequest.getParameter("age")))
                    .team(Team.valueOf(httpServletRequest.getParameter("team")))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}

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
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .secondName(userDto.getSecondName())
                .age(userDto.getAge())
                .team(Team.valueOf(userDto.getTeam()))
                .build();
    }

    @Override
    public UserDto mapRequestToUserDto(HttpServletRequest httpServletRequest) {
        UserDto user;
        try {
            String id = httpServletRequest.getParameter("id");
            user = UserDto.builder()
                    .id(id == null ? null : Long.parseLong(id))
                    .firstName(httpServletRequest.getParameter("firstName"))
                    .secondName(httpServletRequest.getParameter("secondName"))
                    .age(Integer.parseInt(httpServletRequest.getParameter("age")))
                    .team(httpServletRequest.getParameter("team"))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public void updateUserByUserDto(User user, UserDto userDto) {
        user.setFirstName(userDto.getFirstName());
        user.setSecondName(userDto.getSecondName());
        user.setAge(userDto.getAge());
        user.setTeam(Team.valueOf(userDto.getTeam()));
    }
}

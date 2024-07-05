package org.example.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserDto;
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
        return new User(userDto.getId(), userDto.getFirstName(), userDto.getSecondName(), userDto.getAge());
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
                    .build();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return user;
    }


    @Override
    public UserDto mapRequestToUserDto(HttpServletRequest httpServletRequest) {
        String id = httpServletRequest.getParameter("id");
        return UserDto.builder()
                .id(id == null ? null : Integer.parseInt(id))
                .firstName(httpServletRequest.getParameter("firstName"))
                .secondName(httpServletRequest.getParameter("secondName"))
                .age(Integer.parseInt(httpServletRequest.getParameter("age")))
                .build();
    }
}

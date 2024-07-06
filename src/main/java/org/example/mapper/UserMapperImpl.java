package org.example.mapper;

import org.example.dto.UserDto;
import org.example.model.User;

import javax.servlet.http.HttpServletRequest;

public class UserMapperImpl implements UserMapper {

    private static UserMapperImpl instance;

    private UserMapperImpl() {
    }

    public static synchronized UserMapperImpl getInstance() {
        if (instance == null) {
            instance = new UserMapperImpl();
        }
        return instance;
    }

    @Override
    public User mapUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setSecondName(userDto.getSecondName());
        user.setAge(userDto.getAge());
        return user;
    }

    @Override
    public UserDto mapRequestToUserDto(HttpServletRequest httpServletRequest) {
        UserDto userDto = new UserDto();
        userDto.setId(Integer.parseInt(httpServletRequest.getParameter("id")));
        userDto.setFirstName(httpServletRequest.getParameter("firstName"));
        userDto.setSecondName(httpServletRequest.getParameter("secondName"));
        userDto.setAge(Integer.parseInt(httpServletRequest.getParameter("age")));
        return userDto;
    }

    @Override
    public void updateUserFromDto(User user, UserDto userDto) {
        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getSecondName() != null) {
            user.setSecondName(userDto.getSecondName());
        }
        if (userDto.getAge() != 0) {
            user.setAge(userDto.getAge());
        }
    }

    @Override
    public User mapDtoToUser(UserDto userDto) {
        return mapUserDtoToUser(userDto);
    }
}

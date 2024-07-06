package org.example.mapper;

import org.example.dto.UserDto;
import org.example.model.User;

import javax.servlet.http.HttpServletRequest;

public class UserMapperImpl implements UserMapper {

    private static UserMapperImpl instance;

    private UserMapperImpl() {
    }

    public static UserMapperImpl getInstance() {
        if (instance == null) {
            instance = new UserMapperImpl();
        }
        return instance;
    }

    @Override
    public User mapUserDtoToUser(UserDto userDto) {
        return User.builder().id(userDto.getId()).firstName(userDto.getFirstName()).secondName(userDto.getSecondName()).age(userDto.getAge()).build();
    }

    @Override
    public UserDto mapRequestToUserDto(HttpServletRequest httpServletRequest) {
        return UserDto.builder().id(Integer.parseInt(httpServletRequest.getParameter("id"))).firstName(httpServletRequest.getParameter("firstName")).secondName(httpServletRequest.getParameter("secondName")).age(Integer.parseInt(httpServletRequest.getParameter("age"))).build();
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
}

package org.example.command.post;

import org.example.command.UsersCommand;
import org.example.dto.UserDto;
import org.example.mapper.UserMapper;
import org.example.mapper.impl.UserMapperImpl;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.example.util.ValidateUserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUser implements UsersCommand {

    private final UserService userService = UserServiceImpl.getInstance();
    private final UserMapper userMapper = UserMapperImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDto userDto = userMapper.mapRequestToUserDto(request);
        ValidateUserUtils.validate(userDto);
        userService.edit(userDto);
        response.sendRedirect("users");
    }

}

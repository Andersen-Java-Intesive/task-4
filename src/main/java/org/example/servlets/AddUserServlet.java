package org.example.servlets;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserDto;
import org.example.mapper.UserMapper;
import org.example.mapper.UserMapperImpl;
import org.example.model.User;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.example.util.ValidateUserUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();
    private final UserMapper userMapper = UserMapperImpl.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            request.getRequestDispatcher("/WEB-INF/views/addUser.jsp").forward(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(Arrays.asList(e.getStackTrace()).toString());
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=Exception" + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDto userDto = userMapper.mapRequestToUserDto(request);
        String validationError = ValidateUserUtils.validate(userDto);
        if (!validationError.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=" + validationError);
            return;
        }
        try {
            userService.add(userDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(Arrays.asList(e.getStackTrace()).toString());
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=Exception" + e.getMessage());
        }
        response.sendRedirect("users");
    }

}

package org.example.servlets;

import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

@Slf4j
@WebServlet(urlPatterns = {"/users", "/"})
public class UsersServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            LinkedHashSet<User> users = userService.findAll();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(Arrays.asList(e.getStackTrace()).toString());
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=Exception" + e.getMessage());
        }
    }

}

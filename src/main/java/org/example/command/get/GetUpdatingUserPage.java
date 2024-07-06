package org.example.command.get;

import org.example.command.UsersCommand;
import org.example.model.User;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetUpdatingUserPage implements UsersCommand {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        User user = userService.find(id);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/updateUser.jsp").forward(request, response);
    }
}

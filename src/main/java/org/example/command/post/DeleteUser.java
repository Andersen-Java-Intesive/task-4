package org.example.command.post;

import org.example.command.UsersCommand;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUser implements UsersCommand {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.remove(id);
        response.sendRedirect("users");
    }

}

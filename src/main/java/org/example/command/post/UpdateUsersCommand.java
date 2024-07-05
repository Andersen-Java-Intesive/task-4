package org.example.command.post;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.command.UsersCommand;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.example.util.ValidateUserUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUsersCommand implements UsersCommand {

    private final UserRepository userRepository = new UserService();
    private static final Logger logger = LogManager.getLogger(UpdateUsersCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("secondName");
        int age = Integer.parseInt(request.getParameter("age"));
        User user = new User(id, firstName, lastName, age);
        String validationError = ValidateUserUtils.validate(user);
        if (!validationError.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=" + validationError);
            return;
        }
        try {
            userRepository.update(user);
        } catch (Exception e) {
            logger.error(e);
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=Exception" + e.getMessage());
        }
        response.sendRedirect("users");
    }
}

package org.example.command.get;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.command.UsersCommand;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashSet;

public class GetAllUserPage implements UsersCommand {
    private final UserRepository userRepository = new UserService();
    private static final Logger logger = LogManager.getLogger(GetAllUserPage.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LinkedHashSet<User> users = userRepository.all();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error(e);
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=Exception" + e.getMessage());
        }
    }
}

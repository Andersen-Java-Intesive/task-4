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

public class GetUpdateUsersCommand implements UsersCommand {
    private final UserRepository userRepository = new UserService();
    private static final Logger logger = LogManager.getLogger(GetUpdateUsersCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            User user = userRepository.findById(id);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/views/updateUser.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error(e);
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=Exception" + e.getMessage());
        }
    }
}

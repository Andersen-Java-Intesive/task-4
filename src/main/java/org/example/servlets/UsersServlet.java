package org.example.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.repo.UserRepository;
import org.example.model.User;
import org.example.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;

@WebServlet(urlPatterns = {"/users", "/"})
public class UsersServlet extends HttpServlet {

    private final UserRepository userRepository = new UserService();
    private static final Logger logger = LogManager.getLogger(UsersServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            LinkedHashSet<User> users = userRepository.all();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error(e);
            response.sendRedirect("/error.jsp?error=Exception" + Arrays.toString(e.getStackTrace()));
        }
    }
}

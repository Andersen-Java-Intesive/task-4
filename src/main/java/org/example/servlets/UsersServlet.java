package org.example.servlets;

import org.example.repo.UserRepository;
import org.example.model.User;
import org.example.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

@WebServlet(urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {

    private UserRepository userRepository = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Set<User> users = userRepository.all();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("/error.jsp?error=Exception" + Arrays.toString(e.getStackTrace()));
        }
    }
}

package org.example.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.User;
import org.example.repo.UserRepository;
import org.example.service.UserService;
import org.example.util.ValidateUserUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {

    private final UserRepository userRepository = new UserService();
    private static final Logger logger = LogManager.getLogger(UpdateUserServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

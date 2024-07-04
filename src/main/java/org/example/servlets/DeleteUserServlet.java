package org.example.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.repo.UserRepository;
import org.example.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    private final UserRepository userRepository = new UserService();
    private static final Logger logger = LogManager.getLogger(DeleteUserServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            userRepository.deleteById(id);
        } catch (Exception e) {
            logger.error(e);
            response.sendRedirect("MyWebApp/error.jsp?error=Exception" + e.getMessage());
        }
        response.sendRedirect("users");
    }
}

package org.example.servlets;

import org.example.repo.UserRepository;
import org.example.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    private UserRepository userRepository = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            userRepository.deleteById(id);
        } catch (Exception e) {
            response.sendRedirect("MyWebApp/error.jsp?error=Exception" + Arrays.toString(e.getStackTrace()));
        }
        response.sendRedirect("users");
    }
}

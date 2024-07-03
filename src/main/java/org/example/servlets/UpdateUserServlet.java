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

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {

    private UserRepository userRepository = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userRepository.findById(id);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/updateUser.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("secondName");
        int age = Integer.parseInt(request.getParameter("age"));
        if (age < 0) {
            request.setAttribute("errorMessage", "No negative age allowed");
            request.getRequestDispatcher("/WEB-INF/views/addUser.jsp").forward(request, response);
            return;
        }
        User user = new User(id, firstName, lastName, age);
        userRepository.update(user);
        response.sendRedirect("users");
    }
}

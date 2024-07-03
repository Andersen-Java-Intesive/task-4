package org.example.servlets;

import org.example.repo.UserRepository;
import org.example.service.UserService;
import org.example.model.User;
import org.example.validation.ValidateUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/addUser.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        int age = Integer.parseInt(request.getParameter("age"));


        User user = new User();
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setAge(age);

        String validationError = ValidateUser.validate(user);
        if (!validationError.isEmpty()) {
            response.sendRedirect("/error.jsp?error=" + validationError);
            return;
        }
        UserRepository userRepository = new UserService();
        userRepository.create(user);

        response.sendRedirect("users");
    }
}

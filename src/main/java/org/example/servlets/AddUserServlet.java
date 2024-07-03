package org.example.servlets;

import org.example.dao.abs.UserDAO;
import org.example.dao.impl.UserDaoImpl;
import org.example.model.User;

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

        if (age < 0) {
            request.setAttribute("errorMessage", "No negative age allowed");
            request.getRequestDispatcher("/WEB-INF/views/addUser.jsp").forward(request, response);
            return;
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setAge(age);

        UserDAO userDAO = new UserDaoImpl();
        userDAO.create(user);

        response.sendRedirect("users");
    }
}

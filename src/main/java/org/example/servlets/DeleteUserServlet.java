package org.example.servlets;

import lombok.extern.slf4j.Slf4j;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            userService.remove(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(Arrays.asList(e.getStackTrace()).toString());
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=Exception" + e.getMessage());
        }
        response.sendRedirect("users");
    }

}

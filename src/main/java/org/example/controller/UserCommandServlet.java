package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.command.UsersCommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@WebServlet(urlPatterns = {"/users", "/"})
public class UserCommandServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UsersCommand command = UsersCommandFactory.getCommand(request);
            command.execute(request, response);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=Exception" + e.getMessage());
            log.error(e.getMessage(), e);
            log.debug(Arrays.asList(e.getStackTrace()).toString());
        }
    }
}
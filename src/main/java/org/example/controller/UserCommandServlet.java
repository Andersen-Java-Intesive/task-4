package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.command.UsersCommand;
import org.example.exception.DatabaseOperationException;
import org.example.exception.UserNotFoundException;

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
        } catch (UserNotFoundException e) {
            handleException(response, request, "UserNotFoundException", e);
        } catch (DatabaseOperationException e) {
            handleException(response, request, "DatabaseOperationException", e);
        } catch (Exception e) {
            handleException(response, request, "Exception", e);
        }
    }

    private void handleException(HttpServletResponse response, HttpServletRequest request, String exceptionType, Exception e) throws IOException {
        String errorMessage = "/error.jsp?error=" + exceptionType + ": " + e.getMessage();
        response.sendRedirect(request.getContextPath() + errorMessage);
        log.error(e.getMessage(), e);
        log.debug(Arrays.asList(e.getStackTrace()).toString());
    }
}
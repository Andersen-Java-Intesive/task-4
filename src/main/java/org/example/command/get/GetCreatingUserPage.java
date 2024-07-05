package org.example.command.get;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.command.UsersCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetCreatingUserPage implements UsersCommand {
    private static final Logger logger = LogManager.getLogger(GetCreatingUserPage.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.getRequestDispatcher("/WEB-INF/views/addUser.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error(e);
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=Exception" + e.getMessage());
        }
    }
}

package org.example.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface UsersCommand {
    void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
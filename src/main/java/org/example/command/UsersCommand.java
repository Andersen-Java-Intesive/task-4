package org.example.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UsersCommand {
    void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
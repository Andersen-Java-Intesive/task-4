package org.example.controller;

import org.example.command.*;
import org.example.command.get.GetAddUsersFormCommand;
import org.example.command.get.GetUpdateUsersCommand;
import org.example.command.get.ShowUsersCommand;
import org.example.command.post.AddUsersCommand;
import org.example.command.post.DeleteUsersCommand;
import org.example.command.post.UpdateUsersCommand;

import javax.servlet.http.HttpServletRequest;


public class UsersCommandFactory {
    public static UsersCommand getCommand(HttpServletRequest request) {
        //это незаконная строчка, которая считывает из строки запроса экшн для правильного выбора команды
        //TODO: переделать
        String action = request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1);



        String method = request.getMethod();
        if (action.equals("users")) {
            return new ShowUsersCommand();
        }
        if (action.equals("deleteUser")) {
            return new DeleteUsersCommand();
        }
        if (action.equals("addUser") && method.equals("GET")) {
            return new GetAddUsersFormCommand();
        }
        if (action.equals("addUser") && method.equals("POST")) {
            return new AddUsersCommand();
        }
        if (action.equals("updateUser") && method.equals("GET")) {
            return new GetUpdateUsersCommand();
        }
        if (action.equals("updateUser") && method.equals("POST")) {
            return new UpdateUsersCommand();
        }
        return new ShowUsersCommand();
    }
}

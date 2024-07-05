package org.example.controller;

import org.example.command.*;
import org.example.command.get.GetCreatingUserPage;
import org.example.command.get.GetUpdatingUserPage;
import org.example.command.get.GetAllUserPage;
import org.example.command.post.CreateUser;
import org.example.command.post.DeleteUser;
import org.example.command.post.UpdateUser;

import javax.servlet.http.HttpServletRequest;


public class UsersCommandFactory {
    public static UsersCommand getCommand(HttpServletRequest request) {
        //это незаконная строчка, которая считывает из строки запроса экшн для правильного выбора команды
        //TODO: переделать
        String action = request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1);



        String method = request.getMethod();
        if (action.equals("users")) {
            return new GetAllUserPage();
        }
        if (action.equals("deleteUser")) {
            return new DeleteUser();
        }
        if (action.equals("addUser") && method.equals("GET")) {
            return new GetCreatingUserPage();
        }
        if (action.equals("addUser") && method.equals("POST")) {
            return new CreateUser();
        }
        if (action.equals("updateUser") && method.equals("GET")) {
            return new GetUpdatingUserPage();
        }
        if (action.equals("updateUser") && method.equals("POST")) {
            return new UpdateUser();
        }
        return new GetAllUserPage();
    }
}

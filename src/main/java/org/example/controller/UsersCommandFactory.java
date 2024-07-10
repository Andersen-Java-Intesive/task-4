package org.example.controller;

import org.example.command.UsersCommand;
import org.example.command.get.GetCreatingUserPage;
import org.example.command.get.GetUpdatingUserPage;
import org.example.command.get.GetAllUserPage;
import org.example.command.get.GetUserPairs;
import org.example.command.post.CreateMarks;
import org.example.command.post.CreateUser;
import org.example.command.post.DeleteUser;
import org.example.command.post.UpdateUser;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class UsersCommandFactory {
    private static final Map<String, UsersCommand> commandMap = new HashMap<>();

    static {
        init();
    }

    private static void init() {
        commandMap.put("GET/users", new GetAllUserPage());
        commandMap.put("POST/deleteUser", new DeleteUser());
        commandMap.put("GET/addUser", new GetCreatingUserPage());
        commandMap.put("POST/addUser", new CreateUser());
        commandMap.put("GET/updateUser", new GetUpdatingUserPage());
        commandMap.put("POST/updateUser", new UpdateUser());
        commandMap.put("POST/formPairs", new GetUserPairs());
        commandMap.put("POST/addMarks", new CreateMarks());
    }

    public static UsersCommand getCommand(HttpServletRequest request) {
        String action = request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1);
        String method = request.getMethod();
        return commandMap.getOrDefault(method + "/" + action, new GetAllUserPage());
    }
}

package org.example.command.get;

import org.example.command.UsersCommand;
import org.example.model.User;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;


public class GetUserPairs implements UsersCommand {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        userService.generateUserPairs();
        List<Map.Entry<User, User>> userPairs = userService.getUserPairs();
        userPairs.forEach((userPair) -> {
            System.out.println(userPair.getKey().getId() + ", " + userPair.getValue().getId());
        });
        List<User> pairlessUsers = userService.getPairlessUsers();
        request.setAttribute("pairs", userPairs);
        request.setAttribute("pairlessUsers", pairlessUsers);
        request.getRequestDispatcher("/WEB-INF/views/userPairs.jsp").forward(request, response);
    }
}

package org.example.command.get;

import org.example.command.UsersCommand;
import org.example.model.User;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashSet;
import java.util.List;


public class GetUserPairs implements UsersCommand {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<UserServiceImpl.Pair<User, User>> pairs = userService.generateUserPairs();
        request.setAttribute("pairs", pairs);
        LinkedHashSet<User> pairlessUsers = userService.getPairlessUsers(pairs);
        request.setAttribute("pairlessUsers", pairlessUsers);
        request.getRequestDispatcher("/WEB-INF/views/userPairs.jsp").forward(request, response);
    }
}

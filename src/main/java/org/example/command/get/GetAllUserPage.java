package org.example.command.get;

import org.example.command.UsersCommand;
import org.example.model.User;
import org.example.service.MarkService;
import org.example.service.UserService;
import org.example.service.impl.MarkServiceImpl;
import org.example.service.impl.Pair;
import org.example.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Map;

public class GetAllUserPage implements UsersCommand {

    private final UserService userService = UserServiceImpl.getInstance();
    private final MarkService markService = MarkServiceImpl.getInstance();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LinkedHashSet<User> users = userService.findAll();
        request.setAttribute("users", users);
        LinkedHashSet<Pair<Date, Map<User, Double>>> lessons = markService.groupMarksByLessonDate(markService.findAll());
        request.setAttribute("lessons", lessons);
        Map<User, Double> userTotalScores = markService.getUserTotalScores(lessons);
        request.setAttribute("userTotalScores", userTotalScores);
        request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
    }
}

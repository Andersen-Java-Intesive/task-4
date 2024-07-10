package org.example.command.post;

import org.example.command.UsersCommand;
import org.example.dto.MarkDto;
import org.example.model.User;
import org.example.service.MarkService;
import org.example.service.UserService;
import org.example.service.impl.MarkServiceImpl;
import org.example.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.LinkedHashSet;

public class CreateMarks implements UsersCommand {

    private final MarkService markService = MarkServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String lessonDateStr = request.getParameter("lessonDate");
        Date lessonDate = Date.valueOf(lessonDateStr);
        LinkedHashSet<User> users = userService.findAll();
        for (User user : users) {
            for (User pairedUser : users) {
                if (!user.equals(pairedUser)) {
                    String userMarkStr = request.getParameter("userMark_" + user.getId() + "_" + pairedUser.getId());
                    String pairedUserMarkStr = request.getParameter("pairedUserMark_" + user.getId() + "_" + pairedUser.getId());
                    if (userMarkStr != null && pairedUserMarkStr != null) {
                        MarkDto markDto = MarkDto.builder()
                                .lessonDate(lessonDate)
                                .userOneId(user.getId())
                                .userOneMark(Double.parseDouble(userMarkStr))
                                .userTwoId(pairedUser.getId())
                                .userTwoMark(Double.parseDouble(pairedUserMarkStr))
                                .build();
                        markService.add(markDto);
                    }
                }
            }
        }
        response.sendRedirect("users");
    }
}

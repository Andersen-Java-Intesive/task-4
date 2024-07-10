package org.example.command.post;

import org.example.command.UsersCommand;
import org.example.dto.MarkDto;
import org.example.mapper.MarkMapper;
import org.example.mapper.impl.MarkMapperImpl;
import org.example.model.Mark;
import org.example.model.User;
import org.example.service.MarkService;
import org.example.service.UserService;
import org.example.service.impl.MarkServiceImpl;
import org.example.service.impl.UserServiceImpl;
import org.example.validation.util.ValidateMarkUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashSet;

public class CreateMarks implements UsersCommand {
    private MarkService markService = MarkServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();
    private MarkMapper markMapper = MarkMapperImpl.getInstance();

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
                        double userMark = Double.parseDouble(userMarkStr);
                        double pairedUserMark = Double.parseDouble(pairedUserMarkStr);
                        Mark mark = new Mark(0, lessonDate, user.getId(), userMark, pairedUser.getId(), pairedUserMark);
                        MarkDto markDto = markMapper.mapMarkToMarkDto(mark);
                        ValidateMarkUtils.validate(markDto);
                        markService.add(markDto);
                    }
                }
            }
        }
        response.sendRedirect("users");
    }
}

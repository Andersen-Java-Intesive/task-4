package org.example.mapper.impl;

import org.example.dto.MarkDto;
import org.example.mapper.MarkMapper;
import org.example.model.Mark;


import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MarkMapperImpl implements MarkMapper {
    private static MarkMapper instance;

    private MarkMapperImpl() {
    }

    public static synchronized MarkMapper getInstance() {
        if (instance == null) {
            instance = new MarkMapperImpl();
        }
        return instance;
    }

    @Override
    public MarkDto mapMarkToMarkDto(Mark mark) {
        return MarkDto.builder()
                .id(mark.getId())
                .lessonDate(mark.getLessonDate())
                .userOneId(mark.getUserOneId())
                .userOneMark(mark.getUserOneMark())
                .userTwoId(mark.getUserTwoId())
                .userTwoMark(mark.getUserTwoMark())
                .build();
    }

    @Override
    public Mark mapMarkDtoToMark(MarkDto markDto) {
        return new Mark(
                markDto.getId(),
                markDto.getLessonDate(),
                markDto.getUserOneId(),
                markDto.getUserOneMark(),
                markDto.getUserTwoId(),
                markDto.getUserTwoMark()
        );
    }

    @Override
    public Mark mapResultSetToMark(ResultSet resultSet) {
        try {
            return Mark.builder()
                    .id(resultSet.getInt("id"))
                    .lessonDate(resultSet.getDate("lesson_date"))
                    .userOneId(resultSet.getInt("user_one_id"))
                    .userOneMark(resultSet.getDouble("user_one_mark"))
                    .userTwoId(resultSet.getInt("user_two_id"))
                    .userTwoMark(resultSet.getDouble("user_two_mark"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MarkDto mapRequestToUserDto(HttpServletRequest httpServletRequest) {
        MarkDto markDto;
        try {
            String id = httpServletRequest.getParameter("id");
            markDto = MarkDto.builder()
                    .id(id == null ? null : Integer.parseInt(id))
                    .lessonDate(Date.valueOf(httpServletRequest.getParameter("lesson_date")))
                    .userOneId(Integer.parseInt(httpServletRequest.getParameter("user_one_id")))
                    .userOneMark(Double.valueOf(httpServletRequest.getParameter("user_one_mark")))
                    .userTwoId(Integer.parseInt(httpServletRequest.getParameter("user_two_id")))
                    .userTwoMark(Double.valueOf(httpServletRequest.getParameter("user_two_mark")))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return markDto;
    }
}


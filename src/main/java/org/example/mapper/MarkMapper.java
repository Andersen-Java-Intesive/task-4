package org.example.mapper;

import org.example.dto.MarkDto;
import org.example.model.Mark;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

public interface MarkMapper {
    MarkDto mapMarkToMarkDto(Mark mark);

    Mark mapMarkDtoToMark (MarkDto markDto);
    Mark mapResultSetToMark (ResultSet resultSet);
    MarkDto mapRequestToUserDto(HttpServletRequest httpServletRequest);
}

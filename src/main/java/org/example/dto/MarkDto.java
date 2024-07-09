package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class MarkDto {
    private int id;
    private Timestamp lessonDate;
    private int userOneId;
    private Double userOneMark;
    private int userTwoId;
    private Double userTwoMark;
}

package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.sql.Date;
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
    @NotNull(message = "Lesson date cannot be null")
    @PastOrPresent(message = "Lesson date cannot be a future date")
    private Date lessonDate;
    private int userOneId;
    @NotNull(message = "User mark cannot be null")
    private Double userOneMark;
    private int userTwoId;
    @NotNull(message = "User mark cannot be null")
    private Double userTwoMark;
}

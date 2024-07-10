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
    @Pattern(regexp = "^[0-6](\\.0|\\.5)?$", message = "Mark must be between 0 and 6 inclusive with step of 0.5")
    private Double userOneMark;
    private int userTwoId;
    @NotNull(message = "User mark cannot be null")
    @Pattern(regexp = "^[0-6](\\.0|\\.5)?$", message = "Mark must be between 0 and 6 inclusive with step of 0.5")
    private Double userTwoMark;
}

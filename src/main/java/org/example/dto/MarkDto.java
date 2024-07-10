package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.sql.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class MarkDto {

    private UUID id;

    @NotNull(message = "Lesson date cannot be null")
    @PastOrPresent(message = "Lesson date cannot be a future date")
    private Date lessonDate;

    private UUID userOneId;

    @NotNull(message = "User mark cannot be null")
    private Double userOneMark;

    private UUID userTwoId;

    @NotNull(message = "User mark cannot be null")
    private Double userTwoMark;

}

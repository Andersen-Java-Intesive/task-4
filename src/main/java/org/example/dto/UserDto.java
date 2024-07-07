package org.example.dto;

import lombok.*;
import org.example.model.enums.Team;
import org.example.validation.annotation.NotBlankNotNullNotEmpty;
import org.example.validation.annotation.ValidTeam;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class UserDto {

    private Integer id;

    @NotBlankNotNullNotEmpty
    @Size(max = 50, message = "First Name field must be no more than 50 characters long")
    @Pattern(regexp = "^[A-Za-z \\s]*$", message = "No non-latin characters or punctuation allowed")
    private String firstName;

    @NotBlankNotNullNotEmpty
    @Size(max = 50, message = "Second Name field must be no more than 50 characters long")
    @Pattern(regexp = "^[A-Za-z \\s]*$", message = "No non-latin characters or punctuation allowed")
    private String secondName;

    @Min(value = 0, message = "Age is improbable")
    @Max(value = 999, message = "Age is improbable")
    private Integer age;

    @NotNull(message = "Empty Team field")
    @ValidTeam
    private Team team;
}

package org.example.dto;

import lombok.*;
import org.example.model.Team;

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

    @NotBlank(message = "Empty First Name field")
    @NotEmpty(message = "Empty First Name field")
    @Size(max = 50, message = "First Name field must be no more than 50 characters long")
    @Pattern(regexp = "^[A-Za-z\\s]*$", message = "No non-latin characters allowed")
    @Pattern(regexp = "^[^\\p{Punct}]*$", message = "No punctuation allowed")
    private String firstName;

    @NotBlank(message = "Empty Second Name field")
    @NotEmpty(message = "Empty Second Name field")
    @Size(max = 50, message = "Second Name field must be no more than 50 characters long")
    @Pattern(regexp = "^[A-Za-z\\s]*$", message = "No non-latin characters allowed")
    @Pattern(regexp = "^[^\\p{Punct}]*$", message = "No punctuation allowed")
    private String secondName;

    @Min(value = 0, message = "Age is improbable")
    @Max(value = 999, message = "Age is improbable")
    private Integer age;

    @NotNull(message = "Empty Team field")
    private Team team;
}

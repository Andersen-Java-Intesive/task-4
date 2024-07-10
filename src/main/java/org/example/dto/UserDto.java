package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Builder;
import org.example.validation.annotation.NotBlankNotNullNotEmpty;
import org.example.validation.annotation.ValidTeam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class UserDto {

    private UUID id;

    @NotBlankNotNullNotEmpty
    @Size(max = 50, message = "First Name field must be no more than 50 characters long")
    @Pattern(regexp = "^[A-Za-z \\s]*$", message = "No non-latin characters or punctuation allowed")
    private String firstName;

    @NotBlankNotNullNotEmpty
    @Size(max = 50, message = "Second Name field must be no more than 50 characters long")
    @Pattern(regexp = "^[A-Za-z \\s]*$", message = "No non-latin characters or punctuation allowed")
    private String secondName;

    @NotNull(message = "Age field is required")
    private Date age;

    @NotBlank(message = "Empty Team field")
    @ValidTeam
    private String team;
}
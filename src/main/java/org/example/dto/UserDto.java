package org.example.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class UserDto {

    private Integer id;
    private String firstName;
    private String secondName;
    private Integer age;

}

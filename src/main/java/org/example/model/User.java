package org.example.model;

import lombok.*;
import org.example.model.enums.Team;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class User {

    private int id;
    private String firstName;
    private String secondName;
    private int age;
    private Team team;

}
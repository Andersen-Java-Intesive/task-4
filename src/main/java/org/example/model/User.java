package org.example.model;

import lombok.*;
import org.example.model.enums.Team;

import java.sql.Date;

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
    private Date age;
    private Team team;

}
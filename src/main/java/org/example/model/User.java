package org.example.model;

import javax.validation.Constraint;
import javax.validation.constraints.*;

public class User {

    private int id;
    @NotBlank(message = "Empty First Name field")
    @NotNull(message = "Empty First Name field")
    @NotEmpty(message = "Empty First Name field")
    @Size(max = 50, message = "First Name field must be no more than 50 characters long")
    @Pattern(regexp = "^[A-Za-z\\s]*$", message = "No non-latin characters allowed")
    @Pattern(regexp = "^[^\\p{Punct}]*$", message = "No punctuation allowed")
    private String firstName;
    @NotBlank(message = "Empty Second Name field")
    @NotNull(message = "Empty Second Name field")
    @NotEmpty(message = "Empty Second Name field")
    @Size(max = 50, message = "Second Name field must be no more than 50 characters long")
    @Pattern(regexp = "^[A-Za-z\\s]*$", message = "No non-latin characters allowed")
    @Pattern(regexp = "^[^\\p{Punct}]*$", message = "No punctuation allowed")
    private String secondName;
    @Min(value = 0, message = "Age is improbable")
    @Max(value = 999, message = "Age is improbable")
    private int age;

    public User() {}

    public User(String firstName, String secondName, int age) {
        super();
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
    }

    public User(int id, String firstName, String secondName, int age) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                '}';
    }
}
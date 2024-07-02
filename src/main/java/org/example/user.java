package net.javaguides.usermanagement.model;

public class User {
    private int id;
    private String firstName;
    private String secondName;
    private int age;

    public User() {}

    public User(String firstName, String secondName, int age) {
        super();
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
    }
}
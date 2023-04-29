package ru.naumen.dev.entity;

import org.springframework.stereotype.Component;


@Component
public class User {
    private String firstName;
    private int age;


    public User() {
    }

    public User(String firstName, int age) {
        this.firstName = firstName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}

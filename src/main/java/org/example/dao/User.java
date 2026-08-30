package org.example.dao;

public class User {
    private int id;
    private String firstName;
    private String lastName;

    public User( int id,String firstName, String lastName) {
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }
}

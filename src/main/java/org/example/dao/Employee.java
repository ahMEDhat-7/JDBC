package org.example.dao;

public class Employee {
    private int id;
    private String name;
    private boolean gender;

    public Employee() {
    }

    public Employee(int id, String name,boolean gender ) {
        this.gender = gender;
        this.id = id;
        this.name = name;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

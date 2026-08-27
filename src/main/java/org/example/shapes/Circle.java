package org.example.shapes;

import org.example.config.DatabaseOperations;

public class Circle implements Shape{
    private final DatabaseOperations databaseOperations;
    private double radius;

    public Circle(DatabaseOperations databaseOperations) {
        this.databaseOperations = databaseOperations;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }



    @Override
    public String draw() {
        return "Circle";
    }
    @Override
    public double getArea() {
        return Math.PI * Math.pow(this.radius,2);
    }
}

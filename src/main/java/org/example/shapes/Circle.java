package org.example.shapes;

import org.example.config.DatabaseOperations;

public class Circle implements Shape{
    private  DatabaseOperations databaseOperations;
    private double radius;
    public Circle(){}

    public Circle(DatabaseOperations databaseOperations) {
        this.databaseOperations = databaseOperations;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void connectToDb(){
        System.out.println("Connecting to Circle Database...");
    }
    public void disconnectToDb(){
        System.out.println("Disconnecting to Circle Database...");
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

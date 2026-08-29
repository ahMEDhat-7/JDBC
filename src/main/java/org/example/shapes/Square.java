package org.example.shapes;


import org.example.config.DatabaseOperations;

public class Square implements Shape{
    private DatabaseOperations databaseOperations;

    public void setDatabaseOperations(DatabaseOperations databaseOperations){
        this.databaseOperations = databaseOperations;
    }
    private double side;
    public Square() {

    }

    public void setSide(double side) {
        this.side = side;
    }

    public void connectToDb(){
        System.out.println("Connecting to Square Database...");
    }
    public void disconnectToDb(){
        System.out.println("Disconnecting to Square Database...");
    }
    @Override
    public String draw() {
        return "Square";
    }

    @Override
    public double getArea() {
        return Math.pow(this.side,2);
    }
}

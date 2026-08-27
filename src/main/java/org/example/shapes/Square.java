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

    @Override
    public String draw() {
        return "Square";
    }

    @Override
    public double getArea() {
        return Math.pow(this.side,2);
    }
}

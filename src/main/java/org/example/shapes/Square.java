package org.example.shapes;


public class Square implements Shape{
    private double side;
    public Square() {
    }

    public void setSide(double side) {
        this.side = side;
    }

    @Override
    public void draw() {
        System.out.println("Square drawing...");
    }

    @Override
    public double getArea() {
        return Math.pow(this.side,2);
    }
}

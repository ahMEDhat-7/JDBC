package org.example.shapes;

public class Circle implements Shape{
    private double radius;
    public Circle() {
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("Circle drawing...");
    }

    @Override
    public double getArea() {
        return Math.PI * Math.pow(this.radius,2);
    }
}

package org.example.shapes;

public class Circle implements Shape{
    private final DrawShapes drawShapes;
    private double radius;

    public Circle(DrawShapes drawShape) {
        this.drawShapes = drawShape;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public void draw() {
        this.drawShapes.draw("Circle");
    }

    @Override
    public double getArea() {
        return Math.PI * Math.pow(this.radius,2);
    }
}

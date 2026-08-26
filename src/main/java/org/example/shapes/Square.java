package org.example.shapes;


public class Square implements Shape{
    private final DrawShapes drawShapes;

    private double side;
    public Square(DrawShapes drawShape) {
        this.drawShapes = drawShape;
    }

    public void setSide(double side) {
        this.side = side;
    }

    @Override
    public void draw() {
        this.drawShapes.draw("Square");
    }

    @Override
    public double getArea() {
        return Math.pow(this.side,2);
    }
}

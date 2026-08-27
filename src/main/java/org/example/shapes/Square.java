package org.example.shapes;


public class Square implements Shape{
    private DrawShapes drawShapes;

    public void setDrawShapes(DrawShapes drawShapes){
        this.drawShapes = drawShapes;
    }
    private double side;
    public Square() {

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

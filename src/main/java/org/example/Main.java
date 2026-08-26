package org.example;


import org.example.shapes.DrawShapes;
import org.example.shapes.Shape;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
    public static void main(String[] args) {

        ApplicationContext container = new ClassPathXmlApplicationContext("application-context.xml");
        Shape myCircle = container.getBean("circle", Shape.class);
        Shape mySquare = container.getBean("square",Shape.class);

        DrawShapes myDraw = container.getBean("drawShape" , DrawShapes.class);

        myCircle.draw();
        mySquare.draw();




    }
}
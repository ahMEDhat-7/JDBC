package org.example;

import org.example.config.DataBaseConnection;
import org.example.designpatterns.Singleton;
//
//import org.example.config.DatabaseOperations;
//import org.example.shapes.DrawShapes;
import org.example.shapes.Shape;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {

        try {
            Connection connection = DataBaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs=  statement.executeQuery();
            int count =10;
            while (rs.next() && count != 0){
                System.out.printf("%s:%s:%s\n",rs.getString("id"),rs.getString("first_name"),rs.getString("last_name"));
                --count;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
package org.example;

import org.example.config.DataBaseConnection;
import org.example.config.DatabaseOperations;
import org.example.dao.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception{
        DatabaseOperations ops = new DatabaseOperations();

//            List<User> users = ops.selectAll();
//            for (User user: users) {
//                System.out.println(user.toString());
//            }


       ops.update(new User(1500,"Mazen","Mohamed") , 1500);


        System.out.println(ops.selectOneById(1500).toString());
        ops.delete(1500);
        if (ops.selectOneById(1500) == null){
            System.out.println("User not found");

        }


    }
}
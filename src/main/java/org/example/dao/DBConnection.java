package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String host = "127.0.0.1";
    private static final int post = 5432;
    private static final String dbName = "jdbc_course_db";
    private static final String username = "postgres";
    private static final String password = "postgres";

    private static Connection connection;
    public static Connection getDBConnection() {

        try{
            connection = DriverManager.getConnection(String.format("jdbc:postgresql://%s:%d/%s",host,post,dbName),username,password);

        }catch (SQLException se){
            se.printStackTrace();
        }
        return connection;
    }
}

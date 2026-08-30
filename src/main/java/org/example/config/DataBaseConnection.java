package org.example.config;

import oracle.jdbc.OracleDriver;
import oracle.jdbc.driver.*;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class DataBaseConnection {

    private static Connection connection;
    private DataBaseConnection(){}

    public synchronized static Connection getInstance() throws SQLException{

        if (connection ==null || connection.isClosed()){
             connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres","postgres");
        }
        return connection;
    }
}

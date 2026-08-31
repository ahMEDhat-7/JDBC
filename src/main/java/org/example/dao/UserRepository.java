package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.config.DataBaseOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserRepository implements DataBaseOperations<User> {
    private final Connection connection;
    public  UserRepository(Connection connection){
        this.connection = connection;
    }
    @Override
    public List<User> selectAll() throws SQLException {
        List<User> users = new LinkedList<>();
        String selectAllQuery = "SELECT * FROM users";
        try (PreparedStatement statement = this.connection.prepareStatement(selectAllQuery);
             ResultSet resultSet = statement.executeQuery();){

            while (resultSet.next()){
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                ));
            }
            return users;
        }

    }

    @Override
    public User selectOneById(int id)  throws SQLException{
        String selectOneQuery = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(selectOneQuery);){
            statement.setInt(1,id);
            try ( ResultSet resultSet = statement.executeQuery();){
                if (resultSet.next()){
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name")
                    );
                }
            }

        }
        return null;
    }

    @Override
    public void insert(User value)  throws SQLException{
        String insertQuery = "INSERT INTO users(id,first_name,last_name) VALUES(?,?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(insertQuery);){
            statement.setInt(1,value.getId());
            statement.setString(2,value.getFirstName());
            statement.setString(3,value.getLastName());
            int executed = statement.executeUpdate();

        }
    }

    @Override
    public void update(User value, int id) throws SQLException {
        String updateQuery = "UPDATE users SET first_name = ? , last_name = ? WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(updateQuery);){
            statement.setString(1,value.getFirstName());
            statement.setString(2,value.getLastName());
            statement.setInt(3, id);

            int executed = statement.executeUpdate();

        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String deleteQuery = "Delete FROM users WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(deleteQuery);
        ){
            statement.setInt(1, id);
            int executed = statement.executeUpdate();

        }
    }
}

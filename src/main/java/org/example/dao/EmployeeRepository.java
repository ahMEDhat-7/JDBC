package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepository implements EmployeeDao {

    @Override
    public List<Employee> findAll() throws SQLException {
        Connection conn = DBConnection.getDBConnection();
        if (conn == null) {
            System.out.println("Connection Failed!!");
        } else {
            System.out.println("Connection Succeeded!!");
            String query = "SELECT * FROM Employee";
            List<Employee> allEmployees = new LinkedList<>();
            try (conn; PreparedStatement ps = conn.prepareStatement(query);) {
                ResultSet employees = ps.executeQuery();
                while (employees.next()){
                    Employee emp = new Employee(employees.getInt("id"),employees.getString("name"),employees.getBoolean("gender"));
                    allEmployees.add(emp);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return allEmployees;
        }
        return List.of();
    }

    @Override
    public Optional<Employee> findOne() {
        return Optional.empty();
    }
}

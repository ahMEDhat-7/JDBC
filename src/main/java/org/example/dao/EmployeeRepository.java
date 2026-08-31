package org.example.dao;

import org.example.config.DataBaseOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepository implements DataBaseOperations<Employee> {



    @Override
    public List<Employee> selectAll() throws SQLException {
        return List.of();
    }

    @Override
    public Employee selectOneById(int id) throws SQLException {
        return null;
    }

    @Override
    public void insert(Employee value) throws SQLException {

    }

    @Override
    public void update(Employee value, int id) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }
}

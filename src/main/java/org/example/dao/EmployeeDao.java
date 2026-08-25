package org.example.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    public List<Employee> findAll() throws SQLException;
    public Optional<Employee> findOne();


}

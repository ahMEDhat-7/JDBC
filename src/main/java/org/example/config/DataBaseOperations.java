package org.example.config;

import java.sql.SQLException;
import java.util.List;

public interface DataBaseOperations<T> {
    List<T> selectAll() throws SQLException;
    T selectOneById(int id) throws SQLException;
    void insert(T value) throws SQLException;
    void update(T value,int id) throws SQLException;
    void delete(int id) throws SQLException;

}

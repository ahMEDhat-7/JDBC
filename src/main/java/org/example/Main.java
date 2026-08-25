package org.example;


import org.example.dao.DBConnection;
import org.example.dao.EmployeeDao;
import org.example.dao.EmployeeRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        EmployeeDao employeeDao = new EmployeeRepository();
        try {
            var listOfEmp = employeeDao.findAll();
            listOfEmp.forEach(employee -> System.out.println(String.format("%d-%s-%s",employee.getId(),employee.getName(),employee.getGender())));
        }catch (SQLException se){
            se.printStackTrace();
        }
    }
}
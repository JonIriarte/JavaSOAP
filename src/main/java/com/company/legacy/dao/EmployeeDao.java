package com.company.legacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.company.legacy.model.Employee;
import com.company.legacy.util.DBConnection;

public class EmployeeDao {
 public List<Employee> findAll() throws Exception {

    
List<Employee> employees = new ArrayList<>();

        String sql = "SELECT * FROM employee";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getInt("id"));
                e.setFirstName(rs.getString("first_name"));
                e.setLastName(rs.getString("last_name"));
                e.setEmail(rs.getString("email"));
            employees.add(e);
            }
        }

        return employees;
    }



 }


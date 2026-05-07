package com.company.legacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.company.legacy.model.Employee;
import com.company.legacy.model.Role;
import com.company.legacy.util.DBConnection;

public class EmployeeDao {
    public List<Employee> findAll() throws Exception {

        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT e.id, e.first_name, e.last_name, e.email, r.id AS role_id, r.role FROM employee e  LEFT JOIN role r ON e.role_id = r.id;";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee e = mapRow(rs);
                employees.add(e);
            }
        }

        return employees;
    }

    public Employee findById(int id) {
        String sql = "SELECT e.id, e.first_name, e.last_name, e.email, r.role FROM employee e LEFT JOIN role r ON e.role_id = r.id WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void insert(Employee employee) {
        String sql = "INSERT INTO employee (first_name, last_name, email) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getEmail());

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Employee employee) {
        String sql = "UPDATE employee SET first_name = ?, last_name = ?, email = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getEmail());
            ps.setInt(4, employee.getId());

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM employee WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Employee mapRow(ResultSet rs) throws SQLException {
        Employee emp = new Employee();
        emp.setId(rs.getInt("id"));
        emp.setFirstName(rs.getString("first_name"));
        emp.setLastName(rs.getString("last_name"));
        emp.setEmail(rs.getString("email"));

        Role role = new Role();

        if (rs.getInt("role_id") != 0) {

            role.setId(rs.getInt("role_id"));
            role.setRole(rs.getString("role"));
            emp.setRole(role);
        } else {
            role.setRole("No role assigned");
            emp.setRole(role);
        }

        return emp;
    }

}

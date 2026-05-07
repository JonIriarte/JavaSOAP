package com.company.legacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.company.legacy.model.Employee;
import com.company.legacy.model.Role;
import com.company.legacy.util.DBConnection;

public class EmployeeDao {
    public List<Employee> findAll() throws Exception {

        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, email, role_id FROM employee;";
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
        String sql = "SELECT e.id, e.first_name, e.last_name, e.email, e.role_id FROM employee WHERE id = ?";

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
        String sql = "INSERT INTO employee (first_name, last_name, email, role_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getEmail());

            if (employee.getRole() != null) {
                ps.setInt(4, employee.getRole().getId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Employee employee) {
        String sql = "UPDATE employee SET first_name = ?, last_name = ?, email = ? , role_id = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getEmail());

            if (employee.getRole() != null) {
                ps.setInt(4, employee.getRole().getId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setInt(5, employee.getId());
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

        int roleId = rs.getInt("role_id");
        if (!rs.wasNull()) {
            Role role = new Role();
            role.setId(roleId);
            emp.setRole(role);
        } else {
            emp.setRole(null);
        }

        return emp;
    }

}

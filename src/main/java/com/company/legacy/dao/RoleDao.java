package com.company.legacy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.company.legacy.model.Role;
import com.company.legacy.util.DBConnection;

public class RoleDao {
    public List<Role> findAll() throws Exception {

        List<Role> roles = new ArrayList<>();
        String sql = "SELECT id, role AS name FROM role";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Role r = new Role();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                roles.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching roles", e);
        }

        return roles;

    }

    public Role findById(int id) {
        String sql = "SELECT id, role AS name FROM role WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Role r = new Role();
                    r.setId(rs.getInt("id"));
                    r.setName(rs.getString("name"));
                    return r;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}

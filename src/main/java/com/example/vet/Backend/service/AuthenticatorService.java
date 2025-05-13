package com.example.vet.Backend.service;

import com.example.vet.Backend.dao.Db;
import com.example.vet.Backend.model.User;
import com.example.vet.Backend.model.UserType;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AuthenticatorService {

    public User loginAuth(String email, String senha) {
        try (Connection conn = Db.getInstance().getConnection()) {
            String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("nome"));
                String type = rs.getString("tipo_usuario");
                UserType typeEnum = UserType.valueOf(type.toUpperCase());
                u.setType(typeEnum);
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

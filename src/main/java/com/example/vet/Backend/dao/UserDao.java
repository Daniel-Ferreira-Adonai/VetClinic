package com.example.vet.Backend.dao;

import com.example.vet.Backend.model.User;
import com.example.vet.Backend.model.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public void insert(User user) {
        String sql = "INSERT INTO usuarios (nome, email, senha, tipo_usuario) VALUES (?, ?, ?, ?)";
        try (Connection conn = Db.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getType().name());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário", e);
        }
    }

    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM usuarios WHERE email = ?";
        try (Connection conn = Db.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar e-mail", e);
        }
    }
}

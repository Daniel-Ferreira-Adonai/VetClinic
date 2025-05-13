package com.example.vet.Backend.dao;

import com.example.vet.Backend.model.VetService;
import com.example.vet.Backend.model.ServiceType;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VetServiceDao {

    public List<VetService> listAll() {
        List<VetService> services = new ArrayList<>();
        try {
            Connection connection = Db.getInstance().getConnection();
            String query = "SELECT * FROM servico";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                VetService s = new VetService();
                s.setId(results.getInt("id_servico"));
                s.setName(results.getString("nome"));
                s.setDescription(results.getString("descricao"));
                s.setPrice(results.getDouble("preco"));
                s.setUrl(results.getString("imagem_url"));
                s.setServiceType(ServiceType.valueOf(results.getString("tipo")));
                services.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return services;
    }

    public void insert(VetService s) {
        try {
            String query = "INSERT INTO servico (nome, descricao, preco, imagem_url, tipo) VALUES (?, ?, ?, ?, ?)";
            Connection connection = Db.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, s.getName());
            stmt.setString(2, s.getDescription());
            stmt.setDouble(3, s.getPrice());
            stmt.setString(4, s.getUrl());
            stmt.setString(5, s.getServiceType().name());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            String query = "DELETE FROM servico WHERE id_servico = ?";
            Connection connection = Db.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(VetService s) {
        try {
            String query = "UPDATE servico SET nome = ?, descricao = ?, preco = ?, imagem_url = ?, tipo = ? WHERE id_servico = ?";
            Connection connection = Db.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, s.getName());
            stmt.setString(2, s.getDescription());
            stmt.setDouble(3, s.getPrice());
            stmt.setString(4, s.getUrl());
            stmt.setString(5, s.getServiceType().name());
            stmt.setInt(6, s.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

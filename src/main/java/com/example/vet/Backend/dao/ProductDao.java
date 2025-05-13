package com.example.vet.Backend.dao;

import com.example.vet.Backend.model.Product;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDao {

    public List<Product> listAll() {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = Db.getInstance().getConnection();
            String query = "SELECT * FROM produto";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id_produto"));
                p.setName(rs.getString("nome"));
                p.setDescription(rs.getString("descricao"));
                p.setPrice(rs.getDouble("preco"));
                p.setQuantity(rs.getInt("quantidade_estoque"));
                p.setUrl(rs.getString("imagem_url"));
                products.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public void insert(Product p) {
        try {
            String query = "INSERT INTO produto (nome, descricao, preco, quantidade_estoque, imagem_url) VALUES (?, ?, ?, ?, ?)";
            Connection connection = Db.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getDescription());
            stmt.setDouble(3, p.getPrice());
            stmt.setInt(4, p.getQuantity());
            stmt.setString(5, p.getUrl());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Product p) {
        try {
            String query = "UPDATE produto SET nome = ?, descricao = ?, preco = ?, quantidade_estoque = ?, imagem_url = ? WHERE id_produto = ?";
            Connection connection = Db.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getDescription());
            stmt.setDouble(3, p.getPrice());
            stmt.setInt(4, p.getQuantity());
            stmt.setString(5, p.getUrl());
            stmt.setInt(6, p.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            String query = "DELETE FROM produto WHERE id_produto = ?";
            Connection connection = Db.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

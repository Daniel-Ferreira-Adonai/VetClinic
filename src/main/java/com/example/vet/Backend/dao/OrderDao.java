package com.example.vet.Backend.dao;

import com.example.vet.Backend.model.CartItem;
import com.example.vet.Backend.model.Order;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDao {

    public void insert(Order pedido) {
        String sqlPedido = "INSERT INTO pedido (email_cliente, data, total) VALUES (?, ?, ?)";
        String sqlItem = "INSERT INTO item_pedido (id_pedido, id_item, nome, tipo, preco, quantidade) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = Db.getInstance().getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pedidoStmt = connection.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            pedidoStmt.setString(1, pedido.getCustomerEmail());
            pedidoStmt.setTimestamp(2, Timestamp.valueOf(pedido.getDate()));
            pedidoStmt.setDouble(3, pedido.getTotal());
            pedidoStmt.executeUpdate();

            //this was kinda hard, but i needed this to get the id for the cartItem
            ResultSet generatedKeys = pedidoStmt.getGeneratedKeys();
            int idPedido = 0;
            if (generatedKeys.next()) {
                idPedido = generatedKeys.getInt(1);
            }

            PreparedStatement itemStmt = connection.prepareStatement(sqlItem);
            for (CartItem item : pedido.getItems()) {
                itemStmt.setInt(1, idPedido);
                itemStmt.setInt(2, item.getId());
                itemStmt.setString(3, item.getName());
                itemStmt.setString(4, item.getType());
                itemStmt.setDouble(5, item.getPrice());
                itemStmt.setInt(6, item.getQuantity());
                itemStmt.addBatch();
            }
            itemStmt.executeBatch();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Order> listAll() {
        List<Order> orders = new ArrayList<>();

        try {
            Connection connection = Db.getInstance().getConnection();
            String sql = "SELECT * FROM pedido ORDER BY data DESC";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setCustomerEmail(rs.getString("email_cliente"));
                o.setDate(rs.getTimestamp("data").toLocalDateTime());
                o.setTotal(rs.getDouble("total"));

                o.setItems(getItemsByOrderId(o.getId()));
                orders.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    private List<CartItem> getItemsByOrderId(int orderId) {
        List<CartItem> items = new ArrayList<>();

        try {
            Connection connection = Db.getInstance().getConnection();
            String sql = "SELECT * FROM item_pedido WHERE id_pedido = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setId(rs.getInt("id_item"));
                item.setName(rs.getString("nome"));
                item.setType(rs.getString("tipo"));
                item.setPrice(rs.getDouble("preco"));
                item.setQuantity(rs.getInt("quantidade"));
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}



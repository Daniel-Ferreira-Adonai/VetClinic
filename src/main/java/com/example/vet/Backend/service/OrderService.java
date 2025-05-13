package com.example.vet.Backend.service;

import com.example.vet.Backend.dao.OrderDao;
import com.example.vet.Backend.model.Order;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void processOrder(Order order) throws SQLException {
        // Define a data do pedido no momento do processamento
        order.setDate(LocalDateTime.now());

        // Calcula o total, caso não venha do front
        double total = order.getItems()
                .stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        order.setTotal(total);

        // Salva o pedido e os itens no banco
        orderDao.insert(order);
    }
    public List<Order> listAll() {
        return orderDao.listAll();
    }
}

package com.example.vet.Backend.controller;

import com.example.vet.Backend.model.Order;
import com.example.vet.Backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Order order) {
        try {
            service.processOrder(order);
            return ResponseEntity.ok("Order registred with sucess.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error to register order: " + e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<Order>> listAll() {
        try {
            List<Order> pedidos = service.listAll();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

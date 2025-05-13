package com.example.vet.Backend.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private String customerEmail;
    private LocalDateTime date;
    private double total;
    private List<CartItem> items;

    public Order() {}

    public Order(String customerEmail, LocalDateTime date, double total, List<CartItem> items) {
        this.customerEmail = customerEmail;
        this.date = date;
        this.total = total;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}

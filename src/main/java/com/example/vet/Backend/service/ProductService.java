package com.example.vet.Backend.service;

import com.example.vet.Backend.dao.ProductDao;
import com.example.vet.Backend.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService() {
        this.productDao = new ProductDao();
    }

    public List<Product> listProducts() {
        return productDao.listAll();
    }

    public void insert(Product product) {
        validate(product);
        productDao.insert(product);
    }

    public void update(Product product) {
        if (product.getId() <= 0) {
            throw new IllegalArgumentException("invalid id.");
        }
        validate(product);
        productDao.update(product);
    }

    public void delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("invalid id.");
        }
        productDao.delete(id);
    }

    private void validate(Product p) {
        if (p.getName() == null || p.getName().isBlank()) {
            throw new IllegalArgumentException("product name is obligatory.");
        }
        if (p.getPrice() <= 0) {
            throw new IllegalArgumentException("price must be above 0.");
        }
        if (p.getQuantity() < 0) {
            throw new IllegalArgumentException("quantity invalid.");
        }
    }
}

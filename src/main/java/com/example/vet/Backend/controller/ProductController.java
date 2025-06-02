package com.example.vet.Backend.controller;

import com.example.vet.Backend.model.Product;
import com.example.vet.Backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> list() {
        return productService.listProducts();
    }

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Map<String, String> body) {
        try {
            Product p = new Product(
                    body.get("name"),
                    body.get("description"),
                    Double.parseDouble(body.get("price")),
                    Integer.parseInt(body.get("quantity")),
                    body.get("url")
            );

            productService.insert(p);
            return ResponseEntity.ok("Product registred with success.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("validation Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("internal error on registration.");
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Map<String, String> body) {
        try {
            Product p = new Product(
                    Integer.parseInt(body.get("id")),
                    body.get("name"),
                    body.get("description"),
                    Double.parseDouble(body.get("price")),
                    Integer.parseInt(body.get("quantity")),
                    body.get("url")
            );
            productService.update(p);
            return ResponseEntity.ok("Product updated with success.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("validation error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("internal error when updating.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok("Product removed with success.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error to remove product.");
        }
    }
}

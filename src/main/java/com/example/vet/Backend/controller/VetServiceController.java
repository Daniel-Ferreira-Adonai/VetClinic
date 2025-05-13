package com.example.vet.Backend.controller;

import com.example.vet.Backend.model.VetService;
import com.example.vet.Backend.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/servicos")
@CrossOrigin(origins = "*")
public class VetServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping
    public List<VetService> listAll() {
        return serviceService.listServices();
    }

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Map<String, String> body) {
        try {
            String name = body.get("name");
            String description = body.get("description");
            Double price = Double.parseDouble(body.get("price"));
            String url = body.get("url");
            String type = body.get("type");

            serviceService.insert(name, description, price, url, type);
            return ResponseEntity.ok("service was registred with success.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("validation error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("internal error to register VetService.");
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Map<String, String> body) {
        try {
            int id = Integer.parseInt(body.get("id"));
            String name = body.get("name");
            String description = body.get("description");
            Double price = Double.parseDouble(body.get("price"));
            String url = body.get("url");
            String type = body.get("type");

            serviceService.update(id, name, description, price, url, type);
            return ResponseEntity.ok("VetService updated with success.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("validation Error on vetService: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("internal error on VetService.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            serviceService.delete(id);
            return ResponseEntity.ok("vetService deleted with success.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro to delete Vetservice.");
        }
    }
}

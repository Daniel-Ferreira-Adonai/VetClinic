package com.example.vet.Backend.controller;

import com.example.vet.Backend.model.appointment;
import com.example.vet.Backend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @GetMapping
    public List<appointment> listAll() {
        return service.listAll();
    }

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Map<String, String> body) {
        try {
            appointment a = new appointment(
                    Integer.parseInt(body.get("id_animal")),
                    body.get("tutorComplaint"),
                    LocalDateTime.parse(body.get("date_time")),
                    body.get("vet_name"),
                    body.get("diagnosis")
            );
            service.insert(a);
            return ResponseEntity.ok("appointment registred with success.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error to register appointment: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Map<String, String> body) {
        try {
            appointment a = new appointment(
                    Integer.parseInt(body.get("id")),
                    Integer.parseInt(body.get("id_animal")),
                    body.get("tutorComplaint"),
                    LocalDateTime.parse(body.get("date_time")),
                    body.get("vet_name"),
                    body.get("diagnosis")
            );
            service.update(a);
            return ResponseEntity.ok("appointment updated with success.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error to update the appointment: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("appointment deleted with success.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deleting apppointment: " + e.getMessage());
        }
    }
}

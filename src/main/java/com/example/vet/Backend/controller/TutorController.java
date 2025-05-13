package com.example.vet.Backend.controller;

import com.example.vet.Backend.model.Tutor;
import com.example.vet.Backend.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tutores")
@CrossOrigin(origins = "*")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @GetMapping
    public List<Tutor> listTutors() {
        return tutorService.listTutors();
    }
    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Map<String, String> body) {
        try {
            Tutor t = new Tutor( body.get("name"), body.get("phone"), body.get("email"));
            tutorService.insert(t);
            return ResponseEntity.ok("sucess to insert tutor");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("server error");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id) {
        try {
            tutorService.delete(id);
            return ResponseEntity.ok("Tutor was sucessful deleted.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error to delte tutor");
        }
    }
    @PutMapping
    public ResponseEntity<String> update(@RequestBody Map<String, String> body) {
        try {
            int id = Integer.parseInt(body.get("id_tutor"));
            tutorService.update(id, body.get("name"), body.get("phone"), body.get("email"));
            return ResponseEntity.ok("Tutor updated with sucess.");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("invalid id: it must be a number.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("validation error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("server internal error.");
        }
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<Tutor> getTutorByEmail(@PathVariable String email) {
        try {
            Tutor tutor = tutorService.getTutorByEmail(email);
            if (tutor != null) {
                return ResponseEntity.ok(tutor);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}

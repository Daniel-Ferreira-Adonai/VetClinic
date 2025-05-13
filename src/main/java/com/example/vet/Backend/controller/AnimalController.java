package com.example.vet.Backend.controller;

import com.example.vet.Backend.model.Tutor;
import com.example.vet.Backend.model.Animal;
import com.example.vet.Backend.service.AnimalService;
import com.example.vet.Backend.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/animais")
@CrossOrigin(origins = "*")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private TutorService tutorService;

    @GetMapping
    public List<Animal> listAll() {
        return animalService.listAnimals();
    }

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Map<String, String> body) {
        try {
            String name = body.get("name");
            String especie = body.get("especie");
            String breedType = body.get("breedType");
            String emailTutor = body.get("emailTutor");

            animalService.insert(name, especie, breedType, emailTutor);
            return ResponseEntity.ok("Animal added with success");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error to register animal.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            animalService.delete(id);
            return ResponseEntity.ok("Animal deleted with success.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("internal error on animal delete");
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Map<String, String> body) {
        try {
            int id = Integer.parseInt(body.get("id_animal"));
            String name = body.get("name");
            String especie = body.get("especie");
            String breedType = body.get("breedType");
            int tutorId = Integer.parseInt(body.get("id_tutor"));

            animalService.update(id, name, especie, breedType, tutorId);
            return ResponseEntity.ok("Animal updated with sucess.");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Error numeric numbers field.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error on validation: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error animal internal error.");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Animal> getById(@PathVariable int id) {
        Animal animal = animalService.getById(id);
        return (animal != null) ? ResponseEntity.ok(animal) : ResponseEntity.notFound().build();
    }

    @GetMapping("/by-tutor-email")
    public ResponseEntity<List<Animal>> getAnimalsByTutorEmail(@RequestParam String email) {
        try {
            Tutor tutor = tutorService.getTutorByEmail(email);
            if (tutor == null) {
                return ResponseEntity.notFound().build();
            }
            List<Animal> animals = animalService.getAnimalsByTutorId(tutor.getId());
            return ResponseEntity.ok(animals);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}

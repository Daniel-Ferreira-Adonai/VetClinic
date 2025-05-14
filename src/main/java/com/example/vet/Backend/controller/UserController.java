package com.example.vet.Backend.controller;

import com.example.vet.Backend.model.Tutor;
import com.example.vet.Backend.model.User;
import com.example.vet.Backend.model.UserType;
import com.example.vet.Backend.service.TutorService;
import com.example.vet.Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TutorService tutorService;

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Map<String, String> body) {
        try {
            String type = body.get("type");
            UserType typeEnum = UserType.valueOf(type.toUpperCase());
            User user = new User(body.get("name"), body.get("email"), body.get("password"), typeEnum);
            userService.insert(user);
            return ResponseEntity.ok("User registred with success");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server internal Error.");
        }
    }
}

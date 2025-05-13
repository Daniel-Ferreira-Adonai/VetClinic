package com.example.vet.Backend.controller;

import com.example.vet.Backend.model.User;
import com.example.vet.Backend.service.AuthenticatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "*")
public class AuthenticatorController {

    @Autowired
    private AuthenticatorService authService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String senha = body.get("password");

        User user = authService.loginAuth(email, senha);

        if (user != null) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "tipo", user.getType(),
                    "nome", user.getName()
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "Login inválido"
            ));
        }
    }
}

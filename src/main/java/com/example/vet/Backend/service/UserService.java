package com.example.vet.Backend.service;

import com.example.vet.Backend.dao.UserDao;
import com.example.vet.Backend.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDAO;

    public UserService() {
        this.userDAO = new UserDao();
    }

    public void insert(User user) {
        if (user.getEmail() == null || user.getPassword() == null || user.getType() == null) {
            throw new IllegalArgumentException("Obrig fields.");
        }

        if (userDAO.emailExists(user.getEmail())) {
            throw new IllegalArgumentException("E-mail alredy registred.");
        }

        userDAO.insert(user);
    }
}

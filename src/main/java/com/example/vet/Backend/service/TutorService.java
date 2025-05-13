package com.example.vet.Backend.service;


import com.example.vet.Backend.dao.TutorDao;
import com.example.vet.Backend.model.Tutor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TutorService {
    private TutorDao tutorDao;

    public TutorService() {
        this.tutorDao = new TutorDao();
    }

    public List<Tutor> listTutors() {
        return tutorDao.listAll();
    }

    public void insert(Tutor tutor){
        if(validate(tutor.getName(), tutor.getPhone(), tutor.getEmail())){
            tutorDao.insert(tutor);
        }
    }
    public void delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("invalid id, it must be a positive number");
        }

        tutorDao.delete(id);
    }

    public void update(int id,String name, String phone, String email) {
        if (id <= 0) {
            throw new IllegalArgumentException("invalid id, it must be a positive number.");
        }

        tutorDao.update(id,name,phone,email);
    }
    public Tutor getTutorByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email field cannot be empty.");
        }

        return tutorDao.getByEmail(email);
    }



    private boolean validate(String name, String phone, String email) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name is obligatory.");
            return false;
        }

        if (phone == null || phone.trim().isEmpty()) {
            System.out.println("phone is obligatory .");
            return false;
        }

        if (email == null || !email.contains("@") || !email.contains(".")) {
            System.out.println("Email is invalid.");
            return false;
        }

        return true;
    }
}


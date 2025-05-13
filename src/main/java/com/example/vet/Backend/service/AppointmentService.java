package com.example.vet.Backend.service;

import com.example.vet.Backend.dao.AppointmentDao;
import com.example.vet.Backend.model.appointment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentDao dao;

    public AppointmentService() {
        this.dao = new AppointmentDao();
    }

    public List<appointment> listAll() {
        return dao.listAll();
    }

    public void insert(appointment a) {
        if (validate(a)) {
            dao.insert(a);
        } else {
            throw new IllegalArgumentException("invalid data for appointment.");
        }
    }

    public void update(appointment a) {
        if (a.getId() <= 0 || !validate(a)) {
            throw new IllegalArgumentException("invalid data for appointment.");
        }
        dao.update(a);
    }

    public void delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("invalid id for delelition of appointment.");
        }
        dao.delete(id);
    }

    private boolean validate(appointment a) {
        return a.getId_animal() > 0 &&
                a.getTutorComplaint() != null && !a.getTutorComplaint().isBlank() &&
                a.getDate_time() != null &&
                a.getVet_name() != null && !a.getVet_name().isBlank();
    }
}

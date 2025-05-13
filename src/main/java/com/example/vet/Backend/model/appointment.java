package com.example.vet.Backend.model;

import java.time.LocalDateTime;

public class appointment {
    private int id;
    private int id_animal;
    private String tutorComplaint;
    private LocalDateTime date_time;
    private String vet_name;
    private String diagnosis;
    public appointment(int id, int id_animal, String tutorComplaint, LocalDateTime date_time, String vet_name, String diagnosis) {
        this.id = id;
        this.id_animal = id_animal;
        this.tutorComplaint = tutorComplaint;
        this.date_time = date_time;
        this.vet_name = vet_name;
        this.diagnosis = diagnosis;
    }

    public appointment(int id_animal, String tutorComplaint, LocalDateTime date_time, String vet_name, String diagnosis) {
        this.id_animal = id_animal;
        this.tutorComplaint = tutorComplaint;
        this.date_time = date_time;
        this.vet_name = vet_name;
        this.diagnosis = diagnosis;
    }

    public appointment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_animal() {
        return id_animal;
    }

    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }

    public String getTutorComplaint() {
        return tutorComplaint;
    }

    public void setTutorComplaint(String tutorComplaint) {
        this.tutorComplaint = tutorComplaint;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    public String getVet_name() {
        return vet_name;
    }

    public void setVet_name(String vet_name) {
        this.vet_name = vet_name;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}

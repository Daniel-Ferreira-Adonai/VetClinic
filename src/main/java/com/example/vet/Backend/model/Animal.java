package com.example.vet.Backend.model;

public class Animal {
    private int id;
    private String name;
    private String especie;
    private String breedType;
    private int tutor_id;

    public Animal(int tutor_id, String breedType, String especie, String name, int id) {
        this.tutor_id = tutor_id;
        this.breedType = breedType;
        this.especie = especie;
        this.name = name;
        this.id = id;
    }
    public Animal(String breedType, String especie, String name, int id) {

        this.breedType = breedType;
        this.especie = especie;
        this.name = name;
        this.id = id;
    }
    public Animal() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getBreedType() {
        return breedType;
    }

    public void setBreedType(String breedType) {
        this.breedType = breedType;
    }

    public int getTutor_id() {
        return tutor_id;
    }

    public void setTutor_id(int tutor_id) {
        this.tutor_id = tutor_id;
    }






}

package com.example.vet.Backend.service;

import com.example.vet.Backend.dao.AnimalDao;
import com.example.vet.Backend.model.Animal;
import com.example.vet.Backend.model.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalDao animalDao;
    private final TutorService tutorService;

    @Autowired
    public AnimalService(TutorService tutorService) {
        this.animalDao = new AnimalDao();
        this.tutorService = tutorService;
    }

    public List<Animal> listAnimals() {
        return animalDao.listAll();
    }

    public void insert(String nome, String especie, String raca, String emailTutor) {
        Tutor tutor = tutorService.getTutorByEmail(emailTutor);

        if (tutor == null) {
            throw new IllegalArgumentException("Tutor email does not exist");
        }

        Animal animal = new Animal();
        animal.setName(nome);
        animal.setEspecie(especie);
        animal.setBreedType(raca);
        animal.setTutor_id(tutor.getId());

        if (validate(animal)) {
            animalDao.insert(animal);
        } else {
            throw new IllegalArgumentException("Invalid animal field values.");
        }
    }

    public void update(int id, String nome, String especie, String raca, int tutorId) {
        if (id <= 0) {
            throw new IllegalArgumentException("invalid ID.");
        }

        Animal temp = new Animal();
        temp.setName(nome);
        temp.setEspecie(especie);
        temp.setBreedType(raca);
        temp.setTutor_id(tutorId);

        if (validate(temp)) {
            animalDao.update(id, nome, especie, raca, tutorId);
        } else {
            throw new IllegalArgumentException("animal field data invalid.");
        }
    }

    public void delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("invalid id.");
        }
        animalDao.delete(id);
    }
    public List<Animal> getAnimalsByTutorId(int tutorId) {
        if (tutorId <= 0) {
            throw new IllegalArgumentException("invalid tutor id.");
        }
        return animalDao.listByTutorId(tutorId);
    }
    public Animal getById(int id) {
        return animalDao.getById(id);
    }


    private boolean validate(Animal animal) {
        return animal.getName() != null && !animal.getName().isBlank()
                && animal.getEspecie() != null && !animal.getEspecie().isBlank()
                && animal.getBreedType() != null && !animal.getBreedType().isBlank()
                && animal.getTutor_id() > 0;
    }
}

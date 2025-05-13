package com.example.vet.Backend.dao;

import com.example.vet.Backend.model.Animal;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnimalDao {

    public List<Animal> listAll(){
        List<Animal> animals = new ArrayList<>();
        try {
            Connection connection = Db.getInstance().getConnection();
            String query = "SELECT * FROM animal";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                Animal animal = new Animal();
                animal.setId(results.getInt("id_animal"));
                animal.setName(results.getString("nome"));
                animal.setEspecie(results.getString("especie"));
                animal.setBreedType(results.getString("raca"));
                animal.setTutor_id(results.getInt("id_tutor"));
                animals.add(animal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return animals;
    }

    public void insert(Animal animal){
        try {
            String query = "INSERT INTO animal (nome, especie, raca, id_tutor) VALUES (?, ?, ?, ?)";
            Connection connection = Db.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, animal.getName());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getBreedType());
            stmt.setInt(4, animal.getTutor_id());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            String query = "DELETE FROM animal WHERE id_animal = ?";
            Connection connection = Db.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(int id, String nome, String especie, String raca, int idTutor) {
        try {
            String query = "UPDATE animal SET nome = ?, especie = ?, raca = ?, id_tutor = ? WHERE id_animal = ?";
            Connection connection = Db.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, nome);
            stmt.setString(2, especie);
            stmt.setString(3, raca);
            stmt.setInt(4, idTutor);
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Animal> listByTutorId(int tutorId) {
        List<Animal> animals = new ArrayList<>();
        try {
            Connection connection = Db.getInstance().getConnection();
            String query = "SELECT * FROM animal WHERE id_tutor = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, tutorId);
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                Animal animal = new Animal();
                animal.setId(results.getInt("id_animal"));
                animal.setName(results.getString("nome"));
                animal.setEspecie(results.getString("especie"));
                animal.setBreedType(results.getString("raca"));
                animal.setTutor_id(results.getInt("id_tutor"));
                animals.add(animal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return animals;
    }
    public Animal getById(int id) {
        try {
            Connection connection = Db.getInstance().getConnection();
            String query = "SELECT * FROM animal WHERE id_animal = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Animal animal = new Animal();
                animal.setId(rs.getInt("id_animal"));
                animal.setName(rs.getString("nome"));
                animal.setEspecie(rs.getString("especie"));
                animal.setBreedType(rs.getString("raca"));
                animal.setTutor_id(rs.getInt("id_tutor"));
                return animal;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

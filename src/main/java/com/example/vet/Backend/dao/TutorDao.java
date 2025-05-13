package com.example.vet.Backend.dao;

import com.example.vet.Backend.model.Tutor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TutorDao {
    public List<Tutor> listAll(){
        List<Tutor> tutors = new ArrayList<>();
        try{
            Connection connection = Db.getInstance().getConnection();
            String query = "SELECT * FROM tutor";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet results = stmt.executeQuery();

            while(results.next()){
                Tutor t = new Tutor();
                t.setId(results.getInt("id_tutor"));
                t.setName(results.getString("nome"));
                t.setPhone(results.getString("telefone"));
                t.setEmail(results.getString("email"));
                tutors.add(t);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return tutors;
    }
    public void insert(Tutor t){
        try{
            String query = "INSERT INTO tutor (nome, telefone, email) VALUES (?, ?, ?)";
            Connection conecction = Db.getInstance().getConnection();
            PreparedStatement stmt = conecction.prepareStatement(query);
            stmt.setString(1,t.getName());
            stmt.setString(2,t.getPhone());
            stmt.setString(3,t.getEmail());
            stmt.executeUpdate();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void delete(int id) {
        try{
            String query = "DELETE FROM tutor WHERE id_tutor =?";
            Connection conecction = Db.getInstance().getConnection();
            PreparedStatement stmt = conecction.prepareStatement(query);
            stmt.setInt(1,id);
            stmt.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void update(int id,String name, String phone,String email) {
        try{
            String query = "UPDATE tutor SET nome = ?, telefone = ?, email = ? WHERE id_tutor = ?";
            Connection conecction = Db.getInstance().getConnection();
            PreparedStatement stmt = conecction.prepareStatement(query);
            stmt.setString(1,name);
            stmt.setString(2,phone);
            stmt.setString(3,email);
            stmt.setInt(4,id);
            stmt.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public Tutor getByEmail(String email) {
        Tutor tutor = null;
        try {
            String query = "SELECT * FROM tutor WHERE email = ?";
            Connection connection = Db.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tutor = new Tutor();
                tutor.setId(rs.getInt("id_tutor"));
                tutor.setName(rs.getString("nome"));
                tutor.setPhone(rs.getString("telefone"));
                tutor.setEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tutor;
    }

}

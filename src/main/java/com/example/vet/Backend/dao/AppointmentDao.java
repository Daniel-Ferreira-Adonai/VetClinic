package com.example.vet.Backend.dao;

import com.example.vet.Backend.model.appointment;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AppointmentDao {
    public List<appointment> listAll() {
        List<appointment> list = new ArrayList<>();
        try {
            Connection connection = Db.getInstance().getConnection();
            String query = "SELECT * FROM consulta";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                appointment a = new appointment();
                a.setId(rs.getInt("id_consulta"));
                a.setId_animal(rs.getInt("id_animal"));
                a.setTutorComplaint(rs.getString("queixa_do_tutor"));
                a.setDate_time(rs.getTimestamp("data_hora").toLocalDateTime());
                a.setVet_name(rs.getString("veterinario"));
                a.setDiagnosis(rs.getString("diagnostico"));
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(appointment a) {
        try {
            String query = "INSERT INTO consulta (id_animal, queixa_do_tutor, data_hora, veterinario, diagnostico) VALUES (?, ?, ?, ?, ?)";
            Connection connection = Db.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, a.getId_animal());
            stmt.setString(2, a.getTutorComplaint());
            stmt.setTimestamp(3, Timestamp.valueOf(a.getDate_time()));
            stmt.setString(4, a.getVet_name());
            stmt.setString(5, a.getDiagnosis());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            String query = "DELETE FROM consulta WHERE id_consulta = ?";
            Connection connection = Db.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(appointment a) {
        try {
            String query = "UPDATE consulta SET id_animal = ?, queixa_do_tutor = ?, data_hora = ?, veterinario = ?, diagnostico = ? WHERE id_consulta = ?";
            Connection connection = Db.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, a.getId_animal());
            stmt.setString(2, a.getTutorComplaint());
            stmt.setTimestamp(3, Timestamp.valueOf(a.getDate_time()));
            stmt.setString(4, a.getVet_name());
            stmt.setString(5, a.getDiagnosis());
            stmt.setInt(6, a.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.example.vet.Backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class daoTraining {

    public void insert(String name, int data){

     try{
         Connection con = Db.getInstance().getConnection();
         String query = "Insert into x (name, data) VALEUS (?,?)";
         PreparedStatement stmt =  con.prepareStatement(query);
         stmt.setString(1,name);
         stmt.setInt(2,data);
         stmt.executeUpdate();


     }
     catch(Exception e) {

     }

    }

}

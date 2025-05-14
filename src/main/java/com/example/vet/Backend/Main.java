package com.example.vet.Backend;

import com.example.vet.Backend.controller.UserController;
import com.example.vet.Backend.model.Tutor;
import com.example.vet.Backend.model.User;
import com.example.vet.Backend.model.UserType;
import com.example.vet.Backend.service.TutorService;
import com.example.vet.Backend.service.UserService;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public class Main {

    public void createDatabase(){

    }
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        //Exemplo de função para CRUD

        //Insert
        UserService userService = new UserService();
        userService.insert(new User("Daniel","daniel@teste.com","daniel123", UserType.TUTOR ));
        TutorService tutorService = new TutorService();
        tutorService.insert(new Tutor("Daniel","323232","daniel@teste.com"));
        userService.insert(new User("admin2","admin2@teste.com","admin222", UserType.ADMIN ));

        //Update  OBS: seguir formato exemplo
        //tutorService.update("id_do_usario","nome","telefone","email");

        //delete OBS: seguir formato exemplo
        //tutorService.delete("id_do_usuario");

        //Read ou list
        List<Tutor> tutorList =  tutorService.listTutors();
    }
}


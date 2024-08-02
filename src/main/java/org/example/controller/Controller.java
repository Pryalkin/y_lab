package org.example.controller;

import org.example.annotation.Url;
import org.example.model.User;
import org.example.service.Service;


public class Controller {

    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @Url(name = "/registration", method = "POST")
    public Responce registration(User user){
        service.registration(user);
        return new Responce();
    }

    @Url(name = "/get/registration/client", method = "GET")
    public Responce getRegistrationClient(){
        service.getRegistrationClient();
        return new Responce();
    }

}

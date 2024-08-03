package org.example.factory;

import org.example.Server;
import org.example.controller.Controller;
import org.example.repository.Repository;
import org.example.service.Service;

import java.io.InputStream;
import java.util.Properties;

public class Factory {

    private static Service service;
    private static Repository repository;
    private static Controller controller;

    private Factory(){

    }

    public static void init(){
        Properties properties = new Properties();
        try (InputStream in = Factory.class.getClassLoader().getResourceAsStream("app.properties")){
            properties.load(in);
            String serviceClass = properties.getProperty("service");
            String repositoryClass = properties.getProperty("repository");
            String controllerClass = properties.getProperty("controller");
            repository = (Repository) Class.forName(repositoryClass).getConstructor().newInstance();
            service = (Service) Class.forName(serviceClass)
                    .getConstructor(Repository.class).newInstance(repository);
            controller = (Controller) Class.forName(controllerClass)
                    .getConstructor(Service.class).newInstance(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Service getService() {
        return service;
    }

    public static Repository getRepository() {
        return repository;
    }

    public static Controller getController(){
        return controller;
    }
}

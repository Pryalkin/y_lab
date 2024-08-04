package org.example.controller;

import org.example.annotation.Url;
import org.example.model.Car;
import org.example.model.Order;
import org.example.model.User;
import org.example.service.Service;


public class Controller {

    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }


    // service SAFETY
    @Url(name = "/registration", method = "POST")
    public String registration(User user){
        return service.registration(user).toString();
    }

    @Url(name = "/login", method = "POST")
    public String getAuthorization(User user){
        return service.getAuthorization(user);
    }


    // service USER
    @Url(name = "/get/registration/client", method = "GET")
    public String getRegistrationClient(){
        return service.getRegistrationClient().toString();
    }

    @Url(name = "/get/registration/manager", method = "GET")
    public String getRegistrationManager(){
        return service.getRegistrationManager().toString();
    }


    // service CAR
    @Url(name = "/add/car", method = "POST")
    public String addCar(Car car){
        return service.addCar(car).toString();
    }

    @Url(name = "/get/cars", method = "GET")
    public String getCars(){
        return service.getCars().toString();
    }

    @Url(name = "/car/delete", method = "DELETE")
    public String deleteCar(String idCar){
        return service.deleteCar(idCar).toString();
    }

    @Url(name = "/car/update", method = "PUT")
    public String updateCar(Car car){
        return service.updateCar(car).toString();
    }

    @Url(name = "/cars/find/brand/model", method = "POST")
    public String findCarBrandModel(Car car){
        return service.findCarBrandModel(car).toString();
    }

    // service ORDER
    @Url(name = "/create/order", method = "POST")
    public String createOrder(Order order){
        return service.createOrder(order).toString();
    }

    @Url(name = "/get/orders", method = "GET")
    public String getOrders(){
        return service.getOrders().toString();
    }

    @Url(name = "/orders/find/client", method = "POST")
    public String findOrderClient(Order order){
        return service.findOrderClient(order).toString();
    }

    @Url(name = "/logging", method = "POST")
    public String logging(User user){
        return service.logging(user).toString();
    }

    @Url(name = "/export/logging", method = "POST")
    public String exportLogging(User user){
        return service.exportLogging(user).toString();
    }
}

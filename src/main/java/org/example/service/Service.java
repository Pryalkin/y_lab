package org.example.service;

import org.example.model.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Service {

    public void sayHello();

    User registration(User user);

    Set<User> getRegistrationClient();

    String getAuthorization(User user);

    Car addCar(Car car);

    User getUser(User user);

    boolean checkToken(String authorities);

    List<Car> getCars();

    Car deleteCar(String idCar);

    Car updateCar(Car car);

    Order createOrder(Order order);

    Collection<Order> getOrders();

    List<Car> findCarBrandModel(Car car);

    Map<User, List<Order>> findOrderClient(Order order);

    Set<User> getRegistrationManager();

    Map<User, List<LoggingUsersOrder>> logging(User user);

    DTO exportLogging(User user);
}

package org.example.repository.impl;

import org.example.emun.InStock;
import org.example.model.Car;
import org.example.model.LoggingUsersOrder;
import org.example.model.Order;
import org.example.model.User;
import org.example.repository.Repository;

import java.util.*;
import java.util.stream.Collectors;

public class RepositoryImpl implements Repository {

    private Long idUser = 0L;
    private Long idCar = 0L;
    private Long idOrder = 0L;
    private Long idLoggingUsersOrder = 0L;
    private Map<Long, User> users = new HashMap<>();
    private Map<Long, Car> cars = new HashMap<>();
    private Map<Long, Order> orders = new HashMap<>();
    private Map<Long, LoggingUsersOrder> longLoggingUsersOrderHashMap = new HashMap<>();

    @Override
    public void sayHello() {
        System.out.println("Hello Repo");
    }

    @Override
    public User saveUser(User user) {
        idUser++;
        user.setId(idUser.toString());
        users.put(idUser, user);
        return user;
    }


    @Override
    public Collection<User> findUsers() {
        return users.values();
    }

    @Override
    public Car saveCar(Car car) {
        if(car.getId() == null){
            idCar++;
            car.setId(idCar.toString());
            cars.put(idCar, car);
        } else {
            cars.put(Long.parseLong(car.getId()), car);
        }
        return car;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        List<User> userList = users.values().stream()
                .filter(user -> user.getUsername().equals(username)
                        && user.getPassword().equals(password)).toList();
        if(userList.isEmpty()) return null;
        return userList.get(0);
    }

    @Override
    public Collection<Car> findCars() {
        return cars.values();
    }

    @Override
    public Car deleteCar(String idCar) {
        return cars.remove(Long.parseLong(idCar));
    }

    @Override
    public Car findCar(Long id) {
        return cars.get(id);
    }

    @Override
    public User findUser(Long id) {
        return users.get(id);
    }

    @Override
    public Car findCarByIdAndInStock(String id, String name) {
        Car car = cars.get(Long.parseLong(id));
        if(car != null)
            if(car.getInStock().equals(name))
                return car;
        return null;
    }

    @Override
    public Order saveOrder(Order order) {
        if(order.getId() == null){
            idOrder++;
            order.setId(idOrder.toString());
            orders.put(idOrder, order);
        } else {
            orders.put(Long.parseLong(order.getId()), order);
        }
        return order;
    }

    @Override
    public Collection<Order> findOrders() {
        return orders.values();
    }

    @Override
    public List<Car> findCarByBrandAndModel(String brand, String model) {
        return cars.values().stream().filter(car -> car.getBrand().equals(brand)
        && car.getModel().equals(model)).collect(Collectors.toList());
    }

    @Override
    public List<Order> findOrderByClient(String id) {
        return orders.values().stream().filter(order -> order.getIdUser().equals(id)).collect(Collectors.toList());
    }

    @Override
    public LoggingUsersOrder insertLoggingUsersOrder(LoggingUsersOrder loggingUsersOrder) {
        if(loggingUsersOrder.getId() == null){
            idLoggingUsersOrder++;
            loggingUsersOrder.setId(idLoggingUsersOrder.toString());
            longLoggingUsersOrderHashMap.put(idLoggingUsersOrder, loggingUsersOrder);
        } else {
            longLoggingUsersOrderHashMap.put(Long.parseLong(loggingUsersOrder.getId()), loggingUsersOrder);
        }
        return loggingUsersOrder;
    }

    @Override
    public List<LoggingUsersOrder> findLogging(String id) {
        return longLoggingUsersOrderHashMap.values().stream()
                .filter(log -> log.getUserId().equals(id)).collect(Collectors.toList());
    }

}

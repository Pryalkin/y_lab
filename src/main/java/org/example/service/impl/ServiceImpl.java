package org.example.service.impl;

import org.example.emun.Action;
import org.example.emun.InStock;
import org.example.emun.Role;
import org.example.model.*;
import org.example.repository.Repository;
import org.example.service.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceImpl implements Service {

    private final Repository repository;

    public ServiceImpl(Repository repository) {
        this.repository = repository;
    }

    public void sayHello() {
        System.out.println("Hello Service!");
    }

    // service SAFETY
    @Override
    public User registration(User user) {
        return repository.saveUser(user);
    }

    @Override
    public String getAuthorization(User user) {
        boolean authorization = repository.findUsers().stream()
                .anyMatch(u -> u.getUsername().equals(user.getUsername())
                        && u.getPassword().equals(user.getPassword()));
        if (authorization){
            User generateTokenForUser = this.getUser(user);
            return encrypt(user.getUsername() + "." + user.getPassword(), 1);
        }
        return Boolean.FALSE.toString();
    }

    @Override
    public boolean checkToken(String authorities) {
        String usernameAndPassword = decrypt(authorities, 1);
        int index = usernameAndPassword.indexOf('.');
        String username = usernameAndPassword.substring(0, index);
        String password = usernameAndPassword.substring(index+1);
        User user = repository.findUserByUsernameAndPassword(username, password);
        return user == null;
    }

    private String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                boolean isUpperCase = Character.isUpperCase(ch);
                char base = isUpperCase ? 'A' : 'a';
                int shiftedIndex = (ch - base + shift) % 26;
                result.append((char) (base + shiftedIndex));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    private String decrypt(String encryptedText, int shift) {
        return encrypt(encryptedText, -shift);
    }

    // service USER
    @Override
    public Set<User> getRegistrationClient() {
        return repository.findUsers().stream()
                .filter(user -> user.getRole().equals(Role.CLIENT.name())).collect(Collectors.toSet());

    }

    @Override
    public Set<User> getRegistrationManager() {
        return repository.findUsers().stream()
                .filter(user -> user.getRole().equals(Role.MANAGER.name())).collect(Collectors.toSet());
    }


    @Override
    public User getUser(User user) {
        return repository.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    // service CAR
    @Override
    public Car addCar(Car car) {
        return repository.saveCar(car);
    }

    @Override
    public List<Car> getCars() {
        return repository.findCars().stream().filter(car -> car.getInStock().equals("TRUE")).collect(Collectors.toList());
    }

    @Override
    public Car deleteCar(String idCar) {
        return repository.deleteCar(idCar);
    }

    @Override
    public Car updateCar(Car updateCar) {
        Car car = repository.findCar(Long.parseLong(updateCar.getId()));
        if(updateCar.getBrand() != null) car.setBrand(updateCar.getBrand());
        if(updateCar.getModel() != null) car.setModel(updateCar.getModel());
        if(updateCar.getYearOfIssue() != null) car.setYearOfIssue(updateCar.getYearOfIssue());
        if(updateCar.getPrice() != null) car.setPrice(updateCar.getPrice());
        if(updateCar.getState() != null) car.setState(updateCar.getState());
        if(updateCar.getInStock() != null) car.setInStock(updateCar.getInStock());
        return repository.saveCar(car);
    }

    @Override
    public List<Car> findCarBrandModel(Car findCar) {
        return repository.findCarByBrandAndModel(findCar.getBrand(), findCar.getModel());
    }

    //service ORDER
    @Override
    public Order createOrder(Order order) {
        if(repository.findUser(Long.parseLong(order.getIdUser())) != null){
            LoggingUsersOrder loggingUsersOrder = new LoggingUsersOrder();
            loggingUsersOrder.setUserId(order.getIdUser());
            loggingUsersOrder.setAction(Action.CREATE.name());
            loggingUsersOrder.setDate(new Date().toString());
            repository.insertLoggingUsersOrder(loggingUsersOrder);
            Car car = repository.findCar(Long.parseLong(order.getIdCar()));
            if(car != null){
                if (repository.findCarByIdAndInStock(car.getId(), InStock.TRUE.name()) != null){
                    Car updateCar = new Car();
                    updateCar.setId(car.getId());
                    updateCar.setInStock(InStock.FALSE.name());
                    updateCar(updateCar);
                    return repository.saveOrder(order);
                }
            }
        }
        return null;
    }

    @Override
    public Collection<Order> getOrders() {
        return repository.findOrders();
    }

    @Override
    public Map<User, List<Order>> findOrderClient(Order order) {
        User user = repository.findUser(Long.parseLong(order.getIdUser()));
        LoggingUsersOrder loggingUsersOrder = new LoggingUsersOrder();
        loggingUsersOrder.setUserId(order.getIdUser());
        loggingUsersOrder.setAction(Action.FIND_CLIENT.name());
        loggingUsersOrder.setDate(new Date().toString());
        repository.insertLoggingUsersOrder(loggingUsersOrder);
        List<Order> orders = repository.findOrderByClient(user.getId());
        return new HashMap<User, List<Order>>(){{
           put(user, orders);
        }};
    }

    // logging
    @Override
    public Map<User, List<LoggingUsersOrder>> logging(User user) {
        User usLogging = repository.findUser(Long.parseLong(user.getId()));
        List<LoggingUsersOrder> loggingUsersOrderList = repository.findLogging(user.getId());
        return new HashMap<>(){{
            put(usLogging, loggingUsersOrderList);
        }};
    }

    @Override
    public DTO exportLogging(User user) {
        User usLogging = repository.findUser(Long.parseLong(user.getId()));
        String filename = "export_logging_" + usLogging.getUsername() + ".txt";
        String textToWrite = repository.findLogging(user.getId()).toString();
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(textToWrite);
            System.out.println("Текст успешно записан в файл " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
        DTO dto = new DTO();
        dto.setMessage("Экспорт успешно прошел!");
        return dto;
    }

}

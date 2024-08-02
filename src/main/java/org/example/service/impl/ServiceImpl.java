package org.example.service.impl;

import org.example.emun.Role;
import org.example.model.User;
import org.example.repository.Repository;
import org.example.service.Service;

public class ServiceImpl implements Service {

    private final Repository repository;

    public ServiceImpl(Repository repository) {
        this.repository = repository;
    }

    public void sayHello() {
        System.out.println("Hello Service!");
    }

    @Override
    public void registration(User user) {
        repository.saveUser(user);
    }

    @Override
    public void getRegistrationClient() {
        repository.findUserByRole(Role.CLIENT.name());
    }

}

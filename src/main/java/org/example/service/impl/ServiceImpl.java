package org.example.service.impl;

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

}

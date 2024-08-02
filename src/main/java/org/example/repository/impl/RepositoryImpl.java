package org.example.repository.impl;

import org.example.model.User;
import org.example.repository.Repository;

import java.util.HashMap;
import java.util.Map;

public class RepositoryImpl implements Repository {

    private Long idProduct = 0L;
    private Long idDiscountCard = 0L;
    private Map<Long, User> users = new HashMap<Long, User>();

    @Override
    public void sayHello() {
        System.out.println("Hello Repo");
    }
}

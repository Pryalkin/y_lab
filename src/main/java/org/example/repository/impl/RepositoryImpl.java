package org.example.repository.impl;

import org.example.model.User;
import org.example.repository.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RepositoryImpl implements Repository {

    private Long idUser = 0L;
    private Map<Long, User> users = new HashMap<Long, User>();

    @Override
    public void sayHello() {
        System.out.println("Hello Repo");
    }

    @Override
    public void saveUser(User user) {
        idUser++;
        users.put(idUser, user);
    }

    @Override
    public void findUserByRole(String name) {
        Set<User> userSet = users.values().stream().filter(user -> user.getRole().equals(name)).collect(Collectors.toSet());
        System.out.println(userSet);
    }


}

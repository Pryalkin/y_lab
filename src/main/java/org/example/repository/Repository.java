package org.example.repository;

import org.example.model.User;

public interface Repository {

    void sayHello();

    void saveUser(User user);

    void findUserByRole(String name);
}

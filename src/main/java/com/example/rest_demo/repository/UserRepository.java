package com.example.rest_demo.repository;

import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    User save(User user);

    List<User> getAll();

    boolean delete(int id);

    User get(int id);
}

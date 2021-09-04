package com.example.rest_demo.repository;

import com.example.rest_demo.model.Menu;

import java.util.List;

public interface MenuRepository {
    Menu save(Menu menu);

    List<Menu> getAll();

    boolean delete(int id);

    Menu get(int id);

    Menu getRestaurantRec(int id);
}

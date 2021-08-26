package com.example.rest_demo.repository;

import com.example.rest_demo.model.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);

    List<Restaurant> getAll();

    boolean delete(int id);

    Restaurant get(int id);
}

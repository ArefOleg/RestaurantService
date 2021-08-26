package com.example.rest_demo.repository;

import com.example.rest_demo.model.Meal;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository {
    Meal save(Meal meal, int restaurantId);

    List<Meal> getAll();

    boolean delete(int id);

    Meal get(int id);

    List<Meal> getMealByRestaurantId(int id);

    List<Meal> getMealsFromMenu(int id);

}

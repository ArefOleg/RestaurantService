package com.example.rest_demo.service;

import com.example.rest_demo.model.Meal;
import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService {
    private final MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository){
        this.mealRepository = mealRepository;
    }

    public List<Meal> getMeals(){
        return mealRepository.getAll();
    }

    public List<Meal> getMealByRestaurantId(int id){ return mealRepository.getMealByRestaurantId(id);}

    public Meal save(Meal meal, int restaraunt_id){
        return mealRepository.save(meal, restaraunt_id);
    }

    public Meal get(int id){
        return mealRepository.get(id);
    }

    public void delete(int id){
        mealRepository.delete(id);
    }

    public Restaurant getRestaurant(Meal meal){
        return meal.getRestaurant();
    }

    public void saveMealsFromForm(List<Meal> meals){
        meals.forEach(meal -> save(meal, meal.getRestaurantId()));
    };
}

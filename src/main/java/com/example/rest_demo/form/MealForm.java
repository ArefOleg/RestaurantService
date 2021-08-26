package com.example.rest_demo.form;

import com.example.rest_demo.model.Meal;

import java.util.List;

public class MealForm {
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> contacts) {
        this.meals = contacts;
    }
}

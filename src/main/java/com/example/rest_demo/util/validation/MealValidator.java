package com.example.rest_demo.util.validation;

import com.example.rest_demo.model.Meal;
import com.example.rest_demo.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MealValidator implements Validator {
    private final MealService mealService;

    @Autowired
    public MealValidator(MealService mealService){
        this.mealService = mealService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Meal.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Meal meal = (Meal) o;

        if(mealService.showByName(meal.getName(), meal.getRestaurantId()).isPresent()){
            errors.rejectValue("name","","This name is already in use for " + meal.getRestaurant().getName());
        }

    }
}

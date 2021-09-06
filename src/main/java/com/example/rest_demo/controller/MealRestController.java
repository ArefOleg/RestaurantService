package com.example.rest_demo.controller;

import com.example.rest_demo.model.Meal;
import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.service.MealService;
import com.example.rest_demo.service.MenuMealIntersectionService;
import com.example.rest_demo.service.RestaurantService;
import com.example.rest_demo.to.MealTo;
import com.example.rest_demo.util.exception.JSONException;
import com.example.rest_demo.util.validation.MealValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController {
    public static final String REST_URL = "/rest/meals";
    private final MealService mealService;
    private final RestaurantService restaurantService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public MealRestController(MealService mealService, RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
        this.mealService = mealService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String create10000Meals(@RequestBody MealTo mealTo) {
        Meal meal = new Meal(mealTo.getName(), mealTo.getPrice());
        if(restaurantService.get(mealTo.getRestaurantId()) == null)
            throw new JSONException("Restaurant doesn`t exist");
        meal.setRestaurant(restaurantService.get(mealTo.getRestaurantId()));
        mealService.save(meal);
        return "meal created";
    }



}

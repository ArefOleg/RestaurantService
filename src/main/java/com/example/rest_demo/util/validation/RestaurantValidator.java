package com.example.rest_demo.util.validation;
import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.service.MealService;
import com.example.rest_demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RestaurantValidator implements Validator{
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantValidator(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Restaurant.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Restaurant restaurant = (Restaurant) o;

        if(restaurantService.getRestaurantByName(restaurant.getName()).isPresent()){
            errors.rejectValue("name","","This name is already in use");
        }

    }
}

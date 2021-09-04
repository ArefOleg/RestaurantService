package com.example.rest_demo.controller;

import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    public static final String REST_URL = "/rest/restaurants";
    private final RestaurantService restaurantService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public RestaurantRestController (RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{id}")
    public Restaurant show(@PathVariable("id") int id){
        return restaurantService.get(id);
    }

    @RequestMapping()
    public List<Restaurant> getRestaurants(){ return restaurantService.getRestaurants(); }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String create(@RequestBody Restaurant restaurant) {
        restaurantService.save(restaurant);
        return "restaurant created";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        restaurantService.delete(id);
        return "Restaurant: " + id + " deleted";
    }

}

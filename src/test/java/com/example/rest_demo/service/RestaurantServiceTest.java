package com.example.rest_demo.service;

import com.example.rest_demo.RestDemoApplication;
import com.example.rest_demo.config.HSQLDBTestProfileConfig;
import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.repository.RestaurantRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.jdbc.Sql;

public class RestaurantServiceTest extends AbstractServiceTest{
    @Autowired
    RestaurantService restaurantService;

    @Test
    public void init(){
      var restaurant =  restaurantService.get(100000);
      LOGGER.info("Ресторан "  + restaurant.getName());
    }

    @Test
    public void showRestaurants(){
        var restaurants = restaurantService.getRestaurants();
        restaurants.stream().forEach(s -> LOGGER.info(s.getName()));
    }
}

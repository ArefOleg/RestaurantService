package com.example.rest_demo.controllers;


import com.example.rest_demo.controller.MealRestController;
import com.example.rest_demo.controller.RestaurantRestController;
import com.example.rest_demo.model.Meal;
import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.test_data.RestaurantTestData;
import com.example.rest_demo.to.MealTo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@Sql(scripts = "classpath:sql/restaurantTestDBHibernate.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MultipleMealTestController extends AbstractController{
    public static final String uri = MealRestController.REST_URL + '/';
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void createMeals20000() throws Exception {

        var start = LocalDateTime.now();
        for(int i = 0; i < 20000; i++){
            MealTo mealTo = new MealTo(10, "Test", RestaurantTestData.Mirazur.getId());
            String inputJson = super.mapToJson(mealTo);
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(MealRestController.REST_URL )
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            assertEquals(content, "meal created");
        }
        var end = LocalDateTime.now();
        log.info("\nStart time " + start + " End time " + end);
    }

    @Test
    public void createMealWithBadRestaurantId() throws Exception{
        MealTo mealTo = new MealTo(10, "Test", 999);
        String inputJson = super.mapToJson(mealTo);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(MealRestController.REST_URL )
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(500, status);
        String content = mvcResult.getResolvedException().getMessage();
        assertEquals(content, "Restaurant doesn`t exist");
    }
}

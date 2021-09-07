package com.example.rest_demo.controllers;

import com.example.rest_demo.controller.RestaurantRestController;
import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.test_data.RestaurantTestData;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@Sql(scripts = "classpath:sql/restaurantTestDBHibernate.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantControllerTest extends AbstractController {
    public static final String uri = RestaurantRestController.REST_URL + '/';
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }


    @Test
    public void getRestaurant() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + RestaurantTestData.Mirazur.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Restaurant restaurant = super.mapFromJson(content, Restaurant.class);
        assertTrue(restaurant.getId() != null);
    }

    @Test
    public void createRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant("Archolia");
        String inputJson = super.mapToJson(restaurant);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(RestaurantRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "restaurant created");
    }

    @Test
    public void getRestaurants() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Restaurant[] restaurants = super.mapFromJson(content, Restaurant[].class);
        assertTrue(restaurants.length > 0);
    }

    @Test
    public void deleteRestaurant() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri + RestaurantTestData.Mirazur.getId() + "/delete")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Restaurant: " + RestaurantTestData.Mirazur.getId() + " deleted");
    }
}

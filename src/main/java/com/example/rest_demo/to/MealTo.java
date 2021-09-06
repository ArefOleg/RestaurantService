package com.example.rest_demo.to;

public class MealTo {
    private Integer price;
    private String name;
    private Integer restaurantId;
    public MealTo(Integer price, String name, Integer restaurantId){
        this.price = price;
        this.name = name;
        this.restaurantId = restaurantId;
    }

    public Integer getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }
}

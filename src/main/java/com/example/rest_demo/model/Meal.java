package com.example.rest_demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "meal")
public class Meal extends AbstractNamedEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    @Column(name = "meal_type")
    private String mealType;

    @Column(name = "price")
    private Integer price;

    public Integer getPrice(){
        return this.price;
    }

    public void setPrice(Integer price){
        this.price = price;
    }

    public String getMealType(){
        return this.mealType;
    }

    public void setMealType(String type) {
        this.mealType = type;
    }

    public Integer getRestaurantId(){
        return getRestaurant().getId();
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Meal() {}

    public Meal(String name, Integer price){this(null,name, price);}

    public Meal(Integer id, String name, Integer price){
        super(id, name);
        this.price = price;
    }

    public Meal(Integer id, String name, String type, Integer price){
        super(id, name);
        this.mealType = type;
        this.price = price;
    }

}

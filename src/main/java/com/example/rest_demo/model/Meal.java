package com.example.rest_demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "meals")
public class Meal extends AbstractNamedEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaraunt_id", nullable = false)
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

    public Integer getRestaurantId(){
        return getRestaurant().getId();
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setMealType(String type) {
        this.mealType = type;
    }

    public Meal() {
    }

    public Meal(String name) {
        this(null, name);
    }

    public Meal(Integer id, String name) {
        super(id, name);
    }

    public Meal(Integer id, String name, String type, Integer price){
        super(id, name);
        this.mealType = type;
        this.price = price;
    }


}

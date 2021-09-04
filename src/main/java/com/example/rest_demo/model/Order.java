package com.example.rest_demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "restaurant_order")
public class Order extends AbstractBaseEntity{
    @Column(name = "created", nullable = false)
    @NotNull
    private LocalDateTime createdTime;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private User user;

    public boolean getEnabled(){
        return this.isEnabled;
    }

    public void setEnabled(boolean enabled){
        this.isEnabled = enabled;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setUpsertTime(LocalDateTime upsertTime){
        this.createdTime = upsertTime;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant(){return this.restaurant;}

    public LocalDate getLocalCreatedTime(){
        return this.createdTime.toLocalDate();
    }

    public Order(){}

    public Order(Restaurant restaurant, User user, LocalDateTime createdTime,boolean enabled){
        this.user = user;
        this.restaurant = restaurant;
        this.createdTime = createdTime;
        this.isEnabled = enabled;
    }


}

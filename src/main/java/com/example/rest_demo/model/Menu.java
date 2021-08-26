package com.example.rest_demo.model;

import com.example.rest_demo.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "menus")
public class Menu extends AbstractBaseEntity {
    @OneToOne
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;

    @Column(name = "created", nullable = false)
    @NotNull
    private LocalDateTime createdTime;

    public Menu() {
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setUpsertTime(LocalDateTime upsertTime){
        this.createdTime = upsertTime;
    }

    public boolean getWasted(){
        return TimeUtil.compareWastedTime(createdTime);
    }

    public Menu(Integer id, LocalDateTime created) {
        super(id);
        this.createdTime = created;
    }

    public Menu(LocalDateTime created) {
        this(null, created);
    }
}

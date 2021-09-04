package com.example.rest_demo.model;

import com.example.rest_demo.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "menu")
public class Menu extends AbstractBaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "menu_meal",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private Set<Meal> meals = new HashSet<>();

    @Column(name = "created", nullable = false)
    @NotNull
    private LocalDateTime createdTime;

    @Column(name = "is_enabled", nullable = false)
    @NotNull
    private boolean isEnabled;

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

    public boolean getIsEnabled(){return this.isEnabled;}

    public boolean isMealEmpty(){
        return meals.isEmpty();
    }

    public void setEnabled(boolean enabled){
        this.isEnabled = enabled;
    }

    public Menu(Integer id, LocalDateTime created, boolean isEnabled) {
        super(id);
        this.createdTime = created;
        this.isEnabled = isEnabled;
    }

    public Menu(LocalDateTime created, boolean isEnabled) {
        this(null, created, isEnabled);
    }
}

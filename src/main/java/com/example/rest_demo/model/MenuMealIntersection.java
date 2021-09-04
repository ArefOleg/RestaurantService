package com.example.rest_demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "menu_meal")
public class MenuMealIntersection extends AbstractBaseEntity{
    @Column(name = "created", nullable = false)
    @NotNull
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Menu menu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "meal_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Meal meal;

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal){
        this.meal = meal;
    }

    public Menu getMenu(){
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public MenuMealIntersection(){}

    public MenuMealIntersection(Menu menu, Meal meal, LocalDateTime createdTime){
        this.menu = menu;
        this.meal = meal;
        this.createdTime = createdTime;
    }

}

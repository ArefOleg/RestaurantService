package com.example.rest_demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonManagedReference
    private List<Meal> meals;

    @OneToOne(mappedBy = "restaurant")
    private Menu menu;

    public void setMenu(Menu menu){
        this.menu = menu;
    }

    public Menu getMenu(){
        return this.menu;
    }

    public Restaurant() {
    }

    public List<Meal> getMeals(){
        return meals;
    }

    public List<Meal> getMealsFromMenu(){
        return meals.stream().filter(meal -> meal.getMealType()!="Not In Menu").toList();
    }

    public Restaurant(String name) {
        this(null, name);
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}

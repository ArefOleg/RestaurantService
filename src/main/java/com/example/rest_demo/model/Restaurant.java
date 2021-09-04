package com.example.rest_demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonManagedReference
    private List<Meal> meals;

    @OneToMany(mappedBy = "restaurant")
    @JsonManagedReference
    private  List<Menu> menus;

    public boolean isMenuNotExist(){
        if(this.menus == null) return false;
        else{
            List<Menu> activeMenu = menus.stream().filter(menu -> menu.getIsEnabled()==true).collect(Collectors.toList());
            return activeMenu.isEmpty();
        }
    }

    public boolean isMealNotExist(){
        if(this.meals==null) return true;
        else return false;
    }

    public Menu getActiveMenu(){
        if(this.menus == null) return null;
        else {
            List<Menu> activeMenu = menus.stream().filter(menu -> menu.getIsEnabled() == true).collect(Collectors.toList());
            return activeMenu.get(0);
        }
    }

    public Restaurant() {
    }

    public List<Menu> getMenus(){return menus;}

    public List<Meal> getMeals(){
        return meals;
    }

    public List<Meal> getMealsFromMenu(){
        if(this.meals==null) return null;
        else return this.meals.stream().filter(meal -> !meal.getMealType().equals("Not In Menu")).toList();
    }

    public boolean isNoActiveMeal(){// Если true значит, ни одного блюда нету в меню
        if(this.meals==null) return true;
        else return this.getMealsFromMenu().isEmpty();
    }

    public Restaurant(String name) {
        this(null, name);
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}

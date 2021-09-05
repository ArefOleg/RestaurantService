package com.example.rest_demo.controller;

import com.example.rest_demo.model.Meal;
import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.service.MealService;
import com.example.rest_demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    @Autowired
    public RestaurantController (RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    @RequestMapping()
    public String index(Model model){
        model.addAttribute("restaurant", restaurantService.getRestaurants());
        return "restaurants/index";
    }

    @GetMapping( "/create")
    public String showAddRestaurantPage(@ModelAttribute("restaurant") Restaurant restaurant) {
        return "restaurants/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("restaurant") Restaurant restaurant) {
        restaurantService.save(restaurant);
        return "redirect:/meals/create";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("restaurant", restaurantService.get(id));
        return "restaurants/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("restaurant", restaurantService.get(id));
        return "restaurants/edit";
    }

    @PatchMapping("/{id}/change")
    public String update(@ModelAttribute("restaurant") Restaurant restaurant, @PathVariable("id") int id){
        restaurantService.save(restaurant);
        return "redirect:/restaurants";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        restaurantService.delete(id);
        return "redirect:/restaurants";
    }

    @GetMapping("/{id}/upsert_menu")
    public ModelAndView upsertMenu(@PathVariable("id") int id, ModelMap model){
        var restaurant = restaurantService.get(id);
        restaurantService.upsertMenu(restaurant);
        model.addAttribute("menuId", restaurant.getActiveMenu().getId());
        return new ModelAndView("redirect:/meals/{id}/upsert_menu");
    }

    @PatchMapping("/{id}/upserted")
    public String upserted(@ModelAttribute("restaurant") Restaurant restaurant, @PathVariable("id") int id){
        return "redirect:/restaurants";
    }

   @GetMapping("/voting")
   public String getMealsFromMenu(Model model){
        model.addAttribute("restaurants", restaurantService.getRestaurants().
                stream().filter(restaurant -> !restaurant.isNoActiveMeal()).collect(Collectors.toList()));
       return "restaurants/voting";
    }


}

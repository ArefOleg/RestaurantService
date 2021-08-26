package com.example.rest_demo.controller;

import com.example.rest_demo.form.MealForm;
import com.example.rest_demo.model.Meal;
import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.service.MealService;
import com.example.rest_demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/meals")
public class MealController {
    private final MealService mealService;
    private final RestaurantService restaurantService;
    @Autowired
    public MealController(MealService mealService, RestaurantService restaurantService){
        this.mealService = mealService;
        this.restaurantService = restaurantService;
    }

    @RequestMapping()
    public String index(Model model){
        model.addAttribute("meal", mealService.getMeals());
        return "meals/index";
    }

    @GetMapping( "/create")
    public String showAddMealPage(@ModelAttribute("meal") Meal meal, Model model) {
        model.addAttribute("restaurant", restaurantService.getRestaurants());
        return "meals/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("meal") Meal meal, @RequestParam(value = "restrId") int id) {
        mealService.save(meal, id);
        return "redirect:/meals";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("meal", mealService.get(id));
        model.addAttribute("restaurantOwn", mealService.getRestaurant(mealService.get(id)));
        return "meals/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("meal", mealService.get(id));
        model.addAttribute("restaurantOwn", mealService.getRestaurant(mealService.get(id)));
        model.addAttribute("restaurant", restaurantService.getRestaurants());
        return "meals/edit";
    }

    @PatchMapping("/{id}/change")
    public String update(@ModelAttribute("meal") Meal meal, @PathVariable("id") int id, @RequestParam(value = "restrId") int restId){
        mealService.save(meal, restId);
        return "redirect:/meals";
    }

    @GetMapping("/{id}/upsert_menu")
    public String getUpsertMenu(@PathVariable("id") int id, Model model) {
        MealForm mealForm = new MealForm();
        mealForm.setMeals(mealService.getMealByRestaurantId(id));
        model.addAttribute("rest_id", id);
        model.addAttribute("mealForm", mealForm);
        return "meals/upsert_menu";
    }
    @PostMapping("/{id}/upserted")
    public String upserted(@ModelAttribute("mealForm") MealForm mealForm,@PathVariable("id") int id){
        Restaurant restaurant;
        mealService.saveMealsFromForm(mealForm.getMeals());
        restaurantService.upsertMenu(restaurantService.get(id));
        return "redirect:/restaurants";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        mealService.delete(id);
        return "redirect:/meal";
    }
}

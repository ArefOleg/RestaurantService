package com.example.rest_demo.controller;

import com.example.rest_demo.form.MealForm;
import com.example.rest_demo.model.Meal;
import com.example.rest_demo.model.Menu;
import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.service.MealService;
import com.example.rest_demo.service.MenuMealIntersectionService;
import com.example.rest_demo.service.RestaurantService;
import com.example.rest_demo.util.MenuUtil;
import com.example.rest_demo.util.validation.MealValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/meals")
public class MealController {
    private final MealService mealService;
    private final RestaurantService restaurantService;
    private final MenuMealIntersectionService menuMealIntersectionService;
    private final MealValidator mealValidator;

    @Autowired
    public MealController(MealService mealService, RestaurantService restaurantService, MenuMealIntersectionService menuMealIntersectionService, MealValidator mealValidator) {
        this.mealService = mealService;
        this.restaurantService = restaurantService;
        this.menuMealIntersectionService = menuMealIntersectionService;
        this.mealValidator = mealValidator;
    }

    @RequestMapping()
    public String index(Model model) {
        model.addAttribute("meal", mealService.getMeals());
        return "meals/index";
    }

    @GetMapping("/create")
    public String showAddMealPage(@ModelAttribute("meal") Meal meal, Model model) {
        model.addAttribute("restaurant", restaurantService.getRestaurants());
        return "meals/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("meal") @Valid Meal meal, @RequestParam(value = "restrId") int id,
                         BindingResult bindingResult, Model model) {
        meal.setRestaurant(restaurantService.get(id));
        mealValidator.validate(meal, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("restaurant", restaurantService.getRestaurants());
            return "meals/create";
        }
        meal.setMealType("Not In Menu");
        mealService.save(meal);
        return "redirect:/meals";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("meal", mealService.get(id));
        model.addAttribute("restaurantOwn", mealService.getRestaurant(mealService.get(id)));
        return "meals/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("meal", mealService.get(id));
        model.addAttribute("restaurantOwn", mealService.getRestaurant(mealService.get(id)));
        model.addAttribute("restaurant", restaurantService.getRestaurants());
        return "meals/edit";
    }

    @PatchMapping("/{id}/change")
    public String update(@ModelAttribute("meal") @Valid Meal meal, @PathVariable("id") int id, @RequestParam(value = "restrId") int restId,
                         BindingResult bindingResult, Model model) {
        meal.setRestaurant(restaurantService.get(restId));
        mealValidator.validate(meal, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("restaurantOwn", mealService.getRestaurant(mealService.get(id)));
            model.addAttribute("restaurant", restaurantService.getRestaurants());
            return "meals/edit";
        }
        mealService.save(meal);
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
    public String upserted(@ModelAttribute("mealForm") MealForm mealForm, @PathVariable("id") int id) {
        Restaurant restaurant = restaurantService.get(id);
        mealService.saveMealsFromForm(mealForm.getMeals());
        MenuUtil.upsertIntersection(restaurant.getMeals(), restaurant.getActiveMenu(), menuMealIntersectionService);
        return "redirect:/restaurants";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        mealService.delete(id);
        return "redirect:/meals";
    }
}

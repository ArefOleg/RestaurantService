package com.example.rest_demo.repository.datajpa;

import com.example.rest_demo.model.Meal;
import com.example.rest_demo.repository.MealRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class DataJpaMealRepository implements MealRepository {
    private final CrudMealRepository crudMealRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaMealRepository(CrudMealRepository crudMealRepository, CrudRestaurantRepository crudRestaurantRepository){
        this.crudMealRepository = crudMealRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Meal save(Meal meal) {
        return crudMealRepository.save(meal);
    }

    @Override
    public List<Meal> getAll() {
        return crudMealRepository.findAll();
    }

    @Override
    public boolean delete(int id) { return crudMealRepository.delete(id) !=0; }

    @Override
    public Meal get(int id) { return crudMealRepository.findById(id).orElse(null); }

    public List<Meal> getMealByRestaurantId(int id){ return crudMealRepository.getMealByRestaurantId(id);}

    @Override
    public List<Meal> getMealsFromMenu(int restaurantId){return crudMealRepository.getMealsFromMenu(restaurantId);}

    @Override
    public Optional <Meal> getMealByName(String name, Integer restaurantId){return crudMealRepository.getMealByName(name, restaurantId);}
}

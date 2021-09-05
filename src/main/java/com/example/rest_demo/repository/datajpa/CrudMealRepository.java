package com.example.rest_demo.repository.datajpa;

import com.example.rest_demo.model.Meal;
import com.example.rest_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT m FROM Meal m WHERE m.mealType = 'Not In Menu' AND m.restaurant.id = :id")
    public List<Meal> getMealsFromMenu(@Param("id") int id);

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id = :id")
    public List<Meal> getMealByRestaurantId(@Param("id") int id);

    @Query("SELECT m FROM Meal m WHERE m.name=:name AND m.restaurant.id=:restrId")
    public Optional<Meal> getMealByName(@Param("name") String name, @Param("restrId") Integer restaurantId);
}

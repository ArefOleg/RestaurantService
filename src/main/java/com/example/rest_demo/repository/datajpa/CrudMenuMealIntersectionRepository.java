package com.example.rest_demo.repository.datajpa;



import com.example.rest_demo.model.MenuMealIntersection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CrudMenuMealIntersectionRepository extends JpaRepository<MenuMealIntersection, Integer> {


    @Transactional
    @Modifying
    @Query("DELETE FROM MenuMealIntersection m WHERE m.meal.id=:mealId AND m.menu.id=:menuId")
    int deleteByMealIdMenuId(@Param("mealId") int mealId, @Param("menuId") int menuId);

    @Query("SELECT m FROM MenuMealIntersection m WHERE m.meal.id=:mealId AND m.meal.id=:menuId")
    public List<MenuMealIntersection> getIntersectionByMealIdMenuId(@Param("mealId") int mealId, @Param("menuId") int menuId);

    @Query("SELECT m FROM MenuMealIntersection m WHERE m.meal.id=:menuId")
    public List<MenuMealIntersection> getIntersectionByMenuId(@Param("menuId") int menuId);

    @Query("SELECT m.meal.id FROM MenuMealIntersection m WHERE m.meal.id=:menuId")
    public List<Integer> getMealIdsByMenuId(@Param("menuId") int menuId);
}

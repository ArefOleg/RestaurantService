package com.example.rest_demo.repository.datajpa;

import com.example.rest_demo.model.MenuMealIntersection;
import com.example.rest_demo.model.Restaurant;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DataJpaMealIntersectionRepository {
    private final CrudMenuMealIntersectionRepository menuMealIntersectionRepository;

    public DataJpaMealIntersectionRepository(CrudMenuMealIntersectionRepository menuMealIntersectionRepository){
        this.menuMealIntersectionRepository = menuMealIntersectionRepository;
    }

    @Transactional
    public MenuMealIntersection save(MenuMealIntersection menuMealIntersection) {
        return menuMealIntersectionRepository.save(menuMealIntersection);
    }

    public boolean deleteByMealIdMenuId(int mealId, int menuId) {
        return menuMealIntersectionRepository.deleteByMealIdMenuId(mealId, menuId) !=0;
    }

    public List<MenuMealIntersection> getIntersectionByMenuId(int menuId) {return menuMealIntersectionRepository.getIntersectionByMenuId(menuId);}

    public List<MenuMealIntersection> getIntersectionByMealIdMenuId(int mealId, int menuId) { return menuMealIntersectionRepository.getIntersectionByMealIdMenuId(mealId, menuId); }

    public List<Integer> getMealIdsByMenuId(int menuId){ return menuMealIntersectionRepository.getMealIdsByMenuId(menuId);}
}

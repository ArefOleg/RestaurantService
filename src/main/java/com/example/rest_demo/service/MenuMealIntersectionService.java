package com.example.rest_demo.service;

import com.example.rest_demo.model.Meal;
import com.example.rest_demo.model.MenuMealIntersection;
import com.example.rest_demo.repository.datajpa.DataJpaMealIntersectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuMealIntersectionService {

    DataJpaMealIntersectionRepository dataJpaMealIntersectionRepository;

    MenuMealIntersectionService(DataJpaMealIntersectionRepository dataJpaMealIntersectionRepository){
        this.dataJpaMealIntersectionRepository = dataJpaMealIntersectionRepository;
    }

    public void deleteByMealIdMenuId(int mealId, int menuId){
        dataJpaMealIntersectionRepository.deleteByMealIdMenuId(mealId, menuId);
    }

    public MenuMealIntersection save(MenuMealIntersection menuMealIntersection){
        return dataJpaMealIntersectionRepository.save(menuMealIntersection);
    }

    public List<MenuMealIntersection> getIntersectionByMenuId(int menuId) {return dataJpaMealIntersectionRepository.getIntersectionByMenuId(menuId);}//Возможно убрать

    public List<Integer> getMealIdsByMenuId(int menuId){ return dataJpaMealIntersectionRepository.getMealIdsByMenuId(menuId);}

    public List<MenuMealIntersection> getIntersectionByMealIdMenuId(int mealId, int menuId) { return dataJpaMealIntersectionRepository.getIntersectionByMealIdMenuId(mealId, menuId); }

}

package com.example.rest_demo.util;

import com.example.rest_demo.model.*;
import com.example.rest_demo.service.MenuMealIntersectionService;
import com.example.rest_demo.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class MenuUtil {
    protected static final Logger LOGGER = LoggerFactory.getLogger(OrderUtil.class);

    public static void upsertMenu(Restaurant restaurant, RestaurantService restaurantService) {
        /* Проверка меню на наличие хотя бы одной активной записи, если ничего нету, создаем меню
             Если есть хотя бы одна запись выбираем ее, проверяем дату
             Если дата вчера, создаем новую запись
             Если сегодня то обновляем */
        var anyMenu = restaurantService.getRestaurantRec(restaurant.getId());
        if (anyMenu == null) {
            //Создаем меню
            restaurantService.createNewMenu(restaurant);
        } else {
            LOGGER.info("\nMenu INFO: Restaurant name" + anyMenu.getRestaurant().getName());
            if (TimeUtil.compareWastedTime(anyMenu.getCreatedTime())) {
                // Дата создания не совпала.
                anyMenu.setEnabled(false);
                restaurantService.saveMenu(anyMenu);
                //Старая запись уходит в архив, создается новая
                restaurantService.createNewMenu(restaurant);
            }
        }
    }

    public static void upsertIntersection(List<Meal> meals, Menu menu, MenuMealIntersectionService menuMealIntersectionService) {
        //В начале удаляем из интерсекшена блюда, которые убрали из меню
        meals.stream().filter(m -> m.getMealType().equals("Not In Menu")).
                forEach(meal -> menuMealIntersectionService.
                        deleteByMealIdMenuId(meal.getId(), menu.getId()));

        //Отфильтровываем записи, которые остались в бд
        List<Integer> mealsInMenuIdList = menuMealIntersectionService.getMealIdsByMenuId(menu.getId());
        //Проверяем есть ли в них запись, если уже есть, то ничего не делаем
        List<Meal> mealsNotLinkWithMenu = filteredMeals(meals.stream().
                filter(m -> !m.getMealType().equals("Not In Menu")).
                collect(Collectors.toList()), mealsInMenuIdList);
        //Связываем с меню
        mealsNotLinkWithMenu.stream().forEach(meal ->{
            MenuMealIntersection menuMealIntersection = new MenuMealIntersection(menu, meal, LocalDateTime.now());
            menuMealIntersectionService.save(menuMealIntersection);
                }
        );
    }

    public static List<Meal> filteredMeals(List<Meal> meals, List<Integer> mealsInMenuIdList) {
        List<Meal> mealsExistInMenu = new ArrayList<>();
        meals.forEach(meal -> {
            if (!mealsInMenuIdList.contains(meal.getId())) { //Если не содержит
                mealsExistInMenu.add(meal);
            }
        });
        return mealsExistInMenu;
    }

    private MenuUtil() {
    }

}

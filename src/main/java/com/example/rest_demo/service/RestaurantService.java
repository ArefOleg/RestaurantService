package com.example.rest_demo.service;

import com.example.rest_demo.model.Menu;
import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.repository.MenuRepository;
import com.example.rest_demo.repository.RestaurantRepository;
import com.example.rest_demo.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, MenuRepository menuRepository){
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
    }
    public List<Restaurant> getRestaurants(){
        return restaurantRepository.getAll();
    }

    public Restaurant save(Restaurant restaurant){
        if(restaurant.getId() == null)
        {
            Menu menu = new Menu(LocalDateTime.now(), true);
            restaurantRepository.save(restaurant);
            menu.setRestaurant(restaurant);
            menuRepository.save(menu);
        }
        return restaurantRepository.save(restaurant);
    }

    public Restaurant upsertMenu(Restaurant restaurant){
        MenuUtil.upsertMenu(restaurant, this);
        return restaurantRepository.save(restaurant);
    }

    public Menu createNewMenu(Restaurant restaurant){
        var menu = new Menu(LocalDateTime.now(), true);
        menu.setRestaurant(restaurant);
        menuRepository.save(menu);
        return  menu;
    }

    public Restaurant get(int id){
        return restaurantRepository.get(id);
    }

    public void delete(int id){
        restaurantRepository.delete(id);
    }

    public Menu getRestaurantRec(int id) {return menuRepository.getRestaurantRec(id);}

    public Menu saveMenu(Menu menu){
        return menuRepository.save(menu);
    }

    public Optional<Restaurant> getRestaurantByName(String name) {
        return restaurantRepository.getRestaurantByName(name);
    }
    //public List<Restaurant> getRestaurantsName(){
    //   return restaurantRepository.findNameRestaurants();

    //public List<String> getRestaurant;
}

package com.example.rest_demo.util;

import com.example.rest_demo.model.Menu;
import com.example.rest_demo.to.MenuTo;

import java.util.List;
import java.util.stream.Collectors;

public class MenusUtil {

    private MenusUtil(){

    }

    public static List<MenuTo> updateWasted(List <Menu> menus){
        return menus.stream().map(menu -> createTo(menu)).collect(Collectors.toList());
    }

    public static MenuTo createTo(Menu menu){
        return new MenuTo(menu.getId(), menu.getCreatedTime(), menu.getRestaurant().getId());
    }
}

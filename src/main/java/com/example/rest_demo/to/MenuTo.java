package com.example.rest_demo.to;

import com.example.rest_demo.util.TimeUtil;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

public class MenuTo {
    private final Integer id;

    private final LocalDateTime created;

    private final Integer restaurantId;

    private final boolean wasted;

    @ConstructorProperties({"id", "created", "restaurantId"})
    public MenuTo(Integer id, LocalDateTime created, Integer restaurantId){
        this.id = id;
        this.created = created;
        this.restaurantId = restaurantId;
        this.wasted = TimeUtil.compareWastedTime(created);
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getCreatedTime() {
        return created;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public boolean isWasted() {
        return wasted;
    }

    public boolean getWasted() {
        return this.wasted;
    }

}

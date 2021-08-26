package com.example.rest_demo.repository.datajpa;

import com.example.rest_demo.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    public static final String FIND_NAMES = "SELECT name FROM Restaurant r";

    @Query(value = FIND_NAMES, nativeQuery = true)
    public List<Restaurant> findNameRestaurants();

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);
}

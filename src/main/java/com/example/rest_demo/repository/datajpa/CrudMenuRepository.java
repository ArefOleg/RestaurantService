package com.example.rest_demo.repository.datajpa;

import com.example.rest_demo.model.Menu;
import com.example.rest_demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CrudMenuRepository  extends JpaRepository<Menu, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Menu m SET m.isEnabled = false WHERE m.id=:id")
    int disablingMenu(@Param("id") int id);

    @Transactional
    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restrId AND m.isEnabled = true ORDER BY m.createdTime DESC")
    public Menu getRestaurantRec(@Param("restrId") int restrId);
}

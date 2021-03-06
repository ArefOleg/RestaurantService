package com.example.rest_demo.repository.datajpa;

import com.example.rest_demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface CrudOrderRepository extends JpaRepository<Order, Integer> {

    @Transactional
    @Query("SELECT ro FROM Order ro WHERE ro.user.id=:userId AND ro.isEnabled = true ORDER BY ro.createdTime DESC")
    public Order getUserRec(@Param("userId") int userId);

    @Transactional
    @Query("SELECT ro FROM Order ro WHERE ro.user.id=:userId ORDER BY ro.createdTime DESC")
    public List<Order> getAllUserVotes(@Param("userId") int userId);

    @Query("SELECT ro FROM Order ro WHERE ro.user.id=:userId AND ro.restaurant.id=:restId AND ro.isEnabled = true")
    public Order getExistRec(@Param("userId") int userId,@Param("restId") int restId);

    @Transactional
    @Modifying
    @Query("UPDATE Order ro SET ro.isEnabled = false WHERE ro.id=:id")
    int disablingOrder(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Order ro WHERE ro.id=:id")
    int delete(@Param("id") int id);

}

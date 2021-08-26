package com.example.rest_demo.repository;

import com.example.rest_demo.model.Menu;
import com.example.rest_demo.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository {
    Order save(Order order);

    List<Order> getAll();

    boolean delete(int id);

    Order get(int id);
}

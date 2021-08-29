package com.example.rest_demo.service;

import com.example.rest_demo.model.Meal;
import com.example.rest_demo.model.Order;
import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.model.User;
import com.example.rest_demo.repository.datajpa.DataJpaOrderRepository;
import com.example.rest_demo.repository.datajpa.DataJpaRestaurantRepository;
import com.example.rest_demo.repository.datajpa.DataJpaUserRepository;
import com.example.rest_demo.util.AuthorityUserDetails;
import com.example.rest_demo.util.OrderUtil;
import com.example.rest_demo.util.TimeUtil;
import com.example.rest_demo.util.exception.ExceedingTheTimeLimitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {
    private final  DataJpaOrderRepository orderRepository;
    private final DataJpaUserRepository userRepository;
    private final DataJpaRestaurantRepository restaurantRepository;

    public void createOrder(Integer restaurantId) throws ExceedingTheTimeLimitException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthorityUserDetails userDetails = (AuthorityUserDetails) authentication.getPrincipal();
        User user = userRepository.get(userDetails.getUserId());
        Restaurant restaurant = restaurantRepository.get(restaurantId);
        OrderUtil.orderTimeMethod(user, restaurant, LocalDateTime.now(), this);
    }

    public void createUserOrder(Integer restaurantId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthorityUserDetails userDetails = (AuthorityUserDetails) authentication.getPrincipal();
        User user = userRepository.get(userDetails.getUserId());
        user.setOrder(restaurantRepository.get(restaurantId));
    }


    public Order save(Order order){
        return orderRepository.save(order);
    }

    public Order getUserRec(int userId){
        return orderRepository.getUserRec(userId);
    }

    public void disablingOrder(int orderId){ orderRepository.delete(orderId);}

    @Autowired
    public OrderService(DataJpaOrderRepository orderRepository, DataJpaUserRepository userRepository, DataJpaRestaurantRepository restaurantRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }
}

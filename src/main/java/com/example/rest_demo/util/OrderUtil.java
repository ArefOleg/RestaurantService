package com.example.rest_demo.util;

import com.example.rest_demo.model.Order;
import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.model.User;
import com.example.rest_demo.repository.datajpa.CrudOrderRepository;
import com.example.rest_demo.repository.datajpa.DataJpaOrderRepository;
import com.example.rest_demo.service.OrderService;
import com.example.rest_demo.util.exception.ExceedingTheTimeLimitException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class OrderUtil {

    public static void orderTimeMethod(User user, Restaurant restaurant, LocalDateTime time, OrderService orderService) throws ExceedingTheTimeLimitException {
        Order anyUserOrder = orderService.getUserRec(user.getId());
        if (anyUserOrder.getId() == null) {
            saveOrder(user, restaurant, time, orderService);
        } else {
            if (TimeUtil.compareWastedTime(anyUserOrder.getCreatedTime())) {
                disablingOrder(anyUserOrder.id(), orderService);
                saveOrder(user, restaurant, time, orderService);
            }
            else {
                if(TimeUtil.checkLocalTimeDT(anyUserOrder.getCreatedTime()) > 0){
                    throw new ExceedingTheTimeLimitException("Can't vote after 11! You`ve already voted." +
                            " Your vote is " + anyUserOrder.getRestaurant().getName());
                }
                else {
                    disablingOrder(anyUserOrder.id(), orderService);
                    saveOrder(user, restaurant, time, orderService);
                }
            }
        }
    }

    public static void saveOrder(User user, Restaurant restaurant, LocalDateTime time, OrderService orderService) {
        Order order = new Order(restaurant, user, time, true);
        orderService.save(order);
    }

    public static void disablingOrder(Integer orderId, OrderService orderService){
        orderService.disablingOrder(orderId);
    }

}

package com.example.rest_demo.util;

import com.example.rest_demo.model.Order;
import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.model.User;
import com.example.rest_demo.repository.datajpa.CrudOrderRepository;
import com.example.rest_demo.repository.datajpa.DataJpaOrderRepository;
import com.example.rest_demo.service.OrderService;
import com.example.rest_demo.util.exception.ExceedingTheTimeLimitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class OrderUtil {
    protected static final Logger LOGGER = LoggerFactory.getLogger(OrderUtil.class);
    public static void orderTimeMethod(User user, Restaurant restaurant, LocalDateTime time, OrderService orderService) throws ExceedingTheTimeLimitException {
        Order anyUserOrder = orderService.getUserRec(user.getId());
        if (anyUserOrder == null) {
            LOGGER.info("\nCase1: No ORDER");
            saveOrder(user, restaurant, time, orderService);
        } else {
            if (TimeUtil.compareWastedTime(anyUserOrder.getCreatedTime())) {
                disablingOrder(anyUserOrder.id(), orderService);
                LOGGER.info("\nCase2: Disabling Order: " + anyUserOrder.id());
                saveOrder(user, restaurant, time, orderService);
                LOGGER.info("\nCase2: New Order Service");
            }
            else {
                if(TimeUtil.checkLocalTimeDT(anyUserOrder.getCreatedTime()) > 0){
                    LOGGER.info("\nCase3: ExceededTime");
                    throw new ExceedingTheTimeLimitException("\nCan't vote after 11! You`ve already voted." +
                            " Your vote is " + anyUserOrder.getRestaurant().getName());
                }
                else {
                    LOGGER.info("\nCase4: BeforeDT, but with oldOrder");
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

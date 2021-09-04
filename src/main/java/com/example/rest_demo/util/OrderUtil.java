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
        LOGGER.info("\n Input info \n Restaurant name: " + restaurant.getName() +
                "\n User name: " + user.getName());
        if (TimeUtil.checkLocalTimeDT(time) > 0) {
            LOGGER.info("\nCase0: ExceededTime");
            throw new
                    ExceedingTheTimeLimitException("\nThe time for voting is over, applications are accepted until 11 a.m.");
        } else {
            Order anyUserOrder = orderService.getUserRec(user.getId());
            if (anyUserOrder == null) {
                LOGGER.info("\nCase1: No ORDER");
                saveOrder(user, restaurant, time, orderService);
            } else {
                LOGGER.info("\nOld Order Info:" +
                        "\n Restaurant name: " + anyUserOrder.getRestaurant().getName() +
                        "\n User name: " + anyUserOrder.getUser().getName());
                if (TimeUtil.compareWastedTime(anyUserOrder.getCreatedTime())) {//Проверка в этот же день
                    disablingOrder(anyUserOrder, orderService, restaurant);
                    LOGGER.info("\nCase2: Disabling Order: " + anyUserOrder.id());
                    saveOrder(user, restaurant, time, orderService);
                    LOGGER.info("\nCase2: New Order Service");
                } else {
                    if (anyUserOrder.getRestaurant().getId() == restaurant.getId()) {
                        LOGGER.info("\nCase3: Vote for same Restaurant");
                    } else {
                        LOGGER.info("\nCase4: BeforeDT, but with oldOrder " +
                                "\nOld Order Restaurant name: " + anyUserOrder.getRestaurant().getName() +
                                "\nOld Order User name: " + anyUserOrder.getUser().getName());
                        disablingOrder(anyUserOrder, orderService, restaurant);
                        saveOrder(user, restaurant, time, orderService);
                    }
                }
            }
        }

    }

    public static void saveOrder(User user, Restaurant restaurant, LocalDateTime time, OrderService orderService) {
        Order order = new Order(restaurant, user, time, true);
        orderService.save(order);
    }

    public static void disablingOrder(Order order, OrderService orderService, Restaurant restaurant) {
        if(TimeUtil.compareWastedTime(order.getCreatedTime()))
            orderService.disablingOrder(order.id());// Даты не совпали, старый заказ в историю
        else {
            order.setRestaurant(restaurant); // Обновляем сегодняшний заказ
            orderService.save(order);
        }
        //Просто обновить запись существующую
    }

}

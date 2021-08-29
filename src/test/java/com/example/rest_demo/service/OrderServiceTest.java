package com.example.rest_demo.service;

import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.model.User;
import com.example.rest_demo.test_data.CalendarTestData;
import com.example.rest_demo.test_data.RestaurantTestData;
import com.example.rest_demo.test_data.UserTestData;
import com.example.rest_demo.util.AuthorityUserDetails;
import com.example.rest_demo.util.OrderUtil;
import com.example.rest_demo.util.exception.ExceedingTheTimeLimitException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;


public class OrderServiceTest extends AbstractServiceTest{
    @Autowired
    OrderService orderService;

    // Кейс, при котором в бд нету заказа, пользователь инициирует первый заход.
    @Test
    public void createTestFirstOrder() throws ExceedingTheTimeLimitException {
        var restaurant = RestaurantTestData.restaurant2;
        var user = UserTestData.orderUser;
        var date = CalendarTestData.calendarDateAfter_11.getTime();
        var localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LOGGER.info("\nTime is : " + localDate);
        OrderUtil.orderTimeMethod(user, restaurant, localDate, orderService);
    }

    // Кейс, при котором пользователь заказал в одном ресторане сегодня, и хочет поменять заказ до ДТ
    @Test
    public void createTestOrderBefore_11_Time() throws ExceedingTheTimeLimitException {
        var restaurant = RestaurantTestData.restaurant2;
        var user = UserTestData.orderUser;
        var date = CalendarTestData.calendarDateAfter_11.getTime();
        var localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LOGGER.info("\nTime is : " + localDate);
        OrderUtil.orderTimeMethod(user, restaurant, localDate, orderService);
    }

    // Кейс, при котором пользователь заказал в одном ресторане сегодня, и хочет поменять после ДТ
}

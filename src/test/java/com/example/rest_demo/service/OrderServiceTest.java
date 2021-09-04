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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;


public class OrderServiceTest extends AbstractServiceTest{
    @Autowired
    OrderService orderService;

    // Кейс, при котором пользователь заказал в одном ресторане сегодня, и хочет поменять заказ до ДТ, на другой ресторан
    @Test
    public void createTestOrderBefore_11_Time() throws ExceedingTheTimeLimitException {
        var restaurant = RestaurantTestData.Gaggan;
        var user = UserTestData.excUser;
        var date = CalendarTestData.calendarDateBefore_11.getTime();
        var presentLocalDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LOGGER.info("\nTime is : " + presentLocalDate);
        OrderUtil.orderTimeMethod(user, restaurant, presentLocalDate, orderService);
    }

    // Кейс, при котором пользователь заказал в одном ресторане сегодня, и хочет поменять после ДТ
    @Test
    public void createTestOrderAfter_11_Time() throws ExceedingTheTimeLimitException {
        var restaurant = RestaurantTestData.Gaggan;
        var user = UserTestData.excUser;
        var date = CalendarTestData.calendarDateAfter_11.getTime();
        var presentLocalDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LOGGER.info("\nTime is : " + presentLocalDate);
        Exception exception = assertThrows(ExceedingTheTimeLimitException.class, () -> {
            OrderUtil.orderTimeMethod(user, restaurant, presentLocalDate, orderService);
        });
        String expectedMessage = "\nCan't vote after 11! You`ve already voted. Your vote is Mirazur";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}

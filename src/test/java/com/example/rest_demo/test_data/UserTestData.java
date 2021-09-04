package com.example.rest_demo.test_data;

import com.example.rest_demo.model.User;



public class UserTestData {
    public static final User orderUser = new User (100004, "orderUser", "orderUser@mail.ru", "password", true, CalendarTestData.calendarDateBefore_11.getTime());
    public static final User excUser = new User (100005, "excUser", "excUser@mail.ru", "password", true, CalendarTestData.calendarDateBefore_11.getTime());
}

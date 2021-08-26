package com.example.rest_demo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtil {
    public static boolean compareWastedTime(LocalDateTime entityTime){
        LocalDate localDate = LocalDateTime.now().toLocalDate();
        LocalDate entityLocalDate = entityTime.toLocalDate();
        if (entityLocalDate.equals(localDate)){
            return false;
        }
        else return localDate.isAfter(entityLocalDate);
    }

    public static Integer checkLocalTimeDT(LocalDateTime orderTimeLocalDt){
       LocalTime orderTime = orderTimeLocalDt.toLocalTime();
       return orderTime.compareTo(LocalTime.of(11,00));
    }

    public TimeUtil(){}
}

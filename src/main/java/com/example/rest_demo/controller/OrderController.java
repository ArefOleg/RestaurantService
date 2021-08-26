package com.example.rest_demo.controller;

import com.example.rest_demo.service.OrderService;
import com.example.rest_demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/orders")
public class OrderController {
    private final OrderService orderService;
    @Autowired
    public OrderController (OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/voting")
    public String voting(@RequestParam("restrId") int id){
        orderService.createOrder(id);
        return "redirect:/votes";
    }

}

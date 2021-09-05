package com.example.rest_demo.controller;

import com.example.rest_demo.service.OrderService;
import com.example.rest_demo.util.exception.ExceedingTheTimeLimitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String voting(@RequestParam("restrId") int id) throws ExceedingTheTimeLimitException {
        orderService.createOrder(id);
        return "orders/voting";
    }

    @GetMapping("user_vote")
    public String getUserVote(Model model){//Если пользователь голосовал вчера, то старый заказ уходит в историю,
        // если вообще нету заказа, идет заказывать
        var userOrder =  orderService.getUserVote();
        if(userOrder==null){return  "redirect:/restaurants/voting";}
        else model.addAttribute("order", userOrder);
        return "orders/user_vote";
    }

    @GetMapping("/votes")
    public String votes(Model model){
        model.addAttribute("orders", orderService.getAllUserVotes());
        return "orders/votes";
    }



}

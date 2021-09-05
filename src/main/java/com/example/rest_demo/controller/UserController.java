package com.example.rest_demo.controller;

import com.example.rest_demo.model.Meal;
import com.example.rest_demo.model.Restaurant;
import com.example.rest_demo.model.Role;
import com.example.rest_demo.model.User;
import com.example.rest_demo.service.AuthorityUserDetailsService;
import com.example.rest_demo.service.RestaurantService;
import com.example.rest_demo.service.RoleService;
import com.example.rest_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public UserController (UserService userService,RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping()
    public String index(Model model){
        model.addAttribute("user", userService.getAll());
        return "users/index";
    }

    @GetMapping( "/create")
    public String showAddUserPage(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("role", roleService.getAll());
        return "users/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user, @RequestParam(value = "roleId") int id) {
        userService.save(user, id);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("user", userService.get(id));
        return "users/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("user", userService.get(id));
        model.addAttribute("role", roleService.getAll());
        model.addAttribute("userRole", userService.get(id).getRole());
        return "users/edit";
    }

    @PatchMapping("/{id}/change")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id, @RequestParam("roleId") int roleId){
        userService.save(user, roleId);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/users";
    }

}

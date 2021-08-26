package com.example.rest_demo.repository;

import com.example.rest_demo.model.Role;
import com.example.rest_demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository {
    List<Role> getAll();
}

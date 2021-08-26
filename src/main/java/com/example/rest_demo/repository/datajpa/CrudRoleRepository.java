package com.example.rest_demo.repository.datajpa;

import com.example.rest_demo.model.Role;
import com.example.rest_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CrudRoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r FROM Role r WHERE r.id = :id")
    public Role getRoleById(@Param("id") Integer id);
}

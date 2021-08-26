package com.example.rest_demo.repository.datajpa;

import com.example.rest_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CrudUserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.name = :username")
    public User getUserByUsername(@Param("username") String username);


    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);
}

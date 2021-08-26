package com.example.rest_demo.repository.datajpa;

import com.example.rest_demo.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudMenuRepository  extends JpaRepository<Menu, Integer> {
}

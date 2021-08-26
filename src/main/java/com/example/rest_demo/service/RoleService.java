package com.example.rest_demo.service;

import com.example.rest_demo.model.Role;
import com.example.rest_demo.model.User;
import com.example.rest_demo.repository.datajpa.DataJpaRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private DataJpaRoleRepository dataRoleRepository;

    public List<Role> getAll() {
        return dataRoleRepository.getAll();
    }


    @Autowired
    RoleService(DataJpaRoleRepository dataRoleRepository) {
        this.dataRoleRepository = dataRoleRepository;
    }
}

package com.example.rest_demo.repository.datajpa;

import com.example.rest_demo.model.Role;
import com.example.rest_demo.model.User;
import com.example.rest_demo.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class DataJpaRoleRepository implements RoleRepository {

    private final CrudRoleRepository crudRoleRepository;

    public DataJpaRoleRepository(CrudRoleRepository crudRoleRepository){this.crudRoleRepository = crudRoleRepository;}
    public Role getRoleById(Integer id){
        return crudRoleRepository.getRoleById(id);
    }
    @Override
    public List<Role> getAll() {
        return crudRoleRepository.findAll();
    }
}

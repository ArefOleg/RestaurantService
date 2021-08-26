package com.example.rest_demo.repository.datajpa;

import com.example.rest_demo.model.User;
import com.example.rest_demo.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaUserRepository implements UserRepository {

    private final CrudUserRepository crudUserRepository;

    public DataJpaUserRepository(CrudUserRepository crudUserRepository){
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public User save(User user) {
        return crudUserRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return crudUserRepository.findAll();
    }

    @Override
    public boolean delete(int id) {
        return crudUserRepository.delete(id) !=0;
    }

    @Override
    public User get(int id) {
        return crudUserRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String name){
        return crudUserRepository.getUserByUsername(name);
    }
}

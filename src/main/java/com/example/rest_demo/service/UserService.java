package com.example.rest_demo.service;

import com.example.rest_demo.model.Role;
import com.example.rest_demo.model.User;
import com.example.rest_demo.repository.datajpa.DataJpaOrderRepository;
import com.example.rest_demo.repository.datajpa.DataJpaRoleRepository;
import com.example.rest_demo.repository.datajpa.DataJpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final DataJpaUserRepository userRepository;
    private final DataJpaRoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getAll(){
        return userRepository.getAll();
    }

    public User save(User user, Integer id){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.getRoleById(id));
        return userRepository.save(user);
    }

    public User get(int id){
        return userRepository.get(id);
    }

    public void delete(int id){
        userRepository.delete(id);
    }

    @Autowired
    public UserService(DataJpaUserRepository userRepository, DataJpaRoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
}

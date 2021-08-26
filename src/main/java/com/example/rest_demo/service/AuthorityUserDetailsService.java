package com.example.rest_demo.service;

import com.example.rest_demo.model.Meal;
import com.example.rest_demo.model.User;
import com.example.rest_demo.repository.datajpa.CrudUserRepository;
import com.example.rest_demo.repository.datajpa.DataJpaUserRepository;
import com.example.rest_demo.util.AuthorityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;


public class AuthorityUserDetailsService implements UserDetailsService {
    @Autowired
    private CrudUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new AuthorityUserDetails(user);
    }
}

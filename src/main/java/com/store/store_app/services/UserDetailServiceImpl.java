package com.store.store_app.services;

import com.store.store_app.models.UserEntity;
import com.store.store_app.repositories.UserRepository;
import com.store.store_app.models.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository repo;

    @Autowired
    public UserDetailServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading user: " + username);
        UserEntity userEntity = repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("The user " + username + " doesn't exist."));
        System.out.println("User found: " + userEntity.getUsername());

        System.out.println("userEntity = " + userEntity.getUsername());

        return new CustomUserDetails(userEntity);
    }
}

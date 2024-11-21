package com.store.store_app.services;

import com.store.store_app.models.Product;
import com.store.store_app.models.UserEntity;
import com.store.store_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        UserEntity userEntity = repo.findUser(username).orElseThrow(() -> new UsernameNotFoundException("The user " + username + " doesn't exist."));
        System.out.println("User found: " + userEntity.getUsername());
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles().forEach(r -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(r.getRole().name()))));

        userEntity.getRoles().stream().flatMap(r -> r.getPermissions().stream()).forEach(p -> authorityList.add(new SimpleGrantedAuthority(p.getName().name())));

        System.out.println("userEntity = " + userEntity.getUsername());
        return new User(userEntity.getUsername(),
                userEntity.getPassword(), userEntity.isEnabled(),
                userEntity.isAccountNoExpired(), userEntity.isCredentialsNoExpired(),
                userEntity.isAccountNoLocked(), authorityList);
    }
}

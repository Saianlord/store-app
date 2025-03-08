package com.store.store_app.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private final transient UserEntity user;

    public CustomUserDetails(UserEntity user) {
        this.user = user;
    }

    public long getId() {
        return user.getId();
    }

    public UserEntity getUserEntity() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        user.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()))
        );

        user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission ->
                        authorities.add(new SimpleGrantedAuthority(permission.getName().name()))
                );

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}

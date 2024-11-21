package com.store.store_app.controllers;

import com.store.store_app.models.Product;
import com.store.store_app.models.UserEntity;
import com.store.store_app.services.ProductService;
import com.store.store_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }


    @GetMapping
    public List<UserEntity> listAll(){
        return service.listAll();
    }


    @GetMapping("/{userId:[0-9]+}")
    public Optional<UserEntity> getById(@PathVariable("userId") Long userId){
        return service.getById(userId);
    }

    @GetMapping("/{username:[a-zA-Z0-9]+}")
    public Optional<UserEntity> getByUsername(@PathVariable("username") String username){
        return service.findUser(username);
    }

    @PostMapping
    public void save(@RequestBody UserEntity userEntity){
        service.save(userEntity);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") Long userId){
        service.delete(userId);
    }
}

package com.store.store_app.controllers;

import com.store.store_app.models.Category;
import com.store.store_app.models.CustomUserDetails;
import com.store.store_app.models.Product;
import com.store.store_app.models.UserEntity;
import com.store.store_app.services.FirebaseStorageService;
import com.store.store_app.services.RoleService;
import com.store.store_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService uService;

    private final RoleService roleService;

    private final FirebaseStorageService firebaseStorageService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService uService, RoleService roleService, FirebaseStorageService firebaseStorageService, PasswordEncoder passwordEncoder) {
        this.uService = uService;
        this.roleService = roleService;
        this.firebaseStorageService = firebaseStorageService;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('DEVELOPER')")
    @GetMapping
    public String listUsers(Model model){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity user = uService.getById(customUserDetails.getId()).orElseThrow(()-> new NoSuchElementException("User not found with ID: " + customUserDetails.getId()));

        List<UserEntity> users = uService.listAll();

        users.remove(user);

        model.addAttribute("users", users);

        return "usersList";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('DEVELOPER')")
    @GetMapping("/form")
    public String showUserForm(@RequestParam(value = "id", required = false) Long id, Model model){

        model.addAttribute("roles", roleService.listAll());

        if(id == null){
            model.addAttribute("user", new UserEntity());

            return "usersForm";
        }

        UserEntity user = uService.getById(id).orElseThrow(()-> new NoSuchElementException("User not found"));

        user.setPassword(null);

        model.addAttribute("user", user);



        return "usersForm";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('DEVELOPER')")
    @PostMapping("/save")
    public String saveUser(UserEntity user) {

        System.out.println("user = " + user);



        if (user.getId() == null) {

            user.setEnabled(true);

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            uService.save(user);

            return "redirect:/users";

        } else {
            UserEntity existingUser = uService.getById(user.getId()).orElseThrow(() -> new NoSuchElementException("User not found"));

            user.setEnabled(existingUser.isEnabled());

            if(user.getUsername().isBlank()){
                user.setUsername(existingUser.getUsername());
            }

            if(user.getPassword().isBlank()) {

                user.setPassword(existingUser.getPassword());
            }else{
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            if(user.getRoles().isEmpty()){
                user.setRoles(existingUser.getRoles());
            }

            user.setPhotoUrl(existingUser.getPhotoUrl());

            System.out.println("user2 = " + user);

            uService.save(user);

            return "redirect:/users";
        }


    }


    @PostMapping("/update-image")
    public String updateImage(UserEntity user, @RequestParam(value = "image", required = false) MultipartFile image) {

        Long userId = user.getId();

        String path = firebaseStorageService.loadImage(image, "profile_photos", userId);

        UserEntity existingUser = uService.getById(user.getId()).orElseThrow(() -> new NoSuchElementException("User not found"));

        if(existingUser.getPhotoUrl() != null){
            firebaseStorageService.deleteImage(existingUser.getPhotoUrl());
        }

        existingUser.setPhotoUrl(path);

        uService.save(existingUser);

        return "redirect:/account";
    }

    @GetMapping("/deactivate")
    public String deactivateUser(@RequestParam(value = "id", required = false) Long id){

        UserEntity user = uService.getById(id).orElseThrow(() -> new NoSuchElementException("User not found"));

        user.setEnabled(false);

        uService.save(user);

        return "redirect:/users";
    }

    @GetMapping("/reactivate")
    public String reactivateUser(@RequestParam(value = "id", required = false) Long id){

        UserEntity user = uService.getById(id).orElseThrow(() -> new NoSuchElementException("User not found"));

        user.setEnabled(true);

        uService.save(user);

        return "redirect:/users";
    }


}

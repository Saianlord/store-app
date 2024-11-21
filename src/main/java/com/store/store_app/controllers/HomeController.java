package com.store.store_app.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping({"", "/"})
    public String home(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication != null) {
            // Si el usuario está autenticado
            System.out.println("Authenticated user desde home: " + authentication.getName());
        } else {
            // Si no hay un usuario autenticado
            System.out.println("No authenticated user desde home");
        }
        return "index";
    }


    @GetMapping({"/products"})
    public String products(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated username: " + auth.getName());
        return "products";
    }

    @GetMapping({"/cart"})
    public String cart(){
        return "cart";
    }

    @GetMapping({"/account"})
    public String Account(){
        return "account";
    }



}

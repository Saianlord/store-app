package com.store.store_app.controllers;

import com.store.store_app.models.Account;
import com.store.store_app.models.CustomUserDetails;
import com.store.store_app.models.UserEntity;
import com.store.store_app.services.AccountService;
import com.store.store_app.services.OrderDetailsService;
import com.store.store_app.services.OrderService;
import com.store.store_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final OrderService oService;
    private final OrderDetailsService oDService;
    private final AccountService aService;
    private final UserService uService;

    @Autowired
    public AccountController(OrderService oService, OrderDetailsService oDService, AccountService aService, UserService uService) {
        this.oService = oService;
        this.oDService = oDService;
        this.aService = aService;
        this.uService = uService;
    }

    @GetMapping
    public String showAccount(Model model){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity user = uService.getById(customUserDetails.getId()).orElseThrow(()-> new NoSuchElementException("User not found with ID: " + customUserDetails.getId()));

        List<Account> userAccounts = aService.findAllAccountByUserId(user.getId());

        model.addAttribute("user", user);

        model.addAttribute("userAccounts", userAccounts);

        return "/account";
    }
}

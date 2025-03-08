package com.store.store_app.controllers;

import com.store.store_app.exceptions.OrderException;
import com.store.store_app.models.*;
import com.store.store_app.services.AccountService;
import com.store.store_app.services.OrderDetailsService;
import com.store.store_app.services.OrderService;
import com.store.store_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/order")
@SessionAttributes("cart")
@PreAuthorize("hasRole('CUSTOMER') or hasRole('DEVELOPER')")
public class OrderController {

    private final OrderService oService;
    private final OrderDetailsService oDService;
    private final AccountService aService;
    private final UserService uService;

    @Autowired
    public OrderController(OrderService oService, OrderDetailsService oDService, AccountService aService, UserService uService) {
        this.oService = oService;
        this.oDService = oDService;
        this.aService = aService;
        this.uService = uService;
    }


    @GetMapping("/history")
    public String getOrderHistory(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size, Model model) {

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final Pageable pageable = PageRequest.of(page, size);

        Page<Order> ordersPage = oService.findAllOrderByUserId(customUserDetails.getId(), pageable);

        
        ordersPage.getContent().forEach(o ->{
            System.out.println("o.getTotal() = " + o.getTotal());
            System.out.println("o.getOrderDate() = " + o.getOrderDate());

        });

        model.addAttribute("orders", ordersPage.getContent());
        model.addAttribute("currentPage", ordersPage.getNumber());
        model.addAttribute("totalPages", ordersPage.getTotalPages());
        model.addAttribute("pageSize", size);

        return "orders";


    }
    @GetMapping("/new")
    public String newOrder(@RequestParam(value = "accountId", required = true) Long accountId,
                           @ModelAttribute("cart") Cart cart,
                           Model model, RedirectAttributes redirectAttributes) {

        Account userAccount = aService.getById(accountId);

        if (userAccount.getBalance() < cart.getTotal()) {
            System.out.println("Insufficient funds: " + userAccount.getBalance() + " < " + cart.getTotal());
            model.addAttribute("errorMessage", "Insufficient funds to process the purchase");
            return "cart";
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity user = uService.getById(customUserDetails.getId()).orElseThrow(()-> new NoSuchElementException("User not found with ID: " + customUserDetails.getId()));



        try {
            oService.processOrder(user, userAccount, cart);
            redirectAttributes.addFlashAttribute("clearCart", true);
            return "redirect:/cart";
        } catch (OrderException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "cart";
        }

    }

    @GetMapping("/details")
    public String orderDetails(@RequestParam("id") Long orderId, Model model){

        List<OrderDetails> orderDetailsList = oDService.findAllOrderDetailsByOrderId(orderId);

        Order order = oService.getById(orderId);

        model.addAttribute("order", order);

        model.addAttribute("orderDetails", orderDetailsList);
        

        return "orderDetails";
    }


}

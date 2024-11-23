package com.store.store_app.controllers;

import com.store.store_app.models.*;
import com.store.store_app.services.AccountService;
import com.store.store_app.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("cart")
@RequestMapping("/cart")
@PreAuthorize("hasRole('CUSTOMER') or hasRole('DEVELOPER')")
public class CartController {

    private final AccountService aService;
    private final ProductService pService;

    @Autowired
    public CartController(AccountService aService, ProductService pService) {
        this.aService = aService;
        this.pService = pService;
    }

    @ModelAttribute("cart")
    public Cart getCart() {
        return new Cart();
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("id") Long productId,
                            @ModelAttribute("cart") Cart cart) {
        Product product = pService.getById(productId);

        ItemCart itemCart = new ItemCart(product);
        cart.addItemCart(itemCart);

        return "redirect:/cart";
    }


    @GetMapping
    public String showCart(@ModelAttribute("cart") Cart cart, Model model) {

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Account> userAccounts = aService.findAllAccountByUserId(customUserDetails.getId());

        model.addAttribute("userAccounts", userAccounts);

        model.addAttribute("total", cart.getTotal());

        model.addAttribute("carro", cart);

        Boolean clearCart = (Boolean) model.getAttribute("clearCart");

        if(clearCart != null && clearCart){
            cart.clearCart();

        }

        return "/cart";

    }

    @PostMapping("/update")
    public String updateCart(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity,
                             @ModelAttribute("cart") Cart cart) {
        cart.updateQuantity(productId, quantity);
        cart.getItems().forEach(itemCart -> System.out.println("quantity = " + quantity));
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("productId") Long productId, @ModelAttribute("cart") Cart cart) {
        cart.removeProduct(productId);
        System.out.println(cart.getItems().size());
        return "redirect:/cart";
    }
}

package com.store.store_app.controllers;

import com.store.store_app.services.ProductService;
import com.store.store_app.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@PreAuthorize("isAuthenticated()")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> listAll(){
        return service.listAll();
    }


    @GetMapping("/{productId}")
    @PreAuthorize("hasRole('DEVELOPER')")
    public Optional<Product> getById(@PathVariable("productId") Long productId){
        return service.getById(productId);
    }

    @PostMapping
    public void save(@RequestBody Product product){
        service.save(product);
    }

    @DeleteMapping("/{productId}")
    public void delete(@PathVariable("productId") Long productId){
        service.delete(productId);
    }
}

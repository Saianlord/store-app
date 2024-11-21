package com.store.store_app.services;

import com.store.store_app.repositories.ProductRepository;
import com.store.store_app.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repo;

    @Autowired
    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> listAll(){
        return repo.findAll();
    }

    public Optional<Product> getById(Long productId){

        return repo.findById(productId);
    }

    public void save(Product product){
        repo.save(product);
    }

    public void delete(Long productId){
        repo.deleteById(productId);
    }

}

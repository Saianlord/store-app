package com.store.store_app.services;

import com.store.store_app.repositories.ProductRepository;
import com.store.store_app.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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

    public Page<Product> listByPage(Pageable pageable){
        return repo.findAll(pageable);
    }

    public Product getById(Long productId){

        return repo.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + productId));
    }

    public void save(Product product){
        repo.save(product);
    }

    public void delete(Long productId){
        repo.deleteById(productId);
    }

    public Page<Product> listBySpecification(Specification<Product> spec, Pageable pageable) {
        return repo.findAll(spec, pageable);
    }


}

package com.store.store_app.services;

import com.store.store_app.models.OrderDetails;
import com.store.store_app.repositories.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {
    private final OrderDetailsRepository repo;

    @Autowired
    public OrderDetailsService(OrderDetailsRepository repo) {
        this.repo = repo;
    }

    public List<OrderDetails> listAll(){
        return repo.findAll();
    }

    public Optional<OrderDetails> getById(Long orderDetailsId){

        return repo.findById(orderDetailsId);
    }

    public void save(OrderDetails orderDetails){
        repo.save(orderDetails);
    }

    public void delete(Long orderDetailsId){
        repo.deleteById(orderDetailsId);
    }
}

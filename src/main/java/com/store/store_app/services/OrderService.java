package com.store.store_app.services;

import com.store.store_app.models.Order;
import com.store.store_app.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository repo;

    @Autowired
    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    public List<Order> listAll(){
        return repo.findAll();
    }

    public Optional<Order> getById(Long orderId){

        return repo.findById(orderId);
    }

    public void save(Order order){
        repo.save(order);
    }

    public void delete(Long orderId){
        repo.deleteById(orderId);
    }
}

package com.store.store_app.services;

import com.store.store_app.exceptions.OrderException;
import com.store.store_app.models.OrderDetails;
import com.store.store_app.models.Product;
import com.store.store_app.repositories.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {
    private final OrderDetailsRepository repo;

    private final ProductService pService;

    @Autowired
    public OrderDetailsService(OrderDetailsRepository repo, ProductService service) {
        this.repo = repo;
        this.pService = service;
    }

    public List<OrderDetails> findAllOrderDetailsByOrderId(Long orderId){
        return repo.findAllOrderDetailsByOrderId(orderId);
    }

    public Optional<OrderDetails> getById(Long orderDetailsId){

        return repo.findById(orderDetailsId);
    }

    @Transactional
    public void save(OrderDetails orderDetails) {
        Product product = pService.getById(orderDetails.getProduct().getId());

        if (product.getStock() < orderDetails.getQuantity()) {
            throw new OrderException("Stock is insufficient to fulfill the order for product: " + product.getName());
        }

        product.setStock(product.getStock() - orderDetails.getQuantity());
        pService.save(product);

        repo.save(orderDetails);
    }

    public void delete(Long orderDetailsId){
        repo.deleteById(orderDetailsId);
    }
}

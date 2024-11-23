package com.store.store_app.services;

import com.store.store_app.models.*;
import com.store.store_app.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderService {
    private final AccountService aService;
    private final OrderRepository repo;
    private final OrderDetailsService oDService;

    @Autowired
    public OrderService(AccountService aService, OrderRepository repo, OrderDetailsService oDService) {
        this.aService = aService;
        this.repo = repo;
        this.oDService = oDService;
    }

    public Page<Order> findAllOrderByUserId(Long userId, Pageable pageable){
        return repo.findAllOrderByUserId(userId, pageable);
    }

    public List<Order> listAllUserOrders(){
        return repo.findAll();
    }

    public Order getById(Long orderId){

        return repo.findById(orderId).orElseThrow(() -> new NoSuchElementException("Order not found with id: " + orderId));
    }

    public void save(Order order, Account userAccount){
        float updatedBalance = userAccount.getBalance() - order.getTotal();
        userAccount.setBalance(updatedBalance);

        aService.save(userAccount);
        repo.save(order);
    }

    public void delete(Long orderId){
        repo.deleteById(orderId);
    }

    @Transactional
    public void processOrder(UserEntity user, Account userAccount, Cart cart) {

        Order order = new Order();
        order.setUser(user);
        order.setTotal(cart.getTotal());
        repo.save(order);

        cart.getItems().forEach(item -> {
            OrderDetails od = new OrderDetails();
            od.setOrder(order);
            od.setProduct(item.getProduct());
            od.setQuantity(item.getQuantity());
            od.setPrice(item.getProduct().getPrice());
            oDService.save(od); // Este método descuenta stock
        });

        float updatedBalance = userAccount.getBalance() - cart.getTotal();
        userAccount.setBalance(updatedBalance);
        aService.save(userAccount);
    }
}

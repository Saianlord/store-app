package com.store.store_app.repositories;

import com.store.store_app.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    public Page<Order> findAllOrderByUserId(Long userId, Pageable pageable);
}

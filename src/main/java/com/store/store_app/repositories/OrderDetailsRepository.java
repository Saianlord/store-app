package com.store.store_app.repositories;

import com.store.store_app.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    public List<OrderDetails> findAllOrderDetailsByOrderId(Long orderId);

}

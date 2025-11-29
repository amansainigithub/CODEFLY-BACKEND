package com.coder.springjwt.repository.customerRepository.orderItemsRepository;

import com.coder.springjwt.models.customerModels.orders.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
}

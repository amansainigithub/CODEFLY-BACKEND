package com.coder.springjwt.repository.customerRepository.paymentOrderRepo;

import com.coder.springjwt.models.customerModels.paymentsModels.PaymentOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderRepo extends JpaRepository<PaymentOrders, Long> {

    PaymentOrders findByOrderId(String orderId);

}

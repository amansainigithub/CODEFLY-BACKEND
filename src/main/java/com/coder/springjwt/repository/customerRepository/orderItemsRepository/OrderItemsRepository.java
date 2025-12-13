package com.coder.springjwt.repository.customerRepository.orderItemsRepository;

import com.coder.springjwt.models.customerModels.orders.OrderItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {


    Page<OrderItems> findByUserIdAndUsername(String userid , String username , Pageable pageable);

    Page<OrderItems> findBySellerIdAndSellerUsernameAndOrderStatus(String sellerId ,
                                                                   String sellerUsername ,
                                                                   String orderStatus ,
                                                                   Pageable pageable);


}

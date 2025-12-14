package com.coder.springjwt.buckets.shiprocketBucket.shipRocketRepo;

import com.coder.springjwt.buckets.shiprocketBucket.shiprocketModels.CreateOrderRequestResponse_SR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreateOrderShipRocketRepo  extends JpaRepository<CreateOrderRequestResponse_SR, Long> {
}

package com.coder.springjwt.buckets.shiprocketBucket.shipRocketRepo;

import com.coder.springjwt.buckets.shiprocketBucket.shiprocketModels.CancelOrderRequestResponse_SR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelOrderShipRocketRepo extends JpaRepository<CancelOrderRequestResponse_SR, Long> {
}

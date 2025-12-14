package com.coder.springjwt.buckets.shiprocketBucket.shipRocketRepo;

import com.coder.springjwt.buckets.shiprocketBucket.shiprocketModels.PickUpAddress_SR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickUpAddressShipRocketRepo extends JpaRepository<PickUpAddress_SR , Long> {
}

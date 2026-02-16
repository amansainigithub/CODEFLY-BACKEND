package com.coder.springjwt.buckets.shiprocketBucket.shipRocketRepo;

import com.coder.springjwt.buckets.shiprocketBucket.shiprocketModels.ShipRocketToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipRocketTokenRepository extends JpaRepository<ShipRocketToken, Long> {
}

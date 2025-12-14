package com.coder.springjwt.buckets.shiprocketBucket.shipRocketRepo;

import com.coder.springjwt.buckets.shiprocketBucket.shiprocketModels.GenerateToken_SR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerateTokenShipRocketRepo extends JpaRepository<GenerateToken_SR, Long> {


}

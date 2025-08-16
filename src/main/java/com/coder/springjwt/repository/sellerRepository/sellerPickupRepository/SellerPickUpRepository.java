package com.coder.springjwt.repository.sellerRepository.sellerPickupRepository;


import com.coder.springjwt.models.sellerModels.sellerPickup.SellerPickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerPickUpRepository extends JpaRepository<SellerPickup , Long> {
}

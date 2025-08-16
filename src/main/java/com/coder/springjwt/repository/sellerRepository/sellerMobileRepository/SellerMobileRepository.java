package com.coder.springjwt.repository.sellerRepository.sellerMobileRepository;

import com.coder.springjwt.models.sellerModels.SellerMobile.SellerMobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerMobileRepository  extends JpaRepository<SellerMobile,Long> {


    Optional<SellerMobile> findByMobile(String mobile);

    //Optional<SellerMobile>  findByIsVerifiedAndMobile(String verified , String mobile );

    Optional<SellerMobile> findByMobileAndIsVerified(String mobile, Boolean isVerified);

}

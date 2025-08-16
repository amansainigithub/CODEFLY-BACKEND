package com.coder.springjwt.repository.sellerRepository.sellerGstRepository;

import com.coder.springjwt.models.sellerModels.sellerTax.SellerTax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerTaxRepository extends JpaRepository<SellerTax,Long> {


    Optional<SellerTax> findBySellerUsername(String username);

    Optional<SellerTax> findByGstNumber(String gstNumber);

}

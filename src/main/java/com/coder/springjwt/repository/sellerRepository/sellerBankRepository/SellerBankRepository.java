package com.coder.springjwt.repository.sellerRepository.sellerBankRepository;

import com.coder.springjwt.models.sellerModels.sellerBank.SellerBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerBankRepository extends JpaRepository<SellerBank , Long> {


}

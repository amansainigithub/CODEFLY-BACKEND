package com.coder.springjwt.repository.sellerRepository.sellerStoreRepository;


import com.coder.springjwt.models.sellerModels.sellerStore.SellerStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerStoreRepository extends JpaRepository<SellerStore, Long> {


    Optional<SellerStore> findByUsername(String username);

    Boolean existsByUsername(String username);

}

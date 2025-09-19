package com.coder.springjwt.repository.sellerRepository.engineXRepository;

import com.coder.springjwt.models.sellerModels.engineXModels.EngineX;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EngineXRepository  extends JpaRepository<EngineX, Long>  {

    Optional<EngineX> findByVariantCategoryId(long variantId);
}

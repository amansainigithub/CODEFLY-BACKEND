package com.coder.springjwt.repository.sellerRepository.productDetailsRepository;

import com.coder.springjwt.models.sellerModels.productModels.ProductSizeRows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSizeRowsRepo extends JpaRepository<ProductSizeRows, Long> {
}

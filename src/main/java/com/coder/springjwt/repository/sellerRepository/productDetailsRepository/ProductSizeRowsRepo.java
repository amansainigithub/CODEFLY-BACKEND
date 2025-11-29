package com.coder.springjwt.repository.sellerRepository.productDetailsRepository;

import com.coder.springjwt.models.sellerModels.productModels.ProductSizeRows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductSizeRowsRepo extends JpaRepository<ProductSizeRows, Long> {

//    Optional<ProductSizeRows> findByProductDetailsIdAnd__Ms_Val(Long productDetailsId, String msVal);

    @Query(value = "SELECT * FROM Product_Size_Rows p WHERE p.product_details_id = :productDetailsId AND p.__ms_val = :msVal", nativeQuery = true)
    Optional<ProductSizeRows> findByProductDetailsIdAndMsValNative(@Param("productDetailsId") Long productDetailsId,
                                                                   @Param("msVal") String msVal);

}

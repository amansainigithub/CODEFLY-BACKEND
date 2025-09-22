package com.coder.springjwt.repository.sellerRepository.productDetailsRepository;

import com.coder.springjwt.models.adminModels.categories.SubCategoryModel;
import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDetailsRepo extends JpaRepository<ProductDetailsModel, Long> {

    Optional<ProductDetailsModel> findByProductRootId(Long id);


}

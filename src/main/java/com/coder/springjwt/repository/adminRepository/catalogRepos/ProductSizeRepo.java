package com.coder.springjwt.repository.adminRepository.catalogRepos;

import com.coder.springjwt.models.adminModels.catalog.catalogSize.ProductSizeVariantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSizeRepo extends JpaRepository<ProductSizeVariantModel,Long> {
}

package com.coder.springjwt.repository.adminRepository.catalogRepos;

import com.coder.springjwt.models.adminModels.catalog.catalogHeight.ProductHeightModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductHeightRepo extends JpaRepository<ProductHeightModel,Long> {
}

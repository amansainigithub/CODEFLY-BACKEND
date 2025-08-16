package com.coder.springjwt.repository.adminRepository.catalogRepos;

import com.coder.springjwt.models.adminModels.catalog.catalogType.ProductTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepo extends JpaRepository<ProductTypeModel,Long> {
}

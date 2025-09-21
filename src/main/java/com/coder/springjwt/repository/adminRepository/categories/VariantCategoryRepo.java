package com.coder.springjwt.repository.adminRepository.categories;

import com.coder.springjwt.models.adminModels.categories.VariantCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantCategoryRepo extends JpaRepository<VariantCategoryModel,Long> {
    List<VariantCategoryModel> findByTypeCategoryModelId(Long id);
}

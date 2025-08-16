package com.coder.springjwt.repository.adminRepository.categories;

import com.coder.springjwt.models.adminModels.categories.SubCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepo extends JpaRepository<SubCategoryModel,Long> {

//    @Query("SELECT u FROM ChildCategoryModel u WHERE u.parentCategory.id = :id")
//    List<ChildCategoryModel> getChildCategoriesListByParentCategoryId(@Param("id") Long id);

}

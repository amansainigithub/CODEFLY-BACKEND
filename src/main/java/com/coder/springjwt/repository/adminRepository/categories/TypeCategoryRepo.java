package com.coder.springjwt.repository.adminRepository.categories;

import com.coder.springjwt.models.adminModels.categories.TypeCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeCategoryRepo extends JpaRepository<TypeCategoryModel,Long> {

//    @Query("SELECT u FROM BabyCategoryModel u WHERE u.childCategoryModel.id = :id")
//    List<BabyCategoryModel> getBabyCategoryListByChildCategoryId(@Param("id") Long id);
}

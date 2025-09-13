package com.coder.springjwt.repository.adminRepository.categories;

import com.coder.springjwt.models.adminModels.categories.SubCategoryModel;
import com.coder.springjwt.models.adminModels.categories.TypeCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeCategoryRepo extends JpaRepository<TypeCategoryModel,Long> {

//    @Query("SELECT u FROM BabyCategoryModel u WHERE u.childCategoryModel.id = :id")
//    List<BabyCategoryModel> getBabyCategoryListByChildCategoryId(@Param("id") Long id);

    List<TypeCategoryModel> findBySubCategoryModelId(Long id);
}

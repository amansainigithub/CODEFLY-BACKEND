package com.coder.springjwt.repository.adminRepository.categories;

import com.coder.springjwt.dtos.adminDtos.categoriesDtos.SubCategoryDto;
import com.coder.springjwt.models.adminModels.categories.SubCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepo extends JpaRepository<SubCategoryModel,Long> {

//    @Query("SELECT u FROM RootCategoryModel u WHERE u.rootCategory.id = :id")
//    List<SubCategoryModel> getSubCategoriesListByRootCategoryId(@Param("id") Long id);


//    @Query("SELECT new com.example.dto.SubCategoryDTO(s.id, s.categoryName, s.description) " +
//            "FROM SubCategoryModel s WHERE s.rootCategory.id = :id")
//    List<SubCategoryDto> findSubCategoriesWithoutRoot(@Param("id") Long id);


    List<SubCategoryModel> findByRootCategoryId(Long id);

}

package com.coder.springjwt.services.adminServices.categories;

import com.coder.springjwt.dtos.adminDtos.categoriesDtos.SubCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface SubCategoryService {

    ResponseEntity<?> saveSubCategory(SubCategoryDto subCategoryDto);

    ResponseEntity<?> getSubCategoryList();

    ResponseEntity<?> deleteSubCategoryById(long categoryId);

    ResponseEntity<?> getSubCategoryById(long categoryId);

    ResponseEntity<?> updateSubCategory(SubCategoryDto subCategoryDto );
    ResponseEntity<?> updateSubCategoryFile(MultipartFile file, Long subCategoryId);


}

package com.coder.springjwt.services.adminServices.categories;

import com.coder.springjwt.dtos.adminDtos.categoriesDtos.TypeCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface TypeCategoryService {

    ResponseEntity<?> saveTypeCategory(TypeCategoryDto typeCategoryDto);
    ResponseEntity<?> getTypeCategoryList();

    ResponseEntity<?> deleteTypeCategoryById(long categoryId);
    ResponseEntity<?> updateTypeCategory(TypeCategoryDto typeCategoryDto);

    ResponseEntity<?> getTypeCategoryById(long categoryId);

    ResponseEntity<?> updateTypeCategoryFile(MultipartFile file, Long typeCategoryId);

}

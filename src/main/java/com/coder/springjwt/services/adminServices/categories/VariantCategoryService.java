package com.coder.springjwt.services.adminServices.categories;

import com.coder.springjwt.dtos.adminDtos.categoriesDtos.VariantCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface VariantCategoryService {

    ResponseEntity<?> saveVariantCategory(VariantCategoryDto variantCategoryDto);

    ResponseEntity<?> getVariantCategoryList();

    ResponseEntity<?> deleteVariantCategoryById(long categoryId);

    ResponseEntity<?> updateVariantCategory(VariantCategoryDto variantCategoryDto);

    ResponseEntity<?> getVariantCategoryById(long categoryId);

    ResponseEntity<?> updateVariantCategoryFile(MultipartFile file, Long variantCategoryId);

}

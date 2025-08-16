package com.coder.springjwt.services.adminServices.categories;

import com.coder.springjwt.dtos.adminDtos.categoriesDtos.RootCategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface RootCategoryService {

    ResponseEntity<?> saveRootCategory(RootCategoryDto rootCategoryDto);

    ResponseEntity<?> getRootCategoryList();

    ResponseEntity<?> deleteRootCategoryById(long categoryId);

    ResponseEntity<?> getRootCategoryById(long categoryId);

    ResponseEntity<?> updateRootCategory(RootCategoryDto rootCategoryDto );


    ResponseEntity<?> updateRootCategoryFile(MultipartFile file, Long rootCategoryId);


}

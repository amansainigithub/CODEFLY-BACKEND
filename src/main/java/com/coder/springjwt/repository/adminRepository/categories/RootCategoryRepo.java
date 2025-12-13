package com.coder.springjwt.repository.adminRepository.categories;

import com.coder.springjwt.models.adminModels.categories.RootCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RootCategoryRepo  extends JpaRepository<RootCategoryModel,Long> {


    List<RootCategoryModel> findTop1ByOrderByCreationDate();
}

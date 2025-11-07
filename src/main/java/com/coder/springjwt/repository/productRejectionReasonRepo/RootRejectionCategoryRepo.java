package com.coder.springjwt.repository.productRejectionReasonRepo;

import com.coder.springjwt.models.adminModels.ProductRejectionReasonModel.ProductRejectionReason;
import com.coder.springjwt.models.adminModels.ProductRejectionReasonModel.RootRejectionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RootRejectionCategoryRepo extends JpaRepository<RootRejectionCategory, Long> {


    Optional<RootRejectionCategory> findByRootRejectionCategory(String rootRejectionCategory);

}

package com.coder.springjwt.repository.productRejectionReasonRepo;

import com.coder.springjwt.models.adminModels.ProductRejectionReasonModel.ProductRejectionReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRejectionReasonRepo extends JpaRepository<ProductRejectionReason, Long> {
}

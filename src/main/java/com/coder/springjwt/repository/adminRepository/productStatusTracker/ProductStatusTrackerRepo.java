package com.coder.springjwt.repository.adminRepository.productStatusTracker;

import com.coder.springjwt.models.adminModels.productStatusTracker.ProductStatusTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStatusTrackerRepo extends JpaRepository<ProductStatusTracker,Long> {
}

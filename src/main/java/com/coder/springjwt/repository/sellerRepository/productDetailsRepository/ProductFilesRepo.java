package com.coder.springjwt.repository.sellerRepository.productDetailsRepository;

import com.coder.springjwt.models.sellerModels.productModels.ProductFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFilesRepo extends JpaRepository<ProductFiles, Long> {

    List<ProductFiles> findByProductDetailsId(long productId);

}

package com.coder.springjwt.repository.sellerRepository.productDetailsRepository;

import com.coder.springjwt.models.sellerModels.productModels.ProductDetailsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDetailsRepo extends JpaRepository<ProductDetailsModel, Long> {

    Optional<ProductDetailsModel> findByProductRootId(Long id);

    Page<ProductDetailsModel> findByUsernameAndProductStatus(String username, String productStatus , Pageable pageable);

    List<ProductDetailsModel> findByProductStatus(String productStatus ,Sort sort);

    Page<ProductDetailsModel> findByProductStatus(String productStatus ,Pageable pageable);

    Page<ProductDetailsModel> findByUsername(String username ,Pageable pageable);

    int countByProductStatusAndUsername(String productStatus, String username);

    Optional<ProductDetailsModel> findByProductKeyAndProductSeries(String productKey , String productSeries);

    //    List<ProductDetailsModel> findAllByProductKeyAndProductSeries(String productKey, String productSeries);
    List<ProductDetailsModel> findAllByProductKey(String productKey);

    Optional<ProductDetailsModel> findByIdAndUsername(Long id , String username);



    @Query("SELECT DISTINCT p FROM ProductDetailsModel p " +
            "JOIN p.productSizeRows ps " +
            "WHERE ps.inventory = '0' AND p.username = :username")
    Page<ProductDetailsModel> getProductsByInventoryZeroAndUserName(@Param("username") String username, Pageable pageable);

    @Query("SELECT DISTINCT p FROM ProductDetailsModel p " +
            "JOIN p.productSizeRows ps " +
            "WHERE CAST(ps.inventory AS int) > 0 " +
            "AND CAST(ps.inventory AS int) <= 10 " +
            "AND p.username = :username")
    Page<ProductDetailsModel> findLowInventoryProductsByUsername(@Param("username") String username, Pageable pageable);


    List<ProductDetailsModel> findTop100ByProductStatus(String productStatus, Sort sort);


    Optional<ProductDetailsModel> findByIdAndProductStatus(Long id , String productStatus);

    boolean existsByProductFileId(String productFileId);

}

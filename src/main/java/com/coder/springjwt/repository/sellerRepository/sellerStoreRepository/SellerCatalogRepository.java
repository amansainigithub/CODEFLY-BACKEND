//package com.coder.springjwt.repository.sellerRepository.sellerStoreRepository;
//
//import com.coder.springjwt.models.sellerModels.sellerStore.SellerCatalog;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Repository
//public interface SellerCatalogRepository extends JpaRepository<SellerCatalog, Long>{
//
//    @Query(value = "SELECT * FROM seller_catalog WHERE username = :username", nativeQuery = true)
//    List<SellerCatalog> findAllByUsername(@Param("username") String username);
//
//    Page<SellerCatalog> findAllByUsername(@Param("username") String username , Pageable pageable);
//
//    @Query(value = "SELECT * FROM seller_catalog WHERE username = :username and catalog_status = :catalog_status",
//            nativeQuery = true)
//    Page<SellerCatalog> findAllByCatalogStatusAndCatalogStatus(@Param("username") String username , @Param("catalog_status") String catalog_status , Pageable pageable);
//
//
//    @Query(value = "SELECT * FROM seller_catalog WHERE catalog_status = :catalog_status",
//            nativeQuery = true)
//    Page<SellerCatalog> findAllByCatalogStatusAndPageable(@Param("catalog_status") String catalog_status ,  Pageable pageable);
//
//    SellerCatalog findTopByOrderByIdDesc();
//
//    Page<SellerCatalog> findAllByCatalogStatusAndCreationDateBetween(String catalogStatus,
//                                                                  LocalDateTime startDate,
//                                                                  LocalDateTime endDate,
//                                                                  Pageable pageable);
//
//
//
//    @Query(value = "SELECT DATE(creation_date) AS date, COUNT(*) AS count " +
//            "FROM seller_catalog " +
//            "GROUP BY DATE(creation_date) " +
//            "ORDER BY DATE(creation_date)", nativeQuery = true)
//    List<Object[]> getDateWiseData();
//
//
//
//
//
//}

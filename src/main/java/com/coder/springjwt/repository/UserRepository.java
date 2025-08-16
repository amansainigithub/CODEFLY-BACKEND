package com.coder.springjwt.repository;

import com.coder.springjwt.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> findByUsernameAndCustomerRegisterComplete(String username, String rcFlag);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	Boolean existsBySellerEmailAndSellerEmailVerify(String sellerEmail , String sellerEmailVerified);

	Page<User> findByProjectRole(String projectRole , Pageable pageable);

	Optional<User> findBySellerMobileAndSellerRegisterComplete(String mobile , String sellerRegisterComplete );

	Optional<User> findByUsernameAndSellerRegisterComplete(String mobile , String sellerRegisterComplete );

	Optional<User> findByUsernameAndSellerRegisterCompleteAndProjectRole(String mobile , String sellerRegisterComplete ,String projectRole);



}

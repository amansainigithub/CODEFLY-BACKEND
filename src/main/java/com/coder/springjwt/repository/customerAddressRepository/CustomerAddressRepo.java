package com.coder.springjwt.repository.customerAddressRepository;

import com.coder.springjwt.models.customerModels.customerAddressModel.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerAddressRepo extends JpaRepository<CustomerAddress, Long> {

    long countByUserId(Long userId);

    List<CustomerAddress> findByUserIdAndDefaultAddress(Long userId , boolean defaultAddress);

    CustomerAddress findByUserIdAndId(Long userId , Long id);

    List<CustomerAddress> findByUserId(Long userId);
}

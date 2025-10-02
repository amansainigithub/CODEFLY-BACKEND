package com.coder.springjwt.repository.adminRepository.chargeConfigRepo;

import com.coder.springjwt.models.adminModels.chargeConfigModels.ChargeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeConfigRepo extends JpaRepository<ChargeConfig,Long> {
}

package com.coder.springjwt.repository.userRepository;

import com.coder.springjwt.models.customerModels.UserMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMetaDataRepo extends JpaRepository<UserMetaData, Long> {
}

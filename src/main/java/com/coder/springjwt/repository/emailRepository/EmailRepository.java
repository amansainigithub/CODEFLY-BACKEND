package com.coder.springjwt.repository.emailRepository;

import com.coder.springjwt.dtos.emailPayloads.EmailBucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailBucket, Long> {
}

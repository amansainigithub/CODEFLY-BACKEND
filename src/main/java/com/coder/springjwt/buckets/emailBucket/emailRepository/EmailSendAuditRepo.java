package com.coder.springjwt.buckets.emailBucket.emailRepository;

import com.coder.springjwt.buckets.emailBucket.emailPayloads.EmailSendAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailSendAuditRepo extends JpaRepository<EmailSendAudit, Long> {
}

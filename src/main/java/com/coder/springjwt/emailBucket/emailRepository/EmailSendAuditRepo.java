package com.coder.springjwt.emailBucket.emailRepository;

import com.coder.springjwt.emailBucket.emailPayloads.EmailSendAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailSendAuditRepo extends JpaRepository<EmailSendAudit, Long> {
}

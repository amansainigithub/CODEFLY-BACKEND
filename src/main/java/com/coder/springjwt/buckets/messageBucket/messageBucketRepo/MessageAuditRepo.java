package com.coder.springjwt.buckets.messageBucket.messageBucketRepo;

import com.coder.springjwt.buckets.messageBucket.models.MessageAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageAuditRepo extends JpaRepository<MessageAudit, Long> {
}

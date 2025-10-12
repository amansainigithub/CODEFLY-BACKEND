package com.coder.springjwt.emailBucket.emailRepository;

import com.coder.springjwt.emailBucket.emailPayloads.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTemplateRepo extends JpaRepository<EmailTemplate , Long> {
}

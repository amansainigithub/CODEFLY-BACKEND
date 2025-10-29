package com.coder.springjwt.emailBucket.emailRepository;

import com.coder.springjwt.emailBucket.emailPayloads.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTemplateRepo extends JpaRepository<EmailTemplate , Long> {

    Optional<EmailTemplate> findByTemplateKey(String templateKey);
}

package com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostRepo;

import com.coder.springjwt.buckets.nimbusPostBucket.nimbusPostModel.NimbusPostApiLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NimbusPostApiRepository extends JpaRepository<NimbusPostApiLog, Long> {
}

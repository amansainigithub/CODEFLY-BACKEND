package com.coder.springjwt.repository.OtpReqResRepository;

import com.coder.springjwt.models.props.OtpRequestResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRequestResponseRepo extends JpaRepository<OtpRequestResponse,Long> {



}

package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.AuthorizationDetails;

public interface AuthorizationDetailsRepository extends JpaRepository<AuthorizationDetails,Long>{

}

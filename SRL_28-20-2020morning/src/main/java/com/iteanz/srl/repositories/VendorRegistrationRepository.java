package com.iteanz.srl.repositories;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.VendorRegistration;

@Repository
@Transactional
public interface VendorRegistrationRepository extends JpaRepository<VendorRegistration, Serializable> {

	public VendorRegistration findByLifnr(String lifnrId);
	
	

}

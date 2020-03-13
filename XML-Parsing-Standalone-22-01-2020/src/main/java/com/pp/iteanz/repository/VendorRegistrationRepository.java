package com.pp.iteanz.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pp.iteanz.entity.VendorRegistration;

@Repository
public interface VendorRegistrationRepository extends JpaRepository<VendorRegistration, Serializable> {

	VendorRegistration findByLifnr(Long l);

	
	
	
//	@Query(value = "SELECT * FROM Purchase_Order WHERE poNo=:poNo and iteamNo=:iteamNo",nativeQuery = true)
//	public PurchaseOrderMain findByPonoIteamno(@Param("poNo") long pono, @Param("iteamNo") String itemno);

}

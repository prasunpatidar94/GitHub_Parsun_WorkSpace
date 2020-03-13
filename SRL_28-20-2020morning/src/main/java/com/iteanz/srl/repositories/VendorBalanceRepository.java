package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.VendorBalance;

@Repository
public interface VendorBalanceRepository extends JpaRepository<VendorBalance,Long>{
	public VendorBalance getVendorBalanceByLifnr(String lifnr);
	//public VendorBalance getVendorBalanceNoListByLifnr(String lifnr);
}

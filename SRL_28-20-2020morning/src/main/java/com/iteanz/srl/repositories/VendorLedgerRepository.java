package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.VendorBalance;
import com.iteanz.srl.domain.VendorLedger;

@Repository
public interface VendorLedgerRepository extends JpaRepository<VendorLedger,Long>{
	
	public List<VendorLedger> getVendLedgByLifnr(String lifnr);

}

package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.iteanz.srl.domain.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice,Long>{
	public List<Invoice> getInvoicesByCurrentState(String currentState);
	
	//long asnNo
	public List<Invoice> findAllByAsnNoAndVendorNo(String asnNo,String vendorNo); 
	
	public List<Invoice> findByAsnNo(String asnNo);
	
	public List<Invoice> findByVendorNo(String vendorNo); 
	
	
}

package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.PurchaseOrder;
import com.iteanz.srl.domain.RFPHeader;

@Repository
public interface POHeaderRepository extends JpaRepository<PurchaseOrder,Long>{

	public List<PurchaseOrder> getPurchaseOrderByErnam(String ernam);
	public List<PurchaseOrder> getPurchaseOrderByLifnr(String lifnr);
	public PurchaseOrder getPurchaseOrderByEbeln(Long ebeln);
	
	public PurchaseOrder findByEbeln(long ebeln);
	
	//public List<PurchaseOrder> getPOBySessionid(String sessionid);
	
}

package com.iteanz.srl.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

	public List<PurchaseOrder> getPurchaseOrderByErnam(String ernam);
	
	
	
//	@Query(value = "SELECT * FROM Purchase_Order WHERE ebeln=:ebeln and ebelp=:ebelp",nativeQuery = true)
//	public PurchaseOrderMain findByPonoIteamno(@Param("poNo") long ebeln, @Param("iteamNo") String ebelp);

	PurchaseOrder findByEbeln(Long pONO);

}

package com.iteanz.srl.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.PurchaseRequisition;

@Repository
public interface PurchaseRequisitionRepository extends JpaRepository<PurchaseRequisition, Serializable>{

	public List<PurchaseRequisition> findByBanfn(long reqid);

	 @Query("FROM PurchaseRequisition where banfn=:banfn and matnr=:matnr and bnfpo=:bnfpo")
		public PurchaseRequisition findByBanfnAndMatnr(@Param("banfn") long banfn , @Param("matnr") String matnr ,@Param("bnfpo") String bnfpo);

	
	@Query("FROM PurchaseRequisition where banfn=:banfn and status=:status")
	public List<PurchaseRequisition>  findByBanfnAndStatus(@Param("banfn") long banfn,@Param("status") int status);

	//public List<PurchaseRequisition> findByBanfnAndBnfpo(long banfn, String bnfpo);
	
}



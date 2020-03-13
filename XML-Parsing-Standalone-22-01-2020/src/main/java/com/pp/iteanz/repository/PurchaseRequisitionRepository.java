package com.pp.iteanz.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pp.iteanz.entity.PurchaseRequisitionMain;

@Repository("PurchaseRequisitionRepository")
public interface PurchaseRequisitionRepository extends JpaRepository<PurchaseRequisitionMain, Integer> {

	

	@Transactional
	//@Query("FROM PurchaseRequisition where prno=:prno and itemno=:itemno")
	@Query(value = "SELECT * FROM Purchase_Requisition WHERE banfn=:banfn and bnfpo=:bnfpo",nativeQuery = true)
	public PurchaseRequisitionMain findByPrnoIteamno(@Param("banfn") long prno, @Param("bnfpo") String itemno);

	
	
	
//	@Transactional
//	//@Query("FROM PurchaseRequisition where prno=:prno and itemno=:itemno")
//	@Query(value = "SELECT * FROM Purchase_Requisition WHERE prno=:prno and itemno=:itemno",nativeQuery = true)
//	public PurchaseRequisitionMain findByPrnoIteamno(@Param("prno") long prno, @Param("itemno") String itemno);

	
	
	
//	Object findByPRNO(Long long1);

}
//@Repository
//public interface PasswordPolicyRepository extends JpaRepository<PasswordPolicy, Serializable> {
//	

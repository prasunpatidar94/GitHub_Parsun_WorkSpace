package com.pp.iteanz.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pp.iteanz.entity.POItemDetails;

@Repository
public interface POItemDetailsRepository extends JpaRepository<POItemDetails, Serializable> {

	 @Query(value = " FROM POItemDetails where ebelp=:ebelp and  matnr=:matnr")
	public POItemDetails findbyEbelpAndMatnr(@Param("ebelp") long ebelp, @Param("matnr") String matnr);

//	 @Query("FROM ApprovalLog where appid=:appid and  aprv=:aprv and reqNo=:reqNo and mailstatus=:mailstatus")
//		public List<ApprovalLog> getApprovalLogByAprvAndAppidAndReqnoAndMailStatus(
//				@Param("appid") WorkflowConfig appid, @Param("aprv") String userIdNo, @Param("reqNo") long reqNo,
//			/* @Param("status") Status status, */ @Param("mailstatus") int mailstatus);

	

}

package com.iteanz.srl.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.ApprovalLog;
import com.iteanz.srl.domain.Status;
import com.iteanz.srl.domain.WorkflowConfig;

@Repository
public interface ApprovalLogRepo extends JpaRepository<ApprovalLog, Serializable> {
//	@Transactional
//	@Query(value="from ApprovalLog where appId.id=:appId and reqNo=:reqId and aprv=:approver" )
//	public ApprovalLog findByAppIdReqIdApprovelStatus(String appId, long reqId, String approver);
//
//	public WorkflowConfig findByAppId(String appId);

	@Query("FROM ApprovalLog where reqNo=:reqNo")
	public List<ApprovalLog> getApprovalLogListByReqid(@Param("reqNo") long reqNo);

	@Query("FROM ApprovalLog where reqNo=:reqNo and aprv=:aprv")
	public ApprovalLog getApprovalLogByReqNoAndAprv(@Param("reqNo") long reqNo, @Param("aprv") long aprv);

	@Query("FROM ApprovalLog where reqNo=:reqNo and mailstatus=:mailstatus and aprv=:aprv")
	public ApprovalLog getApprovalLogByReqNoAndMailStatusaAndAprv(@Param("reqNo") long reqNo,
			@Param("mailstatus") int mailstatus, @Param("aprv") String aprv);

//	public List<ApprovalLog> findByAprv(String lifnr);

//	@Query("FROM ApprovalLog where aprv=:aprv and status=:status")
//	public List<ApprovalLog> findByAprvAndStatus(@Param("aprv") String aprv ,@Param("status") int status);

	@Query("FROM ApprovalLog where aprv=:aprv and appid=:appid")
	public List<ApprovalLog> findByAprvAndAppid(@Param("aprv") String aprv, @Param("appid") WorkflowConfig appid);

	@Query("FROM ApprovalLog where appid=:appid and subProcess=:subProcess and reqNo=:reqNo")
	public List<ApprovalLog> findByAppidAndSubprocessAndReqid(@Param("appid") WorkflowConfig appid,
			@Param("subProcess") String subProcess, @Param("reqNo") Long reqNo);

	@Query("FROM ApprovalLog where appid=:appid and subProcess=:subProcess and reqNo=:reqNo and aprv=:aprv")
	public ApprovalLog findByAppidAndSubprocessAndReqidAndAprv(@Param("appid") WorkflowConfig appid,
			@Param("subProcess") String subProcess, @Param("reqNo") long reqNo, @Param("aprv") String aprv);

	@Query("FROM ApprovalLog where appid=:appid and subProcess=:subProcess and reqNo=:reqNo and mailstatus=:mailstatus and status=:status")
	public List<ApprovalLog> findByAppidAndSubprocessAndReqidAndMailstatusAndStatus(
			@Param("appid") WorkflowConfig appid, @Param("subProcess") String subProcess, @Param("reqNo") Long reqNo,
			@Param("mailstatus") int mailstatus, @Param("status") Status status);

	@Query("FROM ApprovalLog where appid=:appid and subProcess=:subProcess and reqNo=:reqNo and mailstatus=:mailstatus and status=:status and aprv=:aprv")
	public List<ApprovalLog> findByAppidAndSubprocessAndReqidAndMailstatusAndStatusAndAprv(
			@Param("appid") WorkflowConfig appid, @Param("subProcess") String subProcess, @Param("reqNo") Long reqNo,
			@Param("mailstatus") int mailstatus, @Param("status") Status status, @Param("aprv") String aprv);

	
	
	@Query("FROM ApprovalLog where aprv=:aprv and mailstatus=:mailstatus")
	public List<ApprovalLog> getApprovalLogByAprvAndMailstatus(@Param("aprv") String aprv, @Param("mailstatus") int mailstatus);

//
//	public static ApprovalLog getApprovalLogByReqNoAndAprv(long reqid, long appr) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	

}

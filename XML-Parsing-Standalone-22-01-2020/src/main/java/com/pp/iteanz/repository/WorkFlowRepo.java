package com.pp.iteanz.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pp.iteanz.entity.Status;
import com.pp.iteanz.entity.WorkflowConfig;

@Repository
public interface WorkFlowRepo extends JpaRepository<WorkflowConfig, Serializable> {

//	@Query("FROM WorkflowConfig where appid=:appid and reqNo=:reqNo")
//	WorkflowConfig findByAppidAndReqno(@Param("appid") String appid, @Param("reqNo") long reqNo);
//
//	 @Query("FROM ApprovalLog where appid=:appid and  aprv=:aprv and reqNo=:reqNo")
//		public List<ApprovalLog> getApprovalLogByAprvAndAppidAndReqno(@Param("appid") WorkflowConfig appid, @Param("aprv") long aprv, @Param("reqNo") long reqNo);

	WorkflowConfig findByAppid(String appid);

	@Query("FROM WorkflowConfig where appid=:appid and status=:status")
	public WorkflowConfig findByAppidAndStatus(@Param("appid") String appid, @Param("status")Status status);



}

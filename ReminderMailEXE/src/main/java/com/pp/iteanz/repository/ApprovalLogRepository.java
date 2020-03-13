package com.pp.iteanz.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pp.iteanz.entity.ApprovalLog;

@Repository
public interface ApprovalLogRepository extends JpaRepository<ApprovalLog, Serializable> {

	public List<ApprovalLog> findByStatus(int status);

}

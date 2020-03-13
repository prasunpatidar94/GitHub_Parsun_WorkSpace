package com.pp.iteanz.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pp.iteanz.entity.ApprovalMatrix;

public interface ApprovalMatrixRepository extends JpaRepository<ApprovalMatrix, Serializable> {

	public ApprovalMatrix findByPlant(String plant);
	
	

}

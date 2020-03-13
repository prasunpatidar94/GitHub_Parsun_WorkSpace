package com.iteanz.srl.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.ApprovalMatrix;

@Repository
public interface ApprovalMatrixRepository extends JpaRepository<ApprovalMatrix, Serializable> {


	public ApprovalMatrix findByPlant(String plant);

	@Query("FROM ApprovalMatrix where plant=:plant and lgort=:lgort")
	public ApprovalMatrix findByPlantAndLgort(@Param("plant") String plant, @Param("lgort") String lgort);

}

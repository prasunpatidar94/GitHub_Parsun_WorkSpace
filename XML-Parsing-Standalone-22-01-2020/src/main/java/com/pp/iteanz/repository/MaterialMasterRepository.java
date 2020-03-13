package com.pp.iteanz.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pp.iteanz.entity.MaterialMaster;

@Repository
public interface MaterialMasterRepository extends JpaRepository<MaterialMaster, Serializable> {

	//public MaterialMaster findByMatnr(String matnr);
	
	
	@Query("FROM MaterialMaster where matnr=:matnr and werks=:werks")
	public MaterialMaster findByMatnrAndWerks(@Param("matnr") String matnr, @Param("werks") String werks);

	public MaterialMaster findByMatnr(String tagValue);

	

}

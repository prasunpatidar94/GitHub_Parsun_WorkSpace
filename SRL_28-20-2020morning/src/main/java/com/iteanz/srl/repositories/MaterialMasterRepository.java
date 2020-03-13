package com.iteanz.srl.repositories;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.MaterialMaster;

@Repository
@Transactional
public interface MaterialMasterRepository extends JpaRepository<MaterialMaster, Serializable>{

	

	@Query("FROM MaterialMaster where matnr=:matnr and werks=:werks")
	public MaterialMaster findbyMatnrAndWerks(@Param("matnr") String matnr , @Param("werks") String werks);

//	@Param("matnr") String matnr , @Param("werks") String werks
	
	
}

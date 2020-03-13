package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.StockMaster;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface StockMasterRepository extends JpaRepository<StockMaster,Long>{
	public StockMaster getStockMasterByMaterialNo(String materialNo);
}

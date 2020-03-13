package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.StockTransfer;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface StockTransferRepository extends JpaRepository<StockTransfer,Long>{
	public List<StockTransfer> getStockTransfersByCurrentState(String currentState);
}

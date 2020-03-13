package com.iteanz.srl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.StockTransferReceipt;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface StockTransferReceiptRepository extends JpaRepository<StockTransferReceipt,Long>{
	public List<StockTransferReceipt> getStockTransferReceiptsByCurrentState(String currentState);
}

package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.StockTransferReceiptItem;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface StockTransferReceiptItemRepository extends JpaRepository<StockTransferReceiptItem,Long>{
}

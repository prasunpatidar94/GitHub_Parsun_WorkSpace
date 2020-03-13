package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.StockTransferItem;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface StockTransferItemRepository extends JpaRepository<StockTransferItem,Long>{

}

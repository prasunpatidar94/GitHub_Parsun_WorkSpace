package com.iteanz.srl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.StockReturnItem;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface StockReturnItemRepository extends JpaRepository<StockReturnItem,Long>{

}

package com.iteanz.srl.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.StockReturn;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface StockReturnRepository extends JpaRepository<StockReturn,Long>{
	public List<StockReturn> getStockReturnsByCurrentState(String currentState);
}

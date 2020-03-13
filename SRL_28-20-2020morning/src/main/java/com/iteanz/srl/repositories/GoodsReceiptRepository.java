package com.iteanz.srl.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iteanz.srl.domain.GoodsReceipt;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface GoodsReceiptRepository extends JpaRepository<GoodsReceipt,Long>{
	public List<GoodsReceipt> getGoodsReceiptsByCurrentState(String currentState);
}

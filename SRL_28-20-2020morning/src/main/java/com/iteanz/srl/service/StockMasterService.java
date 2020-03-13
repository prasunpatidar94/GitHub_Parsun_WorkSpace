package com.iteanz.srl.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteanz.srl.domain.StockMaster;
import com.iteanz.srl.repositories.StockMasterRepository;

/*
CurrentState :
1 = Created(Draft)
2 = Created(Pending)
3 = Approved
4 = Reject
5 = Back to Connection
*/

@Service
public class StockMasterService {
	
	@Autowired
	private StockMasterRepository stockMasterRepository;
	
	public List<StockMaster> getAllStockMaster() throws SQLException{
		
		List<StockMaster> stockMasters = stockMasterRepository.findAll();
		return stockMasters;
	}

	public void updateStockMaster() {
		
		System.out.println("update in stock table");
	}

	public void addStockMasterInfo(String materialNo,String materialDesc,String qty) {
		StockMaster stockMaster = stockMasterRepository.getStockMasterByMaterialNo(materialNo);
		if (stockMaster == null ) {
			stockMaster = new StockMaster();
			stockMaster.setMaterialNo(materialNo);
			stockMaster.setMaterialDescription(materialDesc);
			stockMaster.setQuantity(Integer.valueOf(qty));
		}else {
			int oldQty =  stockMaster.getQuantity();
			stockMaster.setQuantity(oldQty + Integer.valueOf(qty));
		}
		stockMasterRepository.save(stockMaster);
	}

	public void removeStockMasterInfo(String materialNo, String returnQuantity) {
		StockMaster stockMaster = stockMasterRepository.getStockMasterByMaterialNo(materialNo);
		if (stockMaster != null ) {
			int oldQty =  stockMaster.getQuantity();
			stockMaster.setQuantity(oldQty - Integer.valueOf(returnQuantity));
			stockMasterRepository.save(stockMaster);
		}
	}
	
}

package com.iteanz.srl.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteanz.srl.domain.StockTransfer;
import com.iteanz.srl.repositories.StockTransferRepository;

/*
CurrentState :
1 = Created(Draft)
2 = Created(Pending)
3 = Approved
4 = Reject
5 = Back to Connection
*/

@Service
public class StockTransferService {
	
	@Autowired
	private StockTransferRepository stockTransferRepository;
	
	public List<StockTransfer> getAllStockTransfer() throws SQLException{
		
		List<StockTransfer> stockTransfers = stockTransferRepository.findAll();
		return stockTransfers;
	}

	public HashMap<String, Object> addStockTransfer(StockTransfer stockTransfer) throws SerialException, SQLException {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		// Add number range id here
		//stockTransfer.setCurrentState("1");
		stockTransfer = stockTransferRepository.save(stockTransfer);
		if(stockTransfer.getStockTransferId() > 0) {
			map.put("Status", "Successfull");
			map.put("StockTransferId", stockTransfer.getStockTransferId());
			
		}else {
			map.put("Status", "Failed");
			map.put("StockTransferId", 0);		
		}
		return map;
	}
	
	public HashMap<String, Object> updateStockTransfer(StockTransfer stockTransfer) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		//stockTransfer.setCurrentState("1");
		stockTransfer = stockTransferRepository.save(stockTransfer);
		if(stockTransfer.getStockTransferId() > 0) {
			map.put("Status", "Successfull");
			map.put("StockTransferId", stockTransfer.getStockTransferId());
			
		}else {
			map.put("Status", "Failed");
			map.put("StockTransferId", 0);		
		}
		return map;
	}

	public StockTransfer getStockTransfer(long stockTransferId) throws SQLException {
		
		StockTransfer stockTransfer = stockTransferRepository.getOne(stockTransferId);	
		return stockTransfer;	
	}

	public List<HashMap<String, Object>> getMetaData() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<StockTransfer> stockTransfers = stockTransferRepository.findAll();
		for(StockTransfer stockTransfer : stockTransfers) {			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("StockTransferId",stockTransfer.getStockTransferId());
			map.put("CurrentState",stockTransfer.getCurrentState());
			map.put("Status",stockTransfer.getStatus());
			metaDataList.add(map);
		}
		return metaDataList;
	}
	
	public List<StockTransfer> getStockTransferByCurrentState(String currentState) throws SerialException, SQLException {
		List<StockTransfer> stockTransfers = stockTransferRepository.getStockTransfersByCurrentState(currentState);
		return stockTransfers;
	}
	
}

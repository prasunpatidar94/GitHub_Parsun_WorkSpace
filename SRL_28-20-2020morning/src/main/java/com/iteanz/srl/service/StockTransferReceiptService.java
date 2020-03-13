package com.iteanz.srl.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteanz.srl.domain.StockTransferReceipt;
import com.iteanz.srl.repositories.StockTransferReceiptRepository;

/*
CurrentState :
1 = Created(Draft)
2 = Created(Pending)
3 = Approved
4 = Reject
5 = Back to Connection
*/

@Service
public class StockTransferReceiptService {
	
	@Autowired
	private StockTransferReceiptRepository stockTransferReceiptRepository;
	
	public List<StockTransferReceipt> getAllStockTransferReceipt() throws SQLException{
		
		List<StockTransferReceipt> stockTransferReceipts = stockTransferReceiptRepository.findAll();
		return stockTransferReceipts;
	}

	public HashMap<String, Object> addStockTransferReceipt(StockTransferReceipt stockTransferReceipt) throws SerialException, SQLException {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		// Add number range id here
		//stockTransferReceipt.setCurrentState("1");
		stockTransferReceipt = stockTransferReceiptRepository.save(stockTransferReceipt);
		if(stockTransferReceipt.getStockTransferReceiptId() > 0) {
			map.put("Status", "Successfull");
			map.put("StockTransferReceiptId", stockTransferReceipt.getStockTransferReceiptId());
			
		}else {
			map.put("Status", "Failed");
			map.put("StockTransferReceiptId", 0);		
		}
		return map;
	}
	
	public HashMap<String, Object> updateStockTransferReceipt(StockTransferReceipt stockTransferReceipt) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		//stockTransferReceipt.setCurrentState("1");
		stockTransferReceipt = stockTransferReceiptRepository.save(stockTransferReceipt);
		if(stockTransferReceipt.getStockTransferReceiptId() > 0) {
			map.put("Status", "Successfull");
			map.put("StockTransferReceiptId", stockTransferReceipt.getStockTransferReceiptId());
			
		}else {
			map.put("Status", "Failed");
			map.put("StockTransferReceiptId", 0);		
		}
		return map;
	}

	public StockTransferReceipt getStockTransferReceipt(long stockTransferReceiptId) throws SQLException {
		
		StockTransferReceipt stockTransferReceipt = stockTransferReceiptRepository.getOne(stockTransferReceiptId);	
		return stockTransferReceipt;	
	}

	public List<HashMap<String, Object>> getMetaData() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<StockTransferReceipt> stockTransferReceipts = stockTransferReceiptRepository.findAll();
		for(StockTransferReceipt stockTransferReceipt : stockTransferReceipts) {			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("StockTransferReceiptId",stockTransferReceipt.getStockTransferReceiptId());
			map.put("CurrentState",stockTransferReceipt.getCurrentState());
			map.put("Status",stockTransferReceipt.getStatus());
			metaDataList.add(map);
		}
		return metaDataList;
	}
	
	public List<StockTransferReceipt> getStockTransferReceiptByCurrentState(String currentState) throws SerialException, SQLException {
		List<StockTransferReceipt> stockTransferReceipts = stockTransferReceiptRepository.getStockTransferReceiptsByCurrentState(currentState);
		return stockTransferReceipts;
	}
	
}

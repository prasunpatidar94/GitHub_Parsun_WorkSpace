package com.iteanz.srl.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteanz.srl.domain.StockReturn;
import com.iteanz.srl.repositories.StockReturnRepository;

/*
CurrentState :
1 = Created(Draft)
2 = Created(Pending)
3 = Approved
4 = Reject
5 = Back to Connection
*/

@Service
public class StockReturnService {
	
	@Autowired
	private StockReturnRepository StockReturnRepository;
	
	public List<StockReturn> getAllStockReturn() throws SQLException{
		
		List<StockReturn> stockReturns = StockReturnRepository.findAll();
		return stockReturns;
	}

	public HashMap<String, Object> addStockReturn(StockReturn stockReturn) throws SerialException, SQLException {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		// Add number range id here
		//StockReturn.setCurrentState("1");
		stockReturn = StockReturnRepository.save(stockReturn);
		if(stockReturn.getStockReturnId() > 0) {
			map.put("Status", "Successfull");
			map.put("StockReturnId", stockReturn.getStockReturnId());
			
		}else {
			map.put("Status", "Failed");
			map.put("StockReturnId", 0);		
		}
		return map;
	}
	
	public HashMap<String, Object> updateStockReturn(StockReturn stockReturn) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		//StockReturn.setCurrentState("1");
		stockReturn = StockReturnRepository.save(stockReturn);
		if(stockReturn.getStockReturnId() > 0) {
			map.put("Status", "Successfull");
			map.put("StockReturnId", stockReturn.getStockReturnId());
			
		}else {
			map.put("Status", "Failed");
			map.put("StockReturnId", 0);		
		}
		return map;
	}

	public StockReturn getStockReturn(long stockReturnId) throws SQLException {
		
		StockReturn stockReturn = StockReturnRepository.getOne(stockReturnId);	
		return stockReturn;	
	}

	public List<HashMap<String, Object>> getMetaData() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<StockReturn> stockReturns = StockReturnRepository.findAll();
		for(StockReturn stockReturn : stockReturns) {			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("StockReturnId",stockReturn.getStockReturnId());
			map.put("CurrentState",stockReturn.getCurrentState());
			map.put("Status",stockReturn.getStatus());
			metaDataList.add(map);
		}
		return metaDataList;
	}
	
	public List<StockReturn> getStockReturnByCurrentState(String currentState) throws SerialException, SQLException {
		List<StockReturn> stockReturns = StockReturnRepository.getStockReturnsByCurrentState(currentState);
		return stockReturns;
	}
	
}

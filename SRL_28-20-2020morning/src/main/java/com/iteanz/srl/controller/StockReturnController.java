package com.iteanz.srl.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iteanz.srl.domain.StockReturn;
import com.iteanz.srl.service.StockReturnService;

@RestController
public class StockReturnController {
	

	@Autowired
	StockReturnService stockReturnService;
	
	@GetMapping("/logistics/stockreturn")
	@CrossOrigin("*")
	public List<StockReturn> getStockReturns() throws SQLException {
		return stockReturnService.getAllStockReturn();
	}
	
	@GetMapping("/logistics/stockreturn/metadata")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getStockReturnBasicDetails() throws SQLException {
		return stockReturnService.getMetaData();
	}
	
	@GetMapping(value="/logistics/stockreturn/{stockReturnId}" , produces=MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public StockReturn getStockReturn(@PathVariable long stockReturnId) throws SQLException {
		return stockReturnService.getStockReturn(stockReturnId);	
	}
	
	@PostMapping("/logistics/stockreturn")
	@CrossOrigin("*")
	public HashMap<String, Object> addStockReturn(@RequestBody StockReturn stockReturn) throws SerialException, SQLException  {
				return stockReturnService.addStockReturn(stockReturn);
	}
	
	@PutMapping("/logistics/stockreturn")
	@CrossOrigin("*")
	public HashMap<String, Object> updateStockReturn(@RequestBody StockReturn stockReturn) throws SerialException, SQLException {
		return stockReturnService.updateStockReturn(stockReturn);
		
	}

}

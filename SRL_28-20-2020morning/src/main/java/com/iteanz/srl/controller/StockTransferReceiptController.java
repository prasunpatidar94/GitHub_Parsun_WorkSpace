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

import com.iteanz.srl.domain.StockTransferReceipt;
import com.iteanz.srl.service.StockTransferReceiptService;

@RestController
public class StockTransferReceiptController {
	

	@Autowired
	StockTransferReceiptService stockTransferReceiptService;
	
	@GetMapping("/logistics/stocktransferreceipt")
	@CrossOrigin("*")
	public List<StockTransferReceipt> getStockTransferReceipts() throws SQLException {
		return stockTransferReceiptService.getAllStockTransferReceipt();
	}
	
	@GetMapping("/logistics/stocktransferreceipt/metadata")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getStockTransferReceiptBasicDetails() throws SQLException {
		return stockTransferReceiptService.getMetaData();
	}
	
	@GetMapping(value="/logistics/stocktransferreceipt/{stockTransferReceiptId}" , produces=MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public StockTransferReceipt getStockTransferReceipt(@PathVariable long stockTransferReceiptId) throws SQLException {
		return stockTransferReceiptService.getStockTransferReceipt(stockTransferReceiptId);	
	}
	
	@PostMapping("/logistics/stocktransferreceipt")
	@CrossOrigin("*")
	public HashMap<String, Object> addStockTransferReceipt(@RequestBody StockTransferReceipt stockTransferReceipt) throws SerialException, SQLException  {
				return stockTransferReceiptService.addStockTransferReceipt(stockTransferReceipt);
	}
	
	@PutMapping("/logistics/stocktransferreceipt")
	@CrossOrigin("*")
	public HashMap<String, Object> updateStockTransferReceipt(@RequestBody StockTransferReceipt stockTransferReceipt) throws SerialException, SQLException {
		return stockTransferReceiptService.updateStockTransferReceipt(stockTransferReceipt);
		
	}

}

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

import com.iteanz.srl.domain.StockTransfer;
import com.iteanz.srl.service.StockTransferService;

@RestController
public class StockTransferController {
	

	@Autowired
	StockTransferService stockTransferService;
	
	@GetMapping("/logistics/stocktransfer")
	@CrossOrigin("*")
	public List<StockTransfer> getStockTransfers() throws SQLException {
		return stockTransferService.getAllStockTransfer();
	}
	
	@GetMapping("/logistics/stocktransfer/metadata")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getStockTransferBasicDetails() throws SQLException {
		return stockTransferService.getMetaData();
	}
	
	@GetMapping(value="/logistics/stocktransfer/{stockTransferId}" , produces=MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public StockTransfer getStockTransfer(@PathVariable long stockTransferId) throws SQLException {
		return stockTransferService.getStockTransfer(stockTransferId);	
	}
	
	@PostMapping("/logistics/stocktransfer")
	@CrossOrigin("*")
	public HashMap<String, Object> addStockTransfer(@RequestBody StockTransfer stockTransfer) throws SerialException, SQLException  {
				return stockTransferService.addStockTransfer(stockTransfer);
	}
	
	@PutMapping("/logistics/stocktransfer")
	@CrossOrigin("*")
	public HashMap<String, Object> updateStockTransfer(@RequestBody StockTransfer stockTransfer) throws SerialException, SQLException {
		return stockTransferService.updateStockTransfer(stockTransfer);
		
	}

}

package com.iteanz.srl.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iteanz.srl.domain.StockMaster;
import com.iteanz.srl.service.StockMasterService;

@RestController
public class StockMasterController {
	

	@Autowired
	private StockMasterService stockMasterService;
	
	@GetMapping("/logistics/stockmaster")
	@CrossOrigin("*")
	public List<StockMaster> getGoodsReceipts() throws SQLException {
		return stockMasterService.getAllStockMaster();
	}
	
}

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

import com.iteanz.srl.domain.GoodsReceipt;
import com.iteanz.srl.service.GoodsReceiptService;

@RestController
public class GoodsReceiptController {
	

	@Autowired
	GoodsReceiptService goodsReceiptService;
	
	@GetMapping("/logistics/goodsreceipt")
	@CrossOrigin("*")
	public List<GoodsReceipt> getGoodsReceipts() throws SQLException {
		return goodsReceiptService.getAllGoodsReceipt();
	}
	
	@GetMapping("/logistics/goodsreceipt/metadata")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getGoodsReceiptBasicDetails() throws SQLException {
		return goodsReceiptService.getMetaData();
	}
	
	@GetMapping(value="/logistics/goodsreceipt/{goodsReceiptId}" , produces=MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public GoodsReceipt getGoodsReceipt(@PathVariable long goodsReceiptId) throws SQLException {
		return goodsReceiptService.getGoodsReceipt(goodsReceiptId);	
	}
	
	@PostMapping("/logistics/goodsreceipt")
	@CrossOrigin("*")
	public HashMap<String, Object> addGoodsReceipt(@RequestBody GoodsReceipt goodsReceipt) throws SerialException, SQLException  {
				return goodsReceiptService.addGoodsReceipt(goodsReceipt);
	}
	
	@PutMapping("/logistics/goodsreceipt")
	@CrossOrigin("*")
	public HashMap<String, Object> updateGoodsReceipt(@RequestBody GoodsReceipt goodsReceipt) throws SerialException, SQLException {
		return goodsReceiptService.updateGoodsReceipt(goodsReceipt);
		
	}

}

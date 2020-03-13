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

import com.iteanz.srl.domain.GoodsIssue;
import com.iteanz.srl.service.GoodsIssueService;

@RestController
public class GoodsIssueController {
	

	@Autowired
	GoodsIssueService goodsIssueService;
	
	@GetMapping("/logistics/goodsissue")
	@CrossOrigin("*")
	public List<GoodsIssue> getGoodsIssues() throws SQLException {
		return goodsIssueService.getAllGoodsIssue();
	}
	
	@GetMapping("/logistics/goodsissue/metadata")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getGoodsIssueBasicDetails() throws SQLException {
		return goodsIssueService.getMetaData();
	}
	
	@GetMapping(value="/logistics/goodsissue/{goodsIssueId}" , produces=MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public GoodsIssue getGoodsIssue(@PathVariable long goodsIssueId) throws SQLException {
		return goodsIssueService.getGoodsIssue(goodsIssueId);	
	}
	
	@PostMapping("/logistics/goodsissue")
	@CrossOrigin("*")
	public HashMap<String, Object> addGoodsIssue(@RequestBody GoodsIssue goodsIssue) throws SerialException, SQLException  {
				return goodsIssueService.addGoodsIssue(goodsIssue);
	}
	
	@PutMapping("/logistics/goodsissue")
	@CrossOrigin("*")
	public HashMap<String, Object> updateGoodsIssue(@RequestBody GoodsIssue goodsIssue) throws SerialException, SQLException {
		return goodsIssueService.updateGoodsIssue(goodsIssue);
		
	}

}

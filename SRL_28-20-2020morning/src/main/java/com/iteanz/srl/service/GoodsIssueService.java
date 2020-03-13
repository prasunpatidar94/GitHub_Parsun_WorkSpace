package com.iteanz.srl.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteanz.srl.domain.GoodsIssue;
import com.iteanz.srl.domain.GoodsIssueItem;
import com.iteanz.srl.repositories.GoodsIssueRepository;

/*
CurrentState :
1 = Created(Draft)
2 = Created(Pending)
3 = Approved
4 = Reject
5 = Back to Connection
*/

@Service
public class GoodsIssueService {
	
	@Autowired
	private GoodsIssueRepository goodsIssueRepository;
	@Autowired
	private StockMasterService stockMasterService;
	
	public List<GoodsIssue> getAllGoodsIssue() throws SQLException{
		
		List<GoodsIssue> goodsIssues = goodsIssueRepository.findAll();
		return goodsIssues;
	}

	public HashMap<String, Object> addGoodsIssue(GoodsIssue goodsIssue) throws SerialException, SQLException {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		// Add number range id here
		//goodsIssue.setCurrentState("1");
		goodsIssue = goodsIssueRepository.save(goodsIssue);
		if(goodsIssue.getGoodsIssueId() > 0) {
			map.put("Status", "Successfull");
			map.put("GoodsIssueId", goodsIssue.getGoodsIssueId());
			if (goodsIssue.getCurrentState().equals("2")) {
				
			List<GoodsIssueItem> goodsIssueItems = goodsIssue.getGoodsIssueItems();
				for (GoodsIssueItem gii : goodsIssueItems) {
					stockMasterService.removeStockMasterInfo(gii.getMaterialNo(),gii.getReturnQuantity());
				}
			}
			
		}else {
			map.put("Status", "Failed");
			map.put("GoodsIssueId", 0);		
		}
		return map;
	}
	
	public HashMap<String, Object> updateGoodsIssue(GoodsIssue goodsIssue) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		//goodsIssue.setCurrentState("1");
		goodsIssue = goodsIssueRepository.save(goodsIssue);
		if(goodsIssue.getGoodsIssueId() > 0) {
			map.put("Status", "Successfull");
			map.put("GoodsIssueId", goodsIssue.getGoodsIssueId());
			
		}else {
			map.put("Status", "Failed");
			map.put("GoodsIssueId", 0);		
		}
		return map;
	}

	public GoodsIssue getGoodsIssue(long goodsIssueId) throws SQLException {
		
		GoodsIssue goodsIssue = goodsIssueRepository.getOne(goodsIssueId);	
		return goodsIssue;	
	}

	public List<HashMap<String, Object>> getMetaData() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<GoodsIssue> goodsIssues = goodsIssueRepository.findAll();
		for(GoodsIssue goodsIssue : goodsIssues) {			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("GoodsIssueId",goodsIssue.getGoodsIssueId());
			map.put("CurrentState",goodsIssue.getCurrentState());
			map.put("Status",goodsIssue.getStatus());
			metaDataList.add(map);
		}
		return metaDataList;
	}
	
	public List<GoodsIssue> getGoodsIssueByCurrentState(String currentState) throws SerialException, SQLException {
		List<GoodsIssue> goodsIssues = goodsIssueRepository.getGoodsIssuesByCurrentState(currentState);
		return goodsIssues;
	}
	
}

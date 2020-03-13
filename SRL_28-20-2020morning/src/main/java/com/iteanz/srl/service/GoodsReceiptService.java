package com.iteanz.srl.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteanz.srl.domain.GoodsReceipt;
import com.iteanz.srl.domain.GoodsReceiptItem;
import com.iteanz.srl.repositories.GoodsReceiptRepository;

/*
CurrentState :
1 = Created(Draft)
2 = Created(Pending)
3 = Approved
4 = Reject
5 = Back to Connection
*/

@Service
public class GoodsReceiptService {
	
	@Autowired
	private GoodsReceiptRepository goodsReceiptRepository;
	
	@Autowired
	private StockMasterService stockMasterService;
	
	public List<GoodsReceipt> getAllGoodsReceipt() throws SQLException{
		
		List<GoodsReceipt> goodsReceipts = goodsReceiptRepository.findAll();
		return goodsReceipts;
	}

	public HashMap<String, Object> addGoodsReceipt(GoodsReceipt goodsReceipt) throws SerialException, SQLException {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		// Add number range id here
		//GoodsReceipt.setCurrentState("1");
		goodsReceipt = goodsReceiptRepository.save(goodsReceipt);
		if(goodsReceipt.getGoodsReceiptId() > 0) {
			map.put("Status", "Successfull");
			map.put("GoodsReceiptId", goodsReceipt.getGoodsReceiptId());
			if (goodsReceipt.getCurrentState().equals("2")) {
				
			List<GoodsReceiptItem> goodsReceiptItems = goodsReceipt.getGoodsReceiptItems();
				for (GoodsReceiptItem gri : goodsReceiptItems) {
					stockMasterService.addStockMasterInfo(gri.getMaterialNo(),gri.getMaterialDescription(),gri.getOrderQuantity());
				}
			}
			
		}else {
			map.put("Status", "Failed");
			map.put("GoodsReceiptId", 0);		
		}
		return map;
	}
	
	public HashMap<String, Object> updateGoodsReceipt(GoodsReceipt goodsReceipt) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		//GoodsReceipt.setCurrentState("1");
		goodsReceipt = goodsReceiptRepository.save(goodsReceipt);
		if(goodsReceipt.getGoodsReceiptId() > 0) {
			map.put("Status", "Successfull");
			map.put("GoodsReceiptId", goodsReceipt.getGoodsReceiptId());
			
		}else {
			map.put("Status", "Failed");
			map.put("GoodsReceiptId", 0);		
		}
		return map;
	}

	public GoodsReceipt getGoodsReceipt(long goodsReceiptId) throws SQLException {
		
		GoodsReceipt goodsReceipt = goodsReceiptRepository.getOne(goodsReceiptId);	
		return goodsReceipt;	
	}

	public List<HashMap<String, Object>> getMetaData() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<GoodsReceipt> goodsReceipts = goodsReceiptRepository.findAll();
		for(GoodsReceipt goodsReceipt : goodsReceipts) {			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("GoodsReceiptId",goodsReceipt.getGoodsReceiptId());
			map.put("CurrentState",goodsReceipt.getCurrentState());
			metaDataList.add(map);
		}
		return metaDataList;
	}
	
	public List<GoodsReceipt> getGoodsReceiptByCurrentState(String currentState) throws SerialException, SQLException {
		List<GoodsReceipt> goodsReceipts = goodsReceiptRepository.getGoodsReceiptsByCurrentState(currentState);
		return goodsReceipts;
	}
	
}

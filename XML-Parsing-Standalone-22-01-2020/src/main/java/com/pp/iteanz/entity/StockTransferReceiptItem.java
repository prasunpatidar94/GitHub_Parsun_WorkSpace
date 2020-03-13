package com.pp.iteanz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StockTransferReceiptItem {
	
	@Id
	@GeneratedValue
	private long itemId;
	private String srNo;
	private String materialNo;
	private String materialDescription;
	private String orderQuantity;
	private String uom;
	private String supplyPlant;
	private String supplyStorage;
	private String deliveryDate;
	private String remarks;
	
	private String materialCode;
	private String dispatchMaterialDescription;
	private String batchNo;
	private String expairyDate;
	private String dispatchQuantity;
	private String storageType;
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public String getSrNo() {
		return srNo;
	}
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}
	public String getMaterialNo() {
		return materialNo;
	}
	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	public String getMaterialDescription() {
		return materialDescription;
	}
	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}
	public String getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(String orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getSupplyPlant() {
		return supplyPlant;
	}
	public void setSupplyPlant(String supplyPlant) {
		this.supplyPlant = supplyPlant;
	}
	public String getSupplyStorage() {
		return supplyStorage;
	}
	public void setSupplyStorage(String supplyStorage) {
		this.supplyStorage = supplyStorage;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getDispatchMaterialDescription() {
		return dispatchMaterialDescription;
	}
	public void setDispatchMaterialDescription(String dispatchMaterialDescription) {
		this.dispatchMaterialDescription = dispatchMaterialDescription;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getExpairyDate() {
		return expairyDate;
	}
	public void setExpairyDate(String expairyDate) {
		this.expairyDate = expairyDate;
	}
	public String getDispatchQuantity() {
		return dispatchQuantity;
	}
	public void setDispatchQuantity(String dispatchQuantity) {
		this.dispatchQuantity = dispatchQuantity;
	}
	public String getStorageType() {
		return storageType;
	}
	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
	
	

}

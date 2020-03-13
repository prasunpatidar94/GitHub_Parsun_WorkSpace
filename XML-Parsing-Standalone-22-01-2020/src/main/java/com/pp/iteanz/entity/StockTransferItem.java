package com.pp.iteanz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StockTransferItem {
	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "StockTransferItem_seq", allocationSize = 1, name = "StockTransferItem_s")
	@Column(name = "itemId")
	private Long itemId;
	private String srNo;
	private String materialNo;
	private String materialDescription;
	private String orderQuantity;
	private String uom;
	private String supplyPlant;
	private String supplyStorage;
	private String deliveryDate;
	private String remarks;
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
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
	
	
}

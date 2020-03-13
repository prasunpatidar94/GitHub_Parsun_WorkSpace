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
public class StockMaster {
	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "StockMaster_seq", allocationSize = 1, name = "StockMaster_s")
	@Column(name = "itemId")
	private Long itemId;
	private String materialNo;
	private String materialDescription;
	private String batchNo;
	private String expairyDate;
	private int quantity;
	private String storageType;
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getStorageType() {
		return storageType;
	}
	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
	
	
	
}

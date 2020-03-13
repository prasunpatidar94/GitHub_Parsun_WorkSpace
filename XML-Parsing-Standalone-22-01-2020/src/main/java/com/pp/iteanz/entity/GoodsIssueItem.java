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
public class GoodsIssueItem {
	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "goodsissueitem_seq", allocationSize = 1, name = "goodsissueitem_s")
	@Column(name = "itemId")
	private Long itemId;
	private String srNo;
	private String materialNo;
	private String materialDescription;
	private String returnQuantity;
	private String uom;
	private String receivingPlant;
	private String receivingStorage;
	private String returnDate;
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
	public String getReturnQuantity() {
		return returnQuantity;
	}
	public void setReturnQuantity(String returnQuantity) {
		this.returnQuantity = returnQuantity;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getReceivingPlant() {
		return receivingPlant;
	}
	public void setReceivingPlant(String receivingPlant) {
		this.receivingPlant = receivingPlant;
	}
	public String getReceivingStorage() {
		return receivingStorage;
	}
	public void setReceivingStorage(String receivingStorage) {
		this.receivingStorage = receivingStorage;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	

}

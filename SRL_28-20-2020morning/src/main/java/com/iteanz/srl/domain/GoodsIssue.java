package com.iteanz.srl.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Proxy(lazy = false)
public class GoodsIssue {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "goodsissue_seq", allocationSize = 1, name = "goodsissue_s")
	@Column(name = "goodsIssueId")
	private Long goodsIssueId;
	private String plant;
	private String stnReferenceNo;
	private String warehouseNo;
	private String storageType;
	private String deliveringStorageLocation;
	private String requestDate;
	private String requestDescription;
	private String status;
	private String currentState;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = GoodsIssueItem.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "goodsIssueId", referencedColumnName = "goodsIssueId")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<GoodsIssueItem> goodsIssueItems;

	public Long getGoodsIssueId() {
		return goodsIssueId;
	}

	public void setGoodsIssueId(Long goodsIssueId) {
		this.goodsIssueId = goodsIssueId;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getStnReferenceNo() {
		return stnReferenceNo;
	}

	public void setStnReferenceNo(String stnReferenceNo) {
		this.stnReferenceNo = stnReferenceNo;
	}

	public String getWarehouseNo() {
		return warehouseNo;
	}

	public void setWarehouseNo(String warehouseNo) {
		this.warehouseNo = warehouseNo;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getDeliveringStorageLocation() {
		return deliveringStorageLocation;
	}

	public void setDeliveringStorageLocation(String deliveringStorageLocation) {
		this.deliveringStorageLocation = deliveringStorageLocation;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getRequestDescription() {
		return requestDescription;
	}

	public void setRequestDescription(String requestDescription) {
		this.requestDescription = requestDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public List<GoodsIssueItem> getGoodsIssueItems() {
		return goodsIssueItems;
	}

	public void setGoodsIssueItems(List<GoodsIssueItem> goodsIssueItems) {
		this.goodsIssueItems = goodsIssueItems;
	}

	
}

package com.pp.iteanz.entity;

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
public class StockTransferReceipt {
	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "StockTransferReceipt_seq", allocationSize = 1, name = "StockTransferReceipt_s")
	@Column(name = "stockTransferReceiptId")
	private Long stockTransferReceiptId;
	private String plant;
	private String warehouseNo;
	private String deliveringStorageLocation;
	private String requestDate;
	private String requestDescription;
	private String status;
	private String currentState;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = StockTransferReceiptItem.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "stockTransferReceiptId", referencedColumnName = "stockTransferReceiptId")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<StockTransferReceiptItem> stockTransferReceiptItems;

	public Long getStockTransferReceiptId() {
		return stockTransferReceiptId;
	}

	public void setStockTransferReceiptId(Long stockTransferReceiptId) {
		this.stockTransferReceiptId = stockTransferReceiptId;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getWarehouseNo() {
		return warehouseNo;
	}

	public void setWarehouseNo(String warehouseNo) {
		this.warehouseNo = warehouseNo;
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

	public List<StockTransferReceiptItem> getStockTransferReceiptItems() {
		return stockTransferReceiptItems;
	}

	public void setStockTransferReceiptItems(List<StockTransferReceiptItem> stockTransferReceiptItems) {
		this.stockTransferReceiptItems = stockTransferReceiptItems;
	}

	
	

}

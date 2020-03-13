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
public class GoodsReceipt {
	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "goodsreceipt_seq", allocationSize = 1, name = "goodsreceipt_s")
	@Column(name = "goodsReceiptId")
	private Long goodsReceiptId;
	private String purchaseOrderNo;
	private String vendorCode;
	private String vendorName;
	private String postingDate;
	private String currentState;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = GoodsReceiptItem.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "goodsReceiptId", referencedColumnName = "goodsReceiptId")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<GoodsReceiptItem> goodsReceiptItems;

	public Long getGoodsReceiptId() {
		return goodsReceiptId;
	}

	public void setGoodsReceiptId(Long goodsReceiptId) {
		this.goodsReceiptId = goodsReceiptId;
	}

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public List<GoodsReceiptItem> getGoodsReceiptItems() {
		return goodsReceiptItems;
	}

	public void setGoodsReceiptItems(List<GoodsReceiptItem> goodsReceiptItems) {
		this.goodsReceiptItems = goodsReceiptItems;
	}

	
}

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
public class InvoiceItem {

	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "invoiceitem_seq", allocationSize = 1, name = "invoiceitem_seq")
	@Column(name = "itemId")
	private Long itemId;
	private String itemNo;
	private String matnr;//material
	private String maktx;//materialDescription
	private String menge;//quantity
	private String totalAmount;
	private String taxAmount;
	private String waers;//currency
	private String status;
	
	private String sgst;
	private String cgst;
	private String igst;
	
	private String bwart;
	private String ebeln; // long ebeln
	private String ebelp; //long ebelp
	private String grn;
	private float menge1; 
	private String pstyp;
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	public String getMenge() {
		return menge;
	}
	public void setMenge(String menge) {
		this.menge = menge;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getWaers() {
		return waers;
	}
	public void setWaers(String waers) {
		this.waers = waers;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSgst() {
		return sgst;
	}
	public void setSgst(String sgst) {
		this.sgst = sgst;
	}
	public String getCgst() {
		return cgst;
	}
	public void setCgst(String cgst) {
		this.cgst = cgst;
	}
	public String getIgst() {
		return igst;
	}
	public void setIgst(String igst) {
		this.igst = igst;
	}
	public String getBwart() {
		return bwart;
	}
	public void setBwart(String bwart) {
		this.bwart = bwart;
	}
	public String getEbeln() {
		return ebeln;
	}
	public void setEbeln(String ebeln) {
		this.ebeln = ebeln;
	}
	public String getEbelp() {
		return ebelp;
	}
	public void setEbelp(String ebelp) {
		this.ebelp = ebelp;
	}
	public String getGrn() {
		return grn;
	}
	public void setGrn(String grn) {
		this.grn = grn;
	}
	public float getMenge1() {
		return menge1;
	}
	public void setMenge1(float menge1) {
		this.menge1 = menge1;
	}
	public String getPstyp() {
		return pstyp;
	}
	public void setPstyp(String pstyp) {
		this.pstyp = pstyp;
	}
	
	
	

}

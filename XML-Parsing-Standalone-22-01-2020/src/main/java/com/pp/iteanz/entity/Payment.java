package com.pp.iteanz.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@EnableTransactionManagement
//@Transactional
@Proxy(lazy = false)
public class Payment {
	@Id
	private long paymentid;
	private String name1; //vendorname
	private String lifnr;  //vendorcode
	private String bldat;  //payment doc date
	private long wrbtr; //payment amount
	private String waers; //currency
	private String xblnr; //invoice reference
	private String rebzg; //invoice no
	private String wrbtr1; //invoice amount
	private String rzawe; //payment method
	private String ubknt; //bank account no
	private String chect; //bank transaction/cheque no-> 
	//private String status;
	private String entrydat; //entry date 
	private String changeddate;
	private String createdBy;
	private String changeddBy;
	public long getPaymentid() {
		return paymentid;
	}
	public void setPaymentid(long paymentid) {
		this.paymentid = paymentid;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getLifnr() {
		return lifnr;
	}
	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}
	public String getBldat() {
		return bldat;
	}
	public void setBldat(String bldat) {
		this.bldat = bldat;
	}
	public long getWrbtr() {
		return wrbtr;
	}
	public void setWrbtr(long wrbtr) {
		this.wrbtr = wrbtr;
	}
	public String getWaers() {
		return waers;
	}
	public void setWaers(String waers) {
		this.waers = waers;
	}
	public String getXblnr() {
		return xblnr;
	}
	public void setXblnr(String xblnr) {
		this.xblnr = xblnr;
	}
	public String getRebzg() {
		return rebzg;
	}
	public void setRebzg(String rebzg) {
		this.rebzg = rebzg;
	}
	public String getWrbtr1() {
		return wrbtr1;
	}
	public void setWrbtr1(String wrbtr1) {
		this.wrbtr1 = wrbtr1;
	}
	public String getRzawe() {
		return rzawe;
	}
	public void setRzawe(String rzawe) {
		this.rzawe = rzawe;
	}
	public String getUbknt() {
		return ubknt;
	}
	public void setUbknt(String ubknt) {
		this.ubknt = ubknt;
	}
	public String getChect() {
		return chect;
	}
	public void setChect(String chect) {
		this.chect = chect;
	}
	/*public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	*/
	public String getEntrydat() {
		return entrydat;
	}
	public void setEntrydat(String entrydat) {
		this.entrydat = entrydat;
	}
	public String getChangeddate() {
		return changeddate;
	}
	public void setChangeddate(String changeddate) {
		this.changeddate = changeddate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getChangeddBy() {
		return changeddBy;
	}
	public void setChangeddBy(String changeddBy) {
		this.changeddBy = changeddBy;
	}
}

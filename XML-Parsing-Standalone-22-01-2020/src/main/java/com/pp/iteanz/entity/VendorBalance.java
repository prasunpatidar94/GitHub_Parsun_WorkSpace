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
public class VendorBalance {
	@Id
	private long vendorledgerid;
	private String name1; //vendorname
	private String lifnr;  //vendorcode
	private String belnr;  //document no
	private String bldat; //document date
	private String xblnr; //invoice reference
	private long shkzgdb; //debit balance
	private long shkzgcr; //credit balance
	private long wrbtr; //running amount
	private String status;
	private String budat; //posting date
	private String changeddate;
	private String createdBy;
	private String changeddBy;
	
	public long getVendorledgerid() {
		return vendorledgerid;
	}
	public void setVendorledgerid(long vendorledgerid) {
		this.vendorledgerid = vendorledgerid;
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
	public String getBelnr() {
		return belnr;
	}
	public void setBelnr(String belnr) {
		this.belnr = belnr;
	}
	public String getBldat() {
		return bldat;
	}
	public void setBldat(String bldat) {
		this.bldat = bldat;
	}
	public String getXblnr() {
		return xblnr;
	}
	public void setXblnr(String xblnr) {
		this.xblnr = xblnr;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBudat() {
		return budat;
	}
	public void setBudat(String budat) {
		this.budat = budat;
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
	public long getShkzgdb() {
		return shkzgdb;
	}
	public void setShkzgdb(long shkzgdb) {
		this.shkzgdb = shkzgdb;
	}
	public long getShkzgcr() {
		return shkzgcr;
	}
	public void setShkzgcr(long shkzgcr) {
		this.shkzgcr = shkzgcr;
	}
	public long getWrbtr() {
		return wrbtr;
	}
	public void setWrbtr(long wrbtr) {
		this.wrbtr = wrbtr;
	}

}

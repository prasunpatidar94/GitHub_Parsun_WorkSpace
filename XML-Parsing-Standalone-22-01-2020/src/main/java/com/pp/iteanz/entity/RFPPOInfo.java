package com.pp.iteanz.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RFPPOInfo")
public class RFPPOInfo {
	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "RFPPOInfo_seq", allocationSize = 1, name = "RFPPOInfo_s")
	@Column(name = "id")
	private long id;

	@Column(name = "rfpno")
	long rfpno;
	
	@Column(name = "proposalno")
	String proposalno;
	
	@Column(name = "lifnr")
	String lifnr;
	
	@Column(name = "ecclifnr")
	String ecclifnr;
	
	@Column(name = "ebeln")
	String ebeln;
	
	@Column(name = "validationString")
	String validationString;
	
	@Column(name = "ihran")
    String ihran;
	
	@Column(name = "verkf")
    String verkf;
	
	@Column(name = "telf1")
	String telf1;
	
	@Column(name = "ihrez")
    String ihrez;
	
	@Column(name = "zihrez")
    String zihrez;
	
	@Column(name = "zcuspr")
    String zcuspr;
	
	@Column(name = "revno")
    String revno;
	
	@Column(name = "prevcontract")
    String prevcontract;
	
	@Column(name = "expiryString")
    String expiryString;
	
	@Column(name = "createdby")
	String createdby;
	
	@Column(name = "changedby")
    String changedby;
	
	@Column(name = "changeddate")
	String changeddate;
	
	public String getChangeddate() {
		return changeddate;
	}

	public void setChangeddate(String changeddate) {
		this.changeddate = changeddate;
	}

	public String getCreateddate() {
		return createddate;
	}

	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}

	@Column(name = "createddate")
	String createddate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRfpno() {
		return rfpno;
	}

	public void setRfpno(long rfpno) {
		this.rfpno = rfpno;
	}

	public String getProposalno() {
		return proposalno;
	}

	public void setProposalno(String proposalno) {
		this.proposalno = proposalno;
	}

	public String getLifnr() {
		return lifnr;
	}

	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}

	public String getEcclifnr() {
		return ecclifnr;
	}

	public void setEcclifnr(String ecclifnr) {
		this.ecclifnr = ecclifnr;
	}

	public String getEbeln() {
		return ebeln;
	}

	public void setEbeln(String ebeln) {
		this.ebeln = ebeln;
	}

	public String getValidationString() {
		return validationString;
	}

	public void setValidationString(String validationString) {
		this.validationString = validationString;
	}

	public String getIhran() {
		return ihran;
	}

	public void setIhran(String ihran) {
		this.ihran = ihran;
	}

	public String getVerkf() {
		return verkf;
	}

	public void setVerkf(String verkf) {
		this.verkf = verkf;
	}

	public String getTelf1() {
		return telf1;
	}

	public void setTelf1(String telf1) {
		this.telf1 = telf1;
	}

	public String getIhrez() {
		return ihrez;
	}

	public void setIhrez(String ihrez) {
		this.ihrez = ihrez;
	}

	public String getZihrez() {
		return zihrez;
	}

	public void setZihrez(String zihrez) {
		this.zihrez = zihrez;
	}

	public String getZcuspr() {
		return zcuspr;
	}

	public void setZcuspr(String zcuspr) {
		this.zcuspr = zcuspr;
	}

	public String getRevno() {
		return revno;
	}

	public void setRevno(String revno) {
		this.revno = revno;
	}

	public String getPrevcontract() {
		return prevcontract;
	}

	public void setPrevcontract(String prevcontract) {
		this.prevcontract = prevcontract;
	}

	public String getExpiryString() {
		return expiryString;
	}

	public void setExpiryString(String expiryString) {
		this.expiryString = expiryString;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getChangedby() {
		return changedby;
	}

	public void setChangedby(String changedby) {
		this.changedby = changedby;
	}
	
	

	}

	


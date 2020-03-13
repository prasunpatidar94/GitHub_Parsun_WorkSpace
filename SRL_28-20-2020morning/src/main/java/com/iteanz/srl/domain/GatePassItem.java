package com.iteanz.srl.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class GatePassItem {
	@Id
	
	private long  zdocno; 
	// Gate Pass Entry No
	
	//Vendor 
	private String lifnr;  
	
	//Vendor name
	private String name1;  
	
	
	 //Plant
	private String werks; 
	
	//Item No
	private double zeile; 
	
	//MaterialNo
	private String matnr; 
	
	 //Quantity
	private float menge;
	
	//UOM
	private String meins; 
	
	//PO No
	private long ebeln;  
	
	//PO item number
	private long ebelp;  
	
	//Material description
	private String maktx;  
	
	//Reference no
	private String zrefno; 
	
	//Doc Creation Date
	Date zcpudt;  
	
	//Requested by
	private String zreqsby; 
	
	  //ASN creation indicator
	private String chec;

	public long getZdocno() {
		return zdocno;
	}

	public void setZdocno(long zdocno) {
		this.zdocno = zdocno;
	}

	public String getLifnr() {
		return lifnr;
	}

	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public double getZeile() {
		return zeile;
	}

	public void setZeile(double zeile) {
		this.zeile = zeile;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	
	public float getMenge() {
		return menge;
	}

	public void setMenge(float menge) {
		this.menge = menge;
	}

	public String getMeins() {
		return meins;
	}

	public void setMeins(String meins) {
		this.meins = meins;
	}

	public long getEbeln() {
		return ebeln;
	}

	public void setEbeln(long ebeln) {
		this.ebeln = ebeln;
	}

	public long getEbelp() {
		return ebelp;
	}

	public void setEbelp(long ebelp) {
		this.ebelp = ebelp;
	}

	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	public String getZrefno() {
		return zrefno;
	}

	public void setZrefno(String zrefno) {
		this.zrefno = zrefno;
	}

	public Date getZcpudt() {
		return zcpudt;
	}

	public void setZcpudt(Date zcpudt) {
		this.zcpudt = zcpudt;
	}

	public String getZreqsby() {
		return zreqsby;
	}

	public void setZreqsby(String zreqsby) {
		this.zreqsby = zreqsby;
	}

	public String getChec() {
		return chec;
	}

	public void setChec(String chec) {
		this.chec = chec;
	}

	
	
}

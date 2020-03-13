package com.pp.iteanz.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GatePass")
public class GatePass {
	@Id
	private Long  zdocno; // Gate Pass Entry No
	private String lifnr;  //Vendor 
	private String name1;  //Vendor name
	private String werks;  //Plant
	private double zeile; //Item No
	private String matnr; //MaterialNo
	private double menge; //Quantity
	private String meins; //UOM
	private long ebeln;  //PO No
	private long ebelp;  //PO item number
	private String maktx;  //Material description
	private String zrefno; //Reference no
	private String  zcpudt;  //Doc Creation Date
	private String zreqsby;//Requested by
	private String chec;   //ASN creation indicator
	public Long getZdocno() {
		return zdocno;
	}
	public void setZdocno(Long zdocno) {
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
	public double getMenge() {
		return menge;
	}
	public void setMenge(double menge) {
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
	public String getZcpudt() {
		return zcpudt;
	}
	public void setZcpudt(String zcpudt) {
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

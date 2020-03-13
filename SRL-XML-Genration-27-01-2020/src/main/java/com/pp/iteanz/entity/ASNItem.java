package com.pp.iteanz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class ASNItem {
	@Id
	@GeneratedValue
	private long asnitemid;
	private long ebeln;//po number
	private long ebelp;//item  no
	private String pstyp;// item category
	private String maktx;//material desc
	private String matnr; //material
	private String menge;//delivery  quantity
	private String werks;//plant
	private String exdate;//expiry date
	private String charg;//batch
	private String barc;//barcode
	private String eindt;//delivery date
	//private int status;
	
	public long getAsnitemid() {
		return asnitemid;
	}
	public void setAsnitemid(long asnitemid) {
		this.asnitemid = asnitemid;
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
	public String getPstyp() {
		return pstyp;
	}
	public void setPstyp(String pstyp) {
		this.pstyp = pstyp;
	}
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getMenge() {
		return menge;
	}
	public void setMenge(String menge) {
		this.menge = menge;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getExdate() {
		return exdate;
	}
	public void setExdate(String exdate) {
		this.exdate = exdate;
	}
	public String getCharg() {
		return charg;
	}
	public void setCharg(String charg) {
		this.charg = charg;
	}
	public String getBarc() {
		return barc;
	}
	public void setBarc(String barc) {
		this.barc = barc;
	}
	public String getEindt() {
		return eindt;
	}
	public void setEindt(String eindt) {
		this.eindt = eindt;
	}
	/*public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}*/
	/*public String getChangeddate() {
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
	}*/
}

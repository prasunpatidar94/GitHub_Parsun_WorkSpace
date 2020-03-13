package com.iteanz.srl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;





@Entity
//@Proxy(lazy = false)
@Table(name = "POItemDetails")
public class POItemDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "POItemDetails_seq", allocationSize = 1, name = "POItemDetails_s")
	@Column(name = "id")
	private Long id;
	private long ebelp;//  item no
	private String matnr;// MaterialNo
	private String werks;/// Plant
	private String lgort;// StorageLocation
	private String matkl;// MaterialGroup
	private Float menge;// POQuantity
	private String netpr;// TotalNetPrice
	private String waers;// Currency
	private String eindt; // DeliveryDate
	private String pstyp; // ItemCategory
	private String maktx;// material description
	private String xchpf;// BatchManagement//BatchManagementIndicator
	private String logrt;// storage location
	private String elikz; // Po stot close indicator//DeliveryCompleted
	private String mfrpn;// ManufacturePartNo
//	private long cp_fk;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getLgort() {
		return lgort;
	}
	public void setLgort(String lgort) {
		this.lgort = lgort;
	}
	public String getMatkl() {
		return matkl;
	}
	public void setMatkl(String matkl) {
		this.matkl = matkl;
	}
	public Float getMenge() {
		return menge;
	}
	public void setMenge(Float menge) {
		this.menge = menge;
	}
	public String getNetpr() {
		return netpr;
	}
	public void setNetpr(String netpr) {
		this.netpr = netpr;
	}
	public String getWaers() {
		return waers;
	}
	public void setWaers(String waers) {
		this.waers = waers;
	}
	public String getEindt() {
		return eindt;
	}
	public void setEindt(String eindt) {
		this.eindt = eindt;
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
	public long getEbelp() {
		return ebelp;
	}
	public void setEbelp(long ebelp) {
		this.ebelp = ebelp;
	}
	public String getXchpf() {
		return xchpf;
	}
	public void setXchpf(String xchpf) {
		this.xchpf = xchpf;
	}
	
	public String getLogrt() {
		return logrt;
	}
	public void setLogrt(String logrt) {
		this.logrt = logrt;
	}
	public String getElikz() {
		return elikz;
	}
	public void setElikz(String elikz) {
		this.elikz = elikz;
	}
	public String getMfrpn() {
		return mfrpn;
	}
	public void setMfrpn(String mfrpn) {
		this.mfrpn = mfrpn;
	}




}

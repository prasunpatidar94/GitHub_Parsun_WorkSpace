package com.pp.iteanz.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PurchaseRequisition")
public class PurchaseRequisitionMain {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "PurchaseRequisition_seq", allocationSize = 1, name = "PurchaseRequisition_s")
	@Column(name = "id")
	private Long id;

//prno
	@Column(name = "banfn")
	Long banfn;

//itemno
	@Column(name = "bnfpo")
	String bnfpo;

//prgrp
	@Column(name = "ekgrp")
	String ekgrp;

//dctype
	@Column(name = "bsart")
	String bsart;

//material
	@Column(name = "matnr")
	String matnr;

//itemdesc
	@Column(name = "maktx")
	String maktx;

//qty
	@Column(name = "menge")
	float menge;

//uom
	@Column(name = "meins")
	String meins;

//storageLoc
	@Column(name = "lgort")
	String lgort;

//currency
	@Column(name = "waers")
	String waers;

//deletion 
	@Column(name = "loekz")
	String loekz;

//plant
	@Column(name = "werks")
	String werks;

	@Column(name = "aprv")
	String aprv;

	@Column(name = "planttext")
	String planttext;

	@Column(name = "lgorttext")
	String lgorttext;

	@Column(name = "matkl")
	String matkl;

	@Column(name = "netwr")
	String netwr;

	@Column(name = "delloc")
	String delloc;

	@Column(name = "lpein")
	String lpein;
	
	@Column(name = "text")//Clarification Box
	String text;
	
	@Column(name = "pstyp")
	String pstyp;

//price
	@Column(name = "preis")
	String preis;

	@Column(name = "knttp")
	String knttp;

	@Column(name = "status")
	int status;

	@Column(name = "crBy")
	String crdBy;

	@Column(name = "cdBy")
	String cdBy;

//crDat
	@Column(name = "badat")
	String badat;

//cdDat
	@Column(name = "cdDat")
	String cdDat;

	@Column(name = "reqstatus")
	String reqstatus;

	@Column(name = "inactive")
	int inactive;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBanfn() {
		return banfn;
	}

	public void setBanfn(Long banfn) {
		this.banfn = banfn;
	}

	public String getBnfpo() {
		return bnfpo;
	}

	public void setBnfpo(String bnfpo) {
		this.bnfpo = bnfpo;
	}

	public String getEkgrp() {
		return ekgrp;
	}

	public void setEkgrp(String ekgrp) {
		this.ekgrp = ekgrp;
	}

	public String getBsart() {
		return bsart;
	}

	public void setBsart(String bsart) {
		this.bsart = bsart;
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

	public String getLgort() {
		return lgort;
	}

	public void setLgort(String lgort) {
		this.lgort = lgort;
	}

	public String getWaers() {
		return waers;
	}

	public void setWaers(String waers) {
		this.waers = waers;
	}

	public String getLoekz() {
		return loekz;
	}

	public void setLoekz(String loekz) {
		this.loekz = loekz;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public String getAprv() {
		return aprv;
	}

	public void setAprv(String aprv) {
		this.aprv = aprv;
	}

	public String getPlanttext() {
		return planttext;
	}

	public void setPlanttext(String planttext) {
		this.planttext = planttext;
	}

	public String getLgorttext() {
		return lgorttext;
	}

	public void setLgorttext(String lgorttext) {
		this.lgorttext = lgorttext;
	}

	public String getMatkl() {
		return matkl;
	}

	public void setMatkl(String matkl) {
		this.matkl = matkl;
	}

	public String getNetwr() {
		return netwr;
	}

	public void setNetwr(String netwr) {
		this.netwr = netwr;
	}

	public String getDelloc() {
		return delloc;
	}

	public void setDelloc(String delloc) {
		this.delloc = delloc;
	}

	public String getLpein() {
		return lpein;
	}

	public void setLpein(String lpein) {
		this.lpein = lpein;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPstyp() {
		return pstyp;
	}

	public void setPstyp(String pstyp) {
		this.pstyp = pstyp;
	}

	public String getPreis() {
		return preis;
	}

	public void setPreis(String preis) {
		this.preis = preis;
	}

	public String getKnttp() {
		return knttp;
	}

	public void setKnttp(String knttp) {
		this.knttp = knttp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCrdBy() {
		return crdBy;
	}

	public void setCrdBy(String crdBy) {
		this.crdBy = crdBy;
	}

	public String getCdBy() {
		return cdBy;
	}

	public void setCdBy(String cdBy) {
		this.cdBy = cdBy;
	}

	public String getBadat() {
		return badat;
	}

	public void setBadat(String badat) {
		this.badat = badat;
	}

	public String getCdDat() {
		return cdDat;
	}

	public void setCdDat(String cdDat) {
		this.cdDat = cdDat;
	}

	public String getReqstatus() {
		return reqstatus;
	}

	public void setReqstatus(String reqstatus) {
		this.reqstatus = reqstatus;
	}

	public int getInactive() {
		return inactive;
	}

	public void setInactive(int inactive) {
		this.inactive = inactive;
	}

	
}

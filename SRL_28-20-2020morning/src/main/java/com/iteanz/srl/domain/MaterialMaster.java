package com.iteanz.srl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Material_Master")
public class MaterialMaster {

	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "materialmaster_seq", allocationSize = 1, name = "materialmaster_s")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "werks")
	private String werks;//plant
	
	@Column(name = "matnr")
	private String matnr;//material no
	
	
	@Column(name = "mtart")
	private String mtart;//material type
	
	@Column(name = "mhdrz")
	private String mhdrz;//self life 
	
	
	@Column(name = "groes")
	private String groes;//Size and Dimantion

	@Column(name = "maktx")
	private String maktx;//material desc

	@Column(name = "meins")
	private String meins;// unit of management

	@Column(name = "ekgrp")//oragenigation
	private String ekgrp;

	@Column(name = "maktl")
	private String maktl;//material group

	@Column(name = "mfrnr")
	private String mfrnr;//manufature no

	@Column(name = "lgort")
	private String lgort;//storage location 

	@Column(name = "mfrpn")
	private String mfrpn;//manufacture part no

	@Column(name = "iprkz")
	private String iprkz;//period indecator for shelf life exp date

	@Column(name = "mhdhb")
	private String mhdhb;// total shelf life

	@Column(name = "barcode")
	private String barcode; //barcode

	@Column(name = "lvorm")
	private String lvorm;//flag maretial for deletion


	@Column(name = "ins_dat")
	private String ins_dat;// installation date 

	@Column(name = "createdby")
	public String createdby;

	@Column(name = "changedby")
	public String changedby;


	@Column(name = "changeddate")
	public String changeddate;

//	@Temporal(TemporalType.DATE)
	@Column(name = "createddate")
	public String createddate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getMtart() {
		return mtart;
	}

	public void setMtart(String mtart) {
		this.mtart = mtart;
	}

	public String getMhdrz() {
		return mhdrz;
	}

	public void setMhdrz(String mhdrz) {
		this.mhdrz = mhdrz;
	}

	public String getGroes() {
		return groes;
	}

	public void setGroes(String groes) {
		this.groes = groes;
	}

	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	public String getMeins() {
		return meins;
	}

	public void setMeins(String meins) {
		this.meins = meins;
	}

	public String getEkgrp() {
		return ekgrp;
	}

	public void setEkgrp(String ekgrp) {
		this.ekgrp = ekgrp;
	}

	public String getMaktl() {
		return maktl;
	}

	public void setMaktl(String maktl) {
		this.maktl = maktl;
	}

	public String getMfrnr() {
		return mfrnr;
	}

	public void setMfrnr(String mfrnr) {
		this.mfrnr = mfrnr;
	}

	public String getLgort() {
		return lgort;
	}

	public void setLgort(String lgort) {
		this.lgort = lgort;
	}

	public String getMfrpn() {
		return mfrpn;
	}

	public void setMfrpn(String mfrpn) {
		this.mfrpn = mfrpn;
	}

	public String getIprkz() {
		return iprkz;
	}

	public void setIprkz(String iprkz) {
		this.iprkz = iprkz;
	}

	public String getMhdhb() {
		return mhdhb;
	}

	public void setMhdhb(String mhdhb) {
		this.mhdhb = mhdhb;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getLvorm() {
		return lvorm;
	}

	public void setLvorm(String lvorm) {
		this.lvorm = lvorm;
	}

	public String getIns_dat() {
		return ins_dat;
	}

	public void setIns_dat(String ins_dat) {
		this.ins_dat = ins_dat;
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

	


	
	
	
}

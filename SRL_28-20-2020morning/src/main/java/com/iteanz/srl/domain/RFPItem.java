package com.iteanz.srl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Proxy;
/*
//prno
@Column(name = "banfn")
Long banfn;

//itemno
@Column(name = "bnfpo")
String bnfpo;

*/

@Entity
@Proxy(lazy = false)
public class RFPItem {

	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "RFPItem_seq", allocationSize = 1, name = "RFPItem_s")
	@Column(name = "itemid")
	private Long itemid;	
    private int srno;
    //private String version;
    private String itemno;
    //private String itemdesc;
    private String matnr;
    private String maktx;
    private Float menge;//String
    private Long prnumber;//String
    private String meins;
    private String werks;
    private String lgort;
    private String pritemno;
   // private String requsitior;
	//private long proposalno;
    private String waers;
    private double preis;
    private String plantdelivloc;
    private String plantdelivcode;
    private String deliverydate;
   // private String attachment;
  //  private String attachmentno;
  //  private String filename;
    private String ekgrp;
    private String createdBy;
    private String changeddBy;
    private String changeddate;
    private String createddate;
    private String budjnonbudj;
    private String matmake;
    private String packsize;
   //private String matmodel;
    private String mfrpn;
   // private String manufby;
    private String mfrnr;
  
	private String lab;

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public int getSrno() {
		return srno;
	}

	public void setSrno(int srno) {
		this.srno = srno;
	}

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
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

	public Float getMenge() {
		return menge;
	}

	public void setMenge(Float menge) {
		this.menge = menge;
	}

	public Long getPrnumber() {
		return prnumber;
	}

	public void setPrnumber(Long prnumber) {
		this.prnumber = prnumber;
	}

	public String getMeins() {
		return meins;
	}

	public void setMeins(String meins) {
		this.meins = meins;
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

	public String getPritemno() {
		return pritemno;
	}

	public void setPritemno(String pritemno) {
		this.pritemno = pritemno;
	}

	public String getWaers() {
		return waers;
	}

	public void setWaers(String waers) {
		this.waers = waers;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public String getPlantdelivloc() {
		return plantdelivloc;
	}

	public void setPlantdelivloc(String plantdelivloc) {
		this.plantdelivloc = plantdelivloc;
	}

	public String getPlantdelivcode() {
		return plantdelivcode;
	}

	public void setPlantdelivcode(String plantdelivcode) {
		this.plantdelivcode = plantdelivcode;
	}

	public String getDeliverydate() {
		return deliverydate;
	}

	public void setDeliverydate(String deliverydate) {
		this.deliverydate = deliverydate;
	}

	public String getEkgrp() {
		return ekgrp;
	}

	public void setEkgrp(String ekgrp) {
		this.ekgrp = ekgrp;
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

	public String getBudjnonbudj() {
		return budjnonbudj;
	}

	public void setBudjnonbudj(String budjnonbudj) {
		this.budjnonbudj = budjnonbudj;
	}

	public String getMatmake() {
		return matmake;
	}

	public void setMatmake(String matmake) {
		this.matmake = matmake;
	}

	public String getPacksize() {
		return packsize;
	}

	public void setPacksize(String packsize) {
		this.packsize = packsize;
	}

	public String getMfrpn() {
		return mfrpn;
	}

	public void setMfrpn(String mfrpn) {
		this.mfrpn = mfrpn;
	}

	public String getMfrnr() {
		return mfrnr;
	}

	public void setMfrnr(String mfrnr) {
		this.mfrnr = mfrnr;
	}

	public String getLab() {
		return lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
	}
	
   
			
}

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
public class VendorLedger {
	@Id
	private long vendorledgerid;
	private String name1; //vendorname
	private String lifnr;  //vendorcode
	private String belnr;  //document no
	private String bldat; //document date
	private String entrydat; //Entry Date
	private String budat; //posting date
	private String xblnr; //invoice reference
	private String pstyp; //Document Type
	private String ebeln; //PO No
	private String gjahr; //Fiscal Year
	private String monat; //Posting Period
	private String shkzg; //Dr/Cr Indicator
	private String wrbtr; //amount
	private String waers; //Currency
	private String zumsk; //Spl G/L Indicator 
	private String wrbtr1; //Invoice Amount
	private String augbl; //Clearing Doc No
	private String augdt; //Clearing Document Date
	private String sgtxt; //Text
	private String status; //document status
	private String sno; //SL No
	private String belnr1; //MIGO Doc No
	private String lfbnr; //MIRO Doc No
	private String rebzg; //Payment Invoice No
	private String changeddate;
	private String createdBy;
	private String changeddBy;
	
	private String text1; //bank name
	private String ubknt; //bank ACC no
	private String rzawe ;//payment method
	private String usnam ;//doc posted by
	private String chect ;//bank transaction/check no
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
	public String getEntrydat() {
		return entrydat;
	}
	public void setEntrydat(String entrydat) {
		this.entrydat = entrydat;
	}
	public String getBudat() {
		return budat;
	}
	public void setBudat(String budat) {
		this.budat = budat;
	}
	public String getXblnr() {
		return xblnr;
	}
	public void setXblnr(String xblnr) {
		this.xblnr = xblnr;
	}
	public String getPstyp() {
		return pstyp;
	}
	public void setPstyp(String pstyp) {
		this.pstyp = pstyp;
	}
	public String getEbeln() {
		return ebeln;
	}
	public void setEbeln(String ebeln) {
		this.ebeln = ebeln;
	}
	public String getGjahr() {
		return gjahr;
	}
	public void setGjahr(String gjahr) {
		this.gjahr = gjahr;
	}
	public String getMonat() {
		return monat;
	}
	public void setMonat(String monat) {
		this.monat = monat;
	}
	public String getShkzg() {
		return shkzg;
	}
	public void setShkzg(String shkzg) {
		this.shkzg = shkzg;
	}
	
	public String getWrbtr() {
		return wrbtr;
	}
	public void setWrbtr(String wrbtr) {
		this.wrbtr = wrbtr;
	}
	public String getWrbtr1() {
		return wrbtr1;
	}
	public void setWrbtr1(String wrbtr1) {
		this.wrbtr1 = wrbtr1;
	}
	public String getWaers() {
		return waers;
	}
	public void setWaers(String waers) {
		this.waers = waers;
	}
	public String getZumsk() {
		return zumsk;
	}
	public void setZumsk(String zumsk) {
		this.zumsk = zumsk;
	}
	
	public String getAugbl() {
		return augbl;
	}
	public void setAugbl(String augbl) {
		this.augbl = augbl;
	}
	public String getAugdt() {
		return augdt;
	}
	public void setAugdt(String augdt) {
		this.augdt = augdt;
	}
	public String getSgtxt() {
		return sgtxt;
	}
	public void setSgtxt(String sgtxt) {
		this.sgtxt = sgtxt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getBelnr1() {
		return belnr1;
	}
	public void setBelnr1(String belnr1) {
		this.belnr1 = belnr1;
	}
	public String getLfbnr() {
		return lfbnr;
	}
	public void setLfbnr(String lfbnr) {
		this.lfbnr = lfbnr;
	}
	public String getRebzg() {
		return rebzg;
	}
	public void setRebzg(String rebzg) {
		this.rebzg = rebzg;
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
	public String getText1() {
		return text1;
	}
	public void setText1(String text1) {
		this.text1 = text1;
	}
	public String getUbknt() {
		return ubknt;
	}
	public void setUbknt(String ubknt) {
		this.ubknt = ubknt;
	}
	public String getRzawe() {
		return rzawe;
	}
	public void setRzawe(String rzawe) {
		this.rzawe = rzawe;
	}
	public String getUsnam() {
		return usnam;
	}
	public void setUsnam(String usnam) {
		this.usnam = usnam;
	}
	public String getChect() {
		return chect;
	}
	public void setChect(String chect) {
		this.chect = chect;
	}
	
	
}

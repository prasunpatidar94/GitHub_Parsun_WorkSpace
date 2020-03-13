package com.pp.iteanz.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "ApproverLogTable")
@Embeddable
public class ApproverLogTable implements Serializable{
	@Id
	@GeneratedValue
	@JoinColumn(name="mandt", nullable = false )
	//@Column(name = "mandt")
	int mandt;
	
	@Id
	@JoinColumn(name="mod", nullable = false )
	//@Column(name = "bukrs", nullable = false)
	String mod;
	
	@Id
	@JoinColumn(name="bukrs", nullable = false )
	//@Column(name = "bukrs", nullable = false)
	String bukrs;
	
	@Id
	@JoinColumn(name="doc_no", nullable = false )
	//@Column(name = "bukrs", nullable = false)
	String doc_no;

	@Id
	@JoinColumn(name="dart", nullable = false )
	//@Column(name = "bukrs", nullable = false)
	String dart;
	
	@Column(name = "terminal")
	String terminal;
	
	@Column(name = "approver")
	String approver;

	@Column(name = "kunnr")
	String kunnr;

	@Column(name = "name1")
	String name1;

	@Column(name = "amount")
	String amount;
	
	@Column(name = "waers")
	String waers;

	@Column(name = "doknr")
	String doknr;

	@Column(name = "dokar")
	String dokar;

	@Column(name = "er_stat")
	String er_stat;
	
	@Column(name = "sign_stat")
	String sign_stat;

	@Column(name = "remarks")
	String remarks;
	
	@Column(name = "canc_stat")
	String canc_stat;

	@Column(name = "erdat")
	Date erdat;

	@Column(name = "erzet")
	Time erzet;
	
	@Column(name = "aenam")
	String aenam;
	
	@Column(name = "laeda")
	Date laeda;
	
	@Column(name = "crzet")
	Time crzet;
	
	public ApproverLogTable() {
    }
	
	 public ApproverLogTable(int mandt, String mod,  String bukrs, String doc_no, String dart) {
	      this.mandt = mandt;
	      this.mod = mod;
	      this.bukrs = bukrs;
	      this.doc_no = doc_no;
	      this.dart = dart;
	  }
	 
	public int getMandt() {
		return mandt;
	}

	public void setMandt(int mandt) {
		this.mandt = mandt;
	}

	public String getMod() {
		return mod;
	}

	public void setMod(String mod) {
		this.mod = mod;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public String getDoc_no() {
		return doc_no;
	}

	public void setDoc_no(String doc_no) {
		this.doc_no = doc_no;
	}

	public String getDart() {
		return dart;
	}

	public void setDart(String dart) {
		this.dart = dart;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getKunnr() {
		return kunnr;
	}

	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getWaers() {
		return waers;
	}

	public void setWaers(String waers) {
		this.waers = waers;
	}

	public String getDoknr() {
		return doknr;
	}

	public void setDoknr(String doknr) {
		this.doknr = doknr;
	}

	public String getDokar() {
		return dokar;
	}

	public void setDokar(String dokar) {
		this.dokar = dokar;
	}

	public String getEr_stat() {
		return er_stat;
	}

	public void setEr_stat(String er_stat) {
		this.er_stat = er_stat;
	}

	public String getSign_stat() {
		return sign_stat;
	}

	public void setSign_stat(String sign_stat) {
		this.sign_stat = sign_stat;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCanc_stat() {
		return canc_stat;
	}

	public void setCanc_stat(String canc_stat) {
		this.canc_stat = canc_stat;
	}

	public Date getErdat() {
		return erdat;
	}

	public void setErdat(Date erdat) {
		this.erdat = erdat;
	}

	public Time getErzet() {
		return erzet;
	}

	public void setErzet(Time erzet) {
		this.erzet = erzet;
	}

	public String getAenam() {
		return aenam;
	}

	public void setAenam(String aenam) {
		this.aenam = aenam;
	}

	public Date getLaeda() {
		return laeda;
	}

	public void setLaeda(Date laeda) {
		this.laeda = laeda;
	}

	public Time getCrzet() {
		return crzet;
	}

	public void setCrzet(Time crzet) {
		this.crzet = crzet;
	}

	
}

package com.pp.iteanz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class ASNItem {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "asnitem_seq", allocationSize = 1, name = "asnitem_s")
	@Column(name = "asnitemid")
	private Long asnitemid;
	private long ebeln;// po number
	private long ebelp;// item no
	private String pstyp;// item category
	private String maktx;// material desc
	private String matnr; // material
	private Float menge;// delivery quantity------->String
	private String werks;// plant
	private String exdate;// expiry date
	private String charg;// batch
	private String barc;// barcode
	private String eindt;// delivery date
	private long lgort;// storage location
	private long xchpf;
	private long mfrpf;
	private long mfrpn;// Supplier Product Code
	private long logrt;
	private long cp_fk;
	private int status;
	public Long getAsnitemid() {
		return asnitemid;
	}
	public void setAsnitemid(Long asnitemid) {
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
	public Float getMenge() {
		return menge;
	}
	public void setMenge(Float menge) {
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
	public long getLgort() {
		return lgort;
	}
	public void setLgort(long lgort) {
		this.lgort = lgort;
	}
	public long getXchpf() {
		return xchpf;
	}
	public void setXchpf(long xchpf) {
		this.xchpf = xchpf;
	}
	public long getMfrpf() {
		return mfrpf;
	}
	public void setMfrpf(long mfrpf) {
		this.mfrpf = mfrpf;
	}
	public long getMfrpn() {
		return mfrpn;
	}
	public void setMfrpn(long mfrpn) {
		this.mfrpn = mfrpn;
	}
	public long getLogrt() {
		return logrt;
	}
	public void setLogrt(long logrt) {
		this.logrt = logrt;
	}
	public long getCp_fk() {
		return cp_fk;
	}
	public void setCp_fk(long cp_fk) {
		this.cp_fk = cp_fk;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

//	1.Display Supplier Product Code in item level
//	SapFieldName : MFRPN
//	2. Add storage location in Item
//	SapFieldName :LGORT

}

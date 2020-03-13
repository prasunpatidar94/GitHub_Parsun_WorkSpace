package com.pp.iteanz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
@Table(name = "POItemDetails")
public class POItemDetails {
	
	
	
	@Id
	@GeneratedValue
	private long poitemid;// id *****
	private String matnr;// MaterialNo
	private String werks;/// Plant
	private String lgort;// StorageLocation
	private String matkl;// MaterialGroup
	//private String menge; // POQuantity------------------->String
	private Float menge;
	private String netpr;// TotalNetPrice
	private String waers;// Currency
	private String eindt; // DeliveryDate
	private String pstyp; // ItemCategory
	private String maktx;// material description
	private long ebelp;//po item no
	
	//private String cdby;
	//private String crdby;
	//private String cddat;
	//private String crdat;
	
	
//	@Id
//	@GeneratedValue
//	@Column(name = "poitemid")
//	private long poitemid;// id *****
//	
//	@Column(name = "ebelp")//item no 
//	private long ebelp;
//	
//	@Column(name = "matnr")
//	private String matnr;// MaterialNo
//	
//	@Column(name = "maktx")
//	private String maktx;// material description
//
//	@Column(name = "werks")
//	private String werks;/// Plant
//	
//	@Column(name = "pstyp")
//	private String pstyp; // ItemCategory
//
//	@Column(name = "lgort")
//	private String lgort;// StorageLocation
//
//	@Column(name = "matkl")
//	private String matkl;// MaterialGroup
//
//	@Column(name = "menge")
//	private String menge; // POQuantity
//
//	@Column(name = "netpr")
//	private String netpr;// TotalNetPrice
//
//	//@Temporal(TemporalType.DATE)
//	@Column(name = "eindt")//eindt
//	private String eindt; // DeliveryDate
//
//	@Column(name = "waers")
//	private String waers;// Currency
//
//	@Column(name = "cdby")
//	private String cdby;
//
//	@Column(name = "crdby")
//	private String crdby;
//
//	//@Temporal(TemporalType.DATE)
//	@Column(name = "cddat")
//	private String cddat;
//
////	@Temporal(TemporalType.DATE)
//	@Column(name = "crdat")
//	private String crdat;


	
	public long getPoitemid() {
		return poitemid;
	}

	public void setPoitemid(long poitemid) {
		this.poitemid = poitemid;
	}

	public long getEbelp() {
		return ebelp;
	}

	public void setEbelp(long ebelp) {
		this.ebelp = ebelp;
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

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public String getPstyp() {
		return pstyp;
	}

	public void setPstyp(String pstyp) {
		this.pstyp = pstyp;
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

	public String getEindt() {
		return eindt;
	}

	public void setEindt(String eindt) {
		this.eindt = eindt;
	}

	public String getWaers() {
		return waers;
	}

	public void setWaers(String waers) {
		this.waers = waers;
	}

/*	public String getCdby() {
		return cdby;
	}

	public void setCdby(String cdby) {
		this.cdby = cdby;
	}

	public String getCrdby() {
		return crdby;
	}

	public void setCrdby(String crdby) {
		this.crdby = crdby;
	}

	public String getCddat() {
		return cddat;
	}

	public void setCddat(String cddat) {
		this.cddat = cddat;
	}

	public String getCrdat() {
		return crdat;
	}

	public void setCrdat(String crdat) {
		this.crdat = crdat;
	}
*/


	

	

	

}

package com.iteanz.srl.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Proxy(lazy = false)
public class ASNHeader {
	@Id
	private long drfgrnno;//asn number
	private long ebeln;//po number
	private String shipmentdetail;
	private String shipmentno;
	private String banfn;//prno
	private String transportname;
	private String vehicleno;
	private String ewaybillno;
	private String vbeln;//vendor invoice no
	private String vendorinvdate;
	private double discamt;
	private double bsamt;//invoice base amount
	private double taxamt;//invoice tax amt
	private int status;
	private String changeddate;
	private String createddate;//use vendorinvdate
	private String createdBy;
	private String changeddBy;
	private String sessionid;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = ASNItem.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "drfgrnno")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<ASNItem> asnitem;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = ASNSection.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "drfgrnno")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<ASNSection> asnsection;
	
	public long getDrfgrnno() {
		return drfgrnno;
	}

	public void setDrfgrnno(long drfgrnno) {
		this.drfgrnno = drfgrnno;
	}

	public String getShipmentdetail() {
		return shipmentdetail;
	}

	public void setShipmentdetail(String shipmentdetail) {
		this.shipmentdetail = shipmentdetail;
	}

	public String getShipmentno() {
		return shipmentno;
	}

	public void setShipmentno(String shipmentno) {
		this.shipmentno = shipmentno;
	}

	public String getBanfn() {
		return banfn;
	}

	public void setBanfn(String banfn) {
		this.banfn = banfn;
	}

	public String getTransportname() {
		return transportname;
	}

	public void setTransportname(String transportname) {
		this.transportname = transportname;
	}

	public String getVehicleno() {
		return vehicleno;
	}

	public void setVehicleno(String vehicleno) {
		this.vehicleno = vehicleno;
	}

	public String getEwaybillno() {
		return ewaybillno;
	}

	public void setEwaybillno(String ewaybillno) {
		this.ewaybillno = ewaybillno;
	}

	public String getVbeln() {
		return vbeln;
	}

	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}

	public String getVendorinvdate() {
		return vendorinvdate;
	}

	public void setVendorinvdate(String vendorinvdate) {
		this.vendorinvdate = vendorinvdate;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public List<ASNItem> getAsnitem() {
		return asnitem;
	}

	public void setAsnitem(List<ASNItem> asnitem) {
		this.asnitem = asnitem;
	}

	public List<ASNSection> getAsnsection() {
		return asnsection;
	}

	public void setAsnsection(List<ASNSection> asnsection) {
		this.asnsection = asnsection;
	}
	
	
	public double getDiscamt() {
		return discamt;
	}

	public void setDiscamt(double discamt) {
		this.discamt = discamt;
	}

	public double getBsamt() {
		return bsamt;
	}

	public void setBsamt(double bsamt) {
		this.bsamt = bsamt;
	}

	public double getTaxamt() {
		return taxamt;
	}

	public void setTaxamt(double taxamt) {
		this.taxamt = taxamt;
	}

	public String getCreateddate() {
		return createddate;
	}

	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public long getEbeln() {
		return ebeln;
	}

	public void setEbeln(long ebeln) {
		this.ebeln = ebeln;
	}

	
}

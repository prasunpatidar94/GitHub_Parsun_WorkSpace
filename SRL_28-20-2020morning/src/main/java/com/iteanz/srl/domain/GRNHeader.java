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
public class GRNHeader {
	@Id
	private long grn;//asn number//------------>grn
	private long ebeln;//po number
	private String budat;//grn date
	private String lifnr;//vendor code
	private String name1;//vendor name
	private String status;
	private String createdBy;
	private String changeddBy;
	//private String createddate;//use grn date
	private String changeddate;
	
	private String delivOnTimeAsPO; //WasTheDeliveryOnTimeAsPerPO;
	private String invReceivedWithDeliv; //WasTheInvoiceReceivedWithDelivery;//
	private String invErrorFree;//WasTheInvoiceErrorFree;
	private String venRatingCpdPoSpoc;//VendorRating_CPD_PO_SPOC; //_based
	private String venRatingUserBased;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = GRNItem.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "grn")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<GRNItem> grnitem;

	

	public long getEbeln() {
		return ebeln;
	}

	public void setEbeln(long ebeln) {
		this.ebeln = ebeln;
	}

	
	public long getGrn() {
		return grn;
	}

	public void setGrn(long grn) {
		this.grn = grn;
	}

	public List<GRNItem> getGrnitem() {
		return grnitem;
	}

	public void setGrnitem(List<GRNItem> grnitem) {
		this.grnitem = grnitem;
	}

	public String getBudat() {
		return budat;
	}

	public void setBudat(String budat) {
		this.budat = budat;
	}

	public String getLifnr() {
		return lifnr;
	}

	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	

	public String getDelivOnTimeAsPO() {
		return delivOnTimeAsPO;
	}

	public void setDelivOnTimeAsPO(String delivOnTimeAsPO) {
		this.delivOnTimeAsPO = delivOnTimeAsPO;
	}

	public String getInvReceivedWithDeliv() {
		return invReceivedWithDeliv;
	}

	public void setInvReceivedWithDeliv(String invReceivedWithDeliv) {
		this.invReceivedWithDeliv = invReceivedWithDeliv;
	}

	public String getInvErrorFree() {
		return invErrorFree;
	}

	public void setInvErrorFree(String invErrorFree) {
		this.invErrorFree = invErrorFree;
	}

	public String getVenRatingCpdPoSpoc() {
		return venRatingCpdPoSpoc;
	}

	public void setVenRatingCpdPoSpoc(String venRatingCpdPoSpoc) {
		this.venRatingCpdPoSpoc = venRatingCpdPoSpoc;
	}

	public String getVenRatingUserBased() {
		return venRatingUserBased;
	}

	public void setVenRatingUserBased(String venRatingUserBased) {
		this.venRatingUserBased = venRatingUserBased;
	}
	
	
	
}

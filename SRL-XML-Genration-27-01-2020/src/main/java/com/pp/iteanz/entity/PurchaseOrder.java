package com.pp.iteanz.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Proxy(lazy = false)
@Table(name = "PurchaseOrder")
public class PurchaseOrder {
	
	@Id
	//@GeneratedValue
	private long ebeln;// PurchaseOrderNo
	private String bsart;// PODocumentType
	private String bukrs;// CompanyCode
	private String lifnr;// VendorCode
	private String ekorg;// PurchaseOrg
	private String ackno;// AckIndicator
	private String cntr_no;// ContractNo
	private String name1; //vendor name
	//private String pstyp; // ItemCategory
	private String status;
	private String aedat; // POCreationDate
	private String cddat;// changed date
	private String ernam;//createdBy;
	private String changeddBy;
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity = PODocument.class, cascade = CascadeType.ALL)
	@Fetch (value = FetchMode.SELECT)
	private PODocument poDocument;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = POItemDetails.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "ebeln")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<POItemDetails> poitem;

	public long getEbeln() {
		return ebeln;
	}

	public void setEbeln(long ebeln) {
		this.ebeln = ebeln;
	}

	public String getBsart() {
		return bsart;
	}

	public void setBsart(String bsart) {
		this.bsart = bsart;
	}

	public String getBukrs() {
		return bukrs;
	}


	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public String getLifnr() {
		return lifnr;
	}

	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}

	public String getEkorg() {
		return ekorg;
	}

	public void setEkorg(String ekorg) {
		this.ekorg = ekorg;
	}

	public String getAckno() {
		return ackno;
	}

	public void setAckno(String ackno) {
		this.ackno = ackno;
	}

	public String getCntr_no() {
		return cntr_no;
	}

	public void setCntr_no(String cntr_no) {
		this.cntr_no = cntr_no;
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

	public String getAedat() {
		return aedat;
	}

	public void setAedat(String aedat) {
		this.aedat = aedat;
	}

	public String getCddat() {
		return cddat;
	}

	public void setCddat(String cddat) {
		this.cddat = cddat;
	}

	public String getErnam() {
		return ernam;
	}

	public void setErnam(String ernam) {
		this.ernam = ernam;
	}

	
	public String getChangeddBy() {
		return changeddBy;
	}

	public void setChangeddBy(String changeddBy) {
		this.changeddBy = changeddBy;
	}

	public PODocument getPoDocument() {
		return poDocument;
	}

	public void setPoDocument(PODocument poDocument) {
		this.poDocument = poDocument;
	}

	public List<POItemDetails> getPoitem() {
		return poitem;
	}

	public void setPoitem(List<POItemDetails> poitem) {
		this.poitem = poitem;
	}

	
	
	


}


package com.iteanz.srl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class GRNItem {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "grnitem_seq", allocationSize = 1, name = "grnitem_s")
	@Column(name = "grnitemid")
	private Long grnitemid;
	private String matnr;//material no
	private String maktx;//material description
	private String drfgrnno;// asn no
	private String menge2;//asn quantity
	private String menge; //po quantity
	private String meins;//uom
	private Float menge1;//grn quantity same as delivery Qty------------>String
	private String werks;//plant
	private String lgort;//storage location
	private String charg;//batch
	private String exdate;//expiry date
	private int status;
	private String createdBy;
	private String changeddBy;
	private String changeddate;
	private String createddate;
	
	private String totalRejectedQtyCount;//TotalNoofQtyRejectedForLineItem;
	private String totalQtyDelivCount;//TotalNoofQtyDeliveredofLineItems;
	private String totalShortExpItemCount;//TotalNoofShortExpiryItemsDelivered;
	private String grnlineitemid;
	private long polineitemid;
	private String poItemCategory;
	
	private long ebeln;
	private long ebelp;
	public Long getGrnitemid() {
		return grnitemid;
	}
	public void setGrnitemid(Long grnitemid) {
		this.grnitemid = grnitemid;
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
	public String getDrfgrnno() {
		return drfgrnno;
	}
	public void setDrfgrnno(String drfgrnno) {
		this.drfgrnno = drfgrnno;
	}
	public String getMenge2() {
		return menge2;
	}
	public void setMenge2(String menge2) {
		this.menge2 = menge2;
	}
	public String getMenge() {
		return menge;
	}
	public void setMenge(String menge) {
		this.menge = menge;
	}
	public String getMeins() {
		return meins;
	}
	public void setMeins(String meins) {
		this.meins = meins;
	}
	public Float getMenge1() {
		return menge1;
	}
	public void setMenge1(Float menge1) {
		this.menge1 = menge1;
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
	public String getCharg() {
		return charg;
	}
	public void setCharg(String charg) {
		this.charg = charg;
	}
	public String getExdate() {
		return exdate;
	}
	public void setExdate(String exdate) {
		this.exdate = exdate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
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
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	public String getTotalRejectedQtyCount() {
		return totalRejectedQtyCount;
	}
	public void setTotalRejectedQtyCount(String totalRejectedQtyCount) {
		this.totalRejectedQtyCount = totalRejectedQtyCount;
	}
	public String getTotalQtyDelivCount() {
		return totalQtyDelivCount;
	}
	public void setTotalQtyDelivCount(String totalQtyDelivCount) {
		this.totalQtyDelivCount = totalQtyDelivCount;
	}
	public String getTotalShortExpItemCount() {
		return totalShortExpItemCount;
	}
	public void setTotalShortExpItemCount(String totalShortExpItemCount) {
		this.totalShortExpItemCount = totalShortExpItemCount;
	}
	public String getGrnlineitemid() {
		return grnlineitemid;
	}
	public void setGrnlineitemid(String grnlineitemid) {
		this.grnlineitemid = grnlineitemid;
	}
	public long getPolineitemid() {
		return polineitemid;
	}
	public void setPolineitemid(long polineitemid) {
		this.polineitemid = polineitemid;
	}
	public String getPoItemCategory() {
		return poItemCategory;
	}
	public void setPoItemCategory(String poItemCategory) {
		this.poItemCategory = poItemCategory;
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
	
	
	
	
	
}

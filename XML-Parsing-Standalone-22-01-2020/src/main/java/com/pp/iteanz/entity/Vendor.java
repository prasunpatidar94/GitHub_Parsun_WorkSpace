package com.pp.iteanz.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;






@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@EnableTransactionManagement
//@Transactional
@Proxy(lazy = false)
public class Vendor {
	
	@Id
	private long vid;
	private String businessNature;
	private String firmNature;
	private String otherFirm;
	private String orgName;
	private String mobile;
	private String ceoName;
	private String email;
	private String designation;
	private String telNo;
	private String totalArea;
	private String refriNos;
	private String storeLiscNo;
	private String storeLiseDate;
	private String ambStorArea;
	private String coldStoreArea;
	private String shortDesc;
	private String staffNos;
	private String gstRegNo;
	private String pan;
	
	private String changeddate;
	private String createddate;
	private String createdBy;
	private String changeddBy;
	private String sessionid;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = Product.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "vid")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<Product> products;
	
	@OneToMany(fetch = FetchType.EAGER,targetEntity = ContactInfo.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "vid")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<ContactInfo> contacts;
	
	private String smeRegNo;
	private String servPerInMarket;
	private String fdaRegNo;
	private String fdaRegVal;
	private String impLiscNo;
	private String impoLiscVal;
	//private int version;
	private String dt;
	private String df;
	private String dfdt;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = VendorDocument.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "vid")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<VendorDocument> venDocuments;

	private String bankName;
	private String branchName;
	private String ifsc;
	private String accNo;

	private String iso9001;
	private String iso9002;
	private String fda;
	private String ce;
	private String bis;
	private String otherCertApprove;
	
	private String status;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getVid() {
		return vid;
	}
	public void setVid(long vid) {
		this.vid = vid;
	}
	public String getBusinessNature() {
		return businessNature;
	}
	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}
	public String getFirmNature() {
		return firmNature;
	}
	public void setFirmNature(String firmNature) {
		this.firmNature = firmNature;
	}
	public String getOtherFirm() {
		return otherFirm;
	}
	public void setOtherFirm(String otherFirm) {
		this.otherFirm = otherFirm;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCeoName() {
		return ceoName;
	}
	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getTotalArea() {
		return totalArea;
	}
	public void setTotalArea(String totalArea) {
		this.totalArea = totalArea;
	}
	public String getRefriNos() {
		return refriNos;
	}
	public void setRefriNos(String refriNos) {
		this.refriNos = refriNos;
	}
	public String getStoreLiscNo() {
		return storeLiscNo;
	}
	public void setStoreLiscNo(String storeLiscNo) {
		this.storeLiscNo = storeLiscNo;
	}
	public String getStoreLiseDate() {
		return storeLiseDate;
	}
	public void setStoreLiseDate(String storeLiseDate) {
		this.storeLiseDate = storeLiseDate;
	}
	public String getAmbStorArea() {
		return ambStorArea;
	}
	public void setAmbStorArea(String ambStorArea) {
		this.ambStorArea = ambStorArea;
	}
	public String getColdStoreArea() {
		return coldStoreArea;
	}
	public void setColdStoreArea(String coldStoreArea) {
		this.coldStoreArea = coldStoreArea;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	public String getStaffNos() {
		return staffNos;
	}
	public void setStaffNos(String staffNos) {
		this.staffNos = staffNos;
	}
	public String getGstRegNo() {
		return gstRegNo;
	}
	public void setGstRegNo(String gstRegNo) {
		this.gstRegNo = gstRegNo;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public List<ContactInfo> getContacts() {
		return contacts;
	}
	public void setContacts(List<ContactInfo> contacts) {
		this.contacts = contacts;
	}
	public String getSmeRegNo() {
		return smeRegNo;
	}
	public void setSmeRegNo(String smeRegNo) {
		this.smeRegNo = smeRegNo;
	}
	public String getServPerInMarket() {
		return servPerInMarket;
	}
	public void setServPerInMarket(String servPerInMarket) {
		this.servPerInMarket = servPerInMarket;
	}
	public String getFdaRegNo() {
		return fdaRegNo;
	}
	public void setFdaRegNo(String fdaRegNo) {
		this.fdaRegNo = fdaRegNo;
	}
	public String getFdaRegVal() {
		return fdaRegVal;
	}
	public void setFdaRegVal(String fdaRegVal) {
		this.fdaRegVal = fdaRegVal;
	}
	public String getImpLiscNo() {
		return impLiscNo;
	}
	public void setImpLiscNo(String impLiscNo) {
		this.impLiscNo = impLiscNo;
	}
	public String getImpoLiscVal() {
		return impoLiscVal;
	}
	public void setImpoLiscVal(String impoLiscVal) {
		this.impoLiscVal = impoLiscVal;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getIfsc() {
		return ifsc;
	}
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getIso9001() {
		return iso9001;
	}
	public void setIso9001(String iso9001) {
		this.iso9001 = iso9001;
	}
	public String getIso9002() {
		return iso9002;
	}
	public void setIso9002(String iso9002) {
		this.iso9002 = iso9002;
	}
	public String getFda() {
		return fda;
	}
	public void setFda(String fda) {
		this.fda = fda;
	}
	public String getCe() {
		return ce;
	}
	public void setCe(String ce) {
		this.ce = ce;
	}
	public String getBis() {
		return bis;
	}
	public void setBis(String bis) {
		this.bis = bis;
	}
	public String getOtherCertApprove() {
		return otherCertApprove;
	}
	public void setOtherCertApprove(String otherCertApprove) {
		this.otherCertApprove = otherCertApprove;
	}
	public String getDfdt() {
		return dfdt;
	}
	public void setDfdt(String dfdt) {
		this.dfdt = dfdt;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public String getDf() {
		return df;
	}
	public void setDf(String df) {
		this.df = df;
	}
	public List<VendorDocument> getVenDocuments() {
		return venDocuments;
	}
	public void setVenDocuments(List<VendorDocument> venDocuments) {
		this.venDocuments = venDocuments;
	}

	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
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
	/*public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
*/	
}

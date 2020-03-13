package com.pp.iteanz.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Proxy(lazy = false)
//@Transactional

public class Contract {
	
	@Id
	//@GeneratedValue
	private long contractId;
	private String contractType;
	private String contractDescription;
	private String contractStartDate;
	private String contractExpiryDate;
	private String status;
	private String reasonForCancellation;
	private String remarks;
	private String rfpNo;
	private String currency;
	private String MonthDuration;//FieldName : MonthDuration(ContractHeader)	
	private String internalMOUNo;
	private String internalMOUDate;
	private String masterContractNo;
	private String expiryReminderdays;
	private String createdBy;
	private String createdOn;
	private String changedBy;
	private String changedOn;
	private String sessionid;
	private String contractStatus;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = Item.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "contractId", referencedColumnName = "contractId")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<Item> items;
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity = FirstParty.class, cascade = CascadeType.ALL)
	@Fetch (value = FetchMode.SELECT)
	FirstParty firstParty;
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity = SecondParty.class, cascade = CascadeType.ALL)
	@Fetch (value = FetchMode.SELECT)
	SecondParty secondParty;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = Invite.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "contractId", referencedColumnName = "contractId")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<Invite> invites;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = ContractDocument.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "contractId", referencedColumnName = "contractId")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<ContractDocument> contractDocuments;
	
	
	
	
	public String getMonthDuration() {
		return MonthDuration;
	}

	public void setMonthDuration(String monthDuration) {
		MonthDuration = monthDuration;
	}

	public long getContractId() {
		return contractId;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractDescription() {
		return contractDescription;
	}

	public void setContractDescription(String contractDescription) {
		this.contractDescription = contractDescription;
	}

	public String getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public String getContractExpiryDate() {
		return contractExpiryDate;
	}

	public void setContractExpiryDate(String contractExpiryDate) {
		this.contractExpiryDate = contractExpiryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReasonForCancellation() {
		return reasonForCancellation;
	}

	public void setReasonForCancellation(String reasonForCancellation) {
		this.reasonForCancellation = reasonForCancellation;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRfpNo() {
		return rfpNo;
	}

	public void setRfpNo(String rfpNo) {
		this.rfpNo = rfpNo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getInternalMOUNo() {
		return internalMOUNo;
	}

	public void setInternalMOUNo(String internalMOUNo) {
		this.internalMOUNo = internalMOUNo;
	}

	public String getInternalMOUDate() {
		return internalMOUDate;
	}

	public void setInternalMOUDate(String internalMOUDate) {
		this.internalMOUDate = internalMOUDate;
	}

	public String getMasterContractNo() {
		return masterContractNo;
	}

	public void setMasterContractNo(String masterContractNo) {
		this.masterContractNo = masterContractNo;
	}

	public String getExpiryReminderdays() {
		return expiryReminderdays;
	}

	public void setExpiryReminderdays(String expiryReminderdays) {
		this.expiryReminderdays = expiryReminderdays;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public String getChangedOn() {
		return changedOn;
	}

	public void setChangedOn(String changedOn) {
		this.changedOn = changedOn;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public FirstParty getFirstParty() {
		return firstParty;
	}

	public void setFirstParty(FirstParty firstParty) {
		this.firstParty = firstParty;
	}

	public SecondParty getSecondParty() {
		return secondParty;
	}

	public void setSecondParty(SecondParty secondParty) {
		this.secondParty = secondParty;
	}

	public List<Invite> getInvites() {
		return invites;
	}

	public void setInvites(List<Invite> invites) {
		this.invites = invites;
	}

	public List<ContractDocument> getContractDocuments() {
		return contractDocuments;
	}

	public void setContractDocuments(List<ContractDocument> contractDocuments) {
		this.contractDocuments = contractDocuments;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	
}

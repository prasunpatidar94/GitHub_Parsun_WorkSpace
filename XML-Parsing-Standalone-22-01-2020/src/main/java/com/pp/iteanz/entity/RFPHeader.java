package com.pp.iteanz.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

public class RFPHeader {
	@Id
	//@GeneratedValue
	private long rfpno;
	private int version;
	private String versioninfo;
	private String bukrs;
	private String ekorg;
	//private long proposalno;
	private String status;
	//private String dfdt;
	private String dt;
	private String df;
	private String transactiontype;
	private String prereq;
	private String changeddate;
	private String createddate;
	private String createdby;
	private String sessionid;
	private String changeddBy;
	private String rfpmode;
	
	public String getRfpmode() {
		return rfpmode;
	}

	public void setRfpmode(String rfpmode) {
		this.rfpmode = rfpmode;
	}

	/*@ManyToOne(targetEntity = UserMaster.class)
	@JoinColumn(name = "changedBy")
	UserMaster changedBy;
	
		public UserMaster getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(UserMaster changedBy) {
		this.changedBy = changedBy;
	}
	*/
	@OneToMany(fetch = FetchType.EAGER, targetEntity = RFPItem.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "rfpno")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<RFPItem> item;
	 
	@OneToMany(fetch = FetchType.EAGER, targetEntity = RFPSection.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "rfpno")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<RFPSection> section;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = RFPInvitedVendors.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "rfpno")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<RFPInvitedVendors> invitedvendors;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = RFPParticipant.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "rfpno")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<RFPParticipant> participant;
	
	public List<RFPItem> getItem() {
		return item;
	}

	public void setItem(List<RFPItem> item) {
		this.item = item;
	}

	public List<RFPSection> getSection() {
		return section;
	}

	public void setSection(List<RFPSection> section) {
		this.section = section;
	}

	public List<RFPInvitedVendors> getInvitedvendors() {
		return invitedvendors;
	}

	public void setInvitedvendors(List<RFPInvitedVendors> invitedvendors) {
		this.invitedvendors = invitedvendors;
	}

	public List<RFPParticipant> getParticipant() {
		return participant;
	}

	public void setParticipant(List<RFPParticipant> participant) {
		this.participant = participant;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public long getRfpno() {
		return rfpno;
	}

	public void setRfpno(long rfpno) {
		this.rfpno = rfpno;
	}

	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getVersioninfo() {
		return versioninfo;
	}

	public void setVersioninfo(String versioninfo) {
		this.versioninfo = versioninfo;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}
	public String getEkorg() {
		return ekorg;
	}

	public void setEkorg(String ekorg) {
		this.ekorg = ekorg;
	}

	
	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	public String getPrereq() {
		return prereq;
	}

	public void setPrereq(String prereq) {
		this.prereq = prereq;
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

	public String getChangeddBy() {
		return changeddBy;
	}

	public void setChangeddBy(String changeddBy) {
		this.changeddBy = changeddBy;
	}
	/*public long getProposalno() {
		return proposalno;
	}

	public void setProposalno(long proposalno) {
		this.proposalno = proposalno;
	}
	*/
	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}


}


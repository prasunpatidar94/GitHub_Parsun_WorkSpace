package com.iteanz.srl.domain;

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
public class VPHeader{
	@Id
	//@GeneratedValue
	//private int vpid;
	private long proposalno;
	private String version;
	private String versioninfo;
	private String lifnr;
	private long rfpno;
	private String rfpversion;
	private String requester;
	private String ekgrp;
	private  String ekorg;
	private String status;
	private String addtext;
	private String startdate;
	private String enddate;
	private String transactiontype;
	private String bukrs;
	private String prereq;
	private String commhistory;
	private String comments;
	private String createdby;
	private String changedby;
	private String createddate;
	private String changeddate;
	private String sessionid;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = VPItem.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "proposalno")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<VPItem> vpitem;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = VPSection.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "proposalno")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<VPSection> vpsection;
	
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = VPGenProposal.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "proposalno")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<VPGenProposal> vpgenproposal;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = VPTCProposal.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "proposalno")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<VPTCProposal> vptcproposal;
	
	/*
	@OneToMany(fetch = FetchType.EAGER, targetEntity = RFPHeader.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "rfpno", referencedColumnName = "rfpno")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<RFPHeader> rfpheaderlist;
	*/
	
	
	public long getProposalno() {
		return proposalno;
	}
	public void setProposalno(long proposalno) {
		this.proposalno = proposalno;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVersioninfo() {
		return versioninfo;
	}
	public void setVersioninfo(String versioninfo) {
		this.versioninfo = versioninfo;
	}
	public String getLifnr() {
		return lifnr;
	}
	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}
	public long getRfpno() {
		return rfpno;
	}
	public void setRfpno(long rfpno) {
		this.rfpno = rfpno;
	}
	public String getRfpversion() {
		return rfpversion;
	}
	public void setRfpversion(String rfpversion) {
		this.rfpversion = rfpversion;
	}
	public String getRequester() {
		return requester;
	}
	public void setRequester(String requester) {
		this.requester = requester;
	}
	public String getEkgrp() {
		return ekgrp;
	}
	public void setEkgrp(String ekgrp) {
		this.ekgrp = ekgrp;
	}
	public String getEkorg() {
		return ekorg;
	}
	public void setEkorg(String ekorg) {
		this.ekorg = ekorg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddtext() {
		return addtext;
	}
	public void setAddtext(String addtext) {
		this.addtext = addtext;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getTransactiontype() {
		return transactiontype;
	}
	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}
	public String getBukrs() {
		return bukrs;
	}
	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}
	public String getPrereq() {
		return prereq;
	}
	public void setPrereq(String prereq) {
		this.prereq = prereq;
	}
	public String getCommhistory() {
		return commhistory;
	}
	public void setCommhistory(String commhistory) {
		this.commhistory = commhistory;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getChangedby() {
		return changedby;
	}
	public void setChangedby(String changedby) {
		this.changedby = changedby;
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
	public List<VPItem> getVpitem() {
		return vpitem;
	}
	public void setVpitem(List<VPItem> vpitem) {
		this.vpitem = vpitem;
	}
	public List<VPSection> getVpsection() {
		return vpsection;
	}
	public void setVpsection(List<VPSection> vpsection) {
		this.vpsection = vpsection;
	}
	
	public List<VPGenProposal> getVpgenproposal() {
		return vpgenproposal;
	}
	public void setVpgenproposal(List<VPGenProposal> vpgenproposal) {
		this.vpgenproposal = vpgenproposal;
	}
	public List<VPTCProposal> getVptcproposal() {
		return vptcproposal;
	}
	public void setVptcproposal(List<VPTCProposal> vptcproposal) {
		this.vptcproposal = vptcproposal;
	}
	/*
	public List<RFPHeader> getRfpheaderlist() {
		return rfpheaderlist;
	}
	public void setRfpheaderlist(List<RFPHeader> rfpheaderlist) {
		this.rfpheaderlist = rfpheaderlist;
	}
	*/
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
}

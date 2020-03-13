package com.iteanz.srl.domain;


import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Proxy(lazy = false)
//@Table(name = "RFPSection")
public class VPSection {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "VPSection_seq", allocationSize = 1, name = "VPSection_s")
	@Column(name = "vpsecid")
	private Long vpsecid;
	
	private long rfpno;
	//private long proposalno;
	private String version;
	private String section;
	private String condition;
	private String conditiontext;
	private String attachment;
	private String attachmentno;
	private String filename;
	private String createdby;
	private String changedby;
	private String changeddate;
	private String createddate;
	private String flag;
	
	@JsonProperty( value = "data")
	@JsonIgnore
	private Blob data;

	public Long getVpsecid() {
		return vpsecid;
	}

	public void setVpsecid(Long vpsecid) {
		this.vpsecid = vpsecid;
	}

	public long getRfpno() {
		return rfpno;
	}

	public void setRfpno(long rfpno) {
		this.rfpno = rfpno;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getConditiontext() {
		return conditiontext;
	}

	public void setConditiontext(String conditiontext) {
		this.conditiontext = conditiontext;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentno() {
		return attachmentno;
	}

	public void setAttachmentno(String attachmentno) {
		this.attachmentno = attachmentno;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Blob getData() {
		return data;
	}

	public void setData(Blob data) {
		this.data = data;
	}
	
	
	
}
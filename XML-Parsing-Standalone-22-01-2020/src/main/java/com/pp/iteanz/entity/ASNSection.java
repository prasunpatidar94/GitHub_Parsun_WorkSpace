package com.pp.iteanz.entity;

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
public class ASNSection {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "asnsection_seq", allocationSize = 1, name = "asnsection_s")
	@Column(name = "asnsecid")
	private Long asnsecid;

	
	private long ebeln;
	private String conditiontext;
	private String attachment;
	private String attachmentno;
	private String filename;
	//private String flag;
	
	@JsonProperty( value = "data")
	@JsonIgnore
	private Blob data;

	public Long getAsnsecid() {
		return asnsecid;
	}

	public void setAsnsecid(Long asnsecid) {
		this.asnsecid = asnsecid;
	}

	public long getEbeln() {
		return ebeln;
	}

	public void setEbeln(long ebeln) {
		this.ebeln = ebeln;
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

	public Blob getData() {
		return data;
	}

	public void setData(Blob data) {
		this.data = data;
	}
	

	
}

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
public class VendorDocument {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "VendorDocument_seq", allocationSize = 1, name = "VendorDocument_s")
	@Column(name = "id")

	private int id;
	private String attachment;
	private String attachmentno;
	private String condition;
	private String conditiontext;
	private String filename;
	private String flag;
	private String saction;
	
	@JsonProperty( value = "data")
	@JsonIgnore
	private Blob data;
	
	public Blob getData() {
		return data;
	}
	public void setData(Blob data) {
		this.data = data;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getSaction() {
		return saction;
	}
	public void setSaction(String saction) {
		this.saction = saction;
	}
	
	

}

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

public class ContractDocument {
	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "contractdocument_seq", allocationSize = 1, name = "contractdocument_s")
	@Column(name = "id")
	private Long id;

	private String attachmentDetails;
	private String createdBy;
	private String createdDate;
	private String attachment;
	private String attachmentno;
	private String condition;
	private String conditiontext;
	private String filename;
	private String flag;
	private String saction;
	
	private int Final;////	FieldName : Final


	
	
	@JsonProperty( value = "data")
	@JsonIgnore
	private Blob data;
	
	public Blob getData() {
		return data;
	}
	public void setData(Blob data) {
		this.data = data;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getAttachmentDetails() {
		return attachmentDetails;
	}
	public void setAttachmentDetails(String attachmentDetails) {
		this.attachmentDetails = attachmentDetails;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;

	}
	public int getFinal() {
		return Final;
	}
	public void setFinal(int final1) {
		Final = final1;
	}
	
	
}

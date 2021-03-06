package com.pp.iteanz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Proxy;


@Entity
@Proxy(lazy = false)
public class TechnoCriteria {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "TechnoCriteria_seq", allocationSize = 1, name = "TechnoCriteria_s")
	@Column(name = "criteriaid")
	private Long criteriaid;
	//private long rfpno;
	private String criteria;
    private String subcriteria;
    private int weightage;
    private int maxscore;
    private String resperson;
    private String remark;
    private String changeddate;
	private String createddate;
	private String createdBy;
	private String changeddBy;
	public Long getCriteriaid() {
		return criteriaid;
	}
	public void setCriteriaid(Long criteriaid) {
		this.criteriaid = criteriaid;
	}
	public String getCriteria() {
		return criteria;
	}
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
	public String getSubcriteria() {
		return subcriteria;
	}
	public void setSubcriteria(String subcriteria) {
		this.subcriteria = subcriteria;
	}
	public int getWeightage() {
		return weightage;
	}
	public void setWeightage(int weightage) {
		this.weightage = weightage;
	}
	public int getMaxscore() {
		return maxscore;
	}
	public void setMaxscore(int maxscore) {
		this.maxscore = maxscore;
	}
	public String getResperson() {
		return resperson;
	}
	public void setResperson(String resperson) {
		this.resperson = resperson;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	
}

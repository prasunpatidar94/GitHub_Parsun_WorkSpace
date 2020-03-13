package com.iteanz.srl.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="WorkflowConfig")
public class WorkflowConfig {
	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "WorkflowConfig_seq", allocationSize = 1, name = "WorkflowConfig_s")
	@Column(name = "id")
	private Long id;
	
	
	@Column(name = "appid")
	String appid;
	
	/*@Column(name = "reqid")
	String reqid;*/

	@Column(name = "description")
	String description;
	
	@Column(name = "subprocess")
	String subProcess;
	
	@Column(name = "aprvLevels")
	int aprvLevels;
	
	@Column(name = "condition")
	String condition;
	
	/*@Column(name = "actstatus")
	String actstatus;*/
	
	@Column(name = "aprv1")
	String aprv1;
	
	@Column(name = "aprv2")
	String aprv2;
	
	@Column(name = "aprv3")
	String aprv3;
	
	@Column(name = "aprv4")
	String aprv4;
	
	@Column(name = "aprv5")
	String aprv5;
	
	@Column(name = "aprv6")
	String aprv6;
	
	@Column(name = "aprv7")
	String aprv7;
	
	@Column(name = "aprv8")
	String aprv8;
	
	@Column(name = "aprv9")
	String aprv9;
	
	@Column(name = "aprv10")
	String aprv10;
	
	@Column(name = "createdBy")
	String createdBy;
	
	@Column(name = "changeddBy")
	String changeddBy;
	
	@Column(name = "createdDate")
	Date createdDate;
	
	@Column(name = "changedDate")
	Date changedDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(value = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "status")
	private Status status;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public int getAprvLevels() {
		return aprvLevels;
	}

	public void setAprvLevels(int aprvLevels) {
		this.aprvLevels = aprvLevels;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	/*public String getActstatus() {
		return actstatus;
	}

	public void setActstatus(String actstatus) {
		this.actstatus = actstatus;
	}*/

	public String getAprv1() {
		return aprv1;
	}

	public void setAprv1(String aprv1) {
		this.aprv1 = aprv1;
	}

	public String getAprv2() {
		return aprv2;
	}

	public void setAprv2(String aprv2) {
		this.aprv2 = aprv2;
	}

	public String getAprv3() {
		return aprv3;
	}

	public void setAprv3(String aprv3) {
		this.aprv3 = aprv3;
	}

	public String getAprv4() {
		return aprv4;
	}

	public void setAprv4(String aprv4) {
		this.aprv4 = aprv4;
	}

	public String getAprv5() {
		return aprv5;
	}

	public void setAprv5(String aprv5) {
		this.aprv5 = aprv5;
	}

	public String getAprv6() {
		return aprv6;
	}

	public void setAprv6(String aprv6) {
		this.aprv6 = aprv6;
	}

	public String getAprv7() {
		return aprv7;
	}

	public void setAprv7(String aprv7) {
		this.aprv7 = aprv7;
	}

	public String getAprv8() {
		return aprv8;
	}

	public void setAprv8(String aprv8) {
		this.aprv8 = aprv8;
	}

	public String getAprv9() {
		return aprv9;
	}

	public void setAprv9(String aprv9) {
		this.aprv9 = aprv9;
	}

	public String getAprv10() {
		return aprv10;
	}

	public void setAprv10(String aprv10) {
		this.aprv10 = aprv10;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getChangeddBy() {
		return changeddBy;
	}

	public void setChangeddBy(String changeddBy) {
		this.changeddBy = changeddBy;
	}

	public Date getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(Date changedDate) {
		this.changedDate = changedDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	public String getSubProcess() {
		return subProcess;
	}

	public void setSubProcess(String subProcess) {
		this.subProcess = subProcess;
	}
	
/*	public String getReqid() {
		return reqid;
	}

	public void setReqid(String reqid) {
		this.reqid = reqid;
	}*/

}


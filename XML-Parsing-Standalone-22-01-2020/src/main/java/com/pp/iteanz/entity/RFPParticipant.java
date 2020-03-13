package com.pp.iteanz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
//@Table(name = "RFPParticipant")
public class RFPParticipant {

	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "RFPParticipant_seq", allocationSize = 1, name = "RFPParticipant_s")
	@Column(name = "participid")
	private Long participid;
	private String version;
	private String userid;
	private String name;
	private String email;
	private String mobile;
	private String position;
	private String createdby;
	private String changedby;
	private String changeddate;
	private String createddate;
	private long cp_fk;
	
	public long getCp_fk() {
		return cp_fk;
	}
	public void setCp_fk(long cp_fk) {
		this.cp_fk = cp_fk;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(value={CascadeType.PERSIST})
	@JoinColumn(name="department")
	private RoleModel department;
	
	
	
	public Long getParticipid() {
		return participid;
	}
	public void setParticipid(Long participid) {
		this.participid = participid;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
/*	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	*/
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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
	/* public long getProposalno() {
		return proposalno;
	}
	public void setProposalno(long proposalno) {
		this.proposalno = proposalno;
	}
*/
	public RoleModel getDepartment() {
		return department;
	}
	public void setDepartment(RoleModel department) {
		this.department = department;
	}
	
	
	
}

package com.iteanz.srl.domain;

import java.sql.Date;
import java.sql.Time;

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

//@JsonIgnoreProperties({"predecessors"}) 
//@JsonSEQUENCEInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="idHitoAux")

@Entity
@Table(name = "UserMaster")
public class UserMaster {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "UserMaster_seq", allocationSize = 1, name = "UserMaster_s")
	@Column(name = "id")
	private Long id;

	@Column(name = "adrid")
	String adrid;

	@Column(name = "pswrd")
	String pswrd;
	
	@Column(name = "sapId")
	String sapId;

	@Column(name = "begda")
	Date begda;

	@Column(name = "endda")
	Date endda;

	@Column(name = "pnam")
	String pnam;
	
	@Column(name = "eunam")
	String eunam;

	@Column(name = "email")
	String email;
	
	@Column(name = "contact")
	String contact;
	
	@Column(name = "userDesignation")
	String userDesignation;
	
	/*@Column(name = "deptdesc")
	String deptdesc;
	
	@Column(name = "desigdesc")
	String desigdesc;
	*/

	@Column(name = "pmble")
	String pmble;

	@Column(name = "bptype")
	String bptype;
	
	@Column(name = "adflag")
	String adflag;

	@Column(name = "crdat")
	Date crdat;

	@Column(name = "crtim")
	Time crtim;

	@Column(name = "cddat")
	Date cddat;

	@Column(name = "cdtim")
	Time cdtim;
	
	@Column(name = "bpvale")
	String bpvale;
	
	@Column(name = "sunam")
	String sunam;
	
	@Column(name = "plant")
	String plant;
	
	@Column(name = "materialspoc")
	String materialspoc;
	
	@Column(name = "labhead")
	String labhead;
	
	@Column(name = "buhead")
	String buhead;
	
	@Column(name = "techhead")
	String techhead;
	
	@Column(name = "prochead")
	String prochead;
	
	@Column(name = "inactive")
	int inactive;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(value={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name="USER_ROLL")
	private RoleModel userRoll;

	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(value = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "status")
	private Status status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade(value = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "department")
	private Department department;

	@ManyToOne(targetEntity = UserMaster.class)
	@JoinColumn(name = "createdBy")
	UserMaster createdBy;

	@ManyToOne(targetEntity = UserMaster.class)
	@JoinColumn(name = "changedBy")
	UserMaster changedBy;

	@ManyToOne(targetEntity = ApproverMaster.class)
	@JoinColumn(name = "approverMaster")
	ApproverMaster approverMaster;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdrid() {
		return adrid;
	}

	public void setAdrid(String adrid) {
		this.adrid = adrid;
	}

	public String getPswrd() {
		return pswrd;
	}

	public void setPswrd(String pswrd) {
		this.pswrd = pswrd;
	}

	public String getSapId() {
		return sapId;
	}

	public void setSapId(String sapId) {
		this.sapId = sapId;
	}

	public Date getBegda() {
		return begda;
	}

	public void setBegda(Date begda) {
		this.begda = begda;
	}

	public Date getEndda() {
		return endda;
	}

	public void setEndda(Date endda) {
		this.endda = endda;
	}

	public String getEunam() {
		return eunam;
	}

	public void setEunam(String eunam) {
		this.eunam = eunam;
	}

	public String getPnam() {
		return pnam;
	}

	public void setPnam(String pnam) {
		this.pnam = pnam;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPmble() {
		return pmble;
	}

	public void setPmble(String pmble) {
		this.pmble = pmble;
	}

	public String getBptype() {
		return bptype;
	}

	public void setBptype(String bptype) {
		this.bptype = bptype;
	}

	public String getAdflag() {
		return adflag;
	}

	public void setAdflag(String adflag) {
		this.adflag = adflag;
	}

	public Date getCrdat() {
		return crdat;
	}

	public void setCrdat(Date crdat) {
		this.crdat = crdat;
	}

	public Time getCrtim() {
		return crtim;
	}

	public void setCrtim(Time crtim) {
		this.crtim = crtim;
	}

	public Date getCddat() {
		return cddat;
	}

	public void setCddat(Date cddat) {
		this.cddat = cddat;
	}

	public Time getCdtim() {
		return cdtim;
	}

	public void setCdtim(Time cdtim) {
		this.cdtim = cdtim;
	}

	public String getBpvale() {
		return bpvale;
	}

	public void setBpvale(String bpvale) {
		this.bpvale = bpvale;
	}

	public String getSunam() {
		return sunam;
	}

	public void setSunam(String sunam) {
		this.sunam = sunam;
	}

	public String getUserDesignation() {
		return userDesignation;
	}

	public void setUserDesignation(String userDesignation) {
		this.userDesignation = userDesignation;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getMaterialspoc() {
		return materialspoc;
	}

	public void setMaterialspoc(String materialspoc) {
		this.materialspoc = materialspoc;
	}

	public String getLabhead() {
		return labhead;
	}

	public void setLabhead(String labhead) {
		this.labhead = labhead;
	}

	public String getBuhead() {
		return buhead;
	}

	public void setBuhead(String buhead) {
		this.buhead = buhead;
	}

	public String getTechhead() {
		return techhead;
	}

	public void setTechhead(String techhead) {
		this.techhead = techhead;
	}

	public String getProchead() {
		return prochead;
	}

	public void setProchead(String prochead) {
		this.prochead = prochead;
	}

	public int getInactive() {
		return inactive;
	}

	public void setInactive(int inactive) {
		this.inactive = inactive;
	}

	public RoleModel getUserRoll() {
		return userRoll;
	}

	public void setUserRoll(RoleModel userRoll) {
		this.userRoll = userRoll;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public UserMaster getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserMaster createdBy) {
		this.createdBy = createdBy;
	}

	public UserMaster getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(UserMaster changedBy) {
		this.changedBy = changedBy;
	}

	public ApproverMaster getApproverMaster() {
		return approverMaster;
	}

	public void setApproverMaster(ApproverMaster approverMaster) {
		this.approverMaster = approverMaster;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
}

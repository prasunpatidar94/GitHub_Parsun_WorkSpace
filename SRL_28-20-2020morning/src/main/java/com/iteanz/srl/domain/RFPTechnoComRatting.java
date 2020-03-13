package com.iteanz.srl.domain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RFPTechnoComRatting")
public class RFPTechnoComRatting {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "RFPSection_seq", allocationSize = 1, name = "RFPSection_s")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "rfpno")
	String rfpno;
	
	@Column(name = "version")
	String version;
	
	@Column(name = "lifnr")
	String lifnr;
	
	@Column(name = "maincriteria")
	String maincriteria;
	
	@Column(name = "subcriteria")
	String subcriteria;
	
	@Column(name = "weight")
	String weight;
	
	@Column(name = "maxscore")
	String maxscore;
	
	@Column(name = "userid")
	String userid;
	
	@Column(name = "rating")
	String rating;
	
	@Column(name = "rating1")
	String rating1;
	
	@Column(name = "rating2")
	String rating2;
	
	@Column(name = "rating3")
	String rating3;
	
	@Column(name = "rating4")
	String rating4;
	
	@Column(name = "respperson")
	String respperson;
	
	@Column(name = "createdby")
	String createdby;
	
	@Column(name = "changedby")
    String changedby;
	
	@Column(name = "changeddate")
	String changeddate;
	
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

	@Column(name = "createddate")
	String createddate;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRfpno() {
		return rfpno;
	}

	public void setRfpno(String rfpno) {
		this.rfpno = rfpno;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLifnr() {
		return lifnr;
	}

	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}

	public String getMaincriteria() {
		return maincriteria;
	}

	public void setMaincriteria(String maincriteria) {
		this.maincriteria = maincriteria;
	}

	public String getSubcriteria() {
		return subcriteria;
	}

	public void setSubcriteria(String subcriteria) {
		this.subcriteria = subcriteria;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getMaxscore() {
		return maxscore;
	}

	public void setMaxscore(String maxscore) {
		this.maxscore = maxscore;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRating1() {
		return rating1;
	}

	public void setRating1(String rating1) {
		this.rating1 = rating1;
	}

	public String getRating2() {
		return rating2;
	}

	public void setRating2(String rating2) {
		this.rating2 = rating2;
	}

	public String getRating3() {
		return rating3;
	}

	public void setRating3(String rating3) {
		this.rating3 = rating3;
	}

	public String getRating4() {
		return rating4;
	}

	public void setRating4(String rating4) {
		this.rating4 = rating4;
	}

	public String getRespperson() {
		return respperson;
	}

	public void setRespperson(String respperson) {
		this.respperson = respperson;
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


}


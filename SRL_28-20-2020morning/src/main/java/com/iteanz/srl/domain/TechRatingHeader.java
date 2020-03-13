package com.iteanz.srl.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@EnableTransactionManagement
//@Transactional
@Proxy(lazy = false)
public class TechRatingHeader {
	@Id
	private long rfpno;
	private String sessionid;
	private String changeddate;
	private String createddate;
	private String createdby;
	private String changeddBy;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity = TechnoRatings.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "rfpno")
	@Fetch (value = FetchMode.SUBSELECT)
	private List<TechnoRatings> rating;


	public long getRfpno() {
		return rfpno;
	}


	public void setRfpno(long rfpno) {
		this.rfpno = rfpno;
	}


	
	public String getSessionid() {
		return sessionid;
	}


	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getChangeddate() {
		return changeddate;
	}


	public List<TechnoRatings> getRating() {
		return rating;
	}


	public void setRating(List<TechnoRatings> rating) {
		this.rating = rating;
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


	public String getCreatedby() {
		return createdby;
	}


	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}


	public String getChangeddBy() {
		return changeddBy;
	}


	public void setChangeddBy(String changeddBy) {
		this.changeddBy = changeddBy;
	}



}

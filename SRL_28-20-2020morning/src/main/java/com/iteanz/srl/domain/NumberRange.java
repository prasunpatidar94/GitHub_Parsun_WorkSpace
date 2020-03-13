package com.iteanz.srl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "NumberRange")
public class NumberRange {
	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "numberrange_seq", allocationSize = 1, name = "numberrange_s")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "year")
    private String year;
	
	@Column(name = "transationType")
    private String transationType;
	
	@Column(name = "fromNumber")
    private long fromNumber;
	
	@Column(name = "toNumber")
    private int toNumber;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTransationType() {
		return transationType;
	}

	public void setTransationType(String transationType) {
		this.transationType = transationType;
	}

	public long getFromNumber() {
		return fromNumber;
	}

	public void setFromNumber(long fromNumber) {
		this.fromNumber = fromNumber;
	}

	public int getToNumber() {
		return toNumber;
	}

	public void setToNumber(int toNumber) {
		this.toNumber = toNumber;
	}


}

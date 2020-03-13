package com.iteanz.srl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name="Currency")
public class Currency {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "currency_seq", allocationSize = 1, name = "currency_s")
	@Column(name = "id")
	private Long id;

	
	@Column(name="waers")
	String waers;
	
	@Column(name="currencydesc")
	String currencydesc;
	
	@Column(name="symbol")
	String symbol;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWaers() {
		return waers;
	}

	public void setWaers(String waers) {
		this.waers = waers;
	}

	public String getCurrencydesc() {
		return currencydesc;
	}

	public void setCurrencydesc(String currencydesc) {
		this.currencydesc = currencydesc;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	
	

}

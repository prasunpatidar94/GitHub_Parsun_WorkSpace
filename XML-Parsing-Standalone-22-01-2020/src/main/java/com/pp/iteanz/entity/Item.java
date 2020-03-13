package com.pp.iteanz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Item {
	
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "item_seq", allocationSize = 1, name = "item_s")
	@Column(name = "itemId")
	private Long itemId;
	//private String materialType;
	private String maktx;
	private String matnr;	
	private String quantity;
	private String oRBasicPrice;	
	private String equipmentSrNo;
	private String automatedSemiAutomated;	
	private String labType;	
	private String vendorSuggestion;
	private String monthlyTestLoad;
	private String standardAccessories;
	private String controlCalibrator;
	private String oemName;
	private String instalationType;
	private String countryOfOrigion;
	private String equipTransfered;
	private String amcCharges;
	private String cmcCharges;
	private String monthlyConsumptionValue;
	private String newEquipRRNo;
	//private String transferedEquipInstallationDate;
	private String transEquipInsDate;
	//private String nameoftheNewRREquipmentRequested;
	private String nameoftheNewRREquipReq;
	private String modelNo;
	private String make;
	private long lgort;// storage location
	private String installationDate;
	private String equipReturnPickupDate;
	private String rrReturnFormReceived;
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getoRBasicPrice() {
		return oRBasicPrice;
	}
	public void setoRBasicPrice(String oRBasicPrice) {
		this.oRBasicPrice = oRBasicPrice;
	}
	public String getEquipmentSrNo() {
		return equipmentSrNo;
	}
	public void setEquipmentSrNo(String equipmentSrNo) {
		this.equipmentSrNo = equipmentSrNo;
	}
	public String getAutomatedSemiAutomated() {
		return automatedSemiAutomated;
	}
	public void setAutomatedSemiAutomated(String automatedSemiAutomated) {
		this.automatedSemiAutomated = automatedSemiAutomated;
	}
	public String getLabType() {
		return labType;
	}
	public void setLabType(String labType) {
		this.labType = labType;
	}
	public String getVendorSuggestion() {
		return vendorSuggestion;
	}
	public void setVendorSuggestion(String vendorSuggestion) {
		this.vendorSuggestion = vendorSuggestion;
	}
	public String getMonthlyTestLoad() {
		return monthlyTestLoad;
	}
	public void setMonthlyTestLoad(String monthlyTestLoad) {
		this.monthlyTestLoad = monthlyTestLoad;
	}
	public String getStandardAccessories() {
		return standardAccessories;
	}
	public void setStandardAccessories(String standardAccessories) {
		this.standardAccessories = standardAccessories;
	}
	public String getControlCalibrator() {
		return controlCalibrator;
	}
	public void setControlCalibrator(String controlCalibrator) {
		this.controlCalibrator = controlCalibrator;
	}
	public String getOemName() {
		return oemName;
	}
	public void setOemName(String oemName) {
		this.oemName = oemName;
	}
	public String getInstalationType() {
		return instalationType;
	}
	public void setInstalationType(String instalationType) {
		this.instalationType = instalationType;
	}
	public String getCountryOfOrigion() {
		return countryOfOrigion;
	}
	public void setCountryOfOrigion(String countryOfOrigion) {
		this.countryOfOrigion = countryOfOrigion;
	}
	public String getEquipTransfered() {
		return equipTransfered;
	}
	public void setEquipTransfered(String equipTransfered) {
		this.equipTransfered = equipTransfered;
	}
	public String getAmcCharges() {
		return amcCharges;
	}
	public void setAmcCharges(String amcCharges) {
		this.amcCharges = amcCharges;
	}
	public String getCmcCharges() {
		return cmcCharges;
	}
	public void setCmcCharges(String cmcCharges) {
		this.cmcCharges = cmcCharges;
	}
	public String getMonthlyConsumptionValue() {
		return monthlyConsumptionValue;
	}
	public void setMonthlyConsumptionValue(String monthlyConsumptionValue) {
		this.monthlyConsumptionValue = monthlyConsumptionValue;
	}
	public String getNewEquipRRNo() {
		return newEquipRRNo;
	}
	public void setNewEquipRRNo(String newEquipRRNo) {
		this.newEquipRRNo = newEquipRRNo;
	}
	public String getTransEquipInsDate() {
		return transEquipInsDate;
	}
	public void setTransEquipInsDate(String transEquipInsDate) {
		this.transEquipInsDate = transEquipInsDate;
	}
	public String getNameoftheNewRREquipReq() {
		return nameoftheNewRREquipReq;
	}
	public void setNameoftheNewRREquipReq(String nameoftheNewRREquipReq) {
		this.nameoftheNewRREquipReq = nameoftheNewRREquipReq;
	}
	public String getModelNo() {
		return modelNo;
	}
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public long getLgort() {
		return lgort;
	}
	public void setLgort(long lgort) {
		this.lgort = lgort;
	}
	public String getInstallationDate() {
		return installationDate;
	}
	public void setInstallationDate(String installationDate) {
		this.installationDate = installationDate;
	}
	public String getEquipReturnPickupDate() {
		return equipReturnPickupDate;
	}
	public void setEquipReturnPickupDate(String equipReturnPickupDate) {
		this.equipReturnPickupDate = equipReturnPickupDate;
	}
	public String getRrReturnFormReceived() {
		return rrReturnFormReceived;
	}
	public void setRrReturnFormReceived(String rrReturnFormReceived) {
		this.rrReturnFormReceived = rrReturnFormReceived;
	}
	
	
	
}

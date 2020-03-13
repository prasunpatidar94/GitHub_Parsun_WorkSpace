package com.pp.iteanz.entity;

public class PRItemDetails {

	private long ItemNo;
	private String MaterialNo;
	private String ItemDescription;
	private String Quantity;
	private String UOM;
	private String StorageLocation;
	private String Plant;
	private String price;
	private String Currency;
	private String DeletionIndicator;

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public long getItemNo() {
		return ItemNo;
	}

	public void setItemNo(long itemNo) {
		ItemNo = itemNo;
	}

	public String getMaterialNo() {
		return MaterialNo;
	}

	public void setMaterialNo(String materialNo) {
		MaterialNo = materialNo;
	}

	public String getItemDescription() {
		return ItemDescription;
	}

	public void setItemDescription(String itemDescription) {
		ItemDescription = itemDescription;
	}

	public String getQuantity() {
		return Quantity;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	public String getUOM() {
		return UOM;
	}

	public void setUOM(String uOM) {
		UOM = uOM;
	}

	public String getPlant() {
		return Plant;
	}

	public void setPlant(String plant) {
		Plant = plant;
	}

	

	public String getStorageLocation() {
		return StorageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		StorageLocation = storageLocation;
	}

	public String getDeletionIndicator() {
		return DeletionIndicator;
	}

	public void setDeletionIndicator(String deletionIndicator) {
		DeletionIndicator = deletionIndicator;
	}



}

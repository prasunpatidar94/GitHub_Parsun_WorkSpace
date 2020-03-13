package com.pp.iteanz.entity;

public class PurchaseRequisition {

	private String PRNO;
	private String DocumentType;
	private String PurchasingGroup;
	private String CreatedOn;
	private String CreatedBy;
	private String Text ;
	private PRItemDetails PRItemDetails;
	
	
	

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public String getPRNO() {
		return PRNO;
	}

	public void setPRNO(String pRNO) {
		PRNO = pRNO;
	}

	public String getDocumentType() {
		return DocumentType;
	}

	public void setDocumentType(String documentType) {
		DocumentType = documentType;
	}

	public String getPurchasingGroup() {
		return PurchasingGroup;
	}

	public void setPurchasingGroup(String purchasingGroup) {
		PurchasingGroup = purchasingGroup;
	}

	public String getCreatedOn() {
		return CreatedOn;
	}

	public void setCreatedOn(String createdOn) {
		CreatedOn = createdOn;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public PRItemDetails getPRItemDetails() {
		return PRItemDetails;
	}

	public void setPRItemDetails(PRItemDetails pRItemDetails) {
		PRItemDetails = pRItemDetails;
	}

	@Override
	public String toString() {
		return "PurchaseRequisition [prNo=" + PRNO + ", DocumentType=" + DocumentType + ", PurchasingGroup="
				+ PurchasingGroup + ", CreatedOn=" + CreatedOn + ", CreatedBy=" + CreatedBy + ", pRItemDetails="
				+ PRItemDetails + "]";
	}

}

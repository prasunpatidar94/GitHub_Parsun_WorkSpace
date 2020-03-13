package com.pp.iteanz.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pp.iteanz.entity.ASNHeader;
import com.pp.iteanz.entity.ASNItem;
import com.pp.iteanz.repository.ASNHeaderRepository;
import com.pp.iteanz.repository.PurchaseOrderRepository;

@Component(value = "AsnXmlCreate")
public class AsnXmlCreator {

	@Autowired
	private ASNHeaderRepository asnHeaderRepository;
//	@Autowired
//	private ASNSectionRepository asnSectionRepository;
//	@Autowired
//	private ASNItemRepository asnItemRepository ;
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	public Boolean asnGenration() {

		FileFormFTP fileFormFTP = new FileFormFTP();

//		List<ASNHeader> asnHeaderList = asnHeaderRepository.findByStatus(1);

		Date date1 = new Date();
		String format = new SimpleDateFormat("yyyy-MM-dd").format(date1);
		String statusheader = "";
		int status = 4;
		List<ASNHeader> asnHeaderList = asnHeaderRepository.findByChangeddateAndStatus(format, status);

		if (asnHeaderList.isEmpty()) {

			return false;

		} else {

			for (ASNHeader asnHeaderOne : asnHeaderList) {

				if (asnHeaderOne == null) {

					System.out.println("1001  : ASN Header is not found");
					continue;

				}

				if (asnHeaderOne.getStatus() == 1) {
					statusheader = "Approved";
				} else if (asnHeaderOne.getStatus() == 2) {
					statusheader = "Rejected";
				} else if (asnHeaderOne.getStatus() == 4) {
					statusheader = "Dispatch";
				} else if (asnHeaderOne.getStatus() == 5) {
					statusheader = "Approve forward";
				}

				String ASNDate = dateConvertFormate(asnHeaderOne.getCreateddate());

				String ASNApprovalDate = dateConvertFormate(asnHeaderOne.getChangeddate());
				String VendorInvoiceDate = dateConvertFormate(asnHeaderOne.getVendorinvdate());

				String fileName = "" + asnHeaderOne.getDrfgrnno();
				String fullASN = "";
				String asnHeader = "";

//					int status = asnHeaderOne.getStatus();`
//					if (status == 1) {
//						String ackStatus = "Completed";
//					} else {
//						String ackStatus = "Completed";
//					}

				asnHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
						+ "<nso:MT_VMS_AdvancedShippingNotification xmlns:nso=\"http://srl.in/VMS_ASN_GRN\">\r\n"
						+ "<ASN_Header>\r\n" + "<ASNNo>" + asnHeaderOne.getDrfgrnno() + "</ASNNo>\r\n" + "<ASNDate>"
						+ ASNDate + "</ASNDate>\r\n" + "<ASNApprovalDate>" + ASNApprovalDate + "</ASNApprovalDate>\r\n"
						+ "<VendorInvoiceDate>" + VendorInvoiceDate + "</VendorInvoiceDate>\r\n" + "<VendorInvoiceNo>"
						+ asnHeaderOne.getVbeln() + "</VendorInvoiceNo>\r\n" + "<Status>" + statusheader
						+ "</Status>\r\n" + "<InvoiceBaseAmount>" + asnHeaderOne.getBsamt() + "</InvoiceBaseAmount>\r\n"
						+ "<InvoiceTaxAmount>" + asnHeaderOne.getTaxamt() + "</InvoiceTaxAmount>\r\n" + "<PONo>"
						+ asnHeaderOne.getEbeln() + "</PONo>\r\n" + "<VendorCode>"
						+ purchaseOrderRepository.findByEbeln(asnHeaderOne.getEbeln()).getLifnr() + "</VendorCode>\r\n";
//						"<ASN_Items>\r\n" + 
//						"<PONo>4000180786</PONo>\r\n" + 
//						"<ItemCategory>Ddfgh</ItemCategory>\r\n" + 
//						"<PlantCode>0002</PlantCode>\r\n" + 
//						"<POLineItemNo>1</POLineItemNo>\r\n" + 
//						"<DeliverQuantity>10</DeliverQuantity>\r\n" + 
//						"<Batch>Batch</Batch>\r\n" + 
//						"<ExpiryDate>24-02-2020</ExpiryDate>\r\n" + 
//						"<BARCODE>3242568</BARCODE>\r\n" + 
//						"<SAPGRN/>\r\n" + 
//						"<SAPInvoice/>\r\n" + 
//						"<InvoiceDate/>\r\n" + 
//						"</ASN_Items>\r\n" + 
//						"</ASN_Header>\r\n" + 
//						"</nso:MT_VMS_AdvancedShippingNotification>";

//				asnHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
//						+ "<nso:MT_VMS_AdvancedShippingNotification xmlns:nso=\"http://srl.in/VMS_ASN_GRN\">\r\n"
//						+ "<ASN_Header>\r\n" + "<ASNNo>" + asnHeaderOne.getDrfgrnno() + "</ASNNo>\r\n" + "<ASNDate>"
//						+ "<ASNApprovalDate>" + asnHeaderOne.getChangeddate() + "</ASNApprovalDate>\r\n"
//						+ "<VendorInvoiceDate>" + asnHeaderOne.getVendorinvdate() + "</VendorInvoiceDate>"
//						+ asnHeaderOne.getCreateddate() + "</ASNDate>\r\n" + "<VendorInvoiceNo>"
//						+ asnHeaderOne.getVbeln() + "</VendorInvoiceNo>\r\n" + "<Status>" + statusheader
//						+ "</Status>\r\n" + "<InvoiceBaseAmount>" + asnHeaderOne.getBsamt() + "</InvoiceBaseAmount>\r\n"
//						+ "<InvoiceTaxAmount>" + asnHeaderOne.getTaxamt() + "</InvoiceTaxAmount>\r\n" + "<PONo>"
//						+ asnHeaderOne.getEbeln() + "</PONo>\r\n" + "<VendorCode>"
//						+ purchaseOrderRepository.findByEbeln(asnHeaderOne.getEbeln()).getLifnr() + "</VendorCode>";
				asnHeader = asnHeader.replaceFirst("\r\n", " ");

				List<ASNItem> itemsList = asnHeaderOne.getAsnitem();
				if (itemsList.isEmpty()) {

					System.out.println("1001  : ASN items List  is not found for " + asnHeaderOne.getDrfgrnno());

				}
				String items = "";
				for (ASNItem asnItemOne : itemsList) {
					
					String ExpiryDate =dateConvertFormate(asnItemOne.getExdate());


					String item = "<ASN_Items>\r\n" + "<PONo>" + asnItemOne.getEbeln() + "</PONo>\r\n"
							+ "<ItemCategory>" + asnItemOne.getPstyp() + "</ItemCategory>\r\n" + "<PlantCode>"
							+ asnItemOne.getWerks() + "</PlantCode>\r\n" + "<POLineItemNo>" + asnItemOne.getEbelp()
							+ "</POLineItemNo>\r\n" + "<DeliverQuantity>" + asnItemOne.getMenge()
							+ "</DeliverQuantity>\r\n" + "<Batch>" + asnItemOne.getCharg() + "</Batch>\r\n"
							+ "<ExpiryDate>" + ExpiryDate + "</ExpiryDate>\r\n" + "<BARCODE>"
							+ asnItemOne.getBarc() + "</BARCODE>\r\n" + "<SAPGRN/>\r\n" + "<SAPInvoice/>\r\n"
							+ "<InvoiceDate/>\r\n" + "</ASN_Items>";
					item = item.replaceFirst("\r\n", " ");
					item = item.replaceFirst("null", "0");

					items = items + item;

				}

				String asnFooter = "</ASN_Header>\r\n" + "</nso:MT_VMS_AdvancedShippingNotification>";
//				asnFooter= asnFooter.replaceFirst("\r\n", " ");

				fullASN = asnHeader + items + asnFooter;
				fullASN = fullASN.replaceFirst("\r\n", " ");
				
				
				boolean inputSrl = new File(System.getProperty("user.home") + "\\Documents\\SRL\\input\\").mkdirs();
				System.out.println(inputSrl);
				File file = new File(
						System.getProperty("user.home") + "\\Documents\\SRL\\Contract\\" + fileName + ".xml");
//				C:/Users/Iteanz Emp/Documents/SRL/Contract
				FileWriter writer;

				String inputFilePath = System.getProperty("user.home") + "\\Documents\\SRL\\Contract\\";
				File f = file;
				if (f.exists()) {
					f.delete();
					try {
						f.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				try {
					writer = new FileWriter(file);
					writer.write(fullASN);

					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}

				boolean storeFTP;

				storeFTP = fileFormFTP.uploadFTP("/VMS/ASN/", fileName, inputFilePath);
				if (storeFTP) {
					System.out.println("The first file is uploaded successfully." + fileName);
				} else {

					System.out.println("The first file is uploaded unsuccessfully." + fileName);
				}

			}

			return true;
		}

	}

	public static String dateConvertFormate(String dateForConvert) {

		if (dateForConvert.isEmpty() || dateForConvert.equalsIgnoreCase("") || dateForConvert == "") {

			return "empty";

		} else {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			Date dateForConvertInDateObj = null;
			try {
				dateForConvertInDateObj = format.parse(dateForConvert);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			return format.format(dateForConvertInDateObj);

		}

	}

}

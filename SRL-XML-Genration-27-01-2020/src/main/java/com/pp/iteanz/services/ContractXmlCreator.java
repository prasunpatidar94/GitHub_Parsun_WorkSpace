package com.pp.iteanz.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pp.iteanz.entity.Contract;
import com.pp.iteanz.entity.Item;
import com.pp.iteanz.repository.ContractRepository;

@Component("contractXmlCreator")
public class ContractXmlCreator {
//	@Autowired
//	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
//			.getLogger(XmlParsingStandalone1Application.class);
	@Autowired
	private ContractRepository contractRepository;

	private String DateOfCancellation;
//	List<Contract> contractsListFor2 = new ArrayList<Contract>();
//	List<Contract> contractsListFor3 = new ArrayList<Contract>();
	List<Contract> contractsList = new ArrayList<Contract>();

	public Boolean contractXmlCreation() {

		FileFormFTP fileFormFTP = new FileFormFTP();

		contractsList = contractRepository.findByStatus("3");
//		contractsListFor2 = contractRepository.findByStatus("2");
		if (contractsList.isEmpty() || contractsList.isEmpty()) {
			return false;
		}

		if (contractsList.isEmpty()) {
			System.out.println("1001 :: ContractList is not found");
			return false;

		} else {
			for (Contract contractOne : contractsList) {

				String fileName = "" + contractOne.getContractId();

				String contractStatus = "";
				if ((contractOne.getStatus()) == "3" || (contractOne.getStatus()).equalsIgnoreCase("3")) {
					contractStatus = "Completed";
				} else {
					contractStatus = "In Progress";
				}

				DateOfCancellation = "<DateOfCancellation>0000/00/00</DateOfCancellation>\r\n";

				if (contractOne.getStatus() == "2" || (contractOne.getStatus()).equalsIgnoreCase("2")) {

					DateOfCancellation = "<DateOfCancellation>" + contractOne.getChangedOn()
							+ "</DateOfCancellation>\r\n";

				}

//				String  DateOfCancellation = contractOne.  

				String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
						+ "<nso:MT_VMS_ContractManagement xmlns:nso=\"http://srl.in/VMS_ContractManagement\">\r\n"
						+ "<VMS_ContractManagement>\r\n" + "<ContractNo>" + contractOne.getContractId()
						+ "</ContractNo>\r\n" + "<ContractType>" + contractOne.getContractType() + "</ContractType>\r\n"
						+ "<FirstParty> " + contractOne.getFirstParty().getBukrs() + "</FirstParty>"
						+ "<PlantCode>" + contractOne.getFirstParty().getWerks()

						+ "</PlantCode>\r\n" + "<Status>Completed</Status>\r\n" + "<Currency>"
						+ contractOne.getCurrency() + "</Currency>\r\n" + "<VendorCodeSecondParty>"
						+ contractOne.getSecondParty().getLifnr() + "</VendorCodeSecondParty>\r\n"
						+ "<ContractCreationDate>" + contractOne.getContractStartDate() + "</ContractCreationDate>\r\n"
						+ "<ContractStartDate>" + contractOne.getContractStartDate() + "</ContractStartDate>\r\n"
						+ "<ContractExpiryDate>" + contractOne.getContractExpiryDate() + "</ContractExpiryDate>\r\n"
						+ DateOfCancellation + "<RFPNo>" + contractOne.getRfpNo() + "</RFPNo>\r\n" + "<Section>"
						+ contractOne.getFirstParty().getLgort() + "</Section>\r\n" + "<MasterContractNo>"
						+ contractOne.getMasterContractNo() + "</MasterContractNo>\r\n" + "<InternalMOU>"
						+ contractOne.getInternalMOUNo() + "</InternalMOU>\r\n" + "<ExpiryContractRemainderDays>"
						+ contractOne.getExpiryReminderdays() + "</ExpiryContractRemainderDays>";

				if (header.contains("0000/00/00")) {
					header = header.replaceAll(DateOfCancellation, " ");

				}

				header = header.replaceAll("\r\n", " ");

				List<Item> itemsList = contractOne.getItems();
				if (itemsList.isEmpty()) {

					// log.error("1001 :: Items List is not found");
					System.out.println("1001  ::  Items List is not found");

				}
				String items = "";
				String item = "";
				for (Item itemOne : itemsList) {

					String InstallationDate = "";

					if (itemOne.getInstallationDate() != null) {

						InstallationDate = "<InstallationDate>" + itemOne.getInstallationDate()
								+ "</InstallationDate>\r\n";

					}

					item = "<Items>\r\n" + "<ItemNo>" + itemOne.getItemId() + "</ItemNo>\r\n" + "<MaterialNo>"
							+ itemOne.getMatnr() + "</MaterialNo>\r\n" + "<Quantity>" + itemOne.getQuantity()
							+ "</Quantity>\r\n" + "" + InstallationDate + "" + "<ORBASICPRICE>"
							+ itemOne.getoRBasicPrice() + "</ORBASICPRICE>\r\n" + "<AMCCHARGESFORORPURCHASE>"
							+ itemOne.getAmcCharges() + "</AMCCHARGESFORORPURCHASE>\r\n" + "<CMCCHARGEFORORPURCHASE>"
							+ itemOne.getCmcCharges() + "</CMCCHARGEFORORPURCHASE>\r\n" + "</Items>";

					item = item.replaceAll("\r\n", " ");

					items = items + item;

				}

//				List<ContractDocument> contractDocumentsList = contractOne.getContractDocuments();
//				if (contractDocumentsList.isEmpty()) {
//
//				//	log.error("1001  ::  ContractDocuments List is not found");
//					System.out.println("1001  ::  ContractDocuments List is not found");
//
//				}
//				String contractDocuments = "";
//				String contractDocument = "";
//				for (ContractDocument contractDocumentOne : contractDocumentsList) {
//					
//					contractDocument = "<ContractDocument>\r\n" + 
//							"<Section>"+contractDocumentOne.getSaction()+"</Section>\r\n" + 
//							"</ContractDocument>";
//					
//					contractDocument = contractDocument.replaceAll("\r\n", " ");
//					
//					
//					
//
//					contractDocuments = contractDocuments + contractDocument;
//
//				}

				String footer = "</VMS_ContractManagement>\r\n" + "</nso:MT_VMS_ContractManagement>";

				String fullXML = header + items/* +contractDocuments */ + footer;
				fullXML = fullXML.replaceAll("null", "0");

//				File file = new File("C:/Users/Iteanz Emp/Desktop/DATA/123455.xml");
				boolean inputSrl = new File(System.getProperty("user.home") + "\\Documents\\SRL\\Contract\\").mkdirs();
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
					writer.write(fullXML);

					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}

				boolean storeFTP;

				storeFTP = fileFormFTP.uploadFTP("/VMS/CMS/", fileName, inputFilePath);
				if (storeFTP) {
					System.out.println("The first file is uploaded successfully." + fileName);
				} else {
					System.out.println("The first file is uploaded unsuccessfully." + fileName);
				}

			}
			return true;

		}
	}
}

//ContractNo = "" + contractOne.getContractId();
//ContractType = "" + contractOne.getContractType();
//PlantCodeFirstParty = "" + contractOne.getFirstParty().getPlant();
//Status = "" + contractOne.getActstatus();
//VendorCodeSecondParty = "" + contractOne.getSecondParty().getSecondPartyId();
//ContractCreationDate = "" + contractOne.getContractStartDate();
//ContractStartDate = "" + contractOne.getContractStartDate();
//ContractExpiryDate = "" + contractOne.getContractExpiryDate();
//DateOfCancellation = "";// confution
//RFPNo = "" + contractOne.getRfpNo();
//MasterContractNo = "" + contractOne.getMasterContractNo();
//InternalMOU = "" + contractOne.getInternalMOUNo();
//ExpiryContractRemainderDays = "" + contractOne.getExpiryReminderdays();

// item
//ItemNo = "";// item
//MaterialNo = "";// item
//Quantity = "";// item
//InstallationDate = "";// item
//ORBASICPRICE = "";// item
//AMCCHARGESFORORPURCHASE = ""; // item
//CMCCHARGEFORORPURCHASE = "";// itean

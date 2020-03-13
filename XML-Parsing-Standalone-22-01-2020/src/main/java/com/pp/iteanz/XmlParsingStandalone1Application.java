package com.pp.iteanz;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.pp.iteanz.service.FileFormFTP;
import com.pp.iteanz.service.GRNXmlReader;
import com.pp.iteanz.service.GetPassReader;
import com.pp.iteanz.service.InvoiceReader;
import com.pp.iteanz.service.MaterialMasterReader;
import com.pp.iteanz.service.PurchaseOrderReader;
import com.pp.iteanz.service.PurchaseRequisitionReader;
import com.pp.iteanz.service.VendorLedgerReader;
import com.pp.iteanz.service.VendorRegistrationReader;
import com.pp.iteanz.utility.UtilityServices;
import com.pp.iteanz.xmlParse.ReturnName;

@SpringBootApplication
@Controller
public class XmlParsingStandalone1Application /* implements CommandLineRunner */ {

	private static String FOLDER_PATH = null;
	private static String filePath;

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(XmlParsingStandalone1Application.class);

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(XmlParsingStandalone1Application.class, args);
		UtilityServices utilityServices = new UtilityServices();

		String outputFile = "";
		String inputFile = "";

		boolean inputSrl = new File(System.getProperty("user.home") + "\\Documents\\SRL\\input\\").mkdirs();

		boolean outputSrl = new File(System.getProperty("user.home") + "\\Documents\\SRL\\output\\").mkdirs();
		System.out.println("INPUT FOLDER PATH  :  " + inputSrl);
		System.out.println("OUTPUT FOLDER PATH  :  " + outputSrl);

		FOLDER_PATH = System.getProperty("user.home") + "\\Documents\\SRL\\input\\";
//		boolean inputSrl=new File(System.getProperty("user.home")+"\\Documents\\SRL\\input").mkdirs();
//		boolean outputSrl=new File(System.getProperty("user.home")+"\\Documents\\SRL\\output").mkdirs();
//		String FOLDER_PATH=System.getProperty("user.home")+"\\Documents\\SRL\\input";
		System.out.println("parth is the darad" + System.getProperty("user.home"));

		System.out.println("111111111111111111111111111");


		FileFormFTP fileFormFTP = new FileFormFTP();

//		try {
//			System.out.println(fileFormFTP.fileData("/VMS/PR/Input/"));
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//
//			log.info("PR files Not Found" + e.getMessage());
//			System.out.println("PR files Not Found" + e.getMessage());
//
//		}

		try {
			System.out.println(fileFormFTP.fileData("/VMS/POACK/Input/"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			log.info("PO files Not Found" + e1.getMessage());
			System.out.println("POACK files Not Found" + e1.getMessage());
		}

//		try {
//			System.out.println(fileFormFTP.fileData("/VMS/VM/Input/"));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			System.out.println("VM files Not Found" + e1.getMessage());
//		}
//
//		try {
//			System.out.println(fileFormFTP.fileData("/VMS/INV/Input/"));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			System.out.println("INV files Not Found" + e1.getMessage());
//		}
//
////
//		try {
//			System.out.println(fileFormFTP.fileData("/VMS/MM/Input/"));
//		} catch (IOException e2) {
//			// TODO Auto-generated catch block
//			System.out.println("MM files Not Found" + e2.getMessage());
//		}
//
//		try {
//			System.out.println(fileFormFTP.fileData("/VMS/GPE/Input/"));
//		} catch (IOException e2) {
//			// TODO Auto-generated catch block
//			System.out.println("GPE files Not Found" + e2.getMessage());
//		}
//
//		try {
//			System.out.println(fileFormFTP.fileData("/VMS/VL/Input/"));
//		} catch (IOException e2) {
//			// TODO Auto-generated catch block
//			System.out.println("VL files Not Found" + e2.getMessage());
//		}
//
//		try {
//			System.out.println(fileFormFTP.fileData("/VMS/DraftGRN/Input/"));
//		} catch (IOException e2) {
//			// TODO Auto-generated catch block
//			System.out.println("DraftGRN files Not Found" + e2.getMessage());
//		}

		// ===================================/VMS/DraftGRN/Input/================================================================
		for (String file : ReturnName.ReturnData(FOLDER_PATH)) {

			filePath = file;

			File xmlFile = new File(filePath);
			System.out.println(xmlFile.getName());
			// System.out.println(xmlFile);
			// System.out.println(" file name " + xmlFile.getName());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(xmlFile);
				// doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

//				ns0:MT_VMS_PurchaseRequisition  ns0:MT_VMS_PurchaseRequisition

				if (doc.getDocumentElement().getNodeName() == "ns0:MT_VMS_PurchaseRequisition"
						|| doc.getDocumentElement().getNodeName().equalsIgnoreCase("ns0:MT_VMS_PurchaseRequisition")) {
					log.info("THIS IS Purchase Requisition FILE  ::  " + filePath);
					PurchaseRequisitionReader purchaseRequisitionReader = (PurchaseRequisitionReader) ctx
							.getBean("purchaseRequisitionRead");
					String returnResult = purchaseRequisitionReader.purchaseRequisitionRead(doc, filePath);
					if (returnResult == "1000   ::    sucess") {

						outputFile = System.getProperty("user.home") + "\\Documents\\SRL\\output\\" + xmlFile.getName();

						inputFile = System.getProperty("user.home") + "\\Documents\\SRL\\input\\" + xmlFile.getName();

						boolean cutpast = utilityServices.cutAndPast(inputFile, outputFile);
						if (cutpast) {

							System.out.println("*******************************file deleted***********************");

						} else {
							System.out
									.println("*******************************file not deleted***********************");

						}

						log.info(returnResult + "  ::  " + filePath);
						System.out.println(returnResult);

					} else {
						System.out.println("not store the data base data");
					}

				} else if (doc.getDocumentElement().getNodeName() == "n0:MT_VMS_PurchaseOrder"
						|| doc.getDocumentElement().getNodeName() == "ns0:MT_VMS_PurchaseOrder"
						|| doc.getDocumentElement().getNodeName().equalsIgnoreCase("ns0:MT_VMS_PurchaseOrder")) {

					PurchaseOrderReader purchaseOrderReader = (PurchaseOrderReader) ctx.getBean("PurchaseOrderRead");
					String returnResult = purchaseOrderReader.purchaseOrderRead(doc, filePath);
					System.out
							.println(";;;;;;;;;;;;;;;;;;;******PurchaseOrderRead********");/* n0:MT_VMS_PurchaseOrder */

					log.info("THIS IS Purchase Order FILE  ::  " + filePath);
					if (returnResult == "1000   ::    sucess") {
						outputFile = System.getProperty("user.home") + "\\Documents\\SRL\\output\\" + xmlFile.getName();

						inputFile = System.getProperty("user.home") + "\\Documents\\SRL\\input\\" + xmlFile.getName();

						boolean cutpast = utilityServices.cutAndPast(inputFile, outputFile);
						if (cutpast) {

							System.out.println("*******************************file deleted***********************");

						} else {
							System.out
									.println("*******************************file not deleted***********************");

						}

						log.info(returnResult + "  ::  " + filePath);
						System.out.println(returnResult);

					} else {
						System.out.println("not store the data base data");
					}

				} else if (doc.getDocumentElement().getNodeName() == "n0:MT_VMS_VendorMaster"
						|| "ns0:MT_VMS_VendorMaster" == doc.getDocumentElement().getNodeName()
						|| doc.getDocumentElement().getNodeName().equalsIgnoreCase("n0:MT_VMS_VendorMaster")) {

					VendorRegistrationReader VendorRegistrationReader = (VendorRegistrationReader) ctx
							.getBean("VendorRegistrationRead");
					String returnResult = VendorRegistrationReader.VendorRegistrationRead(doc, filePath);
					System.out.println(
							"    ;;;;;;;;;;;;;;;;;;;***MT_VMS_VendorMaster***********");/* n0:MT_VMS_PurchaseOrder */

					log.info("THIS IS VendorMaster  FILE  ::  " + filePath);
					if (returnResult == "1000   ::    sucess") {
						outputFile = System.getProperty("user.home") + "\\Documents\\SRL\\output\\" + xmlFile.getName();

						inputFile = System.getProperty("user.home") + "\\Documents\\SRL\\input\\" + xmlFile.getName();

						if (utilityServices.cutAndPast(inputFile, outputFile)) {

							System.out.println("*******************************file deleted***********************");
//							if (fileFormFTP.uploadFTP("/VMS/VM/Output/", xmlFile.getName(), outputFile)) {
//								System.out.println("*******************************file deleted and upload the file  form  FTP***********************");
//							} else {
//								System.out.println("*******************************file  not deleted and upload the file form  FTP***********************");
//							}
//
//						
//						if (fileFormFTP.deleteFileFromFTP("/VMS/VM/Input/", xmlFile.getName())) {
//							System.out.println("*******************************file deleted form  FTP***********************");
//						} else {
//							System.out.println("*******************************file  not deleted form  FTP***********************");
//						}

						} else {
							System.out
									.println("*******************************file not deleted***********************");

						}

						log.info(returnResult + "  ::  " + filePath);
						System.out.println(returnResult);

					} else {
						System.out.println("not store the data base data");
					}

				} else if (doc.getDocumentElement().getNodeName() == "n0:MT_VMS_MaterialMaster"
						|| doc.getDocumentElement().getNodeName() == "ns0:MT_VMS_MaterialMaster"
						|| doc.getDocumentElement().getNodeName().equalsIgnoreCase("n0:MT_VMS_MaterialMaster")) {
//					
					MaterialMasterReader MaterialMasterReader = (MaterialMasterReader) ctx
							.getBean("MaterialMasterRead");
					String returnResult = MaterialMasterReader.materialMasterRead(doc, filePath);

					System.out.println(
							"    ;;;;;;;;;;;;;;;;;;;****MT_VMS_MaterialMaster**********");/* n0:MT_VMS_PurchaseOrder */
					// String returnResult="dsfghjhgfdsdfhjk";
					log.info("THIS IS Purchase Order FILE  ::  " + filePath);

					if (returnResult == "1000   ::    sucess") {

						outputFile = System.getProperty("user.home") + "\\Documents\\SRL\\output\\" + xmlFile.getName();

						inputFile = System.getProperty("user.home") + "\\Documents\\SRL\\input\\" + xmlFile.getName();

						boolean cutpast = utilityServices.cutAndPast(inputFile, outputFile);
						if (cutpast) {

							System.out.println("*******************************file deleted***********************");

						} else {
							System.out
									.println("*******************************file not deleted***********************");

						}

						log.info(returnResult + "  ::  " + filePath);
						System.out.println(returnResult);

					} else {
						System.out.println("not store the data base data");
					}

				}

				else if (doc.getDocumentElement().getNodeName() == "ns0:MT_VMS_VendorLedger"
						|| doc.getDocumentElement().getNodeName().equalsIgnoreCase("ns0:MT_VMS_VendorLedger")) {
//					
					VendorLedgerReader vendorLedgerReader = (VendorLedgerReader) ctx.getBean("VendorLedgerRead");
					String returnResult = vendorLedgerReader.GetVendorLedgerRead(doc, filePath);

					System.out.println(
							"    ;;;;;;;;;;;;;;;;;;;****VMS_VendorLedger**********");/* n0:MT_VMS_PurchaseOrder */
					// String returnResult="dsfghjhgfdsdfhjk";
					log.info("THIS IS VendorLedgerReader Order FILE  ::  " + filePath);

					if (returnResult == "1000   ::    sucess") {

						outputFile = System.getProperty("user.home") + "\\Documents\\SRL\\output\\" + xmlFile.getName();

						inputFile = System.getProperty("user.home") + "\\Documents\\SRL\\input\\" + xmlFile.getName();

						boolean cutpast = utilityServices.cutAndPast(inputFile, outputFile);
						if (cutpast) {

							System.out.println("*******************************file deleted***********************");

						} else {
							System.out
									.println("*******************************file not deleted***********************");

						}

						log.info(returnResult + "  ::  " + filePath);
						System.out.println(returnResult);

					} else {
						System.out.println("not store the data base data");
					}

				}

				else if (doc.getDocumentElement().getNodeName() == "ns0:MT_VMS_GatePassEntry"
						|| doc.getDocumentElement().getNodeName().equalsIgnoreCase("ns0:MT_VMS_GatePassEntry")) {
//					
					GetPassReader gatePassReader = (GetPassReader) ctx.getBean("GetPassRead");
					String returnResult = gatePassReader.GetPassRead(doc, filePath);

					System.out.println(
							"    ;;;;;;;;;;;;;;;;;;;****MT_VMS_MaterialMaster**********");/* n0:MT_VMS_PurchaseOrder */
					// String returnResult="dsfghjhgfdsdfhjk";
					log.info("THIS IS Purchase Order FILE  ::  " + filePath);

					if (returnResult == "1000   ::    sucess") {

						outputFile = System.getProperty("user.home") + "\\Documents\\SRL\\output\\" + xmlFile.getName();

						inputFile = System.getProperty("user.home") + "\\Documents\\SRL\\input\\" + xmlFile.getName();

						boolean cutpast = utilityServices.cutAndPast(inputFile, outputFile);
						if (cutpast) {

							System.out.println("*******************************file deleted***********************");

						} else {
							System.out
									.println("*******************************file not deleted***********************");

						}

						log.info(returnResult + "  ::  " + filePath);
						System.out.println(returnResult);

					} else {
						System.out.println("not store the data base data");
					}

				}

				else if (doc.getDocumentElement().getNodeName() == "n0:MT_SAP_Invoice"
						|| doc.getDocumentElement().getNodeName() == "ns0:MT_SAP_Invoice"
						|| doc.getDocumentElement().getNodeName() == "ns0:MT_VMS_Invoice") {

					InvoiceReader invoiceReader = (InvoiceReader) ctx.getBean("invoiceRead");
					Boolean returnResult = invoiceReader.invoiceRead(doc, filePath);
					System.out.println(
							"    ;;;;;;;;;;;;;;;;;;;******PurchaseOrderRead********");/* n0:MT_VMS_PurchaseOrder */

					log.info("THIS IS Invoice Order FILE  ::  " + filePath);
					if (returnResult) {
						outputFile = System.getProperty("user.home") + "\\Documents\\SRL\\output\\" + xmlFile.getName();

						inputFile = System.getProperty("user.home") + "\\Documents\\SRL\\input\\" + xmlFile.getName();

						boolean cutpast = utilityServices.cutAndPast(inputFile, outputFile);
						if (cutpast) {

							System.out.println("*******************************file deleted***********************");

						} else {
							System.out
									.println("*******************************file not deleted***********************");

						}

						log.info(returnResult + "  ::  " + filePath);
						System.out.println(returnResult);

					} else {
						System.out.println("not store the data base data");
					}

				} else if (doc.getDocumentElement().getNodeName() == "ns0:MT_VMS_DraftGRN"
						|| doc.getDocumentElement().getNodeName().equalsIgnoreCase("ns0:MT_VMS_DraftGRN")) {

					GRNXmlReader grnXmlReader = (GRNXmlReader) ctx.getBean("grnXmlRead");
					Boolean returnResult = grnXmlReader.grnXmlRead(doc, filePath);
					System.out.println(
							"    ;;;;;;;;;;;;;;;;;;;******PurchaseOrderRead********");/* n0:MT_VMS_PurchaseOrder */

					log.info("THIS IS Invoice Order FILE  ::  " + filePath);
					if (returnResult) {
						outputFile = System.getProperty("user.home") + "\\Documents\\SRL\\output\\" + xmlFile.getName();

						inputFile = System.getProperty("user.home") + "\\Documents\\SRL\\input\\" + xmlFile.getName();

						boolean cutpast = utilityServices.cutAndPast(inputFile, outputFile);
						if (cutpast) {

							System.out.println("*******************************file deleted***********************");

						} else {
							System.out
									.println("*******************************file not deleted***********************");

						}

						log.info(returnResult + "  ::  " + filePath);
						System.out.println(returnResult);

					} else {
						System.out.println("not store the data base data");
					}

				}

				else {
					System.out.println("invalid file Name");
					log.error("10001    ::    Invalid File Formate");

				}
//				purchaseRequisitionReader.purchaseRequisitionRead(doc, filePath);

//				PurchaseRequisitionReader purchaseRequisitionReader=new PurchaseRequisitionReader();
//				
//				purchaseRequisitionReader.purchaseRequisitionRead(doc, filePath);
			} catch (SAXException e1) {
				e1.getMessage();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
//			xmlFile.getName();

//		

		}
	}
	
//	@Configuration
//	@EnableScheduling
//	@ConditionalOnProperty(name = "sceduling", matchIfMissing = true)
//	class SchdulingConfiguration {
//
//	}
//
////	@Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
//	@Scheduled(fixedDelay = 10000L, initialDelay = 5000L)
//	public void autoRunner() {
//		
//		
//	
//		try {
//			Thread.sleep(10000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//	}
	
	
}

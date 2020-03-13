
package com.pp.iteanz.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pp.iteanz.XmlParsingStandalone1Application;
import com.pp.iteanz.entity.ApprovalLog;
import com.pp.iteanz.entity.PRItemDetails;
import com.pp.iteanz.entity.Plant;
import com.pp.iteanz.entity.PurchaseRequisition;
import com.pp.iteanz.entity.PurchaseRequisitionMain;
import com.pp.iteanz.repository.ApprovalLogRepository;
import com.pp.iteanz.repository.PlantRepository;
import com.pp.iteanz.repository.PurchaseRequisitionRepository;

@Component("purchaseRequisitionRead")

public class PurchaseRequisitionReader {

	private boolean returnValue;
	private PurchaseRequisition pr;
	private String filePath;
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(XmlParsingStandalone1Application.class);

	private PRItemDetails details;
	private Plant plantForPr;
	@Autowired
	private PurchaseRequisitionRepository purchaseRequisitionRepository;
	@Autowired
	private PlantRepository plantRepository;

//	@Autowired
//	private ApprovalMatrixRepository approvalMatrixRepository;
//
//	@Autowired
//	private StatusRepo statusRepo;

	@Autowired
	private ApprovalLogRepository approvalLogRepository;

//	@Autowired
//	private UserMasterRepo userMasterRepo;
//
//	@Autowired
//	private WorkFlowRepo workFlowRepo;

	Boolean check = false;
//	private String PlantText = "";
//	private String LogerText = "";

	public String purchaseRequisitionRead(Document doc, String filePath) {

		this.filePath = filePath;

		NodeList nodeList = doc.getElementsByTagName("PRHeader");

		System.out.println("child name " + nodeList);
		// now XML is loaded as Document in memory, lets convert it to Object List
		List<Object> prList = new ArrayList<Object>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			prList.add(getPurchaseRequisition(nodeList.item(i)));
		}

		if (returnValue)
			return "1000   ::    sucess";
		else
			return "1001   ::    sucess";

	}

	private PRItemDetails getPRItemDetails(Node node) {
		// XMLReaderDOM domReader = new XMLReaderDOM();
		details = new PRItemDetails();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
try {
	

			
			details.setItemNo(Long.parseLong(getTagValue("ItemNo", element)));
			details.setMaterialNo(getTagValue("MaterialNo", element));
			details.setItemDescription(getTagValue("ItemDescription", element));
			details.setQuantity(getTagValue("Quantity", element));
			details.setUOM(getTagValue("UOM", element));
			details.setStorageLocation((getTagValue("StorageLocation", element)));
			details.setPlant(getTagValue("Plant", element));
			details.setPrice(getTagValue("Price", element));
			details.setCurrency(getTagValue("Currency", element));

			// deletion flage
			details.setDeletionIndicator(getTagValue("DeletionIndicator", element));

			
		} catch (Exception e) {
			System.out.println("****************************************Some Exception in PR **************************************************");
			System.out.println(e.getMessage());
		}
		}

		return details;
	}

	private PurchaseRequisition getPurchaseRequisition(Node node) {
		// XMLReaderDOM domReader = new XMLReaderDOM();
		pr = new PurchaseRequisition();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;

			try {
				
			
			
			
			// System.out.println(pr.getpRItemDetails());
			pr.setPRNO(getTagValue("PRNO", element));
			pr.setText(getTagValue("Text", element));
			pr.setDocumentType(getTagValue("DocumentType", element));
			pr.setPurchasingGroup(getTagValue("PurchasingGroup", element));
			pr.setCreatedOn(getTagValue("CreatedOn", element));
			pr.setCreatedBy(getTagValue("CreatedBy", element));

//			pr.setPRItemDetails((PRItemDetails) setTegValueForiteam("PRItemDetails", element));

			setTegValueForiteam("PRItemDetails", element);

		} catch (Exception e) {
			System.out.println("****************************************Some Exception in PR **************************************************");
			System.out.println(e.getMessage());
		}
			
		}

		// System.out.println("full list is the PurchaseRequisition is :");
		// System.out.println(pr);

		return pr;
	}

	static int k = -1;

	private Object setTegValueForiteam(String tag, Element element) {

		/// String filePath = filePath;
		File xmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		List<Object> prDetailsList = new ArrayList<Object>();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList prDetailsNode = doc.getElementsByTagName(tag);

			for (int i = 0; i < prDetailsNode.getLength(); i++) {
				prDetailsList.add(getPRItemDetails(prDetailsNode.item(i)));
				k = i;
				// System.out.println(pr);

				PurchaseRequisitionMain main = null;/* = new PurchaseRequisitionMain(); */
				PurchaseRequisitionMain purchaseRequisitionMain = null;
				long PRNO = Long.parseLong(pr.getPRNO());
				String iteamNo = "" + details.getItemNo();

				purchaseRequisitionMain = purchaseRequisitionRepository.findByPrnoIteamno(PRNO, iteamNo);

				if (purchaseRequisitionMain != null) {

					main = purchaseRequisitionMain;
					System.out.println("**********************UPdate data dfghjk***********************");

				} else {

					main = new PurchaseRequisitionMain();

					main.setStatus(3);
					System.out
							.println("***************************new data is dfghjkl;********************************");

					check = true;
				}

//					=================================================================================================

				main.setBanfn(Long.parseLong(pr.getPRNO()));
				main.setBadat(pr.getCreatedOn());
				main.setBnfpo("" + details.getItemNo());
				main.setMatnr(details.getMaterialNo());
				main.setText(pr.getText());
				main.setBsart(pr.getDocumentType());
				main.setCrdBy(pr.getCreatedBy());
				main.setEkgrp(pr.getPurchasingGroup());
				main.setInactive(1);

				// delection add
				main.setLoekz(details.getDeletionIndicator());

				main.setLgort("" + details.getStorageLocation());
				plantForPr = plantData(details.getPlant(), "" + details.getStorageLocation());
				
				if (plantForPr != null) {
					main.setPlanttext(plantForPr.getName1());// add karama he
					main.setLgorttext(plantForPr.getLgobe());// add karana he
					System.out.println(" plant ::  "+plantForPr.getName1() +" Strorage location "+plantForPr.getLgobe());
					
				} else {
					main.setPlanttext(" ");// add karama he
					main.setLgorttext(" ");// add karana he

				}
				main.setMaktx(details.getItemDescription());
				main.setMeins(details.getUOM());
				main.setMenge(Float.parseFloat(details.getQuantity()));

				main.setPreis(details.getPrice());
				main.setWaers(details.getCurrency());
				main.setWerks(details.getPlant());

				// **********************************

//					main.setCrDat(date);

				main = purchaseRequisitionRepository.save(main);
				if (main == null) {

					System.out.println("Data Is Not Stored In Database");

					log.error("1001    ::  " + filePath + "NOT STORE DATABASE");
					log.error("1001   ::   Something Is Rong From Database");

				} else {

//					if (check) {
//						EmailSendingService emailSendingService = new EmailSendingService();
//
//						StringBuffer stringBuffer = new StringBuffer();
//
//						stringBuffer.append("<html>");
//						stringBuffer.append("<body>");
//						stringBuffer.append("<per> " + "Purchase Request no. " + main.getBanfn() + ""
//								+ "has been initiated. Request your kind verification and approval for the same." + ""
//								+ "<br>"
//
//								+ "<a href=\"http://192.168.0.56:8080/ZSRL_PR/\">Login</a> </per>");
//
//						stringBuffer.append("</body>");
//						stringBuffer.append("</html>");
//
//						String subject = "Purchase Request no. " + main.getBanfn() + "   has been initiated";
////							String body = "Purchase Request no. " + main.getBanfn()
////									+ " has been initiated. Request your kind verification and approval for the same.";
////
//						String body = stringBuffer.toString();
////						
//
//						ApprovalMatrix approvalMatrixForAprv = approvalMatrixRepository.findByPlant(main.getWerks());
//						if (approvalMatrixForAprv == null) {
//
//							System.out.println(
//									"*********************According to this plant Appraabal matrix is not found*****************************");
//
//						} else {
//
//						}
//
//						// lab head
//						String to = userMasterRepo.findByAdrid((approvalMatrixForAprv.getLabhead())).getEmail();
//
//						Boolean emailCon = emailSendingService.emailSend(to, subject, body, "");
//
//						if (emailCon) {
//
//							System.out.println("***********Email is sent new line Items************");
//
//						} else {
//							System.out.println("***********Email is  not sent new line Items************");
//						}
//
//					}

					System.out.println("Data Is Stored In Database" + filePath);
					log.info("1000   ::  Successfully Store  ::  " + filePath);

					List<ApprovalLog> approvalLog = approvalLogRepository.findByReqNo(main.getBanfn());

					if (approvalLog.isEmpty()) {
						if (workflowStart(main.getBanfn())) {

							System.out.println("************WorkFlow is Srarted********" + filePath);
							log.info("1000   ::  ************WorkFlow is Srarted********   ::  " + filePath);

						} else {

							System.out.println("************WorkFlow is  not Srarted********" + filePath);
							log.info("1000   ::  ************WorkFlow is  not Srarted********   ::  " + filePath);
						}

					} else {

						System.out.println("************WorkFlow is  not  need to  Srarted ********" + filePath);
						log.info("1000   ::  ************WorkFlow is  not  need to Srarted********   ::  " + filePath);

//						

					}

				}

//					===================================================================================================

			}

		} catch (Exception e) {
			log.error("1001   ::   " + e.getMessage());
			e.printStackTrace();
		}

		return prDetailsList.get(k);

	}

	private static String getTagValue(String tag, Element element) {

		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);

		if (node != null) {

			return node.getNodeValue();

		} else {

			return "0000";
		}

	}

	private Plant plantData(String werks, String lgort) {

		Plant plantData = plantRepository.findByWerksAndLgort(werks, lgort);

		if (plantData != null) {
			return plantData;

		} else {

			return null;

		}

	}

	private static Boolean workflowStart(long reqno) {

		HttpURLConnection conn = null;

		try {

			// URL url = new URL("http://localhost:8080/RESTfulExample/json/product/post");
//			URL url = new URL("http://localhost:8080/pendingPRList");

			URL url = new URL("http://localhost:8080/SRL/workflowStart");

			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String subprocess = "PRSUB";
			String appid = "PR";

			String input = "{\"APPID\":\"" + appid + "\",\"SUBPROC\":\"" + subprocess + "\",\"REQID\":" + reqno + "}";

			System.out.println("input is ::  " + input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());

			os.flush();
			System.out.println(HttpURLConnection.HTTP_CREATED);

			if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			System.out.println(conn.getResponseMessage());

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {

				System.out.println(output);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;

		} catch (IOException e) {
			e.printStackTrace();

			return false;

		} finally {
			conn.disconnect();

			System.out.println("fynally block is working***************************************************");
		}

		return true;

	}

}

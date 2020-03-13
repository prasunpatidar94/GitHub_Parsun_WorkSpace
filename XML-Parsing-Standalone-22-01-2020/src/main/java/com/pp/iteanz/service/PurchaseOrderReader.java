package com.pp.iteanz.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pp.iteanz.XmlParsingStandalone1Application;
import com.pp.iteanz.entity.ApprovalLog;
import com.pp.iteanz.entity.PODocument;
import com.pp.iteanz.entity.POItemDetails;
import com.pp.iteanz.entity.PurchaseOrder;
import com.pp.iteanz.entity.UserMaster;
import com.pp.iteanz.repository.ApprovalLogRepository;
import com.pp.iteanz.repository.MaterialMasterRepository;
import com.pp.iteanz.repository.POItemDetailsRepository;
import com.pp.iteanz.repository.PurchaseOrderRepository;
import com.pp.iteanz.repository.UserMasterRepo;

@Component("PurchaseOrderRead")

public class PurchaseOrderReader {

	private PurchaseOrder purchaseOrder = null;
	private POItemDetails poItemDetails = null;
	@Autowired
	private UserMasterRepo userMasterRepo;

	private String filePath;
	private boolean returnValue;
	@Autowired
	private MaterialMasterRepository materialMasterRepository;
	@Autowired
	private POItemDetailsRepository itemDetailsRepository;

	private String to = "";
	private String ccEmail = "";
	UserMaster userMaster = null;
	private List<POItemDetails> poItemDetailsList;

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(XmlParsingStandalone1Application.class);

	// private POItemDetails podetails;purchaseOrderRead
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	@Autowired
	private POItemDetailsRepository poItemDetailsRepository;
	@Autowired
	private ApprovalLogRepository approvalLogRepository;

	FileFormFTP fileFormFTP = new FileFormFTP();

	public String purchaseOrderRead(Document doc, String filePath) {

		this.filePath = filePath;

		NodeList nodeList = doc.getElementsByTagName("POHeader");
		System.out.println("child name " + nodeList);
		// now XML is loaded as Document in memory, lets convert it to Object List
		List<Object> prList = new ArrayList<Object>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			prList.add(getPurchOrder(nodeList.item(i)));
		}

		if (returnValue)
			return "1000   ::    sucess";
		else
			return "1001   ::    sucess";
	}

	private POItemDetails getPOItemDetails(Node node) {
	
		
		
	

	
		POItemDetails poItemDetailsSave = null;
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			
//			List< POItemDetails> poItemDetailsList  = purchaseOrder.getPoitem();
//			POItemDetails itemDetails = poItemDetailsRepository.findbyEbelpAndMatnr(Long.parseLong(getTagValue("ItemNo", element)),getTagValue("MaterialNo", element));
//			if (poItemDetailsList.isEmpty() && itemDetails==null) {
//				poItemDetails =  new POItemDetails();
//			}else if (poItemDetailsList.contains(itemDetails)) {
//				BeanUtils.copyProperties(itemDetails, poItemDetails);
//			}
//			
			poItemDetails =  new POItemDetails();

			
			
			
			poItemDetails.setEbelp(Long.parseLong(getTagValue("ItemNo", element)));
			poItemDetails.setMatnr(getTagValue("MaterialNo", element));
			poItemDetails.setWerks(getTagValue("Plant", element));
			poItemDetails.setLgort(getTagValue("StorageLocation", element));
			poItemDetails.setMatkl(getTagValue("MaterialGroup", element));
			poItemDetails.setMenge(Float.valueOf(getTagValue("POQuantity", element)));
			poItemDetails.setNetpr(getTagValue("TotalNetPrice", element));
			poItemDetails.setWaers(getTagValue("Currency", element));
			poItemDetails.setXchpf(getTagValue("BatchManagementIndicator", element));
			poItemDetails.setElikz(getTagValue("DeliveryCompleted", element));
			poItemDetails.setMfrpn(getTagValue("ManufacturePartNo", element));


			try {
				poItemDetails.setEindt(
						new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH)
								.parse(getTagValue("DeliveryDate", element))));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				poItemDetails.setMaktx(materialMasterRepository.findByMatnr(getTagValue("MaterialNo", element)).getMaktx());

			} catch (Exception e) {
				poItemDetails.setMaktx("00000");
				System.out.println("1001  :: excepetion is coming to fetch"+e.getMessage());
				
			}

			poItemDetailsSave = poItemDetailsRepository.save(poItemDetails);
			if (poItemDetailsSave != null) {
				poItemDetailsList.add(poItemDetailsSave);
				log.info("1000   :   Item Stored");
				System.out.println("1000   :   Item Stored");

			} else {

				log.info("1001   :   Item IS NOT Stored");
				System.out.println("1000   :   Item IS NOT Stored");

			}

		}

		return poItemDetailsSave;
	}

	private PurchaseOrder getPurchOrder(Node node) {
		poItemDetailsList = new ArrayList<POItemDetails>();
		purchaseOrder = new PurchaseOrder();
//		 PODocument poDocument = new PODocument();
//		PODocument poDocument = purchaseOrder.getPoDocument();

		PurchaseOrder purchaseOrderSave = null;
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;

			Optional<PurchaseOrder> purchaseOrder1 = purchaseOrderRepository
					.findById(Long.parseLong(getTagValue("PurchaseOrderNo", element)));

			if (purchaseOrder1.isPresent()) {
				BeanUtils.copyProperties(purchaseOrder1.get(), purchaseOrder);
			} else {
				purchaseOrder.setEbeln(Long.parseLong(getTagValue("PurchaseOrderNo", element)));

			}

			purchaseOrder.setEbeln(Long.parseLong(getTagValue("PurchaseOrderNo", element)));
			purchaseOrder.setBsart(getTagValue("PODocumentType", element));
			purchaseOrder.setBukrs(getTagValue("CompanyCode", element));
			purchaseOrder.setCddat(getTagValue("POCreationDate", element));
			purchaseOrder.setLifnr(getTagValue("VendorCode", element));
			purchaseOrder.setEkorg(getTagValue("PurchaseOrg", element));
			purchaseOrder.setAckno(getTagValue("AckIndicator", element));
			purchaseOrder.setCntr_no(getTagValue("ContractNo", element));
			purchaseOrder.setErnam(getTagValue("CreatedBy", element));
			
			try {
				purchaseOrder.setAedat(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH)
						.parse(getTagValue("POCreationDate", element))));
			} catch (ParseException e1) {
				
				purchaseOrder.setAedat("00000000");
				
			}
			
			if (getTagValue("AckIndicator", element).equalsIgnoreCase("x")
					|| getTagValue("AckIndicator", element) == "X") {
				purchaseOrder.setStatus("3");
			} else {
				purchaseOrder.setStatus("0");
			}
			File returnFile = fileFormFTP.poFileDownload(Long.parseLong(getTagValue("PurchaseOrderNo", element)));
			
			 PODocument poDocument = purchaseOrder.getPoDocument();
			 if (poDocument== null) {
				 poDocument= new PODocument();
			}

			if (returnFile != null) {
				poDocument.setFileName(returnFile.getName());

				try {
					byte[] input_file = Files.readAllBytes(Paths.get(returnFile.getPath()));

//					String encoded = Base64.getEncoder().encodeToString(input_file);
//					
					byte[] encodedBytes = Base64.getEncoder().encode(input_file);
					String encodedString = new String(encodedBytes);
					System.out.println(encodedString);
//			        String encodedString =  new String(encodedBytes);
//					Blob blobFileData = new SerialBlob(encodedString.getBytes());
					Blob blobFileData = new SerialBlob(input_file);


					if (blobFileData.length() > 0) {
						poDocument.setAttachment("data:application/pdf;base64");
						poDocument.setFileData(blobFileData);
						System.out.println("blobFileData");
						purchaseOrder.setPoDocument(poDocument);

					} else {
						System.out.println("file data is currupted");
						log.info("File Data is currupted " + filePath);
						Exception exception = new Exception("File Data is currupted " + filePath);
						exception.printStackTrace();
					}

				} catch (IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				System.out.println("1001  ::   PO file is not Found");

			}

			setTegValueForiteam("POItemDetails", element);
			System.out.println("Item added");
			purchaseOrder.setPoitem(poItemDetailsList);

			purchaseOrderSave = purchaseOrderRepository.save(purchaseOrder);

			Boolean count = true;
			if (purchaseOrderSave.getAckno().equalsIgnoreCase("x")) {
				count = true;
			} else {
				count = false;

				EmailSendingService emailSendingService = new EmailSendingService();

				StringBuffer stringBuffer = new StringBuffer();

				stringBuffer.append("<html>");
				stringBuffer.append("<body>");
				stringBuffer.append("<per> " + "Purchase Orderd no. " + purchaseOrderSave.getEbeln() + " dated "
						+ Calendar.getInstance().getTime() + " has been generated. Kindly verify and"
						+ "revert your kind acknowledgement for the same." + "" + "<br>"

						+ "<a href=\"http://192.168.0.56:8080/ZSRL_FIORI/\">Login</a> </per>");

				stringBuffer.append("</body>");
				stringBuffer.append("</html>");

				String subject = "Purchase Orderd no. " + purchaseOrderSave.getEbeln() + " dated "
						+ Calendar.getInstance().getTime() + " has been generated.";
//					String body = "Purchase Request no. " + main.getBanfn()
//							+ " has been initiated. Request your kind verification and approval for the same.";
//
				String body = stringBuffer.toString();
//				

				// lab head
				try {
					to = userMasterRepo.findByAdrid(purchaseOrderSave.getLifnr().trim()).getEmail();
					ccEmail = userMasterRepo.findBySapId(purchaseOrderSave.getCddat().trim()).getEmail();

					if (to.isEmpty() || to.equalsIgnoreCase("") || to == "" || to == null) {
						System.out.println(
								"***************vendor code or creator   in not present in the user master ***************");
					} else {
						Boolean emailCon = emailSendingService.emailSend(to, subject, body, ccEmail);

						if (emailCon) {

							System.out.println("***********Email is sent new line Items************");

						} else {
							System.out.println("***********Email is  not sent new line Items************");
						}

					}

				} catch (Exception e) {
					System.out.println(
							"***************some exception in the vendor acces in the user  master ***************");
					System.out.println(e.getMessage());
				}

			}

			List<ApprovalLog> approvalLog = approvalLogRepository.findByReqNo(purchaseOrderSave.getEbeln());

			if (/*approvalLog.isEmpty() && count*/true) {
				if (workflowStart(purchaseOrderSave.getEbeln())) {

					System.out.println("************WorkFlow is Srarted********" + filePath);
					log.info("1000   ::  ************WorkFlow is Srarted********   ::  " + filePath);

				} else {

					System.out.println("************WorkFlow is  not Srarted********" + filePath);
					log.info("1000   ::  ************WorkFlow is  not Srarted********   ::  " + filePath);
				}

			} else {

				System.out.println("************WorkFlow is  not  need to  Srarted ********" + filePath);
				log.info("1000   ::  ************WorkFlow is  not  need to Srarted********   ::  " + filePath);

			}

		}

		return purchaseOrderSave;
	}

	static int k = -1;

	private List<Object> setTegValueForiteam(String tag, Element element) {

		/// String filePath = filePath;
		File xmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		List<Object> poItemDetailsListobj = new ArrayList<Object>();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList poItemDetailsNode = doc.getElementsByTagName(tag);

			for (int i = 0; i < poItemDetailsNode.getLength(); i++) {
				poItemDetailsListobj.add(getPOItemDetails(poItemDetailsNode.item(i)));
				k = i;

//				==============================================================================

			}

			log.info("1005 ::  Present data updated " + filePath);
			System.out.println("1005  :: ********** Present data updated " + filePath);

		} catch (Exception e) {

			log.error("1001   ::   " + e.getMessage());
			e.printStackTrace();
		}

		return poItemDetailsListobj;

	}

//ge th element value in from trhe xml 
	private static String getTagValue(String tag, Element element) {

		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);

		if (node != null) {

			return node.getNodeValue();

		} else {

			return "0000";
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

			String subprocess = "POSUB";
			String appid = "PO";

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

//	public Boolean poDownload(Long purchaseOrderNo ) {
//		
////		File returnFile = fileFormFTP.poFileDownload(Long.parseLong(getTagValue("PurchaseOrderNo", element)));
//		File returnFile = fileFormFTP.poFileDownload(purchaseOrderNo);
//
//		if (returnFile != null) {
//			poDocument.setFileName(returnFile.getName());
//
//			try {
//				byte[] input_file = Files.readAllBytes(Paths.get(returnFile.getPath()));
//
////				String encoded = Base64.getEncoder().encodeToString(input_file);
////				
//				byte[] encodedBytes = Base64.getEncoder().encode(input_file);
//				   String encodedString =  new String(encodedBytes);
//				   System.out.println(encodedString);
////		        String encodedString =  new String(encodedBytes);
//				Blob blobFileData = new SerialBlob(encodedString.getBytes());
//
//				if (blobFileData.length() > 0) {
//					poDocument.setAttachment("data:application/pdf;base64");
//					poDocument.setFileData(blobFileData);
//					System.out.println("blobFileData");
//					purchaseOrder.setPoDocument(poDocument);
//					
//				} else {
//					System.out.println("file data is currupted");
//					log.info("File Data is currupted " + filePath);
//					Exception exception = new Exception("File Data is currupted " + filePath);
//					exception.printStackTrace();
//				}
////				String encoded = Base64.getEncoder().encodeToString(encodedBytes);
//
//			} catch (IOException | SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		} else {
//			System.out.println("1001  ::   PO file is not Found");
//
//		}
//		
//		
//		return returnValue;
//		
//	}
}

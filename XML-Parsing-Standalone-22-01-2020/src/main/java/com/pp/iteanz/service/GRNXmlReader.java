package com.pp.iteanz.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pp.iteanz.XmlParsingStandalone1Application;
import com.pp.iteanz.entity.ASNHeader;
import com.pp.iteanz.entity.ApprovalMatrix;
import com.pp.iteanz.entity.GRNHeader;
import com.pp.iteanz.entity.GRNItem;
import com.pp.iteanz.entity.PurchaseOrder;
import com.pp.iteanz.repository.ASNHeaderRepository;
import com.pp.iteanz.repository.ApprovalMatrixRepository;
import com.pp.iteanz.repository.GRNHeaderRepository;
import com.pp.iteanz.repository.GRNItemRepository;
import com.pp.iteanz.repository.PurchaseOrderRepository;
import com.pp.iteanz.repository.UserMasterRepo;

@Component("grnXmlRead")
public class GRNXmlReader {
	private GRNHeader grnHeader;
	private GRNItem grnItem;
	private String filePath;
	private boolean returnValue;
	private String plant;
	private String to = "", body = "", sub = "", ccEmail = "";
	private Boolean confEmail = false;
	private String asnNO;

	private List<GRNItem> grnItemsList ;
	
	private EmailSendingService emailSendingService = new EmailSendingService();
	@Autowired
	private ApprovalMatrixRepository approvalMatrixRepository;

	@Autowired
	private ASNHeaderRepository asnHeaderRepository;

	@Autowired
	PurchaseOrderRepository purchaseOrderRepository;

	private ApprovalMatrix approvalMatrix;

	@Autowired
	private UserMasterRepo userMasterRepo;

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(XmlParsingStandalone1Application.class);

	// private POItemDetails podetails;
	@Autowired
	GRNHeaderRepository grnHeaderRepository;
	@Autowired
	GRNItemRepository grnItemRepository;

	public Boolean grnXmlRead(Document doc, String filePath) {

		this.filePath = filePath;

		NodeList nodeList = doc.getElementsByTagName("DraftGRN_Header");
		System.out.println("child name " + nodeList);
		// now XML is loaded as Document in memory, lets convert it to Object List
		List<Object> grnList = new ArrayList<Object>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			grnList.add(getGRNHeader(nodeList.item(i)));
		}
		return returnValue;

	}

	private GRNItem getIGRNItem(Node node) {
		grnItem = new GRNItem();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;

			grnItem.setStatus(3);
			grnItem.setMatnr(getTagValue("MaterialNo", element));
			grnItem.setMaktx(getTagValue("MaterialDescription", element));
			grnItem.setDrfgrnno(getTagValue("ASNNo", element));
			grnItem.setPoItemCategory(getTagValue("POItemCategory", element));
			grnItem.setWerks(getTagValue("PlantCode", element));
			grnItem.setLgort(getTagValue("StorageLocation", element));
			grnItem.setPolineitemid(Long.parseLong(getTagValue("POLineItem", element)));
			grnItem.setGrnlineitemid((getTagValue("GRNLineItem", element)));
			grnItem.setMenge1(Float.valueOf(getTagValue("DeliveryQuantity", element)));
			grnItem.setCharg(getTagValue("Batch", element));
			grnItem.setExdate(getTagValue("ExpiryDate", element));
			grnItem.setTotalRejectedQtyCount(getTagValue("TotalNoofQtyRejectedForLineItem", element));

			grnItem.setTotalQtyDelivCount(getTagValue("TotalNoofQtyDeliveredofLineItems", element));

			grnItem.setTotalShortExpItemCount(getTagValue("TotalNoofShortExpiryItemsDelivered", element));

			Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(grnHeader.getEbeln());
			if (purchaseOrder.isPresent()) {

				if (purchaseOrder.get().getPoitem().isEmpty()) {
					log.info("1001  : getPoitem is not found  ");
					System.out.println("1001   :   getPoitem is not found");

					grnItem.setMenge("0.0");

				} else {
					grnItem.setMenge(""+(purchaseOrder.get().getPoitem().get(0).getMenge()));

				}
			} else {
				log.info("1001  : PurchaseOrder is not found  ");
				System.out.println("1001   :   PurchaseOrder is not found");
				grnItem.setMenge("0.0");
			}

			Optional<ASNHeader> asnHeader = asnHeaderRepository.findById(Long.parseLong(getTagValue("ASNNo", element)));
			if (asnHeader.isPresent()) {

				if (asnHeader.get().getAsnitem().isEmpty()) {
					log.info("1001  : getAsnitem is not found  ");
					System.out.println("1001   :   getAsnitem is not found");

					grnItem.setMenge2("0");

				} else {
					grnItem.setMenge2(""+(asnHeader.get().getAsnitem().get(0).getMenge()));

				}
			} else {
				log.info("1001  : ASNHEADER is not found  ");
				System.out.println("1001   :   ASNHEADER is not found");
				grnItem.setMenge2("00asn");
			}

			GRNItem grnItemSave = grnItemRepository.save(grnItem);
			
			if (grnItemSave != null) {
				grnItemsList.add(grnItemSave);
				log.info("1000   :   Item Stored");
				System.out.println("1000   :   Item Stored");

			} else {

				log.info("1001   :   Item IS NOT Stored");
				System.out.println("1000   :   Item IS NOT Stored");

			}

		}

		return grnItem;
	}

	// getInvoice
	private GRNHeader getGRNHeader(Node node) {
		grnHeader = new GRNHeader();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			grnItemsList = new ArrayList<GRNItem>();
			
			Optional<GRNHeader> grnHeader1 = grnHeaderRepository.findById(Long.parseLong(getTagValue("GRNNo", element)));
			if (grnHeader1.isPresent()) {
				
				grnHeader = grnHeader1.get();
				
			}else {
				grnHeader.setGrn(Long.parseLong(getTagValue("GRNNo", element)));

			}

			grnHeader.setEbeln(Long.parseLong(getTagValue("PONo", element)));
			grnHeader.setCreateddate(getTagValue("GRNDate", element));
			grnHeader.setLifnr(getTagValue("VendorCode", element));
			grnHeader.setName1(getTagValue("VendorName", element));
			grnHeader.setBudat(getTagValue("GRNDate", element));
			grnHeader.setStatus("3");
			grnHeader.setDelivOnTimeAsPO(getTagValue("WasTheDeliveryOnTimeAsPerPO", element));
			grnHeader.setInvReceivedWithDeliv(getTagValue("WasTheInvoiceReceivedWithDelivery", element));
			grnHeader.setInvErrorFree(getTagValue("WasTheInvoiceErrorFree", element));
			grnHeader.setVenRatingUserBased(getTagValue("VendorRatingUserBased", element));
			grnHeader.setVenRatingCpdPoSpoc(getTagValue("VendorRating_CPD_PO_SPOC_based", element));

			setTegValueForiteam("DraftGRN_Items", element);

			grnHeader.setGrnitem(grnItemsList);
			System.out.println("Item added");
//			
			grnHeader = grnHeaderRepository.save(grnHeader);
			if (grnHeader != null) {

				returnValue = true;
				log.info("1000   :   Item Stored");
				System.out.println("1000   :   Item Stored");

				List<GRNItem> grnItemsForPlant = grnHeader.getGrnitem();
				if (grnItemsForPlant.isEmpty()) {
					log.info("1001   :   Mail Notification is not sent");
					System.out.println("1001   :   Mail Notification is not sent");
				} else {

					GRNItem grnItemForData = grnItemsForPlant.get(0);
					this.plant = grnItemForData.getWerks();

					approvalMatrix = approvalMatrixRepository.findByPlant(plant);
					if (approvalMatrix == null) {
						log.info("1001   :   Plant code is not found for plant : "+plant);
						System.out.println("1001   :   Plant code is not found for plant : "+plant);
					} else {
						
						this.asnNO = grnItemForData.getDrfgrnno();

//						approvalMatrix.getLabhead()
						try {
							
							String labhead = userMasterRepo.findByAdrid(approvalMatrix.getLabhead()).getEmail();
							String pospoc = userMasterRepo.findByAdrid(approvalMatrix.getProchead()).getEmail();
							String vendor = userMasterRepo.findByAdrid(grnHeader.getLifnr()).getEmail();
							to = (labhead + "," + pospoc + "," + vendor).trim();
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						ccEmail = "";
						if (to.equalsIgnoreCase("") || to == "" || to == null) {
							log.info("1001   :   Emmail ID Are noy found for To");
							System.out.println("1001   :   Emmail ID Are noy found for To");
						}
						sub = "GRN/SRN NO. " + grnHeader.getGrn() + " dated " + Calendar.getInstance().getTime()
								+ " against  " + "the ASN no. " + asnNO + " for the Purchase Orderd no. "
								+ grnHeader.getEbeln() + " dated " + grnHeader.getBudat() + "  has been generated.";

						body = "GRN/SRN NO. " + grnHeader.getGrn() + " dated " + Calendar.getInstance().getTime()
								+ " against " + " the ASN no. " + asnNO + " for the Purchase Orderd no. "
								+ grnHeader.getEbeln() + " dated " + grnHeader.getBudat() + "  has been generated." + "";

						confEmail = emailSendingService.grnMailNotification(to, sub, body, ccEmail);
						if (confEmail) {
							log.info("1000   :   Email  is   sent for " + grnHeader.getGrn());
							System.out.println("1000   :   Email  is sent " + grnHeader.getGrn());
						} else {
							log.info("1001   :   Email  is  Not sent for " + grnHeader.getGrn());
							System.out.println("1001   :   Email Not is sent " + grnHeader.getGrn());

						}


					}
					
					
				}

			} else {
				returnValue = false;
				log.info("1001   :   Item IS NOT Stored");
				System.out.println("1000   :   Item IS NOT Stored");

			}

		}

		return grnHeader;
	}

	static int k = -1;

	private List<Object> setTegValueForiteam(String tag, Element element) {

		/// String filePath = filePath;
		File xmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		List<Object> invoiceItemList = new ArrayList<Object>();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList invoiceItemNode = doc.getElementsByTagName(tag);

			for (int i = 0; i < invoiceItemNode.getLength(); i++) {
				invoiceItemList.add(getIGRNItem(invoiceItemNode.item(i)));
				k = i;

//				==============================================================================

			}

			log.info("1005 ::  Present data updated " + filePath);
			System.out.println("1005  :: ********** Present data updated " + filePath);

		} catch (Exception e) {
			log.error("1001   ::   " + e.getMessage());
			e.printStackTrace();
		}

		return invoiceItemList;

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

}

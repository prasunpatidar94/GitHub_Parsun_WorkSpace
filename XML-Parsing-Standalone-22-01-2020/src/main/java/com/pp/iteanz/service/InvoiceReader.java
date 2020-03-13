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
import com.pp.iteanz.entity.Invoice;
import com.pp.iteanz.entity.InvoiceItem;
import com.pp.iteanz.repository.InvoiceItemRepository;
import com.pp.iteanz.repository.InvoiceRepository;
import com.pp.iteanz.repository.UserMasterRepo;

@Component("invoiceRead")
public class InvoiceReader {
	private Invoice invoice;
	private InvoiceItem invoiceItem;
	private String filePath;
	private boolean returnValue;
	private boolean confEmail;

	private List<InvoiceItem> invoiceItemsList;

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(XmlParsingStandalone1Application.class);

	// private POItemDetails podetails;
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	InvoiceItemRepository invoiceItemRepository;
	@Autowired
	private UserMasterRepo userMasterRepo;

	public Boolean invoiceRead(Document doc, String filePath) {

		this.filePath = filePath;

		NodeList nodeList = doc.getElementsByTagName("InvoiceHeader");
		System.out.println("child name " + nodeList);
		// now XML is loaded as Document in memory, lets convert it to Object List
		List<Object> invoiceList = new ArrayList<Object>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			invoiceList.add(getInvoice(nodeList.item(i)));
		}
		return returnValue;

	}

	private InvoiceItem getInvoiceItem(Node node) {
		invoiceItem = new InvoiceItem();

		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			
			invoiceItem.setEbeln(getTagValue("PONo", element));
			invoiceItem.setPstyp(getTagValue("POLineItemCategory", element));
			invoiceItem.setEbelp(getTagValue("POLineItemNo", element));
			invoiceItem.setMenge(getTagValue("GRNQuantity", element));


			invoiceItem.setBwart(getTagValue("MovementType", element));
			invoiceItem.setGrn(getTagValue("GRNNo", element));
			invoiceItem.setItemNo(getTagValue("POLineItemNo", element));
			invoiceItem.setMenge1(Float.valueOf(getTagValue("GRNQuantity", element)));
			invoiceItem.setMaktx(getTagValue("MaterialDescription", element));
			invoiceItem.setTotalAmount(getTagValue("TotalAmount", element));
			invoiceItem.setTaxAmount(getTagValue("TaxAmount", element));
			invoiceItem.setWaers(getTagValue("Currancy", element));
			invoiceItem.setStatus(getTagValue("Status", element));
			invoiceItem.setMatnr(getTagValue("MaterialNo", element));

			InvoiceItem invoiceItemSave = invoiceItemRepository.save(invoiceItem);
			invoiceItemsList.add(invoiceItemSave);
			if (invoiceItemSave != null) {

				log.info("1000   :   Item Stored");
				System.out.println("1000   :   Item Stored");

			} else {

				log.info("1001   :   Item IS NOT Stored");
				System.out.println("1000   :   Item IS NOT Stored");

			}

		}

		return invoiceItem;
	}

	// getInvoice
	private Invoice getInvoice(Node node) {
		invoice = new Invoice();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			invoiceItemsList = new ArrayList<InvoiceItem>();

			Optional<Invoice> invoice1 = invoiceRepository.findById(Long.parseLong(getTagValue("InvoiceNo", element)));
			if (invoice1.isPresent()) {
				invoice = invoice1.get();
			}else {
				invoice.setInvoiceId(Long.parseLong(getTagValue("InvoiceNo", element)));
			}
			

		
			invoice.setVendorNo(getTagValue("VendorNo", element));
			invoice.setVendorName(getTagValue("VendorName", element));
			invoice.setAsnNo(getTagValue("ASNNo", element));
			invoice.setCreationDate(getTagValue("InvoicePostingDate", element));

			setTegValueForiteam("InvoiceItems", element);

			invoice.setInvoiceItems(invoiceItemsList);
			System.out.println("Item added");
//			
			Invoice invoiceSave = invoiceRepository.save(invoice);
			if (invoiceSave != null) {

//				approvalMatrix.getLabhead()

				String to = "";
				try {

					to = userMasterRepo.findByAdrid(invoiceSave.getVendorNo()).getEmail().trim();

				} catch (Exception e) {
					e.printStackTrace();
				}

				String ccEmail = "";
				if (to.equalsIgnoreCase("") || to == "" || to == null) {
					log.info("1001   :   Emmail ID Are noy found for To");
					System.out.println("1001   :   Emmail ID Are noy found for To");
				}

				EmailSendingService emailSendingService = new EmailSendingService();

				String sub = "	Invoice No. " + invoiceSave.getInvoiceId() + " dated "
						+ Calendar.getInstance().getTime() + " for PO no. "+invoiceSave.getInvoiceItems().get(0).getEbeln()+"";

				String body = "	Invoice No. " + invoiceSave.getInvoiceId() + " dated "
						+ Calendar.getInstance().getTime() + " for" + " material no. "+invoiceSave.getInvoiceItems().get(0).getMatnr()+" has been created.";

				confEmail = emailSendingService.grnMailNotification(to, sub, body, ccEmail);
				if (confEmail) {
					log.info("1000   :   Email  is   sent for " + invoiceSave.getInvoiceId());
					System.out.println("1000   :   Email  is sent " + invoiceSave.getInvoiceId());
				} else {
					log.info("1001   :   Email  is  Not sent for " + invoiceSave.getInvoiceId());
					System.out.println("1001   :   Email Not is sent " + invoiceSave.getInvoiceId());

				}

				returnValue = true;
				log.info("1000   :   Item Stored");
				System.out.println("1000   :   Item Stored");

			} else {
				returnValue = false;
				log.info("1001   :   Item IS NOT Stored");
				System.out.println("1000   :   Item IS NOT Stored");

			}

		}

		return invoice;
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
				invoiceItemList.add(getInvoiceItem(invoiceItemNode.item(i)));
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

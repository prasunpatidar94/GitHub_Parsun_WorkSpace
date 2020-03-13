package com.pp.iteanz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pp.iteanz.XmlParsingStandalone1Application;
import com.pp.iteanz.entity.VendorLedger;
import com.pp.iteanz.repository.VendorLedgerRepository;

@Component("VendorLedgerRead")
public class VendorLedgerReader {
//	@Autowired
//	private MaterialMaster materialMaster;
	private String filePath;

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(XmlParsingStandalone1Application.class);


	@Autowired
	private VendorLedgerRepository vendorLedgerRepository ;
	public String GetVendorLedgerRead(Document doc, String filePath) {

		this.filePath = filePath;

		NodeList nodeList = doc.getElementsByTagName("VMS_VendorLedger");
		System.out.println("child name " + nodeList);
		// now XML is loaded as Document in memory, lets convert it to Object List
		List<Object> prList = new ArrayList<Object>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			prList.add(getVendorLedger(nodeList.item(i)));
		}

		return "1000   ::    sucess";
	}


	private VendorLedger getVendorLedger(Node node) {
		// XMLReaderDOM domReader = new XMLReaderDOM();
		VendorLedger vendorLedger = new VendorLedger();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
//			Optional<MaterialMaster> materialMaster1=materialMasterRepository.findByMatnrAndWerks(getTagValue("MaterialNo", element),getTagValue("Plant", element));

//			Boolean check = materialMaster1.isPresent();
			
		Optional<VendorLedger> vendorLedger1= null;
			try {
				
				String long1 = (getTagValue("DocumentNo", element));
				vendorLedger1 = vendorLedgerRepository.findByBelnr(long1);
				if (vendorLedger1.isPresent()) {
					vendorLedger = vendorLedger1.get();
				}else {
					vendorLedger.setBelnr(getTagValue("DocumentNo", element));
					}

				
			
			
			
				vendorLedger.setLifnr(long1.toString());
				vendorLedger.setName1(getTagValue("VendorName", element));	
				vendorLedger.setStatus(getTagValue("DocumentStatus", element));
				
				vendorLedger.setBldat(getTagValue("DocumentDate", element));
				vendorLedger.setBudat(getTagValue("PostingDate", element));
				vendorLedger.setEntrydat(getTagValue("EntryDate", element));
				vendorLedger.setXblnr(getTagValue("Reference", element));
				vendorLedger.setPstyp(getTagValue("DocumentType", element));
				vendorLedger.setEbeln(getTagValue("PONo", element));
				vendorLedger.setBelnr1(getTagValue("MIGODocNo", element));
				
				
				
				

				vendorLedger.setLfbnr(getTagValue("MIRODocNo", element));	
				vendorLedger.setGjahr(getTagValue("FiscalYear", element));
				vendorLedger.setMonat(getTagValue("PostingPeriod", element));
				vendorLedger.setShkzg((getTagValue("DRorCR_Indicator", element)));
				vendorLedger.setWrbtr((getTagValue("Amount", element)));
				vendorLedger.setWaers(getTagValue("Crrny", element));
				vendorLedger.setZumsk(getTagValue("SPGLInd", element));
				vendorLedger.setRebzg(getTagValue("PaymtOnInvoiceNo", element));
				vendorLedger.setWrbtr1((getTagValue("InvoiceAmount", element)));
				vendorLedger.setAugbl(getTagValue("ClearingDocNo", element));
				
				
				
				
				
		
				
				vendorLedger.setAugdt (getTagValue("ClearingDocumentDate", element));	
				vendorLedger.setSgtxt(getTagValue("Text", element));
				vendorLedger.setText1(getTagValue("BankName", element));
				vendorLedger.setUbknt(getTagValue("BankACCNo", element));
				vendorLedger.setRzawe(getTagValue("PaymentMethd", element));
				vendorLedger.setUsnam(getTagValue("DocPostedBy", element));
				vendorLedger.setChect(getTagValue("BankTransactionNoorCheckNo", element));
				
				
				



				vendorLedger=vendorLedgerRepository.save(vendorLedger);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		
	
		System.out.println(vendorLedger.toString());
		return vendorLedger;
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
	

}

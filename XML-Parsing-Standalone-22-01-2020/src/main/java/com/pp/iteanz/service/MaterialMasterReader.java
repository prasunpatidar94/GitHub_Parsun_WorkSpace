package com.pp.iteanz.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pp.iteanz.XmlParsingStandalone1Application;
import com.pp.iteanz.entity.MaterialMaster;
import com.pp.iteanz.repository.MaterialMasterRepository;

@Component("MaterialMasterRead")
public class MaterialMasterReader {
//	@Autowired
//	private MaterialMaster materialMaster;
	private String filePath;

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(XmlParsingStandalone1Application.class);


	@Autowired
	MaterialMasterRepository materialMasterRepository;

	public String materialMasterRead(Document doc, String filePath) {

		this.filePath = filePath;

		NodeList nodeList = doc.getElementsByTagName("VMS_MaterialMaster");
		System.out.println("child name " + nodeList);
		// now XML is loaded as Document in memory, lets convert it to Object List
		List<Object> prList = new ArrayList<Object>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			prList.add(getMaterialMaster(nodeList.item(i)));
		}

		return "1000   ::    sucess";
	}


	private MaterialMaster getMaterialMaster(Node node) {
		// XMLReaderDOM domReader = new XMLReaderDOM();
		
	
		MaterialMaster materialMaster = new MaterialMaster();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
//			Optional<MaterialMaster> materialMaster1=materialMasterRepository.findByMatnrAndWerks(getTagValue("MaterialNo", element),getTagValue("Plant", element));

//			Boolean check = materialMaster1.isPresent();
			
			MaterialMaster materialMaster1= null;
			try {
			try {
				 materialMaster1=materialMasterRepository.findByMatnrAndWerks(getTagValue("MaterialNo", element),getTagValue("Plant", element));

				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (materialMaster1 == null  /* || check==false */) {
				materialMaster= new MaterialMaster();

				materialMaster.setWerks(getTagValue("Plant", element));
				
				materialMaster.setMatnr(getTagValue("MaterialNo", element));
				System.out.println("new created materical master ");
			}else {
				materialMaster = materialMaster1;
			}
			materialMaster.setMatnr(getTagValue("MaterialNo", element));
			materialMaster.setMaktx (getTagValue("MaterialDescription", element));

			

			materialMaster.setMeins(getTagValue("UOM", element));
			materialMaster.setLgort(getTagValue("StorageLocation", element));
			materialMaster.setMhdrz(getTagValue("MinimumRemainingShelfLife", element));
			materialMaster.setMtart(getTagValue("MaterialType", element));
			materialMaster.setEkgrp(getTagValue("PurchasingGroup", element));
			materialMaster.setMaktl(getTagValue("MaterialGroup", element));
			materialMaster.setMfrnr(getTagValue("Manufacturer", element));
			materialMaster.setMfrpn(getTagValue("ManufacturerPartNo", element));
			materialMaster.setIprkz(getTagValue("PeriodIndicatorForSLED", element));
			materialMaster.setGroes(getTagValue("Size_Dimensions", element));
			
			
			//Size_Dimensions
			
			materialMaster.setBarcode(getTagValue("Barcode", element));
			materialMaster.setLvorm(getTagValue("DeletionIndicator", element));
			materialMaster.setCreateddate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			
//			String xmlDate ="";
			
			try {
				// xmlDate = getTagValue("InstallationDate", element);
//				 DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);//30/11/0002
//					Date date = new Date();
//					try {
//						date = format.parse(xmlDate);
//					} catch (ParseException e) {
//						log.error("1002    :: Date Parsing " + e.getMessage());
//						e.printStackTrace();
//					}
//					System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010
//					
					
					materialMaster.setIns_dat(getTagValue("InstallationDate", element));
				 
				 
				 
			} catch (Exception e) {
				materialMaster.setIns_dat("00/00/0000");
				
				
			}
			
			



			materialMaster=materialMasterRepository.save(materialMaster);
			
	
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	
		System.out.println(materialMaster.toString());
		return materialMaster;
	}


	private static String getTagValue(String tag, Element element) {
		
		NodeList nodeList= null;
try {
	 nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
} catch (Exception e) {
	System.out.println("1001   ::  field is not There ***************************");
	return "00000000";
	
}
		
		Node node = (Node) nodeList.item(0);

		if (node != null) {

			return node.getNodeValue();

		} else {

			return "0000";
		}

	}
	

}

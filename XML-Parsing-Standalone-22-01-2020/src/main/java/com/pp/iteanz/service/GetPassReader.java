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
import com.pp.iteanz.entity.GatePass;
import com.pp.iteanz.repository.GatePassRepository;

@Component("GetPassRead")
public class GetPassReader {
//	@Autowired
//	private MaterialMaster materialMaster;
	private String filePath;

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(XmlParsingStandalone1Application.class);


	@Autowired
	private GatePassRepository gatePassRepository ;
	public String GetPassRead(Document doc, String filePath) {

		this.filePath = filePath;

		NodeList nodeList = doc.getElementsByTagName("VMS_GatePassEntry");
		System.out.println("child name " + nodeList);
		// now XML is loaded as Document in memory, lets convert it to Object List
		List<Object> prList = new ArrayList<Object>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			prList.add(getGetPass(nodeList.item(i)));
		}

		return "1000   ::    sucess";
	}


	private GatePass getGetPass(Node node) {
		// XMLReaderDOM domReader = new XMLReaderDOM();
		GatePass gatePass = new GatePass();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
//			Optional<MaterialMaster> materialMaster1=materialMasterRepository.findByMatnrAndWerks(getTagValue("MaterialNo", element),getTagValue("Plant", element));

//			Boolean check = materialMaster1.isPresent();
			
		Optional<GatePass> gatePass1= null;
			try {
				
				Long long1 = Long.parseLong(getTagValue("GatePassEntryNo", element));
				gatePass1 = gatePassRepository.findById(long1);
				if (gatePass1.isPresent()) {
					gatePass = gatePass1.get();
				}else {
					gatePass.setZdocno(long1);
				}

				
			
			
			
			
			gatePass.setLifnr (getTagValue("VendorCode", element));	
			gatePass.setName1(getTagValue("VendorDescription", element));
			gatePass.setWerks(getTagValue("PlantCode", element));
			gatePass.setZeile(Double.parseDouble(getTagValue("ItemNo", element)));
			gatePass.setMatnr(getTagValue("MaterialNo", element));
			gatePass.setMaktx(getTagValue("MaterialDescription", element));
			gatePass.setZrefno(getTagValue("ReferenceNo", element));
			gatePass.setZcpudt(getTagValue("DocCreationDate", element));
			gatePass.setChec(getTagValue("ASNCreationIndicator", element));
			gatePass.setZreqsby(getTagValue("RequestedBy", element));
		



			gatePass=gatePassRepository.save(gatePass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		
	
		System.out.println(gatePass.toString());
		return gatePass;
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

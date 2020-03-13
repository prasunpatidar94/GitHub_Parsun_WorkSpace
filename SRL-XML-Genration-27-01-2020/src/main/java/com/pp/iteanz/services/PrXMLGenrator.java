package com.pp.iteanz.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pp.iteanz.entity.PurchaseRequisition;
import com.pp.iteanz.repository.PurchaseRequisitionRepository;

@Component(value = "prXMLGenrator")
public class PrXMLGenrator {
	@Autowired
	PurchaseRequisitionRepository purchaseRequisitionRepository;

	public Boolean prXMLGenrator() {

		String prXML = "";
		FileFormFTP fileFormFTP = new FileFormFTP();

//		List<PurchaseRequisition> purchaseRequisitionList = purchaseRequisitionRepository.findByBanfnAndStatus(banfn ,1);

		Date date1 = new Date();
		String format = new SimpleDateFormat("yyyy-MM-dd").format(date1);

		// List<PurchaseRequisition> purchaseRequisitionList =
		// purchaseRequisitionRepository.findByStatus(1);
		List<PurchaseRequisition> purchaseRequisitionList = purchaseRequisitionRepository.findByCdDat(format);
		if (purchaseRequisitionList.isEmpty()) {

			return false;

		}

		for (PurchaseRequisition purchaseRequisitionOne : purchaseRequisitionList) {

			String fileName = "" + purchaseRequisitionOne.getBanfn() + "_" + purchaseRequisitionOne.getBnfpo();

			if (purchaseRequisitionOne.getStatus() != 3) {

			System.out.println("**********************Database******************"+purchaseRequisitionOne.getCdDat()+"**************************************");
			
			
			System.out.println("********************External*******************"+format+"**************************************");

		

				String statusheader = "";

				if (purchaseRequisitionOne.getStatus() == 1) {
					statusheader = "Approved";
				} else if (purchaseRequisitionOne.getStatus() == 2) {
					statusheader = "Rejected";
				} else if (purchaseRequisitionOne.getStatus() == 4) {
					statusheader = "Clarification";
				} else if (purchaseRequisitionOne.getStatus() == 5) {
					statusheader = "Approve forward";
				}

				///////////////////////////////////////////////////// *******************************

				prXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
						+ "	<nso:MT_VMS_PRApproval xmlns:nso=\"http://srl.in/VMS_PRApproval\">\r\n"
						+ "	<VMS_PRApproval>\r\n" + "	<PRNO>" + purchaseRequisitionOne.getBanfn() + "</PRNO>\r\n"
						+ "	<ItemNo>" + purchaseRequisitionOne.getBnfpo() + "</ItemNo>\r\n" + "	<Status>" + statusheader
						+ "</Status>\r\n" + "	</VMS_PRApproval>\r\n" + "	</nso:MT_VMS_PRApproval>";

				prXML = prXML.replaceFirst("\r\n", " ");

				boolean inputSrl = new File(System.getProperty("user.home") + "\\Documents\\SRL\\PR\\").mkdirs();
				System.out.println(inputSrl);
				File file = new File(System.getProperty("user.home") + "\\Documents\\SRL\\PR\\" + fileName + ".xml");
//				C:/Users/Iteanz Emp/Documents/SRL/Contract
				FileWriter writer;

				String inputFilePath = System.getProperty("user.home") + "\\Documents\\SRL\\PR\\";

				try {
					writer = new FileWriter(file);
					writer.write(prXML);

					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}

				boolean storeFTP;

				storeFTP = fileFormFTP.uploadFTP("/VMS/PRAPP/", fileName, inputFilePath);
				if (storeFTP) {
					System.out.println("The first file is uploaded successfully." + fileName);

//						 File f = file;
//							if(f.exists()){
//								f.delete();
//								try {
//									f.createNewFile();
//								} catch (IOException e) {
//									e.printStackTrace();
//								}
//							}

				} else {

					System.out.println("The first file is uploaded unsuccessfully." + fileName);
				}

			} else {
				System.out.println("The first file is uploaded unsuccessfully." + fileName);
			}

		}

		return true;

	}

}

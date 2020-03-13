package com.iteanz.srl.service;

import java.io.IOException;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.transaction.Transactional;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteanz.srl.domain.ASNHeader;
import com.iteanz.srl.domain.ASNItem;
import com.iteanz.srl.domain.ApprovalLog;
import com.iteanz.srl.domain.ApprovalMatrix;
import com.iteanz.srl.domain.Invoice;
import com.iteanz.srl.domain.POItemDetails;
import com.iteanz.srl.domain.ProposalSummary;
import com.iteanz.srl.domain.PurchaseOrder;
import com.iteanz.srl.domain.PurchaseRequisition;
import com.iteanz.srl.domain.RFPHeader;
import com.iteanz.srl.domain.RFPItem;
import com.iteanz.srl.domain.Status;
import com.iteanz.srl.domain.Vendor;
import com.iteanz.srl.domain.WorkflowConfig;
import com.iteanz.srl.repositories.ASNRepository;
import com.iteanz.srl.repositories.ApprovalLogRepo;
import com.iteanz.srl.repositories.ApprovalMatrixRepository;
import com.iteanz.srl.repositories.InvoiceRepository;
import com.iteanz.srl.repositories.ProposalSummaryRepository;
import com.iteanz.srl.repositories.PurchaseOrderRepository;
import com.iteanz.srl.repositories.PurchaseRequisitionRepository;
import com.iteanz.srl.repositories.RFPHeaderRepository;
import com.iteanz.srl.repositories.StatusRepo;
import com.iteanz.srl.repositories.UserMasterRepo;
import com.iteanz.srl.repositories.VendorRegistrationRepository;
import com.iteanz.srl.repositories.VendorRepository;
import com.iteanz.srl.repositories.WebServiceRepository;
import com.iteanz.srl.repositories.WorkFlowRepo;
import com.iteanz.srl.utility.DateCompare;
import com.iteanz.srl.utility.EmailSending;

@Service
public class ApproverServices {
//declear the data 
	@Autowired
	WebService webService;

	@Autowired
	WebServiceRepository sampleRepo;

	EmailSending emailSending = new EmailSending();

	
	
	@Autowired
	private ASNRepository asnRepository;


	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private WorkFlowRepo workFlowRepo;

	@Autowired
	private VendorRegistrationRepository vendorRegistrationRepository;

	@Autowired
	private ApprovalLogRepo approvalLogRepo;

	@Autowired
	private RFPHeaderRepository rfpHeaderRepository;

	@Autowired
	private DateCompare dateCompare;

	@Autowired
	private StatusRepo statusRepo;

	@Autowired
	private VendorRepository vendorRepository;

	@Autowired
	private PurchaseOrderRepository poRepository;

	@Autowired
	private ApprovalMatrixRepository approvalMatrixRepository;

	@Autowired
	private PurchaseRequisitionRepository purchaseRequisitionRepository;

	@Autowired
	private ProposalSummaryRepository proposalSummaryRepository;

	@Autowired
	private UserMasterRepo userMasterRepo;

	// **********************workflow start ***********************
	@SuppressWarnings("unchecked")
	@Transactional

	public String workflowStart(String appid, String subpocess, Long reqid)
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		JSONObject js = new JSONObject();
		String res = null;
		Status statusObj = null;
		String plant = null;
		String lgort = "";
		Boolean mailConfig = false;
		String rfpCreator = null;

		String position = null;

		PurchaseOrder purchaseOrder = null;
		ApprovalLog approvalLogForConf = null;
		int aprvlevel = 0;
		String to = "", subject = "", body = "", ccEmail = "";
		String Aprv1 = null;

		statusObj = statusRepo.findOne(6);
		if (statusObj == null) {
			js.put("code", "1001");
			js.put("error", "Status (6) is not found");
			res = js.toString();
			return res;
		}
		WorkflowConfig workflowConfigList = workFlowRepo.getActiveWorkflowConfigByStatus(appid, subpocess, statusObj);
		if (workflowConfigList == null) {
			js.put("code", "1001");
			js.put("error", "Workflow Config is empty");
			res = js.toString();
			return res;
		}
		aprvlevel = workflowConfigList.getAprvLevels();

		WorkflowConfig appIdObj = workFlowRepo.findByAppid(appid);
		if (appIdObj == null) {
			js.put("code", "1001");
			js.put("error", "appIdObj is not found");
			res = js.toString();
			return res;
		}
		ApprovalLog aprvLog = new ApprovalLog();

		aprvLog.setReqNo(reqid);
		aprvLog.setSubProcess(subpocess);
		aprvLog.setAppid(appIdObj);
		aprvLog.setMailstatus(3);
		aprvLog.setStatus(statusRepo.findOne(3));
		
		if (appid == "PR" || appid.equalsIgnoreCase("pr")) { // *****************pr data
			List<PurchaseRequisition> purchaseRequisitionListForCreater = purchaseRequisitionRepository
					.findByBanfn(reqid);
			if (purchaseRequisitionListForCreater.isEmpty() || purchaseRequisitionListForCreater == null) {
				js.put("code", "1001");
				js.put("error", "PurchaseRequisition is Empty");
				res = js.toString();
				return res;
			}
			PurchaseRequisition purchaseRequisitionPlant = purchaseRequisitionListForCreater.get(0);
			aprvLog.setInitiator(purchaseRequisitionPlant.getCrdBy());
			plant = purchaseRequisitionPlant.getWerks();
			lgort = purchaseRequisitionPlant.getLgort();
			aprvLog.setWerks(plant);
			aprvLog.setLgort(lgort);
		} else if (appid == "ASN" || appid.equalsIgnoreCase("ASN")) { // ****************asn data
			ASNHeader asnHeader = asnRepository.findOne(reqid);
			if (asnHeader == null) {
				js.put("code", "1001");
				js.put("error", "ASNHeader  is Not Found");
				res = js.toString();
				return res;
			}

			purchaseOrder = poRepository.findByEbeln(asnHeader.getEbeln());

			if (purchaseOrder == null) {
				js.put("code", "1001");
				js.put("error", "PurchaseOrder  is Not Found");
				res = js.toString();
				return res;
			}

			List<ASNItem> asnItems = asnHeader.getAsnitem();
			if (asnItems.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "  is Not Found");
				res = js.toString();
				return res;
			}

			ASNItem asnItemOne = asnItems.get(0);

			aprvLog.setInitiator(purchaseOrder.getLifnr());
			plant = asnItemOne.getWerks();
			lgort = asnItemOne.getLgort();
			aprvLog.setWerks(plant);
			aprvLog.setLgort(lgort);

			String check = dateCompare.dateDiffrence(plant, reqid);

			if (check.equalsIgnoreCase("ex") || check.equalsIgnoreCase("act")) {
				System.out.println("ok fine it is working");
			} else {
				js.put("code", "1001");
				js.put("error", check);
				res = js.toString();
				return res;
			}

		} else if (appid == "RFPVS" || appid.equalsIgnoreCase("RFPVS")) { //******************* RFPVS data

			RFPHeader rfpHeader = rfpHeaderRepository.findOne(reqid);

			if (rfpHeader == null) {

				js.put("code", "1001");
				js.put("error", "RFPHeader is Not Found");
				res = js.toString();
				return res;
			}

			List<RFPItem> itemsList = rfpHeader.getItem();
			if (itemsList.isEmpty()) {

				js.put("code", "1001");
				js.put("error", "RFPItem List is Empty");
				res = js.toString();
				return res;
			}

			ProposalSummary proposalSummary = proposalSummaryRepository.findByRfpno(reqid);
			if (proposalSummary == null) {

				js.put("code", "1001");
				js.put("error", "ProposalSummary is Not Found");
				res = js.toString();
				return res;
			}

			position = proposalSummary.getPosition();

			RFPItem rfpItem = itemsList.get(0);
			plant = rfpItem.getWerks();
			lgort = rfpItem.getLgort();
			aprvLog.setWerks(plant);
			aprvLog.setLgort(lgort);

			aprvLog.setInitiator(rfpHeader.getCreatedby());

		} else if (appid == "VM" || appid.equalsIgnoreCase("VM")) {// ************************VM data

			Vendor vendor = vendorRepository.findOne(reqid);
			vendor = vendorRepository.findByVid(reqid);
			List<ProposalSummary> proposalSummary = proposalSummaryRepository.findByLifnr(vendor.getCreatedBy());
			if (proposalSummary.isEmpty()) {
				System.err.println("1001  ::  ProposalSummary List is not found");
				js.put("code", "1001");
				js.put("error", " ProposalSummary List is not found");
				res = js.toString();
				return res;

			}

			RFPHeader rfpHeader = rfpHeaderRepository.findOne(proposalSummary.get(0).getRfpno());
			if (rfpHeader == null) {
				js.put("code", "1001");
				js.put("error", " RFPHeader  is not found");
				res = js.toString();
				return res;
			}
			rfpCreator = rfpHeader.getCreatedby();

			List<RFPItem> rfpItems = rfpHeader.getItem();
			if (rfpItems.isEmpty()) {
				js.put("code", "1001");
				js.put("error", " RFPItem List is not found");
				res = js.toString();
				return res;
			}
			RFPItem rfpItem = rfpItems.get(0);
			plant = rfpItem.getWerks();
			lgort = rfpItem.getLgort();
			aprvLog.setWerks(plant);
			aprvLog.setLgort(lgort);

			System.out.println("**************plant :: " + plant + "******************");
			aprvLog.setInitiator("" + reqid);

			System.out.println(" noe not aveleble set the   detnthe the after the some time ");

		}else if (appid == "NONPOINV" || appid.equalsIgnoreCase("NONPOINV")) {// ***************************NONPOINV data

			Invoice invoice = invoiceRepository.findOne(reqid);
			if (invoice == null) {
				js.put("code", "1001");
				js.put("error", "Invoice is Not Found");
				res = js.toString();
				return res;
			}

			plant = "logistics";                   //******change karana he *******
			lgort = "";
			aprvLog.setWerks(plant);
			aprvLog.setLgort(lgort);
			aprvLog.setInitiator(invoice.getCreatedBy());

		}

		ApprovalMatrix approvalMatrixForAprv = approvalMatrixRepository.findByPlantAndLgort(plant, lgort);
		if (approvalMatrixForAprv == null) {
			js.put("code", "1001");
			js.put("error", "ApprovalMatrix is not maintain for plant " + plant + "and storage location " + lgort + "");
			res = js.toString();
			return res;
		}
		// **********************************************
		List<String> list1 = new ArrayList<String>();
		int i = 1;
		if (aprvlevel == 1) {
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv1()));
		} else if (aprvlevel == 2) {
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv1()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv2()));
		} else if (aprvlevel == 3) {
			if (appid == "VM" || appid.equalsIgnoreCase("VM"))
				list1.add(rfpCreator);
			else
				list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv1()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv2()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv3()));

		} else if (aprvlevel == 4) {
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv1()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv2()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv3()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv4()));
		} else if (aprvlevel == 5) {
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv1()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv2()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv3()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv4()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv5()));
		} else if (aprvlevel == 6) {
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv1()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv2()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv3()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv4()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv5()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv6()));
		} else if (aprvlevel == 7) {
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv1()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv2()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv3()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv4()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv5()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv6()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv7()));
		} else if (aprvlevel == 8) {
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv1()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv2()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv3()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv4()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv5()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv6()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv7()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv8()));
		} else if (aprvlevel == 9) {
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv1()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv2()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv3()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv4()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv5()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv6()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv7()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv8()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv9()));
		} else if (aprvlevel == 10) {
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv1()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv2()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv3()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv4()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv5()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv6()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv7()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv8()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv9()));
			list1.add(aprvDesignationFinder(approvalMatrixForAprv, workflowConfigList.getAprv10()));
		}
		if (list1.isEmpty()) {
			js.put("code", "1001");
			js.put("error", "Apvr List is Empty for " + reqid + "");
			res = js.toString();
			return res;
		}
		Aprv1 = list1.get(0);
		for (String appro : list1) {
			ApprovalLog tempAprvLog = new ApprovalLog();
			ApprovalLog approvalLogCheck = approvalLogRepo.findByAppidAndSubprocessAndReqidAndAprv(appIdObj, subpocess,
					reqid, appro);
			if (approvalLogCheck != null) {
				approvalLogCheck.setMailstatus(3);
				approvalLogCheck.setStatus(statusRepo.findOne(3));
				BeanUtils.copyProperties(approvalLogCheck, tempAprvLog);
			} else {
				BeanUtils.copyProperties(aprvLog, tempAprvLog);
			}
			tempAprvLog.setAprv(appro);
			tempAprvLog.setAprvlevel("L" + i);
			i++;

			ApprovalLog aprvLogObj1 = approvalLogRepo.save(tempAprvLog);

			if (aprvLogObj1 != null)
				System.out.println("aprvlog data save level " + aprvlevel + "" + aprvLogObj1.getId());
			else
				System.out.println("aprvlog data not save level " + aprvlevel + "");
		}
		ApprovalLog approvalLogForMail = approvalLogRepo.findByAppidAndSubprocessAndReqidAndAprv(appIdObj, subpocess,
				reqid, Aprv1);
		if (approvalLogForMail == null) {
			js.put("code", "1001");
			js.put("error", "ApprovalLog List is Empty");
			res = js.toString();
			return res;
		}

		if (appid == "PR" || appid.equalsIgnoreCase("pr")) { // *****PR Mail*********************

			subject = "Purchase Request no. " + reqid + "   has been initiated";
			body = "Purchase Request no. " + reqid
					+ " has been initiated. Request your kind verification and approval for the same.";
			to = userMasterRepo.findByAdrid(Aprv1).getEmail().trim();
			if (to.isEmpty() || to.equalsIgnoreCase("")) {
				js.put("code", "1001");
				js.put("error", "APRV EMAIL  is Empty");
				res = js.toString();
				return res;
			}

			mailConfig = emailSending.emailSendService(to, subject, body, ccEmail);
		} else if (appid == "ASN" || appid.equalsIgnoreCase("ASN")) {// ******ASN Mail**********
			to = userMasterRepo.findByAdrid(Aprv1).getEmail().trim();

			subject = "ASN no. " + reqid + " against the Purchase Orderd no. " + purchaseOrder.getEbeln() + " dated "
					+ Calendar.getInstance().getTime() + " has been generated by the vendor.\r\n" + "";
			body = "ASN no. " + reqid + " against the Purchase Orderd no. " + purchaseOrder.getEbeln() + " dated "
					+ Calendar.getInstance().getTime()
					+ " has been generated by the vendor. Kindly verify and confirm the same in revert.\r\n" + "";

			mailConfig = emailSending.emailSendService(to, subject, body, "");

		} else if (appid.equalsIgnoreCase("RFPVS") || appid == "RFPVS") {// ******RFPVS mail*********************

			subject = "" + position + " has been maintained for vendor  against rfp no." + reqid + ". ";
			body = "	" + position + " has been maintained for vendor against rfp no." + reqid + "."
					+ " Kindly initiate the approval.";
			to = userMasterRepo.findByAdrid(Aprv1).getEmail().trim();
			if (to.isEmpty() || to == "" || to.equalsIgnoreCase("") || to == null) {
				js.put("code", "1001");
				js.put("error", "TO Email ID IS NOT FOUND");
				res = js.toString();
				return res;
			}
			mailConfig = emailSending.emailSendService(to, subject, body, "");
		}	else if (appid.equalsIgnoreCase("VM") || appid == "VM") {//******VM mail*****************

			Vendor vendors = vendorRepository.findByVid(reqid);
			if (vendors == null) {
				js.put("code", "1001");
				js.put("error", "Vendor  is empty");
				res = js.toString();
				return res;

			}

			subject = "Details submitted for Vendor" + vendors.getOrgName() + "";
			body = "The required details have been submitted by the vendor" + vendors.getOrgName()
					+ " for the temporary registration purpose." + " Kindly do the needful.";

			to = userMasterRepo.findByAdrid(rfpCreator).getEmail();
			if (to.isEmpty() || to == null || to.equalsIgnoreCase("")) {
				js.put("code", "1001");
				js.put("error", "First Aprv is Not found");
				res = js.toString();
				return res;
			}
			mailConfig = emailSending.emailSendService(to, subject, body, "");			
		}

		if (mailConfig) {// ***********************************mail Status Change
							// *****************************
			approvalLogForMail.setMailstatus(1);
			approvalLogForConf = approvalLogRepo.save(approvalLogForMail);
			if (approvalLogForConf != null) {
				js.put("code", "1000");
				js.put("success", ow.writeValueAsString(approvalLogForConf));
				res = js.toString();
				return res;
			} else {
				js.put("code", "1001");
				js.put("error", "mailStatus is not save in ApprovalLog for " + reqid + "");
				res = js.toString();
				return res;
			}
		} else {
			js.put("code", "1001");
			js.put("error", "somethis is wrong in mail sending for " + reqid + "");
			res = js.toString();
			return res;
		}
	}

	// **********************po Workflow*******************************
	public String workflowStartForPO(String appid, String subpocess, Long reqid)
			throws JSONException, JsonGenerationException, JsonMappingException, IOException {

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		JSONObject js = new JSONObject();
		String res = null;
		Status statusObj = null;
		ApprovalLog aprvLog = null;
		String plant = null;
		String lgort = "";
		Boolean mailConfig = false;
		ApprovalLog approvalLogForConf = null;
		PurchaseOrder purchaseOrder = null;
		int aprvlevel = 0;
		String to = "", subject = "", body = "", ccEmail = "";
		String Aprv1 = null;

		statusObj = statusRepo.findOne(6);
		if (statusObj == null) {
			js.put("code", "1001");
			js.put("error", "Status (6) is not found");
			res = js.toString();
			return res;
		}
		WorkflowConfig workflowConfigList = workFlowRepo.getActiveWorkflowConfigByStatus(appid, subpocess, statusObj);
		if (workflowConfigList == null) {
			js.put("code", "1001");
			js.put("error", "Workflow Config is empty");
			res = js.toString();
			return res;
		}
		aprvlevel = workflowConfigList.getAprvLevels();

		WorkflowConfig appIdObj = workFlowRepo.findByAppid(appid);
		if (appIdObj == null) {
			js.put("code", "1001");
			js.put("error", "appIdObj is not found");
			res = js.toString();
			return res;
		}
		List<ApprovalLog> approvalLogCheck = approvalLogRepo.findByAppidAndSubprocessAndReqid(appIdObj, subpocess,
				reqid);

		
		
		if (approvalLogCheck.isEmpty()) {

//			BeanUtils.copyProperties(source, target);;

			aprvLog = new ApprovalLog();

		} else {
			
			aprvLog = new ApprovalLog();

			BeanUtils.copyProperties(approvalLogCheck.get(0), aprvLog);

//			aprvLog = approvalLogCheck;
		}

		aprvLog.setReqNo(reqid);

		aprvLog.setSubProcess(subpocess);
		aprvLog.setAppid(appIdObj);
		aprvLog.setStatus(statusRepo.findOne(3));// 123456
		// aprvLog.setInitiator(workflowConfigList.getCreatedBy());

		if (appid == "PO" || appid.equalsIgnoreCase("PO")) {

			purchaseOrder = poRepository.findByEbeln(reqid);

			if (purchaseOrder == null) {
				js.put("code", "1001");
				js.put("error", "PurchaseOrder is not found");
				res = js.toString();
				return res;
			}
			List<POItemDetails> poItemDetailsList = purchaseOrder.getPoitem();
			if (poItemDetailsList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "POItemDetails list  is empty");
				res = js.toString();
				return res;
			}

			POItemDetails poItemDetailsOne = poItemDetailsList.get(0);

			aprvLog.setInitiator(purchaseOrder.getErnam());
			plant = poItemDetailsOne.getWerks();
			lgort = poItemDetailsOne.getLgort();
			aprvLog.setWerks(plant);
			aprvLog.setLgort(lgort);
			aprvLog.setAprv(purchaseOrder.getLifnr());
			Aprv1 = purchaseOrder.getLifnr();
			aprvLog.setAprvlevel("L1");

			aprvLog = approvalLogRepo.save(aprvLog);
		}

		Aprv1 = aprvLog.getAprv();
		// **********************************************

		ApprovalLog approvalLogForMail = approvalLogRepo.findByAppidAndSubprocessAndReqidAndAprv(appIdObj, subpocess,
				reqid, Aprv1);
		if (approvalLogForMail == null) {
			js.put("code", "1001");
			js.put("error", "ApprovalLog List is Empty");
			res = js.toString();
			return res;

		}
		// ***********************PO Mail*****************************
		to = userMasterRepo.findByAdrid(Aprv1).getEmail().trim();
		subject = " Purchase Orderd no. " + reqid + " dated " + Calendar.getInstance().getTime()
				+ " has been generated.";
		body = "Purchase Orderd no. " + reqid + "  dated " + Calendar.getInstance().getTime()
				+ " has been generated. Kindly verify and revert your kind acknowledgement for the same.";

		mailConfig = emailSending.emailSendService(to, subject, body, "");

		// *****************************************************************

		if (mailConfig) {
//			for (ApprovalLog approvalLogOne : approvalLogForMail) {

			approvalLogForMail.setMailstatus(1);
			approvalLogForConf = approvalLogRepo.save(approvalLogForMail);

//			}
			if (approvalLogForConf != null) {
				js.put("code", "1000");
				js.put("success", ow.writeValueAsString(approvalLogForMail));
				res = js.toString();
				return res;
			} else {
				js.put("code", "1001");
				js.put("error", "mailStatus is not save in ApprovalLog for " + reqid + "");
				res = js.toString();
				return res;
			}

		} else {
			js.put("code", "1001");
			js.put("error", "somethis is wrong in mail sending for " + reqid + "");
			res = js.toString();
			return res;
		}
	}

////*********************PR ACK***********************************ASN

// PR AcknowledgementMail
//	@SuppressWarnings("unused")
	@Transactional
	public String prAcknowledgementMail(String userIdNo, Long reqid, String appid, int status, String remark,
			String subProcess) throws JSONException {

		JSONObject js = new JSONObject();

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String res = null;
		String plant = null;
		String lgort = null;

		Boolean mailSent = false;
		String apprverId = "";
		String creatorEmail = "";
		Boolean count = false;
		String aprvLevel = "";
		String aprv = "";
		int countSize = 0;
		String touchItem = "";
		StringBuffer stringBuffer = new StringBuffer();
		StringBuffer stringBufferloop = new StringBuffer();
		ApprovalLog touchApprovalLog = null;

		String to = null, body = null, sub = null, ccEmail = null;
		EmailSending emailSending = new EmailSending();
		List<PurchaseRequisition> PurchaseRequisitionListForPlantAndLgort = purchaseRequisitionRepository
				.findByBanfn(reqid);
		if (PurchaseRequisitionListForPlantAndLgort == null) {
			js.put("code", "1001");
			js.put("error", "PurchaseRequisitionListForPlant is not found");
			res = js.toString();
			return res;
		} else {
			PurchaseRequisition purchaseRequisitionForPlantAndLgort = PurchaseRequisitionListForPlantAndLgort.get(0);
			plant = purchaseRequisitionForPlantAndLgort.getWerks();
			lgort = purchaseRequisitionForPlantAndLgort.getLgort();
		}
		ApprovalMatrix approvalMatrixForAprv = approvalMatrixRepository.findByPlantAndLgort(plant, lgort);
		if (approvalMatrixForAprv == null) {
			js.put("code", "1001");
			js.put("error", " ApprovalMatrix is not found");
			res = js.toString();
			return res;
		}

//		if (approvalMatrixForAprv.getLabhead().equalsIgnoreCase("" + userIdNo))
//			aprvLevel = "labhead";
//		else if (approvalMatrixForAprv.getBuhead().equalsIgnoreCase("" + userIdNo))
//			aprvLevel = "buhead";
//		else if (approvalMatrixForAprv.getProchead().equalsIgnoreCase("" + userIdNo))
//			aprvLevel = "prochead";
//		else if (approvalMatrixForAprv.getMatspoc().equalsIgnoreCase("" + userIdNo))
//			aprvLevel = "matspoc";
//		else if (approvalMatrixForAprv.getRfpcreator().equalsIgnoreCase("" + userIdNo))
//			aprvLevel = "rfpcreator";
//		else if (approvalMatrixForAprv.getMdmteam().equalsIgnoreCase("" + userIdNo))
//			aprvLevel = "mdmteam";

		count = true;

		// =========================================================
		Status statusObjForAprv = statusRepo.findOne(status);
		if (statusObjForAprv == null) {

			js.put("code", "1001");
			js.put("error", "Invalid Status for  " + status);
			res = js.toString();
			return res;
		}

		Status statusObj = statusRepo.findOne(3);
		if (statusObj == null) {

			js.put("code", "1001");
			js.put("error", "Invalid Status ");
			res = js.toString();
			return res;
		}

		WorkflowConfig appIdObj = sampleRepo.getWorkflowConfigByAppid(appid)/* .getId() */;
		if (appIdObj == null) {
			js.put("code", "1001");
			js.put("error", "WORKFLOW For This " + appid + " Is Not Maintain ");
			res = js.toString();
			return res;// prasunmail

		}

		List<ApprovalLog> approvalLogValidation = approvalLogRepo
				.findByAppidAndSubprocessAndReqidAndMailstatusAndStatusAndAprv(appIdObj, subProcess, reqid, 1,
						statusObj, userIdNo);
		if (approvalLogValidation.isEmpty()) {
			js.put("code", "1001");
			js.put("error", "Invalid Trying To Approve ");
			res = js.toString();
			return res;

		}

		// *************************PR**************
		ApprovalLog AprvLogForSet = approvalLogRepo.findByAppidAndSubprocessAndReqidAndAprv(appIdObj, subProcess, reqid,
				userIdNo);
		if (AprvLogForSet == null) {
			js.put("code", "1001");
			js.put("error", "ApprovalLog is Not Found ");
			res = js.toString();
			return res;
		}

		creatorEmail = AprvLogForSet.getInitiator().trim();
		AprvLogForSet.setRemark(remark);
		AprvLogForSet.setApprovedBy(userMasterRepo.findByAdrid(userIdNo).getPnam());
		AprvLogForSet.setApprovedId(userIdNo);
		AprvLogForSet.setCddat(Calendar.getInstance().getTime());
		AprvLogForSet.setChangedBy("" + userIdNo);
		AprvLogForSet.setStatus(statusObjForAprv);
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(date);
		AprvLogForSet.setApprovedDate(strDate);
		AprvLogForSet.setApprovedTime(Calendar.getInstance().getTime());

		AprvLogForSet = sampleRepo.save(AprvLogForSet);
		if (AprvLogForSet == null) {
			System.out.println("1000  ::  Data inserted");
		}

		for (PurchaseRequisition purchaseRequisitionsForBody : PurchaseRequisitionListForPlantAndLgort) {

			String poNo = "" + purchaseRequisitionsForBody.getBanfn();
			String mat_code = "" + purchaseRequisitionsForBody.getMatnr();
			String item_no = "" + purchaseRequisitionsForBody.getBnfpo();
			String quantity = "" + purchaseRequisitionsForBody.getMenge();
			String statusdata = "" + purchaseRequisitionsForBody.getStatus();
			String statusStr = "";

			if (poNo.isEmpty() || poNo == null || poNo.equalsIgnoreCase("")) {

				js.put("code", "1001");
				js.put("error", " poNo is Empty ");
				res = js.toString();
				return res;// prasunmail

			} else if (mat_code.isEmpty() || mat_code == null || mat_code.equalsIgnoreCase("")) {
				js.put("code", "1001");
				js.put("error", " poNo is Empty ");
				res = js.toString();
				return res;// prasunmail

			} else if (item_no.isEmpty() || item_no == null || item_no.equalsIgnoreCase("")) {
				js.put("code", "1001");
				js.put("error", " poNo is Empty ");
				res = js.toString();
				return res;// prasunmail

			} else if (quantity.isEmpty() || quantity == null || quantity.equalsIgnoreCase("")) {
				js.put("code", "1001");
				js.put("error", " poNo is Empty ");
				res = js.toString();
				return res;// prasunmail

			} else if (statusdata.isEmpty() || statusdata == null || statusdata.equalsIgnoreCase("")) {
				js.put("code", "1001");
				js.put("error", " status is Empty ");
				res = js.toString();
				return res;// prasunmail

			}

			if (statusdata.equalsIgnoreCase("1") || statusdata == "1") {
				statusStr = "Approved";

			} else if (statusdata.equalsIgnoreCase("2") || statusdata == "2") {
				statusStr = "Rejected";

			} else if (statusdata.equalsIgnoreCase("3") || statusdata == "3") {
				statusStr = "Pending";
			} else if (statusdata.equalsIgnoreCase("4") || statusdata == "4") {
				statusStr = "Clarification";
			} else if (statusdata.equalsIgnoreCase("5") || statusdata == "5") {
				statusStr = "Approve and forword";
			}

			stringBufferloop.append(" <tr> ");
			stringBufferloop.append(" <td>" + poNo + "</td> ");
			stringBufferloop.append(" <td>" + item_no + "</td> ");
			stringBufferloop.append(" <td>" + mat_code + "</td> ");
			stringBufferloop.append(" <td>" + quantity + "</td> ");
			stringBufferloop.append(" <td>" + statusStr + "</td> ");
			stringBufferloop.append(" </tr>");

		}

		// ====================================================================
		// StringBuffer stringBuffer = new StringBuffer();

//		String replace1 = "prasun";
		stringBuffer.append("<html>");
		stringBuffer.append("<body>");
		stringBuffer.append("<p>Prasun</p>");
		stringBuffer.append("<table border = '1' width = '100%'  font color='black'> ");

		stringBuffer.append("<caption><font color=\"red\"><b>SRL Diagnostics</b></font></caption> ");

		stringBuffer.append("<thead>");

		stringBuffer.append(" <tr> ");
//			stringBuffer.append("<font color=\"black\">");
		stringBuffer.append(" <th>PURCHASE REQUISITION</th> ");
		stringBuffer.append("<th>ITEM NO</th> ");
		stringBuffer.append(" <th>MATERIAL NO</th>");
		stringBuffer.append(" <th>QUANTITY</th>");
		stringBuffer.append(" <th>STATUS</th>");
//			stringBuffer.append("</font>");
		stringBuffer.append(" </tr>");

		stringBuffer.append(" </thead> ");
		stringBuffer.append("<tbody>");

//			stringBuffer.append(" <tr> ");	
//			stringBuffer.append(" <td>96.1%</td> ");	
//			stringBuffer.append(" <td>17.0%</td> ");	
//			stringBuffer.append(" <td>14.3%</td> ");	
//			stringBuffer.append(" <td>17.0%</td> ");	
//			stringBuffer.append(" <td>14.3%</td> ");
//			stringBuffer.append(" </tr>");	
		stringBuffer.append(stringBufferloop);

		stringBuffer.append(" </tbody>");
		stringBuffer.append("<tfoot> ");
		stringBuffer.append(" <tr> ");
		stringBuffer.append(" <td colspan=\"5\">");
		stringBuffer.append("<em>REMARK : - </em> " + remark + "</em>");
		stringBuffer.append("<br><em><a href=\"http://192.168.0.56:8080/ZSRL_PR\" target=\"_blank\">Login</a> ");

//		<a href="https://w3techs.com/technologies/overview/javascript_library/all" target="_blank">W3Techs</a> 

		stringBuffer.append("</tr> ");
		stringBuffer.append("</tfoot> ");
		stringBuffer.append("</table>");
		stringBuffer.append("</body>");
		stringBuffer.append("</html>");

		// ====================================================================

		List<ApprovalLog> approvalLogForNextAprv = approvalLogRepo
				.findByAppidAndSubprocessAndReqidAndMailstatusAndStatus(appIdObj, subProcess, reqid, 3, statusObj);
		if (approvalLogForNextAprv.isEmpty()) {

			aprv = userIdNo;
		} else {
			aprv = approvalLogForNextAprv.get(0).getAprv();
		}

		// *****************for approved******************

		aprvLevel = AprvLogForSet.getAprvlevel();

		if (status == 1) {

			if (aprvLevel.equalsIgnoreCase("L1")) {
				String bodyhtml = stringBuffer.toString();
				body = "Purchase Request no. " + reqid + " has been approved. Kindly process the same.";
				body = bodyhtml.replaceAll("Prasun",
						"Purchase Request no. " + reqid + " has been approved. Kindly process the same.");
				try {
					to = userMasterRepo.findBySapId(creatorEmail).getEmail();
				} catch (Exception e) {
					js.put("code", "1001");
					js.put("error", " creator is Empty ");
					res = js.toString();
					return res;
				}
				sub = "Purchase Request no.  " + reqid + "  has been approved.";
				mailSent = emailSending.emailSendService(to, sub, body, "");
			} else if (aprvLevel.equalsIgnoreCase("L2")) {
				String bodyhtml = stringBuffer.toString();
				body = "Purchase Request no. " + reqid + " has been approved. Kindly process the same.";
				body = bodyhtml.replaceAll("Prasun",
						"Purchase Request no. " + reqid + " has been approved. Kindly process the same.");
				;
				sub = "Purchase Request no. " + reqid + " has been approved.";

				to = userMasterRepo.findBySapId(creatorEmail).getEmail();
				if (to == "" || to.isEmpty())
					System.err.println("user is not not found");

				ccEmail = userMasterRepo.findByAdrid((approvalMatrixForAprv.getLabhead())).getEmail();
				if (ccEmail == "" || ccEmail.isEmpty())
					System.err.println("user is not not found");

				System.out.println(ccEmail);
//					emailList.add(workflowConfig.getAprv1());

				mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
				System.out.println("mail sent status = " + mailSent);

			} else if (aprvLevel.equalsIgnoreCase("L3")) {
				sub = "Purchase Request no.  " + reqid + " has been approved";
				body = "Purchase Request no. " + reqid
						+ " has been approved for processing, which contains excess Qty.above the MRP, "
						+ "required for the justifications mentioned.";

				String bodyhtml = stringBuffer.toString();

				body = bodyhtml.replaceAll("Prasun",
						"Purchase Request no. " + reqid
								+ " has been approved for processing, which contains excess Qty.above the MRP, "
								+ "required for the justifications mentioned.");

				// creator
				to = userMasterRepo.findBySapId(creatorEmail).getEmail();
				if (to == "" || to.isEmpty())
					System.err.println("user is not not found");
				// buhead
				String buHead = userMasterRepo.findByAdrid((approvalMatrixForAprv.getBuhead())).getEmail();
				if (buHead == "" || buHead.isEmpty())
					System.err.println("user is not not found");
				// labhead
				String labHead = userMasterRepo.findByAdrid((approvalMatrixForAprv.getLabhead())).getEmail();
				if (labHead == "" || labHead.isEmpty())
					System.err.println("user is not not found");

				ccEmail = buHead + "," + labHead;

				mailSent = emailSending.emailSendService(to, sub, body, ccEmail);

				System.out.println(mailSent);

			} else {
				js.put("code", "1001");
				js.put("error", "INVALID approver");
				res = js.toString();
				return res;
			}

			// *****************for Reject******************
		} else if (status == 2) {

			if (aprvLevel.equalsIgnoreCase("L1")) {
				aprv = userIdNo;

				to = userMasterRepo.findBySapId(creatorEmail).getEmail();
				sub = "Purchase Request no. " + reqid + " has be Rejected";
				body = "Purchase Request no.  " + reqid
						+ "  Rejected. Kindly revert in case of any further justifications.";

				String bodyhtml = stringBuffer.toString();

				body = bodyhtml.replaceAll("Prasun", "Purchase Request no.  " + reqid
						+ "  Rejected. Kindly revert in case of any further justifications.");

				mailSent = emailSending.emailSendService(to, sub, body, "");

			} else if (aprvLevel.equalsIgnoreCase("L2")) {
				aprv = userIdNo;
				sub = "Purchase Request no. " + reqid + " has be Rejected";
				body = "Purchase Request no.  " + reqid
						+ "  has be Rejected. Kindly revert in case of any further justifications." + "" + touchItem;

				String bodyhtml = stringBuffer.toString();

				body = bodyhtml.replaceAll("Prasun", "Purchase Request no.  " + reqid
						+ "  has be Rejected. Kindly revert in case of any further justifications.");

				to = userMasterRepo.findBySapId(creatorEmail).getEmail().trim();
				if (to == "" || to.isEmpty())
					System.err.println("user is not not found");

				ccEmail = userMasterRepo.findByAdrid((approvalMatrixForAprv.getLabhead())).getEmail().trim();
				if (ccEmail == "" || ccEmail.isEmpty())
					System.err.println("user is not not found");

				System.out.println(ccEmail);
//					emailList.add(workflowConfig.getAprv1());

				mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
				System.out.println("mail sent status = " + mailSent);

			} else if (aprvLevel.equalsIgnoreCase("L3")) {
				aprv = userIdNo;

				sub = "Purchase Request no. " + reqid + " has be Rejected";
				body = "Purchase Request no.  " + reqid
						+ "  has be Rejected. Kindly revert in case of any further justifications.";
				String bodyhtml = stringBuffer.toString();

				body = bodyhtml.replaceAll("Prasun", "Purchase Request no.  " + reqid
						+ "  has be Rejected. Kindly revert in case of any further justifications.");
				;

				// creator
				to = userMasterRepo.findBySapId(creatorEmail).getEmail();
				if (to == "" || to.isEmpty())
					System.err.println("user is not not found");
				// buhead
				String buHead = userMasterRepo.findByAdrid((approvalMatrixForAprv.getBuhead())).getEmail().trim();
				if (buHead == "" || buHead.isEmpty())
					System.err.println("user is not not found");
				// labhead
				String labHead = userMasterRepo.findByAdrid((approvalMatrixForAprv.getLabhead())).getEmail().trim();
				if (labHead == "" || labHead.isEmpty())
					System.err.println("user is not not found");

				ccEmail = buHead + "," + labHead;

				mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
				System.out.println(mailSent);

			} else {
				js.put("code", "1001");
				js.put("error", "INVALID approver");
				res = js.toString();
				return res;

			}

			// *****************for approved and forword******************
		}
//		else if (status == 5) {
//
//			if (aprvLevel.equalsIgnoreCase("LABHEAD")) {
//				// buHead
//
//				String r = approvalMatrixForAprv.getBuhead();
//				// String p =
//				to = userMasterRepo.findByAdrid((approvalMatrixForAprv.getBuhead())).getEmail();
//				ccEmail = userMasterRepo.findBySapId(creatorEmail).getEmail();
//				sub = "Purchase Request no. " + reqid + " has been initiated";
//				body = "Purchase Request no. " + reqid
//						+ " has been initiated which contains excess Qty.above the MRP, required for the reasons mentioned. "
//						+ "Request your kind approval for the same.";
//
//				String bodyhtml = stringBuffer.toString();
//
//				body = bodyhtml.replaceAll("Prasun", "Purchase Request no. " + reqid
//						+ " has been initiated which contains excess Qty.above the MRP, required for the reasons mentioned. "
//						+ "Request your kind approval for the same.");
//				;
//
//				mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
//
//			} else if (aprvLevel.equalsIgnoreCase("BUHEAD")) {
//
//				sub = "Purchase Request no. " + reqid + " has been approved";
//				body = "Purchase Request no.  " + reqid
//						+ " has been approved which contains excess Qty.above the MRP, required for the "
//						+ "justifications mentioned. Kindly process the same.";
//
//				String bodyhtml = stringBuffer.toString();
//
//				body = bodyhtml.replaceAll("Prasun",
//						"Purchase Request no.  " + reqid
//								+ " has been approved which contains excess Qty.above the MRP, required for the "
//								+ "justifications mentioned. Kindly process the same.");
//				;
//
//				// pochHead
//				to = userMasterRepo.findByAdrid((approvalMatrixForAprv.getProchead())).getEmail();// to
//				if (to == "" || to.isEmpty())
//					System.err.println("user is not not found");
//
//				creatorEmail = userMasterRepo.findBySapId(creatorEmail).getEmail();// cc
//				if (creatorEmail == "" || creatorEmail.isEmpty())
//					System.err.println("user is not not found");
//
//				String labHead = userMasterRepo.findByAdrid((approvalMatrixForAprv.getLabhead())).getEmail(); // cc
//				if (labHead == "" || labHead.isEmpty())
//					System.err.println("user is not not found");
//
//				System.out.println(ccEmail);
////					emailList.add(workflowConfig.getAprv1());
//
//				ccEmail = creatorEmail + "," + labHead;
//				mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
//				System.out.println("mail sent status = " + mailSent);
//
//			} else if (aprvLevel.equalsIgnoreCase("POCHEAD") || aprvLevel.equalsIgnoreCase("prochead")) {
//
//				sub = "Purchase Request no.  " + reqid + " has been approved";
//				body = "Purchase Request no. " + reqid
//						+ " has been approved for processing, which contains excess Qty.above the MRP, required "
//						+ "for the justifications mentioned.";
//
//				String bodyhtml = stringBuffer.toString();
//
//				body = bodyhtml.replaceAll("Prasun", "Purchase Request no. " + reqid
//						+ " has been approved for processing, which contains excess Qty.above the MRP, required "
//						+ "for the justifications mentioned.");
//				;
//
//				// creator
//				to = userMasterRepo.findBySapId(creatorEmail).getEmail();
//				if (to == "" || to.isEmpty())
//					System.err.println("user is not not found");
//				// buhead
//				String buHead = userMasterRepo.findByAdrid((approvalMatrixForAprv.getBuhead())).getEmail();
//				if (buHead == "" || buHead.isEmpty())
//					System.err.println("user is not not found");
//				// labhead
//				String labHead = userMasterRepo.findByAdrid((approvalMatrixForAprv.getLabhead())).getEmail();
//				if (labHead == "" || labHead.isEmpty())
//					System.err.println("user is not not found");
//
//				ccEmail = buHead + "," + labHead;
//
//				mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
//				System.out.println(mailSent);
//
//			} else {
//				js.put("code", "1001");
//				js.put("error", "INVALID approver");
//				res = js.toString();
//				return res;
//			}
//
//			// *****************for clarification ******************
//		} 
		else if (status == 4) {

			sub = "Purchase Request no. " + reqid + " has clarification" + "";
			body = "Purchase Request no. " + reqid + " has clarification . Kindly revert in case of any query.";

			String bodyhtml = stringBuffer.toString();

			body = bodyhtml.replaceAll("Prasun",
					"Purchase Request no. " + reqid + " has clarification . " + "Kindly revert in case of any query.");
			;

			if (aprvLevel.equalsIgnoreCase("L1")) {
				aprv = "" + userIdNo;

				to = userMasterRepo.findBySapId(creatorEmail).getEmail();

				mailSent = emailSending.emailSendService(to, sub, body, "");

			} else if (aprvLevel.equalsIgnoreCase("L2")) {
				aprv = "" + userIdNo;

				to = userMasterRepo.findBySapId(creatorEmail).getEmail();
				if (to == "" || to.isEmpty())
					System.err.println("user is not not found");

				ccEmail = userMasterRepo.findByAdrid((approvalMatrixForAprv.getLabhead())).getEmail();
				if (ccEmail == "" || ccEmail.isEmpty())
					System.err.println("user is not not found");

				System.out.println(ccEmail);
//					emailList.add(workflowConfig.getAprv1());

				mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
				System.out.println("mail sent status = " + mailSent);

			} else if (aprvLevel.equalsIgnoreCase("L3")) {
				aprv = "" + userIdNo;

				// creator
				to = userMasterRepo.findBySapId(creatorEmail).getEmail();
				if (to == "" || to.isEmpty())
					System.err.println("user is not not found");
				// buhead
				String buHead = userMasterRepo.findByAdrid((approvalMatrixForAprv.getBuhead())).getEmail();
				if (buHead == "" || buHead.isEmpty())
					System.err.println("user is not not found");
				// labhead
				String labHead = userMasterRepo.findByAdrid((approvalMatrixForAprv.getLabhead())).getEmail();
				if (labHead == "" || labHead.isEmpty())
					System.err.println("user is not not found");

				ccEmail = buHead + "," + labHead;

				mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
				System.out.println(mailSent);

			} else {
				js.put("code", "1001");
				js.put("error", "INVALID approver");
				res = js.toString();
				return res;

			}

		}

		// ======================================================================================

		if (mailSent && (aprvLevel.equalsIgnoreCase("L3") || status == 2)) {
			for (PurchaseRequisition purchaseRequisitionStatusChange : PurchaseRequisitionListForPlantAndLgort) {
				if (purchaseRequisitionStatusChange == null) {
					continue;
				}
				purchaseRequisitionStatusChange.setStatus(status);
				purchaseRequisitionStatusChange.setCdBy(userIdNo);

//				Date date = new Date();
//				SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
//				String strDate = formatter.format(date);
				purchaseRequisitionStatusChange.setCdDat(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

				PurchaseRequisition purchaseRequisition1 = purchaseRequisitionRepository
						.save(purchaseRequisitionStatusChange);

				if (purchaseRequisition1.getBanfn() > 0) {
					System.out.println("status is updated  :: " + purchaseRequisition1.getBanfn());
				}

			}

		}

		if (mailSent) {
			List<ApprovalLog> approvalLogForMailStatus = sampleRepo.getApprovalLogByReqNoAndMailStatusAndAprv(reqid, 3,
					aprv);
			for (ApprovalLog approvalLogForMailStatusOne : approvalLogForMailStatus) {
				approvalLogForMailStatusOne.setMailstatus(1);
				approvalLogForMailStatusOne = approvalLogRepo.save(approvalLogForMailStatusOne);
				// approvalLog3 = sampleRepo.save(approvalLog);

				if (approvalLogForMailStatusOne != null)
					System.out.println("********mail status updatd Approve*******");
				else
					System.out.println("********mail status NOt updatd Approve*******");
			}

			js.put("code", "1000");
			try {
				js.put("sucess", ow.writeValueAsString(AprvLogForSet));
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			res = js.toString();
			return res;
		} else {
			System.out.println("mail is not sent   (clearified) ");

			js.put("code", "1001");
			js.put("ERROR", "Somthing is wrong");
			res = js.toString();
			return res;
		}

//		if (mailSent) {
//			System.out.println("mail sent status = " + mailSent);
//
//			System.out.println("Status Update And Mail Sent");
//			return true;
//		} else {
//
//			System.out.println("Something Is Wrong In Mail API Please Try Again");
//			return false;
//
//		}

	}

//  ASN AcknowledgementMail
	@Transactional
	@SuppressWarnings("unused")
	public String asnAcknowledgementMail(String userIdNo, Long reqid, String appid, int status, String remark)
			throws JSONException {
		//// =====================================================
		JSONObject js = new JSONObject();

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String res = null;
		String plant = null;
		Boolean mailSent = false;
		String apprverId = "";

		String creatorEmail = "";
		Boolean count = false;
		ASNHeader asnHeader = null;
		String aprvLevel = "";
		String aprv = "";
		int countSize = 0;
		String touchItem = "";
		ApprovalLog touchApprovalLog = null;

		String to = null, body = null, sub = null, ccEmail = null;
		EmailSending emailSending = new EmailSending();

		ASNHeader asnHeaderForPlant = asnRepository.findOne(reqid);
		if (asnHeaderForPlant == null) {
			js.put("code", "1001");
			js.put("error", "asnHeader is not found ");
			res = js.toString();
			return res;
		}
		asnHeader = asnHeaderForPlant;

		List<ASNItem> asnItemsList = asnHeaderForPlant.getAsnitem();
		if (asnItemsList.isEmpty()) {
			System.err.println("1001  ::  ASNItem are not found");
			js.put("code", "1001");
			js.put("error", "asnItemsList Status ");
			res = js.toString();
			return res;

		} else {
			plant = asnItemsList.get(0).getWerks();
		}
		ApprovalMatrix approvalMatrixForAprv = approvalMatrixRepository.findByPlant(plant);
		if (approvalMatrixForAprv == null) {
			System.err.println("1001  ::  ApprovalMatrix is not found");
			js.put("code", "1001");
			js.put("error", " ApprovalMatrix is not found");
			res = js.toString();
			return res;
		}

		if (approvalMatrixForAprv.getLabhead().equalsIgnoreCase("" + userIdNo))
			aprvLevel = "labhead";
		else if (approvalMatrixForAprv.getBuhead().equalsIgnoreCase("" + userIdNo))
			aprvLevel = "buhead";
		else if (approvalMatrixForAprv.getProchead().equalsIgnoreCase("" + userIdNo))
			aprvLevel = "prochead";
		else if (approvalMatrixForAprv.getMatspoc().equalsIgnoreCase("" + userIdNo))
			aprvLevel = "matspoc";
		else if (approvalMatrixForAprv.getRfpcreator().equalsIgnoreCase("" + userIdNo))
			aprvLevel = "rfpcreator";
		else if (approvalMatrixForAprv.getMdmteam().equalsIgnoreCase("" + userIdNo))
			aprvLevel = "mdmteam";

		count = true;

		// =========================================================
		Status statusObj = statusRepo.findOne(3);
		if (statusObj == null) {

			js.put("code", "1001");
			js.put("error", "Invalid Status ");
			res = js.toString();
			return res;

		}
		WorkflowConfig appid1 = sampleRepo.getWorkflowConfigByAppid(appid)/* .getId() */;

		if (appid1 == null) {
			js.put("code", "1001");
			js.put("error", "WORKFLOW For This " + appid + " Is Not Maintain ");
			res = js.toString();
			return res;// prasunmail

		}

		List<ApprovalLog> approvalLogListForApprverId = sampleRepo.getApprovalLogByAppIdAndReqNO(appid1, statusObj,
				reqid);
		if (approvalLogListForApprverId.isEmpty()) {
			js.put("code", "1001");
			js.put("error", "wrong user try to access or try to again apprave ");
			res = js.toString();
			return res;// prasunmail
		}
		apprverId = approvalLogListForApprverId.get(0).getAprv();

		count = true;

		Status statusForAprv = statusRepo.findOne(3);
		List<ApprovalLog> approvalLogList = sampleRepo.getApprovalLogByAprvAndAppidAndReqnoAndStatusMailStatus(appid1,
				apprverId, reqid, statusForAprv, 1);
		if (approvalLogList.isEmpty()) {
			js.put("code", "1001");
			js.put("error", "Invalid user trying to asscess  ");
			res = js.toString();
			return res;

		}

		ApprovalLog approvalLog = approvalLogList.get(0);
		Status statusForUpdate = statusRepo.findOne(status);
		if (statusForUpdate == null) {
			js.put("code", "1001");
			js.put("error", "invalid Status You Are Passing ");
			res = js.toString();
			return res;
		}
//		Status statusForIncomplit = statusRepo.findOne(3);
//		if (statusForUpdate == null) {
//			js.put("code", "1001");
//			js.put("error", "invalid Status You Are Passing ");
//			res = js.toString();
//			return res;
//		}

		for (ApprovalLog approvalLogOne : approvalLogList) {
			touchApprovalLog = approvalLogOne;
//			approvalLogOne = approvalLog0;
			creatorEmail = approvalLogOne.getInitiator();// prasun1

			approvalLogOne.setRemark(remark);
			approvalLogOne.setApprovedBy(userMasterRepo.findByAdrid(userIdNo).getPnam());
			approvalLogOne.setApprovedId(userIdNo);
			approvalLogOne.setCddat(Calendar.getInstance().getTime());
			approvalLogOne.setChangedBy("" + userIdNo);
			approvalLogOne.setStatus(statusForUpdate);
			approvalLogOne.setApprovedTime(Calendar.getInstance().getTime());

			asnHeader.setStatus(status);
			Date date = new Date();

			String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
			asnHeader.setChangeddate(format);
			approvalLogOne.setApprovedDate(format);

			asnHeader = asnRepository.save(asnHeader);

			approvalLogOne = sampleRepo.save(approvalLogOne);
		}
//		List<ApprovalLog> aprvList = sampleRepo.getApprovalLogByReqNoAndMailStatus(reqid, 3);
//		if (aprvList.isEmpty()) {
//
//			js.put("code", "1001");
//			js.put("error", "ApprovalLog for aprv is not found ");
//			res = js.toString();
//			return res;// prasunmail
//
//		}
//		if (aprvList.isEmpty()) {
//
//			System.out.println("1001  : Next aprv is not found");
//
//		}
//		aprv = (aprvList.get(0).getAprv());
//
//		count = true;

		// ==============================

		if (status == 1) {// approve

			sub = "approval  of the ASN " + reqid + " dated " + Calendar.getInstance().getTime() + " . ";
			body = "Request your kind approval for accepting the short expiry items of the ASN " + reqid + " dated "
					+ Calendar.getInstance().getTime() + " ." + "" + "";

			if (aprvLevel.equalsIgnoreCase("matspoc")) {

				try {
					to = userMasterRepo.findByAdrid(approvalLog.getInitiator()).getEmail();
				} catch (Exception e) {
					js.put("code", "1001");
					js.put("error", "something is wrong in vendor fatching  " + e.getMessage());
					res = js.toString();
					return res;

				}
				if (to.isEmpty() || to == null || to == "") {
					js.put("code", "1001");
					js.put("error", "vendor Id is Empty");
					res = js.toString();
					return res;
				}

				mailSent = emailSending.emailSendService(to, sub, body, "");

//				ccEmail = userMasterRepo
//						.findByAdrid((poRepository.findByEbeln(asnHeaderForPlant.getEbeln()).getLifnr())).getEmail();
//				System.err.println(" to is not getLifnr");
//				if (to == "" || to.equalsIgnoreCase(""))
//					System.err.println("1001  :: To Is Empty of Labhead ");
//				else {
//					mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
//				}

			} else if (aprvLevel.equalsIgnoreCase("labhead")) {

				try {
					to = userMasterRepo.findByAdrid(approvalLog.getInitiator()).getEmail();
				} catch (Exception e) {
					js.put("code", "1001");
					js.put("error", "something is wrong in vendor fatching  " + e.getMessage());
					res = js.toString();
					return res;

				}
				if (to.isEmpty() || to == null || to == "") {
					js.put("code", "1001");
					js.put("error", "vendor Id is Empty");
					res = js.toString();
					return res;
				}

				mailSent = emailSending.emailSendService(to, sub, body, "");
			} else {

				System.err.println("error INVALID approver");
				js.put("code", "1001");
				js.put("error", "invalid Appraval You Are Passing ");
				res = js.toString();
				return res;
			}

		} else if (status == 2) {// reject

			sub = "  ASN no. " + reqid + " against the Purchase Orderd no. " + asnHeader.getEbeln() + " dated "
					+ Calendar.getInstance().getTime() + " has been rejected.\r\n" + "";
			body = "ASN no. " + reqid + " against the Purchase Orderd no. " + asnHeader.getEbeln() + " dated "
					+ Calendar.getInstance().getTime() + " containing short"
					+ " expiry date items has been rejected. Kindly take the corrective actions for the same.\r\n" + "";

			if (aprvLevel.equalsIgnoreCase("matspoc")) {

				try {
					to = userMasterRepo.findByAdrid(approvalLog.getInitiator()).getEmail();
				} catch (Exception e) {
					js.put("code", "1001");
					js.put("error", "something is wrong in vendor fatching  " + e.getMessage());
					res = js.toString();
					return res;

				}
				if (to.isEmpty() || to == null || to == "") {
					js.put("code", "1001");
					js.put("error", "vendor Id is Empty");
					res = js.toString();
					return res;
				}

				mailSent = emailSending.emailSendService(to, sub, body, "");

			} else if (aprvLevel.equalsIgnoreCase("labhead")) {

				try {
					to = userMasterRepo.findByAdrid(approvalLog.getInitiator()).getEmail();
				} catch (Exception e) {
					js.put("code", "1001");
					js.put("error", "something is wrong in vendor fatching  " + e.getMessage());
					res = js.toString();
					return res;

				}
				if (to.isEmpty() || to == null || to == "") {
					js.put("code", "1001");
					js.put("error", "vendor Id is Empty");
					res = js.toString();
					return res;
				}

				mailSent = emailSending.emailSendService(to, sub, body, "");

			} else {
				System.err.println("error INVALID approver");
				js.put("code", "1001");
				js.put("error", "invalid approver You Are Passing ");
				res = js.toString();
				return res;
			}

		}

		if (mailSent) {
			System.out.println("mail sent status = " + mailSent);

			System.out.println("Status Update And Mail Sent");
			js.put("code", "1000");
			js.put("success", "Status Update And Mail Sent ");
			res = js.toString();
			return res;

		} else {

			System.err.println("Something Is Wrong In Mail API Please Try Again");
			js.put("code", "1001");
			js.put("error", "Something Is Wrong In Mail API Please Try Again ");
			res = js.toString();
			return res;

		}

	}

//  po AcknowledgementMail
	@Transactional
	public String poAcknowledgementMail(String userIdNo, Long reqid, String appid, int status, String remark)
			throws JSONException {

		JSONObject js = new JSONObject();

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String res = null;
		String plant = null;
		String link = "";
		String apprverId = "";
		PurchaseOrder purchaseOrderForStatus = null;
		String creatorEmail = "";
		Boolean mailSent;
		String to = null, body = null, sub = null, ccEmail = null;
		EmailSending emailSending = new EmailSending();
		PurchaseOrder purchaseOrderForPlant = poRepository.findByEbeln(reqid);
		if (purchaseOrderForPlant == null) {
			js.put("code", "1001");
			js.put("error", "purchaseOrder is not found ");
			res = js.toString();
			return res;
		}
		List<POItemDetails> poItemDetailsForPlant = purchaseOrderForPlant.getPoitem();
		if (poItemDetailsForPlant.isEmpty()) {
			js.put("code", "1001");
			js.put("error", "PurchaseRequisition list is empty");
			res = js.toString();
			return res;
		}
		purchaseOrderForStatus = purchaseOrderForPlant;
		plant = poItemDetailsForPlant.get(0).getWerks();

		Status statusObj = statusRepo.findOne(3);
		if (statusObj == null) {

			js.put("code", "1001");
			js.put("error", "Invalid Status ");
			res = js.toString();
			return res;

		}
		WorkflowConfig appid1 = sampleRepo.getWorkflowConfigByAppid(appid)/* .getId() */;

		if (appid1 == null) {
			js.put("code", "1001");
			js.put("error", "WORKFLOW For This " + appid + " Is Not Maintain ");
			res = js.toString();
			return res;// prasunmail

		}

		List<ApprovalLog> approvalLogListForApprverId = sampleRepo.getApprovalLogByAppIdAndReqNO(appid1, statusObj,
				reqid);
		if (approvalLogListForApprverId.isEmpty()) {
			js.put("code", "1001");
			js.put("error", "approvalLogList is not found");
			res = js.toString();
			return res;// prasunmail
		}
		apprverId = approvalLogListForApprverId.get(0).getAprv();

//			Status statusForAprv = statusRepo.findOne(3);
		List<ApprovalLog> approvalLogList = sampleRepo.getApprovalLogByAprvAndAppidAndReqnoAndStatusMailStatus(appid1,
				apprverId, reqid, statusObj, 1);
		if (approvalLogList.isEmpty()) {
			js.put("code", "1001");
			js.put("error", "Invalid user trying to asscess  ");
			res = js.toString();
			return res;

		}
		PurchaseOrder purchaseOrder = poRepository.findByEbeln(reqid);
		if (purchaseOrder == null) {
			js.put("code", "1001");
			js.put("error", "PurchaseOrder is not found  for " + reqid);
			res = js.toString();
			return res;
		}

		PurchaseOrder purchaseOrder2 = purchaseOrder;

		for (ApprovalLog approvalLogOne : approvalLogList) {

//				approvalLogOne = approvalLog0;
			creatorEmail = approvalLogOne.getInitiator();// prasun1

			approvalLogOne.setRemark(remark);
			approvalLogOne.setApprovedBy(userMasterRepo.findByAdrid(userIdNo).getPnam());
			approvalLogOne.setApprovedId(userIdNo);
			approvalLogOne.setCddat(Calendar.getInstance().getTime());
			approvalLogOne.setChangedBy("" + userIdNo);
			approvalLogOne.setStatus(statusRepo.findOne(status));
			Date date = new Date();
			String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
			approvalLogOne.setApprovedDate(format);
			approvalLogOne.setApprovedTime(Calendar.getInstance().getTime());

			approvalLogOne = sampleRepo.save(approvalLogOne);
			if (approvalLogOne == null) {
				js.put("code", "1001");
				js.put("error", "Data is not update in  approvalLog table for " + reqid);
				res = js.toString();
				return res;
			}

		}
		try {

			purchaseOrder.setStatus(String.valueOf(status));

			Blob blob = purchaseOrder2.getPoDocument().getFileData();

			Blob b = new SerialBlob(blob);
			purchaseOrder.getPoDocument().setFileData(b);

			purchaseOrder = poRepository.save(purchaseOrder);

			if (purchaseOrder == null) {
				js.put("code", "1001");
				js.put("error", "Data is not update in  purchaseOrder table for " + reqid);
				res = js.toString();
				return res;
			}

		} catch (Exception e) {
			e.getStackTrace();
		}

		ApprovalMatrix approvalMatrixForAprv = approvalMatrixRepository.findByPlant(plant);
		if (approvalMatrixForAprv == null) {
			js.put("code", "1001");
			js.put("error", "According To This Plant " + plant + "  ApprovalMatrix Is Not Maintain");
			res = js.toString();
			return res;
		}
		to = userMasterRepo.findByAdrid((approvalMatrixForAprv.getProchead())).getEmail();
//	      ccEmail = userMasterRepo.findByAdrid((approvalMatrixForAprv.getLabhead())).getEmail();
		ccEmail = "";

		sub = "Purchase Orderd no. " + reqid + " dated " + Calendar.getInstance().getTime()
				+ " has been acknowledged by the vendor.";
		body = "Purchase Orderd no. " + reqid + " dated " + Calendar.getInstance().getTime()
				+ " has been acknowledged by the vendor.";
		link = " http://192.168.0.56:8080/ZSRL_VMS_PO_ACK/";
		mailSent = emailSending.emailSendService(to, sub, body, "");

		if (mailSent) {
			System.out.println("mail sent status = " + mailSent);

			js.put("code", "1000");
			js.put("Sucess", "Status Update And Mail Sent");
			res = js.toString();
			return res;

		} else {

			js.put("code", "1001");
			js.put("error", "Something Is Wrong In Mail API");
			res = js.toString();
			return res;

		}

	}

	// *********VM ACK *************
	public String vmAcknowledgementMail(String userIdNo, Long reqid, String appid, int status, String remark)
			throws JSONException {

		JSONObject js = new JSONObject();

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String res = null;
		String plant = null;
		Boolean mailSent = false;
		String apprverId = "";
		String creatorEmail = "";
		Boolean count = false;
		String aprvLevel = "";
		String aprv = "";
		int countSize = 0;
		String touchItem = "";
		ApprovalLog touchApprovalLog = null;

		String to = null, body = null, sub = null, ccEmail = null;
		EmailSending emailSending = new EmailSending();

		plant = "";

		Vendor vendor = vendorRepository.findByVid(reqid);

		List<ProposalSummary> proposalSummary = proposalSummaryRepository.findByLifnr("" + vendor.getCreatedBy());
		if (proposalSummary.isEmpty()) {
			System.err.println("1001  ::  ProposalSummary List is not found");
			js.put("code", "1001");
			js.put("error", " ProposalSummary List is not found");
			res = js.toString();
			return res;
		}

		RFPHeader rfpHeader = rfpHeaderRepository.findOne(proposalSummary.get(0).getRfpno());
		if (rfpHeader == null) {
			js.put("code", "1001");
			js.put("error", " RFPHeader  is not found");
			res = js.toString();
			return res;
		}

		List<RFPItem> rfpItems = rfpHeader.getItem();
		if (rfpItems.isEmpty()) {
			js.put("code", "1001");
			js.put("error", " RFPItem List is not found");
			res = js.toString();
			return res;
		}

		plant = rfpItems.get(0).getWerks();

		ApprovalMatrix approvalMatrixForAprv = approvalMatrixRepository.findByPlant(plant);
		if (approvalMatrixForAprv == null) {
			System.err.println("1001  ::  ApprovalMatrix is not found");
			js.put("code", "1001");
			js.put("error", " ApprovalMatrix is not found");
			res = js.toString();
			return res;
		}
//
//		if (approvalMatrixForAprv.getLabhead().equalsIgnoreCase("" + userIdNo))
//			aprvLevel = "labhead";
//		else if (approvalMatrixForAprv.getBuhead().equalsIgnoreCase("" + userIdNo))
//			aprvLevel = "buhead";
//		else if (approvalMatrixForAprv.getProchead().equalsIgnoreCase("" + userIdNo))
//			aprvLevel = "prochead";
//		else if (approvalMatrixForAprv.getMatspoc().equalsIgnoreCase("" + userIdNo))
//			aprvLevel = "matspoc";
//		else if (approvalMatrixForAprv.getRfpcreator().equalsIgnoreCase("" + userIdNo))
//			aprvLevel = "rfpcreator";
//		else if (approvalMatrixForAprv.getMdmteam().equalsIgnoreCase("" + userIdNo))
//			aprvLevel = "mdmteam";
//		else if (approvalMatrixForAprv.getTechhead().equalsIgnoreCase("" + userIdNo))
//			aprvLevel = "techhead";
//		else {
//			js.put("code", "1001");
//			js.put("error", "Invalid Aprv try to access ");
//			res = js.toString();
//			return res;
//
//		}
		count = true;

		// =========================================================
		Status statusObj = statusRepo.findOne(3);
		if (statusObj == null) {

			js.put("code", "1001");
			js.put("error", "Invalid Status ");
			res = js.toString();
			return res;

		}
		WorkflowConfig appid1 = sampleRepo.getWorkflowConfigByAppid(appid)/* .getId() */;

		if (appid1 == null) {
			js.put("code", "1001");
			js.put("error", "WORKFLOW For This " + appid + " Is Not Maintain ");
			res = js.toString();
			return res;// prasunmail

		}

		List<ApprovalLog> approvalLogListForApprverId = sampleRepo.getApprovalLogByAppIdAndReqNO(appid1, statusObj,
				reqid);
		if (approvalLogListForApprverId.isEmpty()) {
			js.put("code", "1001");
			js.put("error", "approvalLogList is not found");
			res = js.toString();
			return res;// prasunmail
		}
		apprverId = approvalLogListForApprverId.get(0).getAprv();

		count = true;

		Status statusForAprv = statusRepo.findOne(3);
		List<ApprovalLog> approvalLogList = sampleRepo.getApprovalLogByAprvAndAppidAndReqnoAndStatusMailStatus(appid1,
				apprverId, reqid, statusForAprv, 1);
		if (approvalLogList.isEmpty()) {
			js.put("code", "1001");
			js.put("error", "Invalid user trying to asscess  ");
			res = js.toString();
			return res;

		}

		for (ApprovalLog approvalLogOne : approvalLogList) {
			touchApprovalLog = approvalLogOne;
//			approvalLogOne = approvalLog0;
			creatorEmail = approvalLogOne.getInitiator();// prasun1

			approvalLogOne.setRemark(remark);
			approvalLogOne.setApprovedBy(userMasterRepo.findByAdrid(userIdNo).getPnam());
			approvalLogOne.setApprovedId(userIdNo);
			approvalLogOne.setCddat(Calendar.getInstance().getTime());
			approvalLogOne.setChangedBy("" + userIdNo);

			approvalLogOne.setStatus(statusRepo.findOne(status));

			Date date = new Date();
			String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
			approvalLogOne.setApprovedDate(format);
			approvalLogOne.setApprovedTime(Calendar.getInstance().getTime());

			approvalLogOne = sampleRepo.save(approvalLogOne);

		}

		List<ApprovalLog> aprvList = sampleRepo.getApprovalLogByReqNoAndMailStatus(reqid, 3);
		if (aprvList.isEmpty()) {

			js.put("code", "1001");
			js.put("error", "ApprovalLog for aprv is not found ");
			res = js.toString();
			return res;// prasunmail

		}
		if (aprvList.isEmpty()) {

			System.out.println("1001  : Next aprv is not found");

		}
		aprv = (aprvList.get(0).getAprv());

		count = true;

		if (status == 1) {
//
//			if (aprvLevel.equalsIgnoreCase("RFPCREATOR")) {
//				aprv = "" + userIdNo;

			to = userMasterRepo.findByAdrid(aprv).getEmail();
			// ccEmail= vendorRepository.findOne(reqid).getEmail();
			// to =vendorRepository.findOne(reqid).getEmail();
			if (to == "" || to.isEmpty())
				System.err.println("user is not not found");
			sub = "Creation of New Vendor Code";
			body = "Request your kind approval for the creation of New Vendor Code for " + "preparing the order of "
					+ rfpItems.get(0).getMatnr() + " from vendor " + reqid + " , for " + "the " + plant + " Lab.";

			mailSent = emailSending.emailSendService(to, sub, body, "");

//			}
			if (aprvLevel.equalsIgnoreCase("POCHEAD")) {

				aprv = "" + userIdNo;

				sub = "Creation of New Vendor Code";
				body = "Request your kind approval for the creation of New Vendor Code for preparing"
						+ " the order of  " + rfpItems.get(0).getMatnr() + " from vendor  " + reqid + ""
						+ vendorRepository.findByVid(reqid).getOrgName() + " , " + "for the " + plant + " Lab.";

				to = userMasterRepo.findByAdrid((approvalMatrixForAprv.getMdmteam())).getEmail();
				if (to == "" || to.isEmpty())
					System.err.println("user is not not found");
				// rfp creator
				ccEmail = userMasterRepo.findByAdrid((approvalMatrixForAprv.getRfpcreator())).getEmail();

				if (ccEmail == "" || ccEmail.isEmpty())
					System.err.println("user is not not found");

				System.out.println(ccEmail);
//				emailList.add(workflowConfig.getAprv1());

				mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
				System.out.println("mail sent status = " + mailSent);

			} else if (aprvLevel.equalsIgnoreCase("MDMTEAM")) {
				aprv = "" + userIdNo;

				sub = "Creation of New Vendor Code has been approved";//
				body = "Request for the creation of New Vendor Code for the  vendor " + reqid + ""
						+ "has been approved. Kindly create the vendor code for the same.";//

				// creator
				to = vendorRepository.findOne(reqid).getEmail();
				if (to == "" || to.isEmpty())
					System.err.println("user is not not found");
				// RFP Creator
				String rfpcreator = userMasterRepo.findByAdrid((approvalMatrixForAprv.getRfpcreator())).getEmail();
				if (rfpcreator == "" || rfpcreator.isEmpty())
					System.err.println("user is not not found");
				// POCHEAD
				String pochead = userMasterRepo.findByAdrid((approvalMatrixForAprv.getProchead())).getEmail();
				if (pochead == "" || pochead.isEmpty())
					System.err.println("user is not not found");

				ccEmail = rfpcreator + "," + pochead;

				mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
				System.out.println(mailSent);

			} /*
				 * else { js.put("code", "1001"); js.put("error", "INVALID approver"); res =
				 * js.toString(); return res;
				 * 
				 * }
				 */

			// *****************for Reject******************

		} else if (status == 2) {

//			if (aprvLevel.equalsIgnoreCase("RFPCREATOR")) {
			aprv = "" + userIdNo;

			to = vendorRepository.findOne(reqid).getEmail();
			sub = "Details submitted for Vendor " + reqid;

			body = "The required details have been submitted by the " + "vendor " + reqid
					+ " for the temporary registration purpose. Kindly do the needful.";//
			mailSent = emailSending.emailSendService(to, sub, body, "");

//			} 
			if (aprvLevel.equalsIgnoreCase("POCHEAD")) {
				aprv = "" + userIdNo;

				sub = "Creation of New Vendor Code";//
				body = "Your request for the creation of New Vendor Code for preparing the order" + " of "
						+ rfpItems.get(0).getMatnr() + " from vendor " + reqid + ", for" + " the " + plant
						+ " Lab, has been rejected by " + userIdNo + "";//
				to = vendorRepository.findOne(reqid).getEmail();
				if (to == "" || to.isEmpty())
					System.err.println("user is not not found");
				// rfp creator
				ccEmail = userMasterRepo.findByAdrid((approvalMatrixForAprv.getRfpcreator())).getEmail();
				if (ccEmail == "" || ccEmail.isEmpty())
					System.err.println("user is not not found");

				System.out.println(ccEmail);
//				emailList.add(workflowConfig.getAprv1());

				mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
				System.out.println("mail sent status = " + mailSent);

//			} else if (aprvLevel.equalsIgnoreCase("MDM TEAM")) {
//				aprv = "MDMTEAM";
//
//				subject = "Purchase Request no. " + reqid + " has be Rejected";//change
//				body = "Purchase Request no.  " + reqid
//						+ "  has be Rejected. Kindly revert in case of any further justifications.";//change
//				// creator
//				to = vendorRepository.findOne(reqid).getEmail();
//				if (to == "" || to.isEmpty())
//					System.err.println("user is not not found");
//				//rfp cteator
//				String rfpcreator = masterRepo.findByAdrid((approvalMatrixForAprv.getRfpcreator())).getEmail();
//				if (rfpcreator == "" || rfpcreator.isEmpty())
//					System.err.println("user is not not found");
//				// POCHEAD
//				String pochead = masterRepo.findByAdrid((approvalMatrixForAprv.getProchead())).getEmail();
//				if (pochead == "" || pochead.isEmpty())
//					System.err.println("user is not not found");
//
//				ccEmail = rfpcreator + "," + pochead;
//
//			
//				mailSent = emailSending.emailSendService(to, subject, body, ccEmail);
//				System.out.println(mailSent);

			} /*
				 * else { js.put("code", "1001"); js.put("error", "INVALID approver"); res =
				 * js.toString(); return res;
				 * 
				 * }
				 */

			// *****************for approved and forword******************
		} else if (status == 5) {

			if (aprvLevel.equalsIgnoreCase("RFPCREATOR")) {

				String r = approvalMatrixForAprv.getBuhead();
				// String p =
				to = userMasterRepo.findByAdrid((approvalMatrixForAprv.getProchead())).getEmail();
				// ccEmail = vendorRepository.findOne(reqid).getEmail();

				sub = "Creation of New Vendor Code";
				body = "Request your kind approval for the creation of New Vendor Code for " + "preparing the order of "
						+ rfpItems.get(0).getMatnr() + " from vendor " + reqid + ", for " + "the " + plant + " Lab.";

				mailSent = emailSending.emailSendService(to, sub, body, "");

			} else if (aprvLevel.equalsIgnoreCase("POCHEAD")) {

				sub = "Creation of New Vendor Code";
				body = "Request your kind approval for the creation of New Vendor Code for preparing" + " the order of "
						+ rfpItems.get(0).getMatnr() + " from vendor" + reqid + "ÃƒÂ¢Ã¢â€šÂ¬Ã‚Â, " + "for the " + plant
						+ " Lab.";
				// pochHead
				to = userMasterRepo.findByAdrid((approvalMatrixForAprv.getMdmteam())).getEmail();// to
				if (to == "" || to.isEmpty())
					System.err.println("user is not not found");

				creatorEmail = userMasterRepo.findByAdrid((approvalMatrixForAprv.getRfpcreator())).getEmail();

				if (creatorEmail == "" || creatorEmail.isEmpty())
					System.err.println("user is not not found");

				String mdmteam = userMasterRepo.findByAdrid((approvalMatrixForAprv.getMdmteam())).getEmail(); //
				if (mdmteam == "" || mdmteam.isEmpty())
					System.err.println("user is not not found");

				System.out.println(ccEmail);
//				emailList.add(workflowConfig.getAprv1());

				ccEmail = creatorEmail + "," + mdmteam;// change creator
				mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
				System.out.println("mail sent status = " + mailSent);

			} else if (aprvLevel.equalsIgnoreCase("MDMTEAM")) {

				sub = "Creation of New Vendor Code has been approved";//
				body = "Request for the creation of New Vendor Code for the  vendor ÃƒÂ¢Ã¢â€šÂ¬Ã…â€œ " + reqid
						+ " ÃƒÂ¢Ã¢â€šÂ¬Ã‚Â, " + "has been approved. Kindly create the vendor code for the same.";//

				// creator
				to = vendorRepository.findOne(reqid).getEmail();

				if (to == "" || to.isEmpty())
					System.err.println("user is not not found");

				String rfpcreator = userMasterRepo.findByAdrid((approvalMatrixForAprv.getRfpcreator())).getEmail();
				if (rfpcreator == "" || rfpcreator.isEmpty())
					System.err.println("user is not not found");

				String prochead = userMasterRepo.findByAdrid((approvalMatrixForAprv.getProchead())).getEmail();
				if (prochead == "" || prochead.isEmpty())
					System.err.println("user is not not found");

				ccEmail = prochead + "," + rfpcreator;

				mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
				System.out.println(mailSent);

			} else {
				js.put("code", "1001");
				js.put("error", "INVALID approver");
				res = js.toString();
				return res;

			}

		}

		// ======================================================================================

		if (mailSent) {
			List<ApprovalLog> approvalLogForMailStatus = sampleRepo.getApprovalLogByReqNoAndMailStatusAndAprv(reqid, 3,
					aprv);
			for (ApprovalLog approvalLogForMailStatusOne : approvalLogForMailStatus) {
				approvalLogForMailStatusOne.setMailstatus(1);
				approvalLogForMailStatusOne = approvalLogRepo.save(approvalLogForMailStatusOne);
				// approvalLog3 = sampleRepo.save(approvalLog);

				if (approvalLogForMailStatusOne != null)
					System.out.println("********mail status updatd Approve*******");
				else
					System.out.println("********mail status NOt updatd Approve*******");
			}

			js.put("code", "1000");
			try {
				js.put("sucess", ow.writeValueAsString(touchApprovalLog));
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			res = js.toString();
			return res;
		} else {
			System.out.println("mail is not sent   (clearified) ");

			js.put("code", "1001");
			js.put("ERROR", "Somthing is wrong");
			res = js.toString();
			return res;
		}

//		if (mailSent) {
//			System.out.println("mail sent status = " + mailSent);
//
//			System.out.println("Status Update And Mail Sent");
//			return true;
//		} else {
//
//			System.out.println("Something Is Wrong In Mail API Please Try Again");
//			return false;
//
//		}

	}

	// TC RATING
	@Transactional
	public String rfpvsAcknowledgementMail(String userIdNo, Long reqid, String appid, int status, String remark)
			throws JSONException {
		//// =====================================================

		JSONObject js = new JSONObject();

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String res = null;
		String plant = null;
		Boolean mailSent = false;
		String apprverId = "";
		String creatorEmail = "";
		Boolean count = false;
		ASNHeader asnHeader = null;
		String aprvLevel = "";
		String aprv = "";
		int countSize = 0;
		String touchItem = "";
		ApprovalLog touchApprovalLog = null;

		String to = null, body = null, sub = null, ccEmail = null;
		EmailSending emailSending = new EmailSending();

		RFPHeader rfpHeaderForPlant = rfpHeaderRepository.findOne(reqid);
		if (rfpHeaderForPlant == null) {
			js.put("code", "1001");
			js.put("error", "RFPHeader is not found ");
			res = js.toString();
			return res;
		}
		// asnHeader = asnHeaderForPlant;

		List<RFPItem> rfpItemsList = rfpHeaderForPlant.getItem();
		if (rfpItemsList.isEmpty()) {
			System.err.println("1001  ::  ASNItem are not found");
			js.put("code", "1001");
			js.put("error", "rfpItemsList Status ");
			res = js.toString();
			return res;

		} else {
			plant = rfpItemsList.get(0).getWerks();
		}

		ApprovalMatrix approvalMatrixForAprv = approvalMatrixRepository.findByPlant(plant);
		if (approvalMatrixForAprv == null) {
			System.err.println("1001  ::  ApprovalMatrix is not found");
			js.put("code", "1001");
			js.put("error", " ApprovalMatrix is not found");
			res = js.toString();
			return res;
		}

		if (approvalMatrixForAprv.getProchead().equalsIgnoreCase("" + userIdNo))
			aprvLevel = "prochead";
		else if (approvalMatrixForAprv.getTechhead().equalsIgnoreCase("" + userIdNo))
			aprvLevel = "techhead";
		else {
			js.put("code", "1001");
			js.put("error", " invalid Aprv");
			res = js.toString();
			return res;
		}

		count = true;

		// =========================================================
		Status statusObj = statusRepo.findOne(3);
		if (statusObj == null) {

			js.put("code", "1001");
			js.put("error", "Invalid Status ");
			res = js.toString();
			return res;

		}
		WorkflowConfig appid1 = sampleRepo.getWorkflowConfigByAppid(appid)/* .getId() */;

		if (appid1 == null) {
			js.put("code", "1001");
			js.put("error", "WORKFLOW For This " + appid + " Is Not Maintain ");
			res = js.toString();
			return res;// prasunmail

		}

		List<ApprovalLog> approvalLogListForApprverId = sampleRepo.getApprovalLogByAppIdAndReqNO(appid1, statusObj,
				reqid);
		if (approvalLogListForApprverId.isEmpty()) {
			js.put("code", "1001");
			js.put("error", "approvalLogList is not found");
			res = js.toString();
			return res;// prasunmail
		}
		apprverId = approvalLogListForApprverId.get(0).getAprv();

		count = true;

		Status statusForAprv = statusRepo.findOne(3);
		List<ApprovalLog> approvalLogList = sampleRepo.getApprovalLogByAprvAndAppidAndReqnoAndStatusMailStatus(appid1,
				userIdNo, reqid, statusForAprv, 1);
		if (approvalLogList.isEmpty()) {
			js.put("code", "1001");
			js.put("error", "Invalid user trying to asscess  ");
			res = js.toString();
			return res;

		}
		Status statusForUpdate = statusRepo.findOne(status);
		if (statusForUpdate == null) {
			js.put("code", "1001");
			js.put("error", "invalid Status You Are Passing ");
			res = js.toString();
			return res;
		}
//		Status statusForIncomplit = statusRepo.findOne(3);
//		if (statusForUpdate == null) {
//			js.put("code", "1001");
//			js.put("error", "invalid Status You Are Passing ");
//			res = js.toString();
//			return res;
//		}

		for (ApprovalLog approvalLogOne : approvalLogList) {
			touchApprovalLog = approvalLogOne;
//			approvalLogOne = approvalLog0;
			creatorEmail = approvalLogOne.getInitiator();// prasun1

			approvalLogOne.setRemark(remark);
			approvalLogOne.setApprovedBy(userMasterRepo.findByAdrid(userIdNo).getPnam());
			approvalLogOne.setApprovedId(userIdNo);
			approvalLogOne.setCddat(Calendar.getInstance().getTime());
			approvalLogOne.setChangedBy(userIdNo);
			approvalLogOne.setStatus(statusForUpdate);
			Date date = new Date();
			String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
			approvalLogOne.setApprovedDate(format);
			approvalLogOne.setApprovedTime(Calendar.getInstance().getTime());

			approvalLogOne = sampleRepo.save(approvalLogOne);
		}
		List<ApprovalLog> aprvList = sampleRepo.getApprovalLogByReqNoAndMailStatus(reqid, 3);

		if (aprvList.isEmpty()) {

			System.out.println("1001  : Next aprv is not found");
			aprv = userIdNo;

		} else {
			aprv = (aprvList.get(0).getAprv());

		}

		count = true;

		creatorEmail = rfpHeaderForPlant.getCreatedby().trim();

		// ==============================

		if (status == 1) {// approve

			if (aprvLevel.equalsIgnoreCase("techhead")) {// 1 st

				sub = " TC commercial Ratings against RFP " + reqid + " has been approved	";
				body = "Please be informend that the TC Commercial Rating against RFP No." + reqid
						+ " for thr subject matter"
						+ "	has been approved on technical reasons as mentioned. Kindly revert in case of any quries.";

				to = userMasterRepo.findByAdrid((approvalMatrixForAprv.getProchead())).getEmail().trim();
				if (to.equalsIgnoreCase("") || to == null)
					System.err.println(" to is not getLabhead");

				ccEmail = userMasterRepo.findByAdrid(creatorEmail).getEmail().trim();
				System.err.println(" to is not getLifnr");
				if ((to.isEmpty() || to == null || to.equalsIgnoreCase(""))
						&& (ccEmail.isEmpty() || ccEmail == null || ccEmail.equalsIgnoreCase(""))) {

					js.put("code", "1001");
					js.put("error", "Some probleam in the To And ccEmail in Fatching ");
					res = js.toString();
					return res;

				} else {
					mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
				}

			} else if (aprvLevel.equalsIgnoreCase("prochead")) {

				sub = " TC commercial Ratings against RFP " + reqid + " has been approved";
				body = "Kindly note that the  TC Commercial Rating against RFP No." + reqid
						+ " for thr subject matter has"
						+ "				been approved. Kindly initiate the further process.";

				to = (userMasterRepo.findByAdrid(creatorEmail).getEmail().trim());
				if (to.equalsIgnoreCase("") || to == null)
					System.err.println(" to is not getLabhead");

				ccEmail = (userMasterRepo.findByAdrid((approvalMatrixForAprv.getLabhead())).getEmail().trim());
				if ((to.isEmpty() || to == null || to.equalsIgnoreCase(""))
						&& (ccEmail.isEmpty() || ccEmail == null || ccEmail.equalsIgnoreCase(""))) {

					js.put("code", "1001");
					js.put("error", "Some probleam in the To And ccEmail in Fatching ");
					res = js.toString();
					return res;

				} else {
					mailSent = emailSending.emailSendService(to, sub, body, ccEmail);

					ProposalSummary proposalSummary = proposalSummaryRepository.findByRfpno(reqid);
					if (proposalSummary == null) {

						js.put("code", "1001");
						js.put("error", "ProposalSummary is Not Found");
						res = js.toString();
						return res;
					}

					proposalSummary.setStatus("" + status);
//					rfpHeaderForPlant.setStatus("1");
//					rfpHeaderForPlant = rfpHeaderRepository.save(rfpHeaderForPlant);
					proposalSummary = proposalSummaryRepository.save(proposalSummary);

//					

				}

			} else {

				System.err.println("error INVALID approver");
				js.put("code", "1001");
				js.put("error", "invalid Appraval You Are Passing ");
				res = js.toString();
				return res;
			}

		} else if (status == 2) {// reject

			aprv = userIdNo;

			if (aprvLevel.equalsIgnoreCase("prochead")) {

				sub = "  TC commercial Ratings against RFP " + reqid + "has been rejected	";
				body = "Please be informend that the TC Commercial Rating against RFP No." + reqid
						+ " for thr subject matter"
						+ "				has been rejected on technical reasons as mentioned. Kindly revert in case of any quries.";

				ccEmail = userMasterRepo.findByAdrid(creatorEmail).getEmail().trim();
				to = userMasterRepo.findByAdrid((approvalMatrixForAprv.getLabhead())).getEmail().trim();

//				ccEmail = userMasterRepo
//						.findByAdrid((poRepository.findByEbeln(asnHeaderForPlant.getEbeln()).getLifnr()))
//						.getEmail();

				if ((to.isEmpty() || to == null || to.equalsIgnoreCase(""))
						&& (ccEmail.isEmpty() || ccEmail == null || ccEmail.equalsIgnoreCase(""))) {

					js.put("code", "1001");
					js.put("error", "Some probleam in the To And ccEmail in Fatching ");
					res = js.toString();
					return res;

				} else {
					mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
				}
			} else if (aprvLevel.equalsIgnoreCase("techhead")) {// 1St

				sub = "  TC commercial Ratings against RFP " + reqid + "has been rejected	";
				body = "Please be informend that the TC Commercial Rating against RFP No." + reqid
						+ " for thr subject matter"
						+ "				has been rejected on technical reasons as mentioned. Kindly revert in case of any quries.";

				to = userMasterRepo.findByAdrid(creatorEmail).getEmail().trim();
				ccEmail = "";
				if (to.equalsIgnoreCase("") || to == null)
					System.err.println(" to is not getLabhead");

				if ((to.isEmpty() || to == null || to.equalsIgnoreCase(""))
						&& (ccEmail.isEmpty() || ccEmail == null || ccEmail.equalsIgnoreCase(""))) {

					js.put("code", "1001");
					js.put("error", "Some probleam in the To And ccEmail in Fatching ");
					res = js.toString();
					return res;

				} else {
					mailSent = emailSending.emailSendService(to, sub, body, ccEmail);
				}

			} else {
				System.err.println("error INVALID approver");
				js.put("code", "1001");
				js.put("error", "invalid approver You Are Passing ");
				res = js.toString();
				return res;
			}

		}

		if (mailSent) {
			List<ApprovalLog> approvalLogForMailStatus = sampleRepo.getApprovalLogByReqNoAndMailStatusAndAprv(reqid, 3,
					aprv);
			for (ApprovalLog approvalLogForMailStatusOne : approvalLogForMailStatus) {
				approvalLogForMailStatusOne.setMailstatus(1);
				approvalLogForMailStatusOne = approvalLogRepo.save(approvalLogForMailStatusOne);
				// approvalLog3 = sampleRepo.save(approvalLog);

				if (approvalLogForMailStatusOne != null)
					System.out.println("********mail status updatd Approve*******");
				else
					System.out.println("********mail status NOt updatd Approve*******");
			}

			js.put("code", "1000");
			try {
				js.put("sucess", ow.writeValueAsString(touchApprovalLog));
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			res = js.toString();
			return res;
		} else {
			System.out.println("mail is not sent   (clearified) ");

			js.put("code", "1001");
			js.put("ERROR", "Somthing is wrong");
			res = js.toString();
			return res;
		}

	}

	// vendor display service

	// public List<HashMap<String, Object>> getVendorBasicDetailsBySessionid(String
	// sessionid) {

	// public String getVendorBasicDetails(String aprv) {

	public List<HashMap<String, Object>> getVendorBasicDetails(String aprv) {

		JSONObject js = new JSONObject();
		List<Vendor> vendorList = new ArrayList<Vendor>();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String res = null;
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		try {

			WorkflowConfig appid = workFlowRepo.findByAppid("VM");
			if (appid == null) {

				System.out.println("Workflow Config is Empty");
				map1.put("code", "1001");
				map1.put("error", "Workflow Config is Empty");
				metaDataList.add(map1);
				// res = js.toString();
				return metaDataList;
			}

			List<ApprovalLog> approvalLogsList = approvalLogRepo.findByAprvAndAppid(aprv, appid);

			if (approvalLogsList.isEmpty()) {
				System.out.println("ApprovalLog List is Empty");
				map1.put("code", "1001");
				map1.put("error", "ApprovalLog List is Empty");
				// res = js.toString();
				metaDataList.add(map1);
				// res = js.toString();
				return metaDataList;
			}

			for (ApprovalLog approvalLogOne : approvalLogsList) {

				HashMap<String, Object> map = new HashMap<String, Object>();

				if (approvalLogOne == null) {
					System.out.println("ApprovalLog in not found");
					map.put("code", "1001");
					map.put("error", "ApprovalLog in not found");
					metaDataList.add(map);
					// res = js.toString();
					return metaDataList;
				}

				// String lifnrId = String.valueOf(approvalLogOne.getReqNo());
				long lifnrId = approvalLogOne.getReqNo();

				// vendorList.add(vendorRepository.findByCreatedBy(lifnrId));

				Vendor vendor = vendorRepository.findByVid(lifnrId);
				// HashMap<String, Object> map = new HashMap<String, Object>();

				map.put("vid", vendor.getVid());
				map.put("orgName", vendor.getOrgName());
				map.put("status", approvalLogOne.getStatus());
				map.put("level", approvalLogOne.getAprvlevel());
				metaDataList.add(map);
				// res = js.toString();
				// return metaDataList;

				/*
				 * List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String,
				 * Object>>(); List<Vendor> vendors =
				 * vendorRepository.getVendorByCreatedBy(cradrid); for (Vendor vendor : vendors)
				 * { HashMap<String, Object> map = new HashMap<String, Object>(); map.put("vid",
				 * vendor.getVid()); map.put("orgName", vendor.getOrgName()); map.put("status",
				 * vendor.getStatus()); metaDataList.add(map); }
				 * 
				 */

			}

			/*
			 * if (vendorList.isEmpty()) {
			 * System.out.println("List Of vendor is not found in Vendor Registration table"
			 * ); map1.put("code", "1001"); map1.put("error",
			 * "List Of vendor is not found in Vendor Registration table");
			 * metaDataList.add(map1); //res = js.toString(); //return metaDataList; } else
			 * { map1.put("code", "1000"); map1.put("VendorList",
			 * ow.writeValueAsString(vendorList)); metaDataList.add(map1); //res =
			 * js.toString(); //return metaDataList; }
			 */
		} catch (Exception e) {

			e.printStackTrace();
		}
		return metaDataList;

	}

	public String nonPoInvAcknowledgementMail(String userIdNo, Long reqid, String appid, int status, String remark)
			throws JSONException {

		JSONObject js = new JSONObject();

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String res = null;
		String plant = null;
		String link = "";
		String apprverId = "";
		PurchaseOrder purchaseOrderForStatus = null;
		String creatorEmail = "";
		Boolean mailSent;
		String to = null, body = null, sub = null, ccEmail = null;
		EmailSending emailSending = new EmailSending();

		Invoice invoice = invoiceRepository.findOne(reqid);

		plant = "logistics";

		Status statusObj = statusRepo.findOne(3);
		if (statusObj == null) {

			js.put("code", "1001");
			js.put("error", "Invalid Status ");
			res = js.toString();
			return res;

		}
		WorkflowConfig appid1 = sampleRepo.getWorkflowConfigByAppid(appid)/* .getId() */;

		if (appid1 == null) {
			js.put("code", "1001");
			js.put("error", "WORKFLOW For This " + appid + " Is Not Maintain ");
			res = js.toString();
			return res;// prasunmail

		}

		List<ApprovalLog> approvalLogListForApprverId = sampleRepo.getApprovalLogByAppIdAndReqNO(appid1, statusObj,
				reqid);
		if (approvalLogListForApprverId.isEmpty()) {
			js.put("code", "1001");
			js.put("error", "approvalLogList is not found");
			res = js.toString();
			return res;// prasunmail
		}
		apprverId = approvalLogListForApprverId.get(0).getAprv();

//				Status statusForAprv = statusRepo.findOne(3);
		List<ApprovalLog> approvalLogList = sampleRepo.getApprovalLogByAprvAndAppidAndReqnoAndStatusMailStatus(appid1,
				apprverId, reqid, statusObj, 1);
		if (approvalLogList.isEmpty()) {
			js.put("code", "1001");
			js.put("error", "Invalid user trying to asscess  ");
			res = js.toString();
			return res;

		}
		PurchaseOrder purchaseOrder = poRepository.findByEbeln(reqid);
		if (purchaseOrder == null) {
			js.put("code", "1001");
			js.put("error", "PurchaseOrder is not found  for " + reqid);
			res = js.toString();
			return res;
		}

		PurchaseOrder purchaseOrder2 = purchaseOrder;

		for (ApprovalLog approvalLogOne : approvalLogList) {

//					approvalLogOne = approvalLog0;
			creatorEmail = approvalLogOne.getInitiator();// prasun1

			approvalLogOne.setRemark(remark);
			approvalLogOne.setApprovedBy(userMasterRepo.findByAdrid(userIdNo).getPnam());
			approvalLogOne.setApprovedId(userIdNo);
			approvalLogOne.setCddat(Calendar.getInstance().getTime());
			approvalLogOne.setChangedBy("" + userIdNo);
			approvalLogOne.setStatus(statusRepo.findOne(status));
			Date date = new Date();

			String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
			approvalLogOne.setApprovedDate(format);
			approvalLogOne.setApprovedTime(Calendar.getInstance().getTime());

			approvalLogOne = sampleRepo.save(approvalLogOne);
			if (approvalLogOne == null) {
				js.put("code", "1001");
				js.put("error", "Data is not update in  approvalLog table for " + reqid);
				res = js.toString();
				return res;
			}

		}
		try {

			purchaseOrder.setStatus(String.valueOf(status));

			Blob blob = purchaseOrder2.getPoDocument().getFileData();

			Blob b = new SerialBlob(blob);
			purchaseOrder.getPoDocument().setFileData(b);

			purchaseOrder = poRepository.save(purchaseOrder);

			if (purchaseOrder == null) {
				js.put("code", "1001");
				js.put("error", "Data is not update in  purchaseOrder table for " + reqid);
				res = js.toString();
				return res;
			}

		} catch (Exception e) {
			e.getStackTrace();
		}

		ApprovalMatrix approvalMatrixForAprv = approvalMatrixRepository.findByPlant(plant);
		if (approvalMatrixForAprv == null) {
			js.put("code", "1001");
			js.put("error", "According To This Plant " + plant + "  ApprovalMatrix Is Not Maintain");
			res = js.toString();
			return res;
		}
		to = userMasterRepo.findByAdrid((approvalMatrixForAprv.getProchead())).getEmail();
//		      ccEmail = userMasterRepo.findByAdrid((approvalMatrixForAprv.getLabhead())).getEmail();
		ccEmail = "";

		sub = "Purchase Orderd no. " + reqid + " dated " + Calendar.getInstance().getTime()
				+ " has been acknowledged by the vendor.";
		body = "Purchase Orderd no. " + reqid + " dated " + Calendar.getInstance().getTime()
				+ " has been acknowledged by the vendor.";
		link = " http://192.168.0.56:8080/ZSRL_VMS_PO_ACK/";
		mailSent = emailSending.emailSendService(to, sub, body, "");

		if (mailSent) {
			System.out.println("mail sent status = " + mailSent);

			js.put("code", "1000");
			js.put("Sucess", "Status Update And Mail Sent");
			res = js.toString();
			return res;

		} else {

			js.put("code", "1001");
			js.put("error", "Something Is Wrong In Mail API");
			res = js.toString();
			return res;

		}

	}

	public String aprvDesignationFinder(/* WorkflowConfig workflowConfigList, */ ApprovalMatrix approvalMatrixForAprv,
			String designation) {

		String aprv = null;

		String aprvString = "";

//		for (int i = 1; i <= workflowConfigList.getAprvLevels() ; i++) {
//			String Aprv""

		if (designation.equalsIgnoreCase("labhead"))
			aprv = approvalMatrixForAprv.getLabhead();
		else if (designation.equalsIgnoreCase("buhead"))
			aprv = approvalMatrixForAprv.getBuhead();
		else if (designation.equalsIgnoreCase("prochead"))
			aprv = approvalMatrixForAprv.getProchead();
		else if (designation.equalsIgnoreCase("techhead"))
			aprv = approvalMatrixForAprv.getTechhead();
		else if (designation.equalsIgnoreCase("matspoc"))
			aprv = approvalMatrixForAprv.getMatspoc();
		else if (designation.equalsIgnoreCase("mdmteam"))
			aprv = approvalMatrixForAprv.getMdmteam();
		else if (designation.equalsIgnoreCase("rfpcreator"))
			aprv = approvalMatrixForAprv.getRfpcreator();
		else if (designation.equalsIgnoreCase("logistics_spoc"))
			aprv = approvalMatrixForAprv.getLogistics_spoc();
		else if (designation.equalsIgnoreCase("logistics_head"))
			aprv = approvalMatrixForAprv.getLogistics_head();

//			aprvString = aprvString +","+aprv;

//		}

		if (aprv.isEmpty() || aprv.equalsIgnoreCase("") || aprv == null) {

			return "0";

		} else {
			return aprv;
		}

	}

	
	//mail notification in RFP inities
//	public Map<String, Object> mailnotificationForRFP(String rfpno) {
//		
//		RFPHeader rfpHeader = rfpHeaderRepository.findByRfpno(longrfpno)
//		
//		
//		return null;
//	}

//	public String rfpAcknowledgementMail(String userIdNo, Long reqid, String appid, int status, String remark) {
//		
//		
//		
//		
//		
//		
//		return null;
//	}

//	public String asnWorkflow(long reqid) {
//		
//		
//		String res = null;
//		JSONObject js = new JSONObject();
//		String plant = "" ;
//
//		ASNHeader asnHeader = asnRepository.findOne(reqid);
//		if (asnHeader == null) {
//			js.put("code", "1001");
//			js.put("error", "ASNHeader  is Not Found");
//			res = js.toString();
//			return res;
//		}
//		
//		PurchaseOrder  purchaseOrder =poRepository.findByEbeln(asnHeader.getEbeln());
//		
//		if (purchaseOrder == null) {
//			js.put("code", "1001");
//			js.put("error", "PurchaseOrder  is Not Found");
//			res = js.toString();
//			return res;
//		}
//		
//		List<ASNItem> asnItems = asnHeader.getAsnitem();
//		if (asnItems.isEmpty()) {
//			js.put("code", "1001");
//			js.put("error", "PurchaseOrder  is Not Found");
//			res = js.toString();
//			return res;
//		}
//		plant = asnItems.get(0).getWerks();
//		
//		
//		aprvLog.setInitiator("" + purchaseOrder.getLifnr());
//		count = true;
//
//		System.out.println(" noe not aveleble set the   detnthe the after the some time ");
//
//	
//		
//		
//		
//		return null;
//	}

}

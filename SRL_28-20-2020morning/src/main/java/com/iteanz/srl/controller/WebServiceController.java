
package com.iteanz.srl.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iteanz.srl.domain.ASNHeader;
import com.iteanz.srl.domain.ASNItem;
import com.iteanz.srl.domain.ApprovalLog;
import com.iteanz.srl.domain.ApprovalMatrix;
import com.iteanz.srl.domain.ApproverMaster;
import com.iteanz.srl.domain.Contract;
import com.iteanz.srl.domain.Currency;
import com.iteanz.srl.domain.Department;
import com.iteanz.srl.domain.Designation;
import com.iteanz.srl.domain.DocumentType;
import com.iteanz.srl.domain.FirstParty;
import com.iteanz.srl.domain.GRNHeader;
import com.iteanz.srl.domain.Invoice;
import com.iteanz.srl.domain.MaterialMaster;
import com.iteanz.srl.domain.OrgMasterAndUserMapping;
import com.iteanz.srl.domain.POItemDetails;
import com.iteanz.srl.domain.PasswordPolicy;
import com.iteanz.srl.domain.Payment;
import com.iteanz.srl.domain.Plant;
import com.iteanz.srl.domain.ProposalSummary;
import com.iteanz.srl.domain.PurOrg;
import com.iteanz.srl.domain.PurchaseOrder;
import com.iteanz.srl.domain.PurchaseRequisition;
import com.iteanz.srl.domain.RFPHeader;
import com.iteanz.srl.domain.RFPInvitedVendors;
import com.iteanz.srl.domain.RFPItem;
import com.iteanz.srl.domain.RFPParticipant;
import com.iteanz.srl.domain.RFPQuery;
import com.iteanz.srl.domain.RFPSection;
import com.iteanz.srl.domain.RoleModel;
import com.iteanz.srl.domain.SampleEntity;
import com.iteanz.srl.domain.SecondParty;
import com.iteanz.srl.domain.SessionsTable;
import com.iteanz.srl.domain.Status;
import com.iteanz.srl.domain.Storage;
import com.iteanz.srl.domain.TechCriteriaHeader;
import com.iteanz.srl.domain.TechRatingHeader;
import com.iteanz.srl.domain.TechnoCriteria;
import com.iteanz.srl.domain.TechnoRatings;
import com.iteanz.srl.domain.Uom;
import com.iteanz.srl.domain.UserADIntegration;
import com.iteanz.srl.domain.UserMaster;
import com.iteanz.srl.domain.UserRolesAssignment;
import com.iteanz.srl.domain.VPGenProposal;
import com.iteanz.srl.domain.VPHeader;
import com.iteanz.srl.domain.VPItem;
//import com.iteanz.srl.domain.VPSection;
import com.iteanz.srl.domain.VPTCProposal;
import com.iteanz.srl.domain.Vendor;
import com.iteanz.srl.domain.VendorBalance;
import com.iteanz.srl.domain.VendorLedger;
import com.iteanz.srl.domain.VendorRegistration;
import com.iteanz.srl.domain.WorkflowConfig;
import com.iteanz.srl.repositories.ASNHeaderRepository;
import com.iteanz.srl.repositories.ApprovalLogRepo;
import com.iteanz.srl.repositories.ApprovalMatrixRepository;
import com.iteanz.srl.repositories.DepartmentRepo;
import com.iteanz.srl.repositories.InvoiceRepository;
import com.iteanz.srl.repositories.PlantCrudrepo;
import com.iteanz.srl.repositories.ProposalSummaryRepository;
import com.iteanz.srl.repositories.PurchaseOrderRepository;
import com.iteanz.srl.repositories.PurchaseRequisitionRepository;
import com.iteanz.srl.repositories.RFPHeaderRepository;
import com.iteanz.srl.repositories.RFPQueryRepository;
import com.iteanz.srl.repositories.StatusRepo;
import com.iteanz.srl.repositories.UserMasterRepo;
import com.iteanz.srl.repositories.VPHeaderRepository;
import com.iteanz.srl.repositories.VendorRepository;
import com.iteanz.srl.repositories.WebServiceRepository;
import com.iteanz.srl.service.ApproverServices;
import com.iteanz.srl.service.GoodsIssueService;
import com.iteanz.srl.service.GoodsReceiptService;
import com.iteanz.srl.service.StockMasterService;
import com.iteanz.srl.service.WebGetServices;
import com.iteanz.srl.service.WebService;
import com.iteanz.srl.utility.DateCompare;
import com.iteanz.srl.utility.EmailSending;
import com.iteanz.srl.utility.FileFormFTP;

//import net.minidev.json.JSONObject;

@RestController
public class WebServiceController {

	@Autowired
	WebService webService;

	@Autowired
	WebServiceRepository sampleRepo;
	
	@Autowired
	private WebGetServices webGetServices;

	@Autowired
	MailSender iteanzmail;
	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private PlantCrudrepo plantrepo;
	
	@Autowired
	private RFPQueryRepository rfpQueryRepo;
	
	@Autowired
	private RFPHeaderRepository rfpHeaderRepository;

	@Autowired
	private ProposalSummaryRepository proposalSummaryRepository;

	@Autowired
	private ASNHeaderRepository asnHeaderRepository;
	
	@Autowired
	private VPHeaderRepository vpheaderrepo;

	@Autowired
	private UserMasterRepo masterRepo;

	EmailSending emailSending = new EmailSending();

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private ApprovalLogRepo approvalLogRepo;

	@Autowired
	private DateCompare dateCompare;

	@Autowired
	private ApproverServices approverServices;

	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private StatusRepo statusRepo;

	@Autowired
	private ApprovalMatrixRepository approvalMatrixRepository;

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
	private VendorRepository vendorRepository;

	@Autowired
	private PurchaseRequisitionRepository purchaseRequisitionRepository;
	@Autowired
	GoodsReceiptService goodsReceiptService;

	@Autowired
	GoodsIssueService goodsIssueService;

	@Autowired
	private StockMasterService stockMasterService;

	@GetMapping(value="/poQty/{ebeln}" , produces=MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public List<HashMap<String, Object>> poQty(@PathVariable long ebeln) throws SQLException {
		return webService.poQty(ebeln);		
	}
	
 
	@PostMapping("/inv/invoice")
	@CrossOrigin("*")
	public HashMap<String, Object> addInvoice(@RequestBody Invoice invoice) throws SerialException, SQLException {
		return webService.addInvoice(invoice);
	}

	@GetMapping("/incompleterequisition")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> incompleterequisitiontest() throws SQLException {
		return webService.incompleterequisition();
	}
	
	

	@PostMapping("/createPO")
	@CrossOrigin("*")
	public HashMap<String, Object> addPO(@RequestBody PurchaseOrder po) throws SerialException, SQLException {
		return webService.addPO(po);
	}

	@RequestMapping(value = "/incompleterequisitionold", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> incompleterequisitionold(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;

		try {
			int statusint = 0;
			
			String reqstat = "I";// Not 'C'
			String Status = "1";
			// List<PurchaseRequisition> incompPrList =
			// webService.getincompPrListByReqStatus(reqstat,statusint);
			List<PurchaseRequisition> incompPrList = webService.getAllPurchaseRequisition();
			if (incompPrList == null || incompPrList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Incomplete PR List Is Empty");
				json = js.toString();
			} else {
				js.put("incompPrList", ow.writeValueAsString(incompPrList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@GetMapping(value = "/getRFPEmailIdsByVP/{proposalno}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getRFPEmailIdsByVP(@PathVariable long proposalno) throws SQLException {
		return webService.getRFPEmailIdsByVP(proposalno);
	}

	@GetMapping(value = "/getVPHeaderByRfpno1/{rfpno}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public VPHeader getVPHeaderByRfpno1(@PathVariable long rfpno) throws SQLException {
		return webService.getVPHeaderByRfpno1(rfpno);
	}

	@GetMapping(value = "/getRFPwithVPNO/{lifnr}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getRFPwithVPNO(@PathVariable String lifnr) throws SQLException {
		return webService.getRFPwithVPNO(lifnr);
	}

	@GetMapping(value = "/getRFPwithVPNOAndSession/{sessionid}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getRFPwithVPNOWithSessionid(@PathVariable String sessionid)
			throws SQLException {
		return webService.getRFPwithVPNOWithSessionid(sessionid);
	}

	@GetMapping("/inv/invoice")
	@CrossOrigin("*")
	public List<Invoice> getInvoices() throws SQLException {
		return webService.getAllInvoice();
	}

	@GetMapping("/inv/invoice/metadata")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getInvoiceBasicDetails() throws SQLException {
		return webService.getMetaData();
	}

	@GetMapping(value = "/getInvByAsnAndLifnr/{asnNo}/{vendorNo}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public List<Invoice> getInvoiceByasnNo(@PathVariable String asnNo, @PathVariable String vendorNo)
			throws SQLException {
		return webService.getInvByasnNoAndLifnr(asnNo, vendorNo);
	}

	@GetMapping(value = "/inv/invoice/{invoiceId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public Invoice getInvoice(@PathVariable long invoiceId) throws SQLException {
		return webService.getInvoice(invoiceId);
	}

	@PostMapping("/proposalSummary")
	@CrossOrigin("*")
	public HashMap<String, Object> addProposalSummary(@RequestBody ProposalSummary proposalsummary)
			throws SerialException, SQLException {
		return webService.addProposalSummary(proposalsummary);
	}

	@PutMapping("/proposalSummary")
	@CrossOrigin("*")
	public HashMap<String, Object> updateProposalSummary(@RequestBody ProposalSummary proposalsummary)
			throws SerialException, SQLException {
		return webService.updateProposalSummary(proposalsummary);
	}

	@GetMapping(value = "/proposalSummaryByRfpno/{rfpno}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public ProposalSummary getProposalSummary(@PathVariable long rfpno) throws SQLException {
		return webService.getProposalSummary(rfpno);
	}

	@PutMapping("/inv/invoice")
	@CrossOrigin("*")
	public HashMap<String, Object> updateInvoice(@RequestBody Invoice invoice) throws SerialException, SQLException {
		return webService.updateInvoice(invoice);

	}

	@GetMapping("/createVendorBalance")
	@CrossOrigin("*")
	public List<VendorBalance> getVendorBalances() throws SQLException {
		return webService.getVendorBalances();
	}

	/*
	 * @GetMapping(value="/createVendorBalance/{lifnr}" ,
	 * produces=MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @CrossOrigin("*") public VendorBalance getVendorBalancer(@PathVariable long
	 * lifnr) throws SQLException { return webService.getVendorBalance(lifnr); }
	 */
	@GetMapping(value = "/createVendorBalance/{lifnr}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public VendorBalance getVendorBalancer(@PathVariable String lifnr) throws SQLException {
		return webService.getVendorBalance(lifnr);
	}

	@GetMapping("/createVendorBalance/metadata")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getVendorBalanceBasicDetails() throws SQLException {
		return webService.getAllVendorBalanceMetaData();
	}

	@GetMapping("/createVendorLedger")
	@CrossOrigin("*")
	public List<VendorLedger> getVendorLedgers() throws SQLException {
		return webService.getVendorLedgers();
	}

	/*
	 * @GetMapping(value="/createVendorLedger/{lifnr}" ,
	 * produces=MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @CrossOrigin("*") public VendorLedger getVendorLedger(@PathVariable long
	 * lifnr) throws SQLException { return webService.getVendorLedger(lifnr); }
	 */
	@GetMapping(value = "/createVendorLedger/{lifnr}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public List<VendorLedger> getVendorLedger(@PathVariable String lifnr) throws SQLException {
		return webService.getVendorLedger(lifnr);
	}

	@GetMapping("/createVendorLedger/metadata/{sessionid}")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getVendorLedgerBasicDetails(String sessionid) throws SQLException {
		return webService.getAllVendorLedgerMetaData(sessionid);
	}

	@GetMapping("/createPayment")
	@CrossOrigin("*")
	public List<Payment> getPayments() throws SQLException {
		return webService.getAllPayments();
	}

	/*
	 * @GetMapping(value="/createPayment/{lifnr}" ,
	 * produces=MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @CrossOrigin("*") public Payment getPayment(@PathVariable long lifnr) throws
	 * SQLException { return webService.getPayment(lifnr); }
	 */

	@GetMapping(value = "/createPayment/{lifnr}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public Payment getPayment(@PathVariable String lifnr) throws SQLException {
		return webService.getPayment(lifnr);
	}

	@GetMapping("/createPayment/metadata")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getPaymentBasicDetails() throws SQLException {
		return webService.getAllPaymentMetaData();
	}

	@GetMapping(value = "/getGRNByAsno/{grn}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public GRNHeader getGRNByAsno(@PathVariable long grn) throws SQLException {
		return webService.getGRNByAsno(grn);
	}

	@GetMapping("/createGRN")
	@CrossOrigin("*")
	public List<GRNHeader> getGRNHeaders() throws SQLException {
		return webService.getAllGRNHeaders();
	}

	/*
	 * @GetMapping(value="/createGRN/{lifnr}" ,
	 * produces=MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @CrossOrigin("*") public GRNHeader getGRNHeader(@PathVariable String lifnr)
	 * throws SQLException { return webService.getGRNHeader(lifnr); }
	 */

	@GetMapping(value = "/createGRN/{lifnr}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public GRNHeader getGRNHeader(@PathVariable String lifnr) throws SQLException {
		return webService.getGRNHeader(lifnr);
	}

	@GetMapping("/createGRN/metadata")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getGRNBasicDetails() throws SQLException {
		return webService.getAllGRNMetaData();
	}

	@GetMapping(value = "/createASN/{drfgrnno}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public ASNHeader getASNHeader(@PathVariable long drfgrnno) throws SQLException {
		return webService.getASNHeader(drfgrnno);
	}

	@PutMapping("/createASN")
	@CrossOrigin("*")
	public HashMap<String, Object> updateASN(@RequestBody ASNHeader asnheader) throws SerialException, SQLException {
		return webService.updateASN(asnheader);

	}

	@GetMapping("/createASN")
	@CrossOrigin("*")
	public List<ASNHeader> getASNHeaders() throws SQLException {
		return webService.getAllASNHeaders();
	}

	@GetMapping("/createASN/metadata")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getASNBasicDetails() throws SQLException {
		return webService.getAllASNMetaData();
	}
	@GetMapping("/approveASN/metadata/{sessionid}")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getASNBasicDetails1(@PathVariable String sessionid) throws SQLException {
		return webService.getAllASNMetaDataByApprover(sessionid);
	}
	@GetMapping(value = "/getASNByEbeln/{ebeln}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public List<ASNHeader> getASNByEbeln(@PathVariable long ebeln) throws SQLException {
		return webService.getASNByEbeln(ebeln);
	}

	@GetMapping("/createPO/metadata")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getPOBasicDetails() throws SQLException {
		return webService.getAllPOMetaData();
	}

	@GetMapping("/createPO/metadata/{sessionid}")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getPOBasicDetailsBySessionid(@PathVariable String sessionid)
			throws SQLException {
		return webService.getPOBasicDetailsBySessionid(sessionid);
	}

	@GetMapping(value = "/createPO", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public List<PurchaseOrder> getPOs() throws SQLException {

		
		return webService.getAllPOs();
	}
	
	
//	public String getPOs()  {
//	String res = null;
//	JSONObject js = new JSONObject();
//	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//
//	
//	try {
//		js.put("code", "1000");
//		js.put("PurchaseOrderList", ow.writeValueAsString(webService.getAllPOs()));
//		res = js.toString();
//	} catch (JSONException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (JsonGenerationException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (JsonMappingException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
//	return res;
//	
	
	
	
	

	@GetMapping(value = "/createPO/{ebeln}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public PurchaseOrder getPurchaseOrder(@PathVariable long ebeln) throws SQLException {
		return webService.getPurchaseOrder(ebeln);
	}

	@GetMapping(value = "/getPObyLifnr/{lifnr}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public List<PurchaseOrder> getPurchaseOrderByVendor(@PathVariable String lifnr) throws SQLException {
		return webService.getPurchaseOrderByVendor(lifnr);
	}

	@GetMapping("/venreg/vendor")
	public List<Vendor> getVendors() throws SQLException {
		return webService.getAllVendors1();
	}

	/*
	 * @GetMapping("/venreg/vendor/metadata")
	 * 
	 * @CrossOrigin("*") public List<HashMap<String, Object>>
	 * getVendorBasicDetails() throws SQLException { return
	 * webService.getAllVendorsMetaData(); }
	 */

	
	@GetMapping("/venreg/vendor/metadata/{sessionid}")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getVendorBasicDetails(@PathVariable String sessionid)
			throws SQLException {
	
	//@RequestMapping(value = "/venreg/vendor/metadata", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")

		//public ResponseEntity<String> getVendorBasicDetails(HttpServletRequest request, HttpServletResponse response,
		//	@RequestParam("SESID") String sesId) {

	//	response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();

		String json = null;
		String aprv = null;
		String id = null;

//		String sesId =ackvenreg.get("SESID");

		UserMaster createdBy = null;
		String ardid = null;
		try {
			long sesid = Long.parseLong(sessionid);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();
				}
			}
			if (ardid == null) {
				js.put("error", "Session Expired");
				json = js.toString();
				return (List<HashMap<String, Object>>) new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		long reqno1 = 0l;
		try {
			System.out.println("APPROVER:" + createdBy.getId());
			id = Long.toString(createdBy.getId());
			aprv = createdBy.getAdrid().trim();
			if (aprv == "" || aprv.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "USER ID is Empty");
				json = js.toString();
				return (List<HashMap<String, Object>>) new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return approverServices.getVendorBasicDetails(aprv);
	}
	

	@GetMapping("/venreg/vendor/metadataAll/{sessionid}")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getVendorBasicDetailsBySessionid(@PathVariable String sessionid)
			throws SQLException {
		return webService.getVendorBasicDetailsBySessionid(sessionid);
	}

	@GetMapping(value = "/venreg/vendor/{vid}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public Vendor getVendor(@PathVariable long vid) throws SQLException {
		return webService.getVendor(vid);
	}

	@PostMapping("/venreg/vendor")
	@CrossOrigin("*")
	public HashMap<String, Object> addVendor(@RequestBody Vendor vendor) throws SerialException, SQLException {
		return webService.addVendor(vendor);
	}

	@PutMapping("/venreg/vendor")
	@CrossOrigin("*")
	public HashMap<String, Object> updateVendor(@RequestBody Vendor vendor) throws SerialException, SQLException {
		return webService.updateVendor(vendor);

	}

	/*
	 * @PostMapping("/createTCR")
	 * 
	 * @CrossOrigin("*") public HashMap<String, Object> addTechnocom(@RequestBody
	 * TechnocomHeader techhead) throws SerialException, SQLException { return
	 * webService.addTechnocom(techhead); }
	 * 
	 * @PutMapping("/createTCR")
	 * 
	 * @CrossOrigin("*") public HashMap<String, Object> updateTechnocom(@RequestBody
	 * TechnocomHeader techhead) throws SerialException, SQLException { return
	 * webService.updateTechnocom(techhead); }
	 * 
	 * @GetMapping("/createTCR")
	 * 
	 * @CrossOrigin("*") public List<TechCriteriaHeader> getTechnocoms() throws
	 * SQLException { return webService.getAllTechnocom1(); }
	 * 
	 * @GetMapping(value="/createTCR/{rfpno}" ,
	 * produces=MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @CrossOrigin("*") public TechCriteriaHeader getTechnocom(@PathVariable long
	 * rfpno) throws SQLException { return webService.getTechnocom(rfpno); }
	 */

	@PostMapping("/createCriteria")
	@CrossOrigin("*")
	public HashMap<String, Object> addTechCriteriaHeader(@RequestBody TechCriteriaHeader techcriteriahead)
			throws SerialException, SQLException {
		return webService.addTechCriteriaHeader(techcriteriahead);
	}

	@PutMapping("/createCriteria")
	@CrossOrigin("*")
	public HashMap<String, Object> updateTechCriteriaHeader(@RequestBody TechCriteriaHeader techcriteriahead)
			throws SerialException, SQLException {
		return webService.updateTechCriteriaHeader(techcriteriahead);
	}

	@GetMapping("/createCriteria")
	@CrossOrigin("*")
	public List<TechCriteriaHeader> getAllTechnoCriterias() throws SQLException {
		return webService.getAllTechnoCriterias();
	}

	@GetMapping(value = "/createCriteria/{rfpno}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public TechCriteriaHeader getTechnoCriterias(@PathVariable long rfpno) throws SQLException {
		return webService.getTechnoCriterias(rfpno);
	}

	@PostMapping("/createRating")
	@CrossOrigin("*")
	public HashMap<String, Object> addTechRatingHeader(@RequestBody TechRatingHeader techratinghead)
			throws SerialException, SQLException {
		return webService.addTechRatingHeader(techratinghead);
	}

	@PutMapping("/createRating")
	@CrossOrigin("*")
	public HashMap<String, Object> updateTechRatingHeader(@RequestBody TechRatingHeader techratinghead)
			throws SerialException, SQLException {
		return webService.updateTechRatingHeader(techratinghead);
	}

	@GetMapping("/createRating")
	@CrossOrigin("*")
	public List<TechRatingHeader> getAllTechnoRatings() throws SQLException {
		return webService.getAllTechnoRatings();
	}

	@GetMapping(value = "/createRating/{rfpno}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public TechRatingHeader getTechnoRatings(@PathVariable long rfpno) throws SQLException {
		return webService.getTechnoRatings(rfpno);
	}

	@PostMapping("/createRFP")
	@CrossOrigin("*")
	public HashMap<String, Object> addRFPHeader(@RequestBody RFPHeader rfpheader) throws SerialException, SQLException {
		return webService.addRFPHeader(rfpheader);
	}

	@PutMapping("/createRFP")
	@CrossOrigin("*")
	public HashMap<String, Object> updateRFPHeader(@RequestBody RFPHeader rfpheader)
			throws SerialException, SQLException {
		return webService.updateRFPHeader(rfpheader);
	}

	@GetMapping("/createRFP/metadata")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getRFPBasicDetails() throws SQLException {
		return webService.getAllRFPMetaData();
	}

	@GetMapping("/createRFP/metadata/{sessionid}")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getRFPBasicDetailsBySessionid(@PathVariable String sessionid)
			throws SQLException {
		return webService.getRFPBasicDetailsBySessionid(sessionid);
	}

	
	@GetMapping("/rfpQuery/{rfpno}/{proposalno}")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> rfpQuery(@PathVariable long rfpno, @PathVariable long proposalno) throws SQLException {
		return webService.rfpQuery(rfpno, proposalno);
	}
	
	@GetMapping("/rfpQueryByRfpno/{rfpno}")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> rfpQueryByRfpno(@PathVariable long rfpno) throws SQLException {
		return webService.rfpQueryByRfpno(rfpno);
	}
	
	
	
	//get data rfp data by session id
	@GetMapping("/createAprvRFPA/metadata/{sessionid}")//prasun
	@CrossOrigin("*")
	public String getRFPBasicDetailsByAprvSessionid(@RequestParam("SESID")  String sessionid)
			throws SQLException {
		return webService.getRFPBasicDetailsByAprvSessionid(sessionid);
	}

	
	
	
	@GetMapping("/createRFP")
	@CrossOrigin("*")
	public List<RFPHeader> getRFPHeaders() throws SQLException {
		return webService.getAllRFPHeaders();
	}

	@GetMapping(value = "/createRFP/{rfpno}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public RFPHeader getRFPHeader(@PathVariable long rfpno) throws SQLException {
		return webService.getRFPHeader(rfpno);
	}

	/*
	 * @GetMapping(value="/createRFPWithVP/{rfpno}" ,
	 * produces=MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @CrossOrigin("*") public List<HashMap<String, Object>>
	 * getRFPWithVP(@PathVariable long rfpno) throws SQLException { return
	 * webService.getRFPWithVP(rfpno); }
	 */

	@PostMapping("/createVP")
	@CrossOrigin("*")
	public HashMap<String, Object> addVendorProposal(@RequestBody VPHeader vpheader)
			throws SerialException, SQLException {
		return webService.addVendorProposal(vpheader);
	}

	/*
	 * @PostMapping("/createASN")
	 * 
	 * @CrossOrigin("*") public HashMap<String, Object> addASN(@RequestBody
	 * ASNHeader asnheader) throws SerialException, SQLException { return
	 * webService.addASN(asnheader); }
	 */
	@PostMapping("/createASN")
	@CrossOrigin("*")
	public HashMap<String, Object> createASN(@RequestBody ASNHeader asnheader) throws SerialException, SQLException {
		return webService.createASN(asnheader);
	}

	@PutMapping("/createVP")
	@CrossOrigin("*")
	public HashMap<String, Object> updateVendorProposal(@RequestBody VPHeader vpheader)
			throws SerialException, SQLException {
		return webService.updateVendorProposal(vpheader);

	}

	@GetMapping("/createVP")
	@CrossOrigin("*")
	public List<VPHeader> getVPHeaders() throws SQLException {
		return webService.getAllVPHeaders();
	}

	@GetMapping(value = "/createVP/{proposalno}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public VPHeader getVPHeader(@PathVariable long proposalno) throws SQLException {
		return webService.getVPHeader(proposalno);
	}

	@GetMapping("/createVP/metadata")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getVPBasicDetails() throws SQLException {
		return webService.getAllVPMetaData();
	}

	@GetMapping("/createVP/metadata/{sessionid}")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getAllVPMetaDataBySessionid(@PathVariable String sessionid)
			throws SQLException {
		return webService.getAllVPMetaDataBySessionid(sessionid);
	}

	@GetMapping("/VPWithRFPHeader")
	@CrossOrigin("*")
	public List<VPHeader> getVPWithRFPHeader() throws SQLException {
		return webService.getAllVPWithRFPHeader();
	}

	@GetMapping(value = "/createVPWithRFP/{proposalno}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getVPHeaderVPWithRFP(@PathVariable long proposalno) throws SQLException {
		return webService.getVPHeaderVPWithRFP(proposalno);
	}

	@GetMapping(value = "/getVPHeaderByRfpno/{rfpno}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public VPHeader getVPHeaderByRfpno(@PathVariable long rfpno) throws SQLException {
		return webService.getVPHeaderByRfpno(rfpno);
	}

	@GetMapping(value = "/getVPByRfpno/{rfpno}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public List<VPHeader> getVPByRfpno(@PathVariable Long rfpno) throws SQLException {
		return webService.getVPByRfpno(rfpno);
	}

	@GetMapping(value = "/proposalSummary/{rfpno}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public List<HashMap<String, Object>> proposalSummaryTest(@PathVariable long rfpno) throws SQLException {
		return webService.proposalSummaryTest(rfpno);
	}

	@GetMapping(value = "/proposalSummaryTest/{rfpno}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public List<HashMap<String, Object>> proposalSummary(@PathVariable long rfpno) throws SQLException {
		return webService.proposalSummary(rfpno);
	}

	@RequestMapping(value = "/userDetails", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> userDetails(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ADRID") String adrid) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (adrid.isEmpty() || adrid == "") {
				js.put("code", "1002");
				js.put("error", "ADRID is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			RoleModel userRoll = new RoleModel();
			userRoll.setId(1);
			List<UserMaster> usersList = null;
			UserMaster userMaster = null;
			try {
				userMaster = webService.checkUserAdmin(userRoll, adrid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (userMaster != null) {
				usersList = webService.getUsersByAdrid(adrid);
				js.put("code", "1000");
				js.put("user", "admin login");
				js.put("usersList", ow.writeValueAsString(usersList));
			} else {
				js.put("code", "1001");
				js.put("error", "InvalidUser");
			}

			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/designationList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> designationList(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			List<Designation> designationsList = webService.getAllDesignation();
			if (designationsList == null || designationsList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Designation Not Found");
				json = js.toString();
			} else {
				js.put("designationsList", ow.writeValueAsString(designationsList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/roleList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> roleList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ROLEFLAG") String roleflag) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			List<RoleModel> roleList = webService.getByRoleFlag(roleflag);
			if (roleList == null || roleList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Role Not Found");
				json = js.toString();
			} else {
				js.put("roleList", ow.writeValueAsString(roleList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/userAssignedRoles", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> userAssignedRoles(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("URID") String userId) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			UserMaster userMasterObject = new UserMaster();
			userMasterObject.setId(Long.parseLong(userId));
			int statusId = 1;
			Status status = webService.getstatusById(statusId);
			List<UserRolesAssignment> userRolesAssignmentList = webService.getUserRolesAssignment(userMasterObject,
					status);
			if (userRolesAssignmentList == null || userRolesAssignmentList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "userRolesAssignmentList is empty");
				json = js.toString();
			} else {
				js.put("userRolesAssignmentList", ow.writeValueAsString(userRolesAssignmentList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/CreateUser", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> CreateUser(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ADRID") String adrid, @RequestParam("PSWRD") String pswrd,
			@RequestParam("BEGDA") String begda, @RequestParam("ENDDA") String endda,
			@RequestParam("SUNAME") String sunam, @RequestParam("ENAME") String ename,
			@RequestParam("EMAIL") String email, @RequestParam("MOBILE") String pmble,
			@RequestParam("BPTYPE") String bptype, @RequestParam("CRNAM") String crnam,
			@RequestParam("CRDAT") String crdat, @RequestParam("CRTIM") String crtim,
			@RequestParam("CDNAM") String cdnam, @RequestParam("CDDAT") String cddat,
			@RequestParam("CDTIM") String cdtim, @RequestParam("ROLE") String role, @RequestParam("ALEVL") String alevl,
			@RequestParam("DEGFLG") String deg, @RequestParam("ADFLG") String adflg,
			@RequestParam("BPVALE") String bpvale, @RequestParam("DEPT") String dept,
			@RequestParam("ID") String userTableId, @RequestParam("SISID") String sesId,
			@RequestParam("STATUS") String actstatus, @RequestParam("SAPID") String sapId) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		Time currentTime = new Time(utilDate.getTime());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 3);
		java.util.Date dt = cal.getTime();
		java.sql.Date endDate = new java.sql.Date(dt.getTime());

		/*
		 * if(sampleRepo.findByadrid(adrid) != null) // && (userTableId!= null) { try {
		 * //return new ResponseEntity<>(js.put("code", "1001").put("error",
		 * "ADRID already exists.").toString(),HttpStatus.OK); js.put("code", "1002");
		 * js.put("error", "ADRID already exists."); json = js.toString(); return new
		 * ResponseEntity<String>(json, HttpStatus.OK); } catch (JSONException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } }
		 */

		System.out.println("------ID--" + userTableId);
		int blockstatus = Integer.parseInt(actstatus);
		UserMaster userMaster = new UserMaster();
		try {
			if (adrid.isEmpty() || adrid == "") {
				js.put("code", "1002");
				js.put("error", "ADRID is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (!userTableId.isEmpty() || userTableId != "") {
				System.out.println("Edit user userTable id = " + userTableId);
				userMaster.setId(Long.parseLong(userTableId));
			}

			if (pswrd.isEmpty() || pswrd == "") {
				js.put("code", "1002");
				js.put("error", "PSWRD Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (email.isEmpty() || email == "") {
				js.put("code", "1002");
				js.put("error", "EMAIL Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (pmble.isEmpty() || pmble == "") {
				js.put("code", "1002");
				js.put("error", "MOBILE Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (role.isEmpty() || role == "") {

				js.put("code", "1002");
				js.put("error", "ROLE Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (deg.isEmpty() || deg == "") {

				js.put("code", "1002");
				js.put("error", "DEG Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String ardid = null;
		UserMaster approverTo = null;
		try {
			long sesid = Long.parseLong(sesId);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();
				}
			}
			if (ardid == null) {
				js.put("error", "Session Expired");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			approverTo = webService.getUserDetails(ardid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int statusId3 = 1;
		int approverCheckFlag = 0;
		Status status3 = webService.getstatusById(statusId3);
		ApproverMaster approverMaster = webService.getApprover(adrid, status3);
		if (approverMaster != null)
			if (approverMaster.getStatus().getStatusId() == 1)
				approverCheckFlag = 1;

		ApproverMaster approverMasterObject = null;
		String[] rolesPartApprover = role.split(",");
		for (String id : rolesPartApprover) {
			int userId = Integer.parseInt(id);
			if (userId == 3) {
				if (approverCheckFlag == 1) {
					userMaster.setApproverMaster(approverMaster);
					System.out.println("Apprver Flg2");
					// Approver Record is available, don't update
					approverCheckFlag = 2;
				} else {
					// Approver Record is not available, create.
					try {
						approverMaster = new ApproverMaster();
						approverMaster.setBegda(today);
						approverMaster.setEndda(endDate);
						approverMaster.setCddat(today);
						approverMaster.setCdtim(currentTime);
						approverMaster.setCrdat(today);
						approverMaster.setCrtim(currentTime);
						approverMaster.setAdrid(adrid);
						approverMaster.setAlevl(alevl);
						approverMaster.setStatus(status3);
						approverMasterObject = webService.saveApprover(approverMaster);
						// whenever its create approver, assign object to usermaster
						userMaster.setApproverMaster(approverMasterObject);
						approverCheckFlag = 2;

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (approverCheckFlag == 1) {
			try {
				statusId3 = 2;
				status3 = webService.getstatusById(statusId3);
				approverMaster.setBegda(today);
				approverMaster.setEndda(endDate);
				approverMaster.setCddat(today);
				approverMaster.setCdtim(currentTime);
				approverMaster.setCrdat(today);
				approverMaster.setCrtim(currentTime);
				approverMaster.setAdrid(adrid);
				approverMaster.setAlevl(alevl);
				approverMaster.setStatus(status3);
				approverMasterObject = webService.saveApprover(approverMaster);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// UserMaster userMaster = new UserMaster();
		UserMaster userMasterObject = null;
		try {
			userMaster.setAdrid(adrid);
			userMaster.setBegda(today);
			userMaster.setBptype(bptype);
			userMaster.setCddat(today);
			userMaster.setCdtim(currentTime);
			userMaster.setCrdat(today);
			userMaster.setEmail(email);
			userMaster.setEndda(endDate);
			userMaster.setPmble(pmble);
			userMaster.setPswrd(pswrd);
			userMaster.setEunam(ename);
			userMaster.setSunam(sunam);
			userMaster.setSapId(sapId);
			userMaster.setInactive(0);
			Status status = new Status();
			// status.setStatusId(1);
			status.setStatusId(blockstatus);
			userMaster.setStatus(status);

			RoleModel roleObjectDepartment = new RoleModel();
			int id = Integer.parseInt(dept);
			RoleModel roleModle = webService.getRoleByRoleId(id);
			userMaster.setUserRoll(roleModle);
			userMaster.setUserDesignation(deg);
			userMaster.setBpvale(bpvale);
			userMaster.setAdflag(adflg);
			userMaster.setCreatedBy(approverTo);
			userMasterObject = webService.createUser(userMaster);
			int statusId2 = 1;
			Status status1 = webService.getstatusById(statusId2);
			List<UserRolesAssignment> userRoleAssignedList = webService.getAssignedRolls(userMasterObject, status1);
			System.out.println("userRoleAssignedList:" + userRoleAssignedList.size());
			for (UserRolesAssignment userRolesAssignment : userRoleAssignedList) {
				System.out.println("123");
				try {
					int statusId = 2;
					Status statusObject = webService.getstatusById(statusId);
					userRolesAssignment.setStatus(statusObject);
					UserRolesAssignment userRolesObject1 = webService.saveUserAssignment(userRolesAssignment);
				} catch (Exception e) {
					System.out.println("Exception: Assigned Roles not Deleted");
					e.printStackTrace();
				}
			}

			System.out.println("Role List :" + role);
			String[] rolesPart = role.split(",");
			for (String roleid : rolesPart) {
				try {
					UserRolesAssignment userRolesAssignment = new UserRolesAssignment();
					System.out.println("1");
					RoleModel roleObject1 = new RoleModel();
					System.out.println("2");
					roleObject1.setId(Integer.parseInt(roleid));
					System.out.println("3");
					userRolesAssignment.setUserMasterObject(userMasterObject);
					System.out.println("4");
					int statusId = 1;
					Status statusObject = new Status();
					System.out.println("5");
					statusObject.setStatusId(1);
					// Status statusObject = sampleService.getstatusById(statusId);
					System.out.println("6");
					userRolesAssignment.setDepartment(roleObject1);
					System.out.println("7");
					userRolesAssignment.setStatus(statusObject);
					System.out.println("8");
					UserRolesAssignment userRolesObject1 = webService.saveUserAssignment(userRolesAssignment);
					System.out.println("Saved Successfully");
				} catch (Exception e) {
					System.out.println("Exception---------:" + e);
					e.printStackTrace();
				}

				System.out.println("Saved");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (userMasterObject == null) {
				js.put("code", "1001");
				js.put("error", "User Not Saved");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} else {
				js.put("User", "User Saved Successfully");
				js.put("userMasterObject", ow.writeValueAsString(userMasterObject));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}

		try {

		} catch (Exception e) {

		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/orgMasterAndUserMapping", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> orgMasterAndUserMapping(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ADRID") String activeDirectoryId, @RequestParam("BUKRS") String companyCode,
			@RequestParam("VKORG") String salesOrganisation, @RequestParam("EKORG") String purchaseOrganisation,
			@RequestParam("DOCTYP") String documentType, @RequestParam("PLANT") String plant,
			@RequestParam("KUNNR") String custormeCode, @RequestParam("CRNAM") String createdBy,
			@RequestParam("CRDAT") String createdDate, @RequestParam("CRTIM") String createdTime,
			@RequestParam("CDNAM") String changedBy, @RequestParam("CDDAT") String changedDateOn,
			@RequestParam("CDTIM") String changedTime, @RequestParam("ROLE") String role) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		Time currentTime = new Time(utilDate.getTime());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 3);
		java.util.Date dt = cal.getTime();
		java.sql.Date endDate = new java.sql.Date(dt.getTime());

		try {
			if (activeDirectoryId.isEmpty() || activeDirectoryId == "") {
				js.put("code", "1002");
				js.put("error", "ADRID is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (companyCode.isEmpty() || companyCode == "") {
				js.put("code", "1002");
				js.put("error", "BUKRS Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (salesOrganisation.isEmpty() || salesOrganisation == "") {
				js.put("code", "1002");
				js.put("error", "VKORG Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (purchaseOrganisation.isEmpty() || purchaseOrganisation == "") {
				js.put("code", "1002");
				js.put("error", "EKORG Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (documentType.isEmpty() || documentType == "") {

				js.put("code", "1002");
				js.put("error", "DOCTYP Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (plant.isEmpty() || plant == "") {

				js.put("code", "1002");
				js.put("error", "PLANT Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (custormeCode.isEmpty() || custormeCode == "") {

				js.put("code", "1002");
				js.put("error", "KUNNR Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		OrgMasterAndUserMapping orgMasterAndUserMapping = new OrgMasterAndUserMapping();
		UserMaster userMasterObject = null;
		try {
			orgMasterAndUserMapping.setArdid(activeDirectoryId);
			orgMasterAndUserMapping.setDctyp(documentType);
			orgMasterAndUserMapping.setCddat(today);
			orgMasterAndUserMapping.setCdtim(currentTime);
			orgMasterAndUserMapping.setCrdat(today);
			orgMasterAndUserMapping.setBukrs(companyCode);
			orgMasterAndUserMapping.setEkorg(purchaseOrganisation);
			orgMasterAndUserMapping.setVkorg(salesOrganisation);
			orgMasterAndUserMapping.setKunnr(custormeCode);
			orgMasterAndUserMapping.setPlant(plant);
			com.iteanz.srl.domain.Status status = new com.iteanz.srl.domain.Status();
			status.setStatusId(1);
			orgMasterAndUserMapping.setStatus(status);
			userMasterObject = webService.saveMasterAndUserMapping(orgMasterAndUserMapping);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (userMasterObject == null) {
				js.put("code", "1001");
				js.put("error", "User Not Saved");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} else {
				js.put("User", "User Saved Successfully");
				js.put("userMasterObject", ow.writeValueAsString(userMasterObject));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);

	}

	@RequestMapping(value = "/getUesr", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> getUesr(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "ADRID") String adrid) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;

		if (adrid.isEmpty()) {
			try {
				js.put("code", "1002");
				js.put("error", "ADRID Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			UserMaster userMasterObject = webService.getUserDetails(adrid);
			if (userMasterObject == null) {
				js.put("code", "1001");
				js.put("error", "User Not Found");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} else {
				js.put("User", "Success");
				js.put("userMasterObject", ow.writeValueAsString(userMasterObject));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	// @SuppressWarnings("unused")

	@RequestMapping(value = "/approvers", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> approvers(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("SESID") String sesId) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			// RoleModel userRoll = new RoleModel();
			// userRoll.setId(3);
			int statusId3 = 1;
			Status status = webService.getstatusById(statusId3);
			List<UserMaster> userMasters = new ArrayList<>();
			List<ApproverMaster> approverList = webService.getAllApprovers(status);
			for (ApproverMaster approverMaster : approverList) {
				UserMaster user = webService.getUserByApproverMaster(approverMaster);
				userMasters.add(user);
			}
			// List<UserMaster> userMasters = sampleService.getAllUserMasterApprovers();
			if (userMasters == null) {
				js.put("code", "1001");
				js.put("error", "Header Not Found");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} else {
				js.put("Header", "Success");
				js.put("approvers", ow.writeValueAsString(userMasters));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}


	
	@RequestMapping(value = "/vendorQuery", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> vendorQuery(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("RFPNO") String rfpno, @RequestParam("BODY") String body,
			@RequestParam("APPID") String appId, @RequestParam("SESID") String sesId,
			@RequestParam("VPNO") String vpno) throws JSONException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		String json = null;
		String fromEmail = null;
		String toemailid = null;
		String loggedinUseremailid = null;
		String rfpCreatoremailid = null;
		String vpCreatoremailid = null;
		String subject = null;
		String loggedInUserName = null;
		String loggedInAdrid = null;
		Long rfpno1 =0L;
		Long vpno1 = 0L;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		if (!rfpno.isEmpty())
		{
		rfpno1 = Long.parseLong(rfpno);
		RFPHeader rfphead = rfpHeaderRepository.findOne(rfpno1);
		String cradrid = rfphead.getCreatedby();
		UserMaster usermaster = sampleRepo.findByadrid(cradrid);
		rfpCreatoremailid = usermaster.getEmail();
		}
		if(!vpno.isEmpty())
		{	
		vpno1 = Long.parseLong(vpno);
		VPHeader vphead = vpheaderrepo.findOne(vpno1);
		String vpcradrid = vphead.getCreatedby();
		UserMaster vpusermaster = sampleRepo.findByadrid(vpcradrid);
		vpCreatoremailid = vpusermaster.getEmail();
		}
		
		try {
			if (sesId.isEmpty() || sesId == "") {
				js.put("code", "1002");
				js.put("error", "sesId is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		UserMaster createdBy = null;
		String ardid = null;
		try {
			long sesid = Long.parseLong(sesId);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();
				}
			}
			if (ardid == null) {
				js.put("error", "Session Expired");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			loggedinUseremailid = createdBy.getEmail();
			loggedInUserName = createdBy.getEunam();
			loggedInAdrid = createdBy.getAdrid();
			System.out.println("CREATEDBY:" + createdBy.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		if(appId == "RFP")
			{
			subject = "RFP response against RFPNO:"+ rfpno +" from "+loggedInUserName;
			fromEmail = loggedinUseremailid;
			toemailid = vpCreatoremailid;
			}
			else if(appId == "VENREG")
			{
				subject = "Vendor registration against RFPNO:"+ rfpno +" is initiated from "+loggedInUserName;
				fromEmail = loggedinUseremailid;
				toemailid = vpCreatoremailid;
			}
		else
		{
			subject = "Vendor query against RFPNO:"+ rfpno +" from "+loggedInUserName;
			fromEmail = loggedinUseremailid;
			toemailid = rfpCreatoremailid;
		}
	
		RFPQuery rfpquery = new RFPQuery();
		rfpquery.setRfpno(rfpno1);
		rfpquery.setMailbody(body);
		rfpquery.setCreatedBy(loggedInAdrid);
		rfpquery.setCreatedDate(today.toString());
		rfpquery.setProposalno(vpno1);
		rfpQueryRepo.save(rfpquery);
		
		
		vendorQuery(fromEmail, toemailid, body, subject);
		
		js.put("code", "1000");
		js.put("error", "Email send successfully");
		json = js.toString();
		
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	
	}
	

	public boolean vendorQuery(String fromEmail, String toemailid, String body, String subject) {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "webmail.srl.in");
			props.put("mail.smtp.port", "587");
			props.put("mail.debug", "true");
			props.put("mail.smtp.starttls.enable", "false");
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("srl-intelligence@srl.in", "Inteli#007");
				}
			});
			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("srl-intelligence@srl.in"));
				//msg.setFrom(new InternetAddress(fromEmail));
				msg.setRecipients(Message.RecipientType.TO, toemailid);
				msg.setSubject(subject);
				// msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail));

				System.out.println("****");
				Multipart multipart = new MimeMultipart();

				MimeBodyPart htmlPart = new MimeBodyPart();

				String htmlContent = "<html><body><font color=black>"+ body
						+ "<br> "
						+ "<br>"
						+ "<br>"
						+"Email sent from :"+fromEmail
						+ "</font></body></html>";
				htmlPart.setContent(htmlContent, "text/html");
				multipart.addBodyPart(htmlPart);
				msg.setContent(multipart);
				Transport.send(msg);
				System.out.println("---yep mails send---");
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		} finally {
			System.out.println("runnnnnning *********");
		}
	}
	
	
	
	
	
	@RequestMapping(value = "/sendMailVendor", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> senMail(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("MAILID") String mailId, @RequestParam("REQID") String reqid) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		String json = null;
		String otp = null;
		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "webmail.srl.in");
			props.put("mail.smtp.port", "587");
			props.put("mail.debug", "true");
			props.put("mail.smtp.ssl", "false");
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("srl-intelligence@srl.in", "Inteli#007");
				}
			});

			try {
				MimeMessage msg = new MimeMessage(session);
				System.out.println("*");
				msg.setFrom(new InternetAddress("srl-intelligence@srl.in"));
				System.out.println("**");
				msg.setRecipients(Message.RecipientType.TO, "sonuskings2015@gmail.com");
				System.out.println("***");
				msg.setSubject(
						"Digital Signature Req.ID:\"+requesId+\" \" + \"[\" + \" \" + uploader + \" \" + \"]For Approval");
				System.out.println("****");
				msg.setSubject("Digital Signature Req.ID:10020 [ Admin ]For Approval");
				System.out.println("*****");
				msg.setSentDate(new Date());
				System.out.println("1");
				Multipart multipart = new MimeMultipart();

				MimeBodyPart htmlPart = new MimeBodyPart();

				System.out.println("2");
				String htmlContent = "<html><body><font color=black>Hello,</b>" + "<br>"
						+ "<br>+fileName has been sent for your approval, created by +uploader +senderMailId" + "<br>"
						+ "<br><b>You are requested to review the attached document and proceed on Approval process for Digital Signature on document.</b>"
						+ "<br>"
						+ "<br><a href='mailLink'>Approve :</a></b>  The document will be approved and sent to next level approvers if any further level. If this final level, document will be Digitally Signed and notified to concern person."
						+ "<br>"
						+ "<br><a href='mailLink'>Reject :</a></b>  Please note, this will reject the document and send it back to the creator. Approvals which have already happened will be reset. Post correction the document will be resent to all approvers."
						+ "<br>"
						+ "<br><a href='mailLink'>Forward :</a></b>  The document will be forwarded to next level approvers if any further level. If this final level, document will be Digitally Signed and notified to concern person."
						+ "<br>" + "</font></body></html>";
				// <font font-weight=normal face=Tahoma>
				htmlPart.setContent(htmlContent, "text/html");
				System.out.println("3");
				multipart.addBodyPart(htmlPart);
				System.out.println("5");

				/*
				 * MimeBodyPart attachementPart = new MimeBodyPart();
				 * attachementPart.attachFile(new File("D:/apple.jpg"));
				 * multipart.addBodyPart(attachementPart);
				 */
				msg.setContent(multipart);
				System.out.println("---55-");
				Transport.send(msg);
				System.out.println("---Done---");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			js.put("code", "1001");
			js.put("error", "approverMaster Not Saved");
			json = js.toString();
			return new ResponseEntity<String>(json, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveUserADIntegration", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> saveUserADIntegration(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ADRID") String adrid, @RequestParam("PSWRD") String pswrd,
			@RequestParam("BEGDA") String begda, @RequestParam("ENDDA") String endda,
			@RequestParam("ADINTEG") String adintge, @RequestParam("CRNAM") String crnam,
			@RequestParam("CRDAT") String crdat, @RequestParam("CRTIM") String crtim,
			@RequestParam("CDNAM") String cdnam, @RequestParam("CDDAT") String cddat,
			@RequestParam("CDTIM") String cdtim) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (adrid.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "ADRID is empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (pswrd.isEmpty()) {
				js.put("code", "1002");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserADIntegration userADIntegrationObject = null;

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		Time currentTime = new Time(utilDate.getTime());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 3);
		java.util.Date dt = cal.getTime();
		java.sql.Date endDate = new java.sql.Date(dt.getTime());
		try {
			UserADIntegration userADIntegration = new UserADIntegration();
			userADIntegration.setAdinteg("X");
			userADIntegration.setAdrid(adrid);
			userADIntegration.setCddat(today);
			userADIntegration.setCrnam(crnam);
			userADIntegration.setCrtim(currentTime);
			userADIntegration.setEndda(endDate);
			userADIntegration.setGegda(today);
			userADIntegration.setPswrd(pswrd);
			userADIntegrationObject = webService.saveADIntegration(userADIntegration);
			if (userADIntegrationObject == null) {
				js.put("code", "1001");
				js.put("ADIntegration", "AD Integration Not Saved");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} else {
				js.put("ADIntegration", "AD Integration Saved Successfully");
				js.put("userADIntegrationObject", ow.writeValueAsString(userADIntegrationObject));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveApprover", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> saveApprover(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("APPR") String appr) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			java.util.Date utilDate = new java.util.Date();
			java.sql.Date today = new java.sql.Date(utilDate.getTime());
			Time currentTime = new Time(utilDate.getTime());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 3);
			java.util.Date dt = cal.getTime();
			java.sql.Date endDate = new java.sql.Date(dt.getTime());
			ApproverMaster approverMasterObject = null;
			try {
				ApproverMaster approverMaster = new ApproverMaster();
				approverMaster.setBegda(today);
				approverMaster.setEndda(endDate);
				approverMaster.setCddat(today);
				approverMaster.setCdtim(currentTime);
				approverMaster.setCrdat(today);
				approverMaster.setCrtim(currentTime);
				approverMaster.setAdrid("2005");
				approverMaster.setAlevl("2");
				approverMaster.setBukrs("01");
				approverMasterObject = webService.saveApprover(approverMaster);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (approverMasterObject == null) {
				js.put("code", "1001");
				js.put("error", "approverMaster Not Saved");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} else {
				js.put("Status", "approverMaster Saved Successfully");
				js.put("approverMasterObject", ow.writeValueAsString(approverMasterObject));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/savePasswordPolicy", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> savePasswordPolicy(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("LENGTHLOW") String lengthlow, @RequestParam("LENGTHHIGH") String lengthhigh,
			@RequestParam("BEGDA") String begda, @RequestParam("ENDDA") String endda,
			@RequestParam("SPLCHARCK") String splchrck, @RequestParam("LOWERCASECK") String lowercaseck,
			@RequestParam("UPPERCASECK") String uppercaseck, @RequestParam("CNTPWDHIST") String cntpwdhist,
			@RequestParam("CRNAM") String crnam, @RequestParam("PWDHT") String pwdth,
			@RequestParam("CRDAT") String crdat, @RequestParam("CRTIM") String crtim,
			@RequestParam("CDNAM") String cdnam, @RequestParam("CDDAT") String cddat,
			@RequestParam("CDTIM") String cdtim) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			PasswordPolicy passwordPolicyObject = null;

			java.util.Date utilDate = new java.util.Date();
			java.sql.Date today = new java.sql.Date(utilDate.getTime());
			Time currentTime = new Time(utilDate.getTime());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 3);
			java.util.Date dt = cal.getTime();
			java.sql.Date endDate = new java.sql.Date(dt.getTime());
			if (lengthlow.isEmpty() || lengthlow == "") {
				js.put("code", "1001");
				js.put("error", "LENGTHLOW Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (lengthhigh.isEmpty() || lengthhigh == "") {
				js.put("code", "1001");
				js.put("error", "LENGTHHIGH Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (pwdth.isEmpty() || endda == "") {
				js.put("code", "1001");
				js.put("error", "PWDTH Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			try {
				PasswordPolicy passwordPolicy = new PasswordPolicy();
				passwordPolicy.setBegda(today);
				passwordPolicy.setEndda(endDate);
				passwordPolicy.setCddat(today);
				passwordPolicy.setCrtim(currentTime);
				passwordPolicy.setLghigh(Integer.parseInt(lengthhigh));
				passwordPolicy.setLglow(Integer.parseInt(lengthlow));
				passwordPolicy.setLwck(lowercaseck);
				passwordPolicy.setPwdht(Integer.parseInt(pwdth));
				passwordPolicy.setUpchk(uppercaseck);
				passwordPolicyObject = webService.createPasswordPolicy(passwordPolicy);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (passwordPolicyObject == null) {
				js.put("code", "1001");
				js.put("error", "Password Policy Not Saved");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} else {
				js.put("PasswordPolicy", "Password Policy Saved Successfully");
				js.put("passwordPolicyObject", ow.writeValueAsString(passwordPolicyObject));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	public String toJson(Object data) {
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder builder = new StringBuilder();
		try {
			builder.append(mapper.writeValueAsString(data));
		} catch (Exception e) {

			e.printStackTrace();
		}
		return builder.toString();
	}

	@RequestMapping(value = "/updatesample", method = RequestMethod.POST)
	public SampleEntity updateSample(@Valid SampleEntity sampleEntity) {
		System.out.println("id=" + 1);
		Optional<SampleEntity> searchEntity = sampleRepo.findById(1);
		if (searchEntity == null) {
			System.out.println("Null Value");
		} else {
			System.out.println("Not Null");
		}

		sampleEntity.setId(1);
		sampleEntity.setAge(30);
		sampleEntity.setName("Avinash Prachand Update");

		return webService.updateSample(1, sampleEntity);
	}

	@RequestMapping(value = "/sample")
	public List<SampleEntity> sample() {
		return webService.getAll();
	}

	//

	
	
	
	
	
	
	
	
	
	
	
	@CrossOrigin("*")
	@RequestMapping(value = "/prList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> prList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("SESID") String sesId) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		String aprv = null;
		String id = null;
		try {
			if (sesId.isEmpty() || sesId == "") {
				js.put("code", "1002");
				js.put("error", "sesId is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		UserMaster createdBy = null;
		String ardid = null;
		try {
			long sesid = Long.parseLong(sesId);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();
				}
			}
			if (ardid == null) {
				js.put("error", "Session Expired");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		long reqno1 = 0l;
		try {
			System.out.println("APPROVER:" + createdBy.getId());
			id = Long.toString(createdBy.getId());
			aprv = createdBy.getAdrid();
			if (aprv == "" || aprv.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "USER ID is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println("APRVVVVVVVVVV:" + createdBy.getId());
			int statusint = 3;
//			List<PurchaseRequisition> prList = webService.getPurchaseRequisitionByAprv(aprv);
			List<ApprovalLog> prList1 = approvalLogRepo.getApprovalLogByAprvAndMailstatus(aprv,1);
			List<ApprovalLog> prListDisplay = new ArrayList<ApprovalLog>();

			if (prList1 == null || prList1.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "PR List Not Found");
				json = js.toString();
			} else {

				for (ApprovalLog prList : prList1) {

					if (reqno1 != prList.getReqNo()) {

						prListDisplay.add(prList);

						reqno1 = prList.getReqNo();
					}

				}

				js.put("prList", ow.writeValueAsString(prListDisplay));
				js.put("code", "1000");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);

			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/downloadPO", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> downloadPO(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("PONO") String pono) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		// ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String res = "";

		FileFormFTP fileFormFTP = new FileFormFTP();

		try {

			js.put("code", "1000");
			js.put("RESULT", fileFormFTP.fileData(pono));// geth the file);
			res = js.toString();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<String>(res, HttpStatus.OK);

	}

	@CrossOrigin("*")
	@RequestMapping(value = "/pendingPRList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> pendingPRList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("SESID") String sesId, @RequestParam("STATUS") String status,
			@RequestParam("REQNO") String reqno) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		boolean count = true;

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		String userid = null;
		UserMaster createdBy = null;
		String ardid = null;
		String id = null;
		try {
			long sesid = Long.parseLong(sesId);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();
				}
			}
			if (ardid == null) {
				js.put("error", "Session Expired");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (status == "" || status.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "STATUS is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} else if (reqno == "" || reqno.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "REQNO is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} else if (sesId == "" || sesId.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "SESSIONID is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			id = Long.toString(createdBy.getId());
			userid = createdBy.getAdrid();
			System.out.println("LOGGED IN APPROVER:" + userid);
			List<PurchaseRequisition> purchaseRequisitionList = new ArrayList<PurchaseRequisition>();

			// int statusint = 3;
			Status status2 = statusRepo.findOne(Integer.parseInt(status));
			if (status2 == null) {

				js.put("code", "1001");
				js.put("error", "Status Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			// approval log table
			List<ApprovalLog> pendingPRList = webService.getListApprovalLogByReqnoAprvStatus(Long.parseLong(reqno),
					userid, status2);
			if (pendingPRList == null || pendingPRList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "pending Approver List Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);

			}
			for (ApprovalLog approvalLog : pendingPRList) {

				// approvalLog.getReqNo();
				// PurchaseRequisition
				List<PurchaseRequisition> purchaseRequisition = webService
						.getPurchaseRequisitionByReqid(approvalLog.getReqNo());

				for (PurchaseRequisition purchaseRequisition2 : purchaseRequisition) {

					if (count) {

						if (purchaseRequisition2 != null) {
							System.out.println(purchaseRequisition.toString());
							purchaseRequisitionList.addAll(purchaseRequisition);

							count = false;

						} else {
							js.put("code", "1001");
							js.put("error", "pending Approver List Is Empty");
							json = js.toString();

							return new ResponseEntity<String>(json, HttpStatus.OK);

						}
					}
				}
			}

			if (pendingPRList == null || pendingPRList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "pending Approver List Is Empty");
				json = js.toString();
			} else {
				js.put("pendingAprvList", ow.writeValueAsString(purchaseRequisitionList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/prStatus", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> prStatus(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "PRNO") String prno, @RequestParam(value = "ITEMNO") String itemno,
			@RequestParam("SESID") String sesId, @RequestParam(value = "STATUS") int status) throws JSONException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		String aprv = null;
		UserMaster createdBy = null;
		String ardid = null;
		String id = null;
		try {
			long sesid = Long.parseLong(sesId);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();
				}
			}
			if (ardid == null) {
				js.put("error", "Session Expired");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (prno == "" || prno.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "PR NO is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (itemno == "" || itemno.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "ITEM NO is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (sesId == "" || sesId.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "SESSIONID is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			id = Long.toString(createdBy.getId());
			aprv = createdBy.getAdrid();
			System.out.println("LOGGED IN APPROVER: " + aprv);
			PurchaseRequisition prreq = new PurchaseRequisition();
			long prnum;
			prnum = Long.parseLong(prno);
			prreq = sampleRepo.gePRStatusByPrnoItemno(prnum, itemno, aprv);
			prreq.setStatus(status);
			sampleRepo.save(prreq);
			js.put("code", "1000");
			js.put("success", "PR Status updated successfully");
			json = js.toString();

		} catch (Exception e) {

			js.put("code", "1001");
			js.put("error", "PR Status not updated successfully");
			json = js.toString();
			e.printStackTrace();

		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/prStatusOld", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> prStatusOld(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "PRNO") String prno, @RequestParam(value = "ITEMNO") String itemno,
			@RequestParam(value = "USERID") String aprv, @RequestParam(value = "STATUS") int status,
			@RequestParam(value = "APPID") String appid, @RequestParam(value = "REMARK") int remark)
			throws JSONException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;

		try {
			if (prno == "" || prno.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "PR NO is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (itemno == "" || itemno.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "ITEM NO is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (aprv == "" || aprv.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "USER ID is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			PurchaseRequisition prreq = new PurchaseRequisition();
			long prnum;
			prnum = Long.parseLong(prno);
			prreq = sampleRepo.gePRStatusByPrnoItemno(prnum, itemno, aprv);
			prreq.setStatus(status);
			sampleRepo.save(prreq);
			js.put("code", "1000");
			js.put("success", "PR Status updated successfully");
			json = js.toString();

		} catch (Exception e) {

			js.put("code", "1001");
			js.put("error", "PR Status not updated successfully");
			json = js.toString();
			e.printStackTrace();

		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/createApprovalMatrix", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> createApprovalMatrix(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("PLANT") String plant, @RequestParam("MATSPOC") String matspoc,
			@RequestParam("LABHEAD") String labhead, @RequestParam("BUHEAD") String buhead,
			@RequestParam("TECHHEAD") String techhead, @RequestParam("PROCHEAD") String prochead,
			@RequestParam("ACTSTATUS") String actstatus, @RequestParam("ID") String approvalid,
			@RequestParam("SESID") String sesId) throws JSONException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		/*
		 * Time currentTime = new Time(utilDate.getTime()); Calendar cal =
		 * Calendar.getInstance(); cal.add(Calendar.MONTH, 3); java.util.Date dt =
		 * cal.getTime(); java.sql.Date endDate = new java.sql.Date(dt.getTime());
		 */
		// System.out.println("------ID--"+userTableId);
		// int actdeactstatus = Integer.parseInt(actstatus);
		ApprovalMatrix aprvMatrix = new ApprovalMatrix();
		UserMaster createdBy = null;
		UserMaster changedBy = null;
		String ardid = null;
		String crby = null;
		String cdby = null;

		try {
			if (plant.isEmpty() || plant == "") {
				js.put("code", "1002");
				js.put("error", "PLANT is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			/*
			 * if (!userTableId.isEmpty() || userTableId != "") {
			 * System.out.println("Edit user userTable id = "+userTableId);
			 * userMaster.setId(Long.parseLong(userTableId)); }
			 */
			if (matspoc.isEmpty() || matspoc == "") {
				js.put("code", "1002");
				js.put("error", "MATERIAL SPOC Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (labhead.isEmpty() || labhead == "") {
				js.put("code", "1002");
				js.put("error", "LAB HEAD Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (buhead.isEmpty() || buhead == "") {
				js.put("code", "1002");
				js.put("error", "BU HEAD Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (techhead.isEmpty() || techhead == "") {

				js.put("code", "1002");
				js.put("error", "TECH HEAD Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (prochead.isEmpty() || prochead == "") {

				js.put("code", "1002");
				js.put("error", "PROCUREMENT HEAD Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ApprovalMatrix aprvmatrixObject = null;
		Status statusObject = webService.getStatusById(Integer.parseInt(actstatus));

		if (approvalid == null || approvalid.isEmpty() || approvalid == "") {
			try {
				try {
					long sesid = Long.parseLong(sesId);
					List<SessionsTable> sessionsTableList = webService.getAllSessions();
					for (SessionsTable sessionsTable2 : sessionsTableList) {
						if (sessionsTable2.getSesid() == sesid) {
							ardid = sessionsTable2.getAdrid();
						}
					}
					if (ardid == null) {
						js.put("error", "Session Expired");
						json = js.toString();
						return new ResponseEntity<String>(json, HttpStatus.OK);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				UserMaster approverTo = null;
				try {
					createdBy = webService.getUserDetails(ardid);
					System.out.println("CREATEDBY:" + createdBy.getId());
					crby = Long.toString(createdBy.getId());
					System.out.println("CREATEDDATE:" + today.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}

				aprvMatrix.setPlant(plant);
				aprvMatrix.setMatspoc(matspoc);
				aprvMatrix.setLabhead(labhead);
				aprvMatrix.setBuhead(buhead);
				aprvMatrix.setTechhead(techhead);
				aprvMatrix.setProchead(prochead);
				// status.setStatusId(1);
				// status.setStatusId(Integer.parseInt(actstatus));
				aprvMatrix.setStatus(statusObject);
				aprvMatrix.setCrdat(today);
				// aprvMatrix.setCddat(today);
				aprvMatrix.setCreatedBy(crby);
				// aprvMatrix.setChangeddBy(cdby);
				aprvmatrixObject = webService.createApproval(aprvMatrix);

				try {
					if (aprvmatrixObject == null) {
						js.put("code", "1001");
						js.put("error", "Approval Not Saved");
						json = js.toString();
						return new ResponseEntity<String>(json, HttpStatus.OK);
					} else {
						js.put("User", "Approval Saved Successfully");
						js.put("ApprovalMatrixObject", ow.writeValueAsString(aprvmatrixObject));
						js.put("code", "1000");
						json = js.toString();
					}
					try {
						json = js.toString();
					} catch (Exception e) {
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e);
				}

			}

			catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {

				try {
					long sesid = Long.parseLong(sesId);
					List<SessionsTable> sessionsTableList = webService.getAllSessions();
					for (SessionsTable sessionsTable2 : sessionsTableList) {
						if (sessionsTable2.getSesid() == sesid) {
							ardid = sessionsTable2.getAdrid();
						}
					}
					if (ardid == null) {
						js.put("error", "Session Expired");
						json = js.toString();
						return new ResponseEntity<String>(json, HttpStatus.OK);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				UserMaster approverTo = null;
				try {
					changedBy = webService.getUserDetails(ardid);
					System.out.println("CHANGEDBY:" + changedBy.getId());
					cdby = Long.toString(changedBy.getId());
					System.out.println("CHANGEDDATE:" + today.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				ApprovalMatrix approvalMatrixObject = webService.getApprovalMatrixById(Integer.parseInt(approvalid));
				ApprovalMatrix approverMatrix1 = new ApprovalMatrix();
				approverMatrix1.setId(Long.parseLong(approvalid));
				approverMatrix1.setPlant(plant);
				approverMatrix1.setMatspoc(matspoc);
				approverMatrix1.setLabhead(labhead);
				approverMatrix1.setBuhead(buhead);
				approverMatrix1.setTechhead(techhead);
				approverMatrix1.setProchead(prochead);
				approverMatrix1.setStatus(statusObject);
				// Date date1 = formatter.parse(crdat);
				approverMatrix1.setCrdat(approvalMatrixObject.getCrdat());
				approverMatrix1.setCddat(today);
				approverMatrix1.setCreatedBy(approvalMatrixObject.getCreatedBy());
				approverMatrix1.setChangeddBy(cdby);
				// ApprovalMatrix approvalMatrixObject2
				ApprovalMatrix approvalMatrixObject2 = webService.createApproval(approverMatrix1);
				System.out.println("Approval Matrix is updated");
				js.put("User", "Approval Matrix updated Successfully");
				js.put("ApprovalMatrixObject", ow.writeValueAsString(approverMatrix1));
				js.put("code", "1000");
				json = js.toString();

			} catch (Exception e) {
				js.put("code", "1001");
				js.put("error", "Approval Matrix Not Updated");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/createWorkflow", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> createWorkflow(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("APPID") String appid, @RequestParam("DESCRIPTION") String description,
			@RequestParam("SPROCESS") String subprocess, @RequestParam("APRVLEVEL") String aprvlevel,
			@RequestParam("CONDITION") String condition, @RequestParam("ACTSTATUS") String actstatus,
			@RequestParam("APRV1") String aprv1, @RequestParam("APRV2") String aprv2,
			@RequestParam("APRV3") String aprv3, @RequestParam("APRV4") String aprv4,
			@RequestParam("APRV5") String aprv5, @RequestParam("APRV6") String aprv6,
			@RequestParam("APRV7") String aprv7, @RequestParam("APRV8") String aprv8,
			@RequestParam("APRV9") String aprv9, @RequestParam("APRV10") String aprv10,
			@RequestParam("ID") String workflowid, @RequestParam("SESID") String sesId) throws JSONException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		Time currentTime = new Time(utilDate.getTime());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 3);
		java.util.Date dt = cal.getTime();
		java.sql.Date endDate = new java.sql.Date(dt.getTime());
		// System.out.println("------ID--"+userTableId);
		int actdeactstatus = Integer.parseInt(actstatus);
		WorkflowConfig workflowConfig = new WorkflowConfig();
		Status statusObject = webService.getStatusById(Integer.parseInt(actstatus));

		UserMaster createdBy = null;
		UserMaster changedBy = null;
		String ardid = null;
		String crby = null;
		String cdby = null;
		int aprlevel = Integer.parseInt(aprvlevel);
		try {

			if (appid.isEmpty() || appid == "") {
				js.put("code", "1002");
				js.put("error", "APPID is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			/*
			 * if (!userTableId.isEmpty() || userTableId != "") {
			 * System.out.println("Edit user userTable id = "+userTableId);
			 * userMaster.setId(Long.parseLong(userTableId)); }
			 */
			if (description.isEmpty() || description == "") {
				js.put("code", "1002");
				js.put("error", "MATERIAL SPOC Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (subprocess.isEmpty() || subprocess == "") {
				js.put("code", "1002");
				js.put("error", "SUB PROCESS Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (aprvlevel.isEmpty() || aprvlevel == "") {
				js.put("code", "1002");
				js.put("error", "APPROVAL LEVELS Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (condition.isEmpty() || condition == "") {

				js.put("code", "1002");
				js.put("error", "CONDITION Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (actstatus.isEmpty() || actstatus == "") {

				js.put("code", "1002");
				js.put("error", "STATUS Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		WorkflowConfig workflowConfigObject = null;
		if (workflowid == null || workflowid.isEmpty() || workflowid == "") {
			try {
				try {
					long sesid = Long.parseLong(sesId);
					List<SessionsTable> sessionsTableList = webService.getAllSessions();
					for (SessionsTable sessionsTable2 : sessionsTableList) {
						if (sessionsTable2.getSesid() == sesid) {
							ardid = sessionsTable2.getAdrid();
						}
					}
					if (ardid == null) {
						js.put("error", "Session Expired");
						json = js.toString();
						return new ResponseEntity<String>(json, HttpStatus.OK);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				UserMaster approverTo = null;
				try {
					createdBy = webService.getUserDetails(ardid);
					System.out.println("CREATEDBY:" + createdBy.getId());
					crby = Long.toString(createdBy.getId());
					System.out.println("CREATEDDATE:" + today.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				workflowConfig.setAppid(appid);
				workflowConfig.setDescription(description);
				workflowConfig.setSubProcess(subprocess);
				workflowConfig.setAprvLevels(aprlevel);
				workflowConfig.setCondition(condition);
				workflowConfig.setStatus(statusObject);
				workflowConfig.setAprv1(aprv1);
				workflowConfig.setAprv2(aprv2);
				workflowConfig.setAprv3(aprv3);
				workflowConfig.setAprv4(aprv4);
				workflowConfig.setAprv5(aprv5);
				workflowConfig.setAprv6(aprv6);
				workflowConfig.setAprv7(aprv7);
				workflowConfig.setAprv8(aprv8);
				workflowConfig.setAprv9(aprv9);
				workflowConfig.setAprv10(aprv10);
				workflowConfig.setCreatedBy(crby);
				// workflowConfig.setChangeddBy(cdby);
				workflowConfig.setCreatedDate(today);
				// workflowConfig.setChangedDate(today);
				workflowConfigObject = webService.createWorkflow(workflowConfig);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (workflowConfigObject == null) {
					js.put("code", "1001");
					js.put("error", "Workflow Configuration Not Saved");
					json = js.toString();
					return new ResponseEntity<String>(json, HttpStatus.OK);
				} else {
					js.put("User", "Workflow Configuration Saved Successfully");
					js.put("workflowConfigObject", ow.writeValueAsString(workflowConfigObject));
					js.put("code", "1000");
					json = js.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			try {
				try {
					long sesid = Long.parseLong(sesId);
					List<SessionsTable> sessionsTableList = webService.getAllSessions();
					for (SessionsTable sessionsTable2 : sessionsTableList) {
						if (sessionsTable2.getSesid() == sesid) {
							ardid = sessionsTable2.getAdrid();
						}
					}
					if (ardid == null) {
						js.put("error", "Session Expired");
						json = js.toString();
						return new ResponseEntity<String>(json, HttpStatus.OK);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				UserMaster approverTo = null;
				try {
					changedBy = webService.getUserDetails(ardid);
					System.out.println("CHANGEDBY:" + changedBy.getId());
					cdby = Long.toString(changedBy.getId());
					System.out.println("CHANGEDDATE:" + today.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}

				WorkflowConfig workflowConfigObject1 = webService.getWorkflowConfigById(Integer.parseInt(workflowid));
				WorkflowConfig workflowConfig1 = new WorkflowConfig();
				workflowConfig1.setId(Long.parseLong(workflowid));
				workflowConfig1.setAppid(appid);
				workflowConfig1.setDescription(description);
				workflowConfig1.setSubProcess(subprocess);
				workflowConfig1.setAprvLevels(aprlevel);
				workflowConfig1.setCondition(condition);
				workflowConfig1.setStatus(statusObject);
				workflowConfig1.setAprv1(aprv1);
				workflowConfig1.setAprv2(aprv2);
				workflowConfig1.setAprv3(aprv3);
				workflowConfig1.setAprv4(aprv4);
				workflowConfig1.setAprv5(aprv5);
				workflowConfig1.setAprv6(aprv6);
				workflowConfig1.setAprv7(aprv7);
				workflowConfig1.setAprv8(aprv8);
				workflowConfig1.setAprv9(aprv9);
				workflowConfig1.setAprv10(aprv10);
				workflowConfig1.setCreatedBy(workflowConfigObject1.getCreatedBy());
				workflowConfig1.setChangeddBy(cdby);
				workflowConfig1.setCreatedDate(workflowConfigObject1.getCreatedDate());
				workflowConfig1.setChangedDate(today);
				WorkflowConfig workflowConfigObject2 = webService.createWorkflow(workflowConfig1);
				System.out.println("Workflow Config is updated");
				js.put("User", "Workflow Config updated Successfully");
				js.put("ApprovalMatrixObject", ow.writeValueAsString(workflowConfig1));
				js.put("code", "1000");
				json = js.toString();

			} catch (Exception e) {
				js.put("code", "1001");
				js.put("error", "Workflow Config Not Updated");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		}

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/getApprovalMatrix", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> getApprovalMatrix(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			List<ApprovalMatrix> approvalMatrixList = webService.getAllApprovalMatrix();
			if (approvalMatrixList == null || approvalMatrixList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Approval Matrix is Empty");
				json = js.toString();
			} else {
				js.put("approvalMatrixList", ow.writeValueAsString(approvalMatrixList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/getWorkflowConfig", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> getWorkflowConfig(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			List<WorkflowConfig> workflowConfigList = webService.getAllWorkflowConfig();
			if (workflowConfigList == null || workflowConfigList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Workflow Config is empty");
				json = js.toString();
			} else {
				js.put("workflowConfigList", ow.writeValueAsString(workflowConfigList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	
	
	@GetMapping("/getStorageLocation/metadata")//pp
	@CrossOrigin("*")
	public Map<String, Object> getStorageLocationBasedOnPlant(@RequestParam("WERKS") String werks) throws SQLException {
		return webGetServices.getStorageLocationBasedOnPlant(werks);
	}
	
	
	
//	@GetMapping("/mailnotificationForRFP")//pp
//	@CrossOrigin("*")
//	public Map<String, Object> mailnotificationForRFP(@RequestParam("rfpno") String rfpno) throws SQLException {
//		return approverServices.mailnotificationForRFP(rfpno);
//	}
//	
	
	
	
	@CrossOrigin("*")
	@Transactional
	@RequestMapping(value = "/workflowStart", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<String> workflowStart(HttpServletRequest request, HttpServletResponse response,
			@RequestBody HashMap<String, String> workflowStart) {


		String appid = workflowStart.get("APPID");
		String subprocess = workflowStart.get("SUBPROC");
		// String x= (String) workflowStart.get("REQID");
		String reqid1 = "";
		long reqid = 0L;

		// long reqid = Long.parseLong(x);

		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		String Aprv1 = null, Aprv2 = null, Aprv3 = null, Aprv4 = null, Aprv5 = null, Aprv6 = null, Aprv7 = null,
				Aprv8 = null, Aprv9 = null, Aprv10 = null;
		ApprovalLog aprvLog = null;
		try {

			reqid = Long.parseLong(workflowStart.get("REQID"));

			if (reqid == 0) {
				js.put("code", "1002");
				js.put("error", "REQID is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (appid.isEmpty() || appid == "") {
				js.put("code", "1002");
				js.put("error", "APPID is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (subprocess.isEmpty() || subprocess == "") {
				js.put("code", "1002");
				js.put("error", "SUB PROCESS Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//call internal services
		
		
		if (appid == "PR" || appid.equalsIgnoreCase("pr")) {
			
			try {
				return new ResponseEntity<String>(approverServices.workflowStart(appid, subprocess, reqid), HttpStatus.OK);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (org.json.JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}else if (appid == "PO" || appid.equalsIgnoreCase("PO")) {
			
			try {
				return new ResponseEntity<String>(approverServices.workflowStartForPO(appid, subprocess, reqid), HttpStatus.OK);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (org.json.JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}	
		
		
		
		
		java.util.Date utilDate = new java.util.Date();
		boolean count = true;
		boolean emailconf1 = false;
		String subject = "";
		String body = "";
		String to = "";
		String rfpCreator ="";
		String plant = "";
		RFPHeader rfpHeader = null;
		String check = "";
		String position = "";
		ASNItem asnItemA = null;
		PurchaseOrder purchaseOrder = null;

		String AprvForMailStatus = "";
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		// acknowledgement.setCrdat(new java.sql.Date(new Date().getTime()));
		Time currentTime = new Time(utilDate.getTime());
		// Time.valueOf(LocalTime.now()
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 3);
		java.util.Date dt = cal.getTime();
		java.sql.Date endDate = new java.sql.Date(dt.getTime());
		int statusId3 = 3;
		Status status = webService.getstatusById(statusId3);
		Status status01 = webService.getstatusById(6);
		try {
			WorkflowConfig workflowConfigList = webService.getActiveWorkflowConfigByStatus(appid, subprocess, status01);
			if (workflowConfigList == null) {
				js.put("code", "1001");
				js.put("error", "Workflow Config is empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);

			}

			else {

				aprvLog = new ApprovalLog();
				Status len = workflowConfigList.getStatus();
				int statact = len.getStatusId();

				int aprvlevel = workflowConfigList.getAprvLevels();
				System.out.println("Int Status:" + statact);
				System.out.println("Approverlevel:" + aprvlevel);
				ArrayList<String> list1 = new ArrayList<>();
				// for (int i = 1; i <= aprvlevel; i++) {3000053195
				{
//					aprvLog.setReqNo(workflowConfigList.getReqno());
					aprvLog.setReqNo(reqid);

					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					String strDate = formatter.format(date);
					aprvLog.setApprovedDate(strDate);					aprvLog.setApprovedTime(Time.valueOf(LocalTime.now()));
					aprvLog.setSubProcess(workflowConfigList.getSubProcess());
					aprvLog.setAppid(workflowConfigList);
					aprvLog.setStatus(statusRepo.findOne(3));// 123456
					// aprvLog.setInitiator(workflowConfigList.getCreatedBy());

					if (appid == "ASN" || appid.equalsIgnoreCase("ASN")) {
						ASNHeader asnHeader = asnHeaderRepository.findOne(reqid);
						if (asnHeader == null) {
							js.put("code", "1001");
							js.put("error", "ASNHeader  is Not Found");
							json = js.toString();
							return new ResponseEntity<String>(json, HttpStatus.OK);
						}

						purchaseOrder = purchaseOrderRepository.findByEbeln(asnHeader.getEbeln());

						if (purchaseOrder == null) {
							js.put("code", "1001");
							js.put("error", "PurchaseOrder  is Not Found");
							json = js.toString();
							return new ResponseEntity<String>(json, HttpStatus.OK);
						}

						List<ASNItem> asnItems = asnHeader.getAsnitem();
						if (asnItems.isEmpty()) {
							js.put("code", "1001");
							js.put("error", "  is Not Found");
							json = js.toString();
							return new ResponseEntity<String>(json, HttpStatus.OK);
						}

						asnItemA = asnItems.get(0);
						plant = asnItemA.getWerks();
						aprvLog.setWerks(plant);

						aprvLog.setInitiator("" + purchaseOrder.getLifnr());

						check = dateCompare.dateDiffrence(plant, reqid);

						if (check.equalsIgnoreCase("ex") || check.equalsIgnoreCase("act")) {
							System.out.println("ok fine it is working");
						} else {
							js.put("code", "1001");
							js.put("error", check);
							json = js.toString();
							return new ResponseEntity<String>(json, HttpStatus.OK);
						}

//						ApprovalMatrix approvalMatrixForAprvAsn = approvalMatrixRepository.findByPlant(plant);
//						if (approvalMatrixForAprvAsn == null) {
//							js.put("code", "1001");
//							js.put("error", "According To This Plant " + plant + "  ApprovalMatrix Is Not Maintain");
//							json = js.toString();
//							return new ResponseEntity<String>(json, HttpStatus.OK);
//
//						}
//
//						
//						String check = dateCompare.dateDiffrence(asnItemA.getMatnr(), plant, reqid);
//
//						if (check == "act" || check.equalsIgnoreCase("act")) {
//							aprvLog.setAprv(approvalMatrixForAprvAsn.getLabhead());
//						} else if (check == "ex" || check.equalsIgnoreCase("ex")) {
//							aprvLog.setAprv(approvalMatrixForAprvAsn.getMatspoc());
//						}
//						aprvLog.setInitiator("" + purchaseOrder.getLifnr());
//						aprvLog.setAprvlevel("L1");
//						aprvLog.setMailstatus(3);
//						ApprovalLog aprvPoObj = webService.createApprovalLog(aprvLog);
//
//						count = true;
//						 Aprv1 = aprvPoObj.getAprv();
//						to = masterRepo.findByAdrid(Aprv1).getEmail();
//						
//						subject = "ASN no. "+reqid+" against the Purchase Orderd no. "+purchaseOrder.getEbeln()+" dated "+Calendar.getInstance().getTime()+" has been generated by the vendor.";
//						 body = "ASN no. "+reqid+" against the Purchase Orderd no. "+purchaseOrder.getEbeln()+" dated "+Calendar.getInstance().getTime()+" has been generated by the vendor. "
//								+ "Kindly verify and confirm the same in revert." ;
//						 
//						 
//						 emailconf1 = emailSending.emailSendService(to, subject, body, "");
//						 
//						 if (emailconf1) {
//							 
//							 
//								ApprovalLog approvalLog123 = approvalLogRepo.getApprovalLogByReqNoAndMailStatusaAndAprv(reqid, 3,
//										Aprv1);
//								approvalLog123.setMailstatus(1);
//							 ApprovalLog aprvPoObj1 = approvalLogRepo.save(aprvLog);
//							 if (aprvPoObj1 != null) {
//								 js.put("code", "1000");
//									js.put("success", ow.writeValueAsString(aprvPoObj1));
//									json = js.toString();
//									return new ResponseEntity<String>(json, HttpStatus.OK);
//							}else {
//								js.put("code", "1001");
//								js.put("success", "mail is not sent plz check something is wrong");
//								json = js.toString();
//								return new ResponseEntity<String>(json, HttpStatus.OK);
//							}
//						}
//						System.out.println(" noe not aveleble set the   detnthe the after the some time ");

					}

					if (appid == "PR" || appid.equalsIgnoreCase("pr")) {

						List<PurchaseRequisition> purchaseRequisitionListForCreater = purchaseRequisitionRepository
								.findByBanfn(reqid);
						for (PurchaseRequisition purchaseRequisitionForCreater1 : purchaseRequisitionListForCreater) {
							if (count && purchaseRequisitionForCreater1 != null) {
								aprvLog.setInitiator(purchaseRequisitionForCreater1.getCrdBy());
								plant = purchaseRequisitionForCreater1.getWerks();
								aprvLog.setWerks(plant);
								count = false;
							} else if (count) {
								js.put("code", "1001");
								js.put("error", "PurchaseRequisition list is empty");
								json = js.toString();
								return new ResponseEntity<String>(json, HttpStatus.OK);

							}

						}
						count = true;

					} else if (appid == "NONPOINV" || appid.equalsIgnoreCase("NONPOINV")) {

						Invoice invoice = invoiceRepository.findOne(reqid);
						if (invoice == null) {
							js.put("code", "1001");
							js.put("error", "Invoice is Not Found");
							json = js.toString();
							return new ResponseEntity<String>(json, HttpStatus.OK);
						}

						plant = "logistics";
						aprvLog.setWerks(plant);
						aprvLog.setInitiator(invoice.getCreatedBy());

					} else if (appid == "RFPVS" || appid.equalsIgnoreCase("RFPVS")) {

						rfpHeader = rfpHeaderRepository.findOne(reqid);

						if (rfpHeader == null) {

							js.put("code", "1001");
							js.put("error", "RFPHeader is Not Found");
							json = js.toString();
							return new ResponseEntity<String>(json, HttpStatus.OK);
						}

						List<RFPItem> itemsList = rfpHeader.getItem();
						if (itemsList.isEmpty()) {

							js.put("code", "1001");
							js.put("error", "RFPItem List is Empty");
							json = js.toString();
							return new ResponseEntity<String>(json, HttpStatus.OK);
						}

						ProposalSummary proposalSummary = proposalSummaryRepository.findByRfpno(reqid);
						if (proposalSummary == null) {

							js.put("code", "1001");
							js.put("error", "ProposalSummary is Not Found");
							json = js.toString();
							return new ResponseEntity<String>(json, HttpStatus.OK);
						}

						position = proposalSummary.getPosition();

						plant = itemsList.get(0).getWerks();
						aprvLog.setWerks(plant);

						aprvLog.setInitiator(rfpHeader.getCreatedby());

					}

					else if (appid == "VM" || appid.equalsIgnoreCase("VM")) {

						Vendor vendor = vendorRepository.findOne(reqid);
						vendor = vendorRepository.findByVid(reqid);
						List<ProposalSummary> proposalSummary = proposalSummaryRepository
								.findByLifnr(vendor.getCreatedBy());
						if (proposalSummary.isEmpty()) {
							System.err.println("1001  ::  ProposalSummary List is not found");
							js.put("code", "1001");
							js.put("error", " ProposalSummary List is not found");
							json = js.toString();
							return new ResponseEntity<String>(json, HttpStatus.OK);

						}

						rfpHeader = rfpHeaderRepository.findOne(proposalSummary.get(0).getRfpno());
						if (rfpHeader == null) {
							js.put("code", "1001");
							js.put("error", " RFPHeader  is not found");
							json = js.toString();
							return new ResponseEntity<String>(json, HttpStatus.OK);

						}
						rfpCreator = rfpHeader.getCreatedby();

						List<RFPItem> rfpItems = rfpHeader.getItem();
						if (rfpItems.isEmpty()) {
							js.put("code", "1001");
							js.put("error", " RFPItem List is not found");
							json = js.toString();
							return new ResponseEntity<String>(json, HttpStatus.OK);

						}

						plant = rfpItems.get(0).getWerks();
						aprvLog.setWerks(plant);

						System.out.println("**************plant :: " + plant + "******************");
						aprvLog.setInitiator("" + reqid);
						count = true;

						System.out.println(" noe not aveleble set the   detnthe the after the some time ");

					} else if (appid == "PO" || appid.equalsIgnoreCase("PO")) {

						PurchaseOrder PurchaseOrderForPlant = purchaseOrderRepository.findByEbeln(reqid);

						if (PurchaseOrderForPlant != null) {

							List<POItemDetails> poItemDetailsForPlant = PurchaseOrderForPlant.getPoitem();

							for (POItemDetails poItemDetailsForPlant1 : poItemDetailsForPlant) {// 123
								if (count && poItemDetailsForPlant1 != null) {

									plant = poItemDetailsForPlant1.getWerks();
									count = false;
								} else if (count) {
									js.put("code", "1001");
									js.put("error", "PurchaseRequisition list is empty");
									json = js.toString();
									return new ResponseEntity<String>(json, HttpStatus.OK);

								}

							}
							count = true;

						} else {
							js.put("code", "1001");
							js.put("error", "Plant is  empty");
							json = js.toString();
							return new ResponseEntity<String>(json, HttpStatus.OK);
						}

					}

//					aprvLog.setInitiator(workflowConfigList.getCreatedBy());

//					aprvLog.setWerks(sampleRepo.findByBanfn());
					// aprvLog.setFlag2(0);
					aprvLog.setMailstatus(3);

					ApprovalMatrix approvalMatrixForAprv = approvalMatrixRepository.findByPlant(plant);
					if (approvalMatrixForAprv == null) {
						js.put("code", "1001");
						js.put("error", "According To This Plant " + plant + "  ApprovalMatrix Is Not Maintain");
						json = js.toString();
						return new ResponseEntity<String>(json, HttpStatus.OK);

					}

					// ******************appr = "workflowObject.getAprv" + i + "()";

					if (appid == "po" || appid.equalsIgnoreCase("po")) {

						PurchaseOrder purchaseOrderListForCreater = purchaseOrderRepository.findByEbeln(reqid);

						if (purchaseOrderListForCreater != null) {
							aprvLog.setInitiator(purchaseOrderListForCreater.getErnam());
							aprvLog.setAprvlevel("L1");
							aprvLog.setWerks(plant);
							aprvLog.setAprv(purchaseOrderListForCreater.getLifnr());

							ApprovalLog aprvPoObj = webService.createApprovalLog(aprvLog);

							Aprv1 = aprvPoObj.getAprv();
							if (aprvPoObj != null) {
								to = masterRepo.findByAdrid(Aprv1).getEmail();
								subject = " Purchase Orderd no. " + reqid + " dated " + Calendar.getInstance().getTime()
										+ " has been generated.";
								body = "Purchase Orderd no. " + reqid + "  dated " + Calendar.getInstance().getTime()
										+ " has been generated. Kindly verify and revert your kind acknowledgement for the same.";

								emailconf1 = emailSending.emailSendService(to, subject, body, "");
//						
							}

						} else {
							js.put("code", "1001");
							js.put("error", "PurchaseRequisition list is empty");
							json = js.toString();
							return new ResponseEntity<String>(json, HttpStatus.OK);

						}

						count = true;

					} else if (appid == "ASN" || appid.equalsIgnoreCase("ASN")) {

						if (check == "act" || check.equalsIgnoreCase("act")) {
							aprvLog.setAprv(approvalMatrixForAprv.getLabhead());
						} else if (check == "ex" || check.equalsIgnoreCase("ex")) {
							aprvLog.setAprv(approvalMatrixForAprv.getMatspoc());
						}
						aprvLog.setAprvlevel("L1");
						aprvLog.setMailstatus(3);
						ApprovalLog aprvPoObj = webService.createApprovalLog(aprvLog);

						count = true;
						Aprv1 = aprvPoObj.getAprv();
						to = masterRepo.findByAdrid(Aprv1).getEmail();

						subject = "ASN no. " + reqid + " against the Purchase Orderd no. " + purchaseOrder.getEbeln()
								+ " dated " + Calendar.getInstance().getTime()
								+ " has been generated by the vendor.\r\n" + "";
						body = "ASN no. " + reqid + " against the Purchase Orderd no. " + purchaseOrder.getEbeln()
								+ " dated " + Calendar.getInstance().getTime()
								+ " has been generated by the vendor. Kindly verify and confirm the same in revert.\r\n"
								+ "";

						emailconf1 = emailSending.emailSendService(to, subject, body, "");

//						 if (emailconf1) {
//							 
//							 
//								ApprovalLog approvalLog123 = approvalLogRepo.getApprovalLogByReqNoAndMailStatusaAndAprv(reqid, 3,
//										Aprv1);
//								approvalLog123.setMailstatus(1);
//							 ApprovalLog aprvPoObj1 = approvalLogRepo.save(aprvLog);
//							 if (aprvPoObj1 != null) {
//								 js.put("code", "1000");
//									js.put("success", ow.writeValueAsString(aprvPoObj1));
//									json = js.toString();
//									return new ResponseEntity<String>(json, HttpStatus.OK);
//							}else {
//								js.put("code", "1001");
//								js.put("success", "mail is not sent plz check something is wrong");
//								json = js.toString();
//								return new ResponseEntity<String>(json, HttpStatus.OK);
//							}
//						}
//						System.out.println(" noe not aveleble set the   detnthe the after the some time ");
//						

					}

					else {

						if (aprvlevel == 1) {
							// for approveal 1
							if (workflowConfigList.getAprv1().equalsIgnoreCase("labhead"))
								Aprv1 = approvalMatrixForAprv.getLabhead();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("buhead"))
								Aprv1 = approvalMatrixForAprv.getBuhead();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("prochead"))
								Aprv1 = approvalMatrixForAprv.getProchead();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("techhead"))
								Aprv1 = approvalMatrixForAprv.getTechhead();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("matspoc"))
								Aprv1 = approvalMatrixForAprv.getMatspoc();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("mdmteam"))
								Aprv1 = approvalMatrixForAprv.getMdmteam();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("rfpcreator"))
								Aprv1 = approvalMatrixForAprv.getRfpcreator();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("logistics_spoc"))
								Aprv1 = approvalMatrixForAprv.getLogistics_spoc();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("logistics_head"))
								Aprv1 = approvalMatrixForAprv.getLogistics_head();

							aprvLog.setAprv(Aprv1);
							aprvLog.setAprvlevel("L1");
							ApprovalLog aprvLogObj = webService.createApprovalLog(aprvLog);
						} else if (aprvlevel == 2) {

							// for approveal 1
							if (workflowConfigList.getAprv1().equalsIgnoreCase("labhead"))
								Aprv1 = approvalMatrixForAprv.getLabhead();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("buhead"))
								Aprv1 = approvalMatrixForAprv.getBuhead();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("prochead"))
								Aprv1 = approvalMatrixForAprv.getProchead();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("techhead"))
								Aprv1 = approvalMatrixForAprv.getTechhead();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("matspoc"))
								Aprv1 = approvalMatrixForAprv.getMatspoc();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("mdmteam"))
								Aprv1 = approvalMatrixForAprv.getMdmteam();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("rfpcreator"))
								Aprv1 = approvalMatrixForAprv.getRfpcreator();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("logistics_spoc"))
								Aprv1 = approvalMatrixForAprv.getLogistics_spoc();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("logistics_head"))
								Aprv1 = approvalMatrixForAprv.getLogistics_head();

							// for approveal 2
							if (workflowConfigList.getAprv2().equalsIgnoreCase("labhead"))
								Aprv2 = approvalMatrixForAprv.getLabhead();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("buhead"))
								Aprv2 = approvalMatrixForAprv.getBuhead();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("prochead"))
								Aprv2 = approvalMatrixForAprv.getProchead();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("techhead"))
								Aprv2 = approvalMatrixForAprv.getTechhead();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("matspoc"))
								Aprv2 = approvalMatrixForAprv.getMatspoc();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("mdmteam"))
								Aprv2 = approvalMatrixForAprv.getMdmteam();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("rfpcreator"))
								Aprv2 = approvalMatrixForAprv.getRfpcreator();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("logistics_spoc"))
								Aprv2 = approvalMatrixForAprv.getLogistics_spoc();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("logistics_head"))
								Aprv2 = approvalMatrixForAprv.getLogistics_head();

							aprvLog.setAprv(Aprv1);
							aprvLog.setAprvlevel("L1");
							ApprovalLog aprvLogObj1 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv2);
							aprvLog.setAprvlevel("L2");
							ApprovalLog aprvLogObj2 = webService.createApprovalLog(aprvLog);

						} else if (aprvlevel == 3) {

							// for approveal 1
							if (workflowConfigList.getAprv1().equalsIgnoreCase("labhead"))
								Aprv1 = approvalMatrixForAprv.getLabhead();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("buhead"))
								Aprv1 = approvalMatrixForAprv.getBuhead();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("prochead"))
								Aprv1 = approvalMatrixForAprv.getProchead();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("techhead"))
								Aprv1 = approvalMatrixForAprv.getTechhead();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("matspoc"))
								Aprv1 = approvalMatrixForAprv.getMatspoc();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("mdmteam"))
								Aprv1 = approvalMatrixForAprv.getMdmteam();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("rfpcreator"))
								Aprv1 = approvalMatrixForAprv.getRfpcreator();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("logistics_spoc"))
								Aprv1 = approvalMatrixForAprv.getLogistics_spoc();
							else if (workflowConfigList.getAprv1().equalsIgnoreCase("logistics_head"))
								Aprv1 = approvalMatrixForAprv.getLogistics_head();

							// for approveal 2
							if (workflowConfigList.getAprv2().equalsIgnoreCase("labhead"))
								Aprv2 = approvalMatrixForAprv.getLabhead();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("buhead"))
								Aprv2 = approvalMatrixForAprv.getBuhead();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("prochead"))
								Aprv2 = approvalMatrixForAprv.getProchead();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("techhead"))
								Aprv2 = approvalMatrixForAprv.getTechhead();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("matspoc"))
								Aprv2 = approvalMatrixForAprv.getMatspoc();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("mdmteam"))
								Aprv2 = approvalMatrixForAprv.getMdmteam();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("rfpcreator"))
								Aprv2 = approvalMatrixForAprv.getRfpcreator();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("logistics_spoc"))
								Aprv2 = approvalMatrixForAprv.getLogistics_spoc();
							else if (workflowConfigList.getAprv2().equalsIgnoreCase("logistics_head"))
								Aprv2 = approvalMatrixForAprv.getLogistics_head();


							// for approveal 3
							if (workflowConfigList.getAprv3().equalsIgnoreCase("labhead"))
								Aprv3 = approvalMatrixForAprv.getLabhead();
							else if (workflowConfigList.getAprv3().equalsIgnoreCase("buhead"))
								Aprv3 = approvalMatrixForAprv.getBuhead();
							else if (workflowConfigList.getAprv3().equalsIgnoreCase("prochead"))
								Aprv3 = approvalMatrixForAprv.getProchead();
							else if (workflowConfigList.getAprv3().equalsIgnoreCase("techhead"))
								Aprv3 = approvalMatrixForAprv.getTechhead();
							else if (workflowConfigList.getAprv3().equalsIgnoreCase("matspoc"))
								Aprv3 = approvalMatrixForAprv.getMatspoc();
							else if (workflowConfigList.getAprv3().equalsIgnoreCase("mdmteam"))
								Aprv3 = approvalMatrixForAprv.getMdmteam();
							else if (workflowConfigList.getAprv3().equalsIgnoreCase("rfpcreator"))
								Aprv3 = approvalMatrixForAprv.getRfpcreator();
							else if (workflowConfigList.getAprv3().equalsIgnoreCase("logistics_spoc"))
								Aprv3 = approvalMatrixForAprv.getLogistics_spoc();
							else if (workflowConfigList.getAprv3().equalsIgnoreCase("logistics_head"))
								Aprv3 = approvalMatrixForAprv.getLogistics_head();
							
							if (appid == "VM"  || appid.equalsIgnoreCase("VM")) {
								Aprv1 = rfpCreator;
								aprvLog.setAprv(rfpCreator);
								aprvLog.setAprvlevel("L1");
							}else {
								aprvLog.setAprv(Aprv1);
								aprvLog.setAprvlevel("L1");
							}

							
							ApprovalLog aprvLogObj1 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv2);
							aprvLog.setAprvlevel("L2");
							ApprovalLog aprvLogObj2 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv3);
							aprvLog.setAprvlevel("L3");
							ApprovalLog aprvLogObj3 = webService.createApprovalLog(aprvLog);

						} else if (aprvlevel == 4) {
							aprvLog.setAprv(Aprv1);
							aprvLog.setAprvlevel("L1");
							ApprovalLog aprvLogObj1 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv2);
							aprvLog.setAprvlevel("L2");
							ApprovalLog aprvLogObj2 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv3);
							aprvLog.setAprvlevel("L3");
							ApprovalLog aprvLogObj3 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv4);
							aprvLog.setAprvlevel("L4");
							ApprovalLog aprvLogObj4 = webService.createApprovalLog(aprvLog);

						} else if (aprvlevel == 5) {
							aprvLog.setAprv(Aprv1);
							aprvLog.setAprvlevel("L1");
							ApprovalLog aprvLogObj1 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv2);
							aprvLog.setAprvlevel("L2");
							ApprovalLog aprvLogObj2 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv3);
							aprvLog.setAprvlevel("L3");
							ApprovalLog aprvLogObj3 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv4);
							aprvLog.setAprvlevel("L4");
							ApprovalLog aprvLogObj4 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv5);
							aprvLog.setAprvlevel("L5");
							ApprovalLog aprvLogObj5 = webService.createApprovalLog(aprvLog);

						} else if (aprvlevel == 6) {
							aprvLog.setAprv(Aprv1);
							aprvLog.setAprvlevel("L1");
							ApprovalLog aprvLogObj1 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv2);
							aprvLog.setAprvlevel("L2");
							ApprovalLog aprvLogObj2 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv3);
							aprvLog.setAprvlevel("L3");
							ApprovalLog aprvLogObj3 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv4);
							aprvLog.setAprvlevel("L4");
							ApprovalLog aprvLogObj4 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv5);
							aprvLog.setAprvlevel("L5");
							ApprovalLog aprvLogObj5 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv6);
							aprvLog.setAprvlevel("L6");
							ApprovalLog aprvLogObj6 = webService.createApprovalLog(aprvLog);

						} else if (aprvlevel == 7) {
							aprvLog.setAprv(Aprv1);
							aprvLog.setAprvlevel("L1");
							ApprovalLog aprvLogObj1 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv2);
							aprvLog.setAprvlevel("L2");
							ApprovalLog aprvLogObj2 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv3);
							aprvLog.setAprvlevel("L3");
							ApprovalLog aprvLogObj3 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv4);
							aprvLog.setAprvlevel("L4");
							ApprovalLog aprvLogObj4 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv5);
							aprvLog.setAprvlevel("L5");
							ApprovalLog aprvLogObj5 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv6);
							aprvLog.setAprvlevel("L6");
							ApprovalLog aprvLogObj6 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv7);
							aprvLog.setAprvlevel("L7");
							ApprovalLog aprvLogObj7 = webService.createApprovalLog(aprvLog);

						} else if (aprvlevel == 8) {
							aprvLog.setAprv(Aprv1);
							aprvLog.setAprvlevel("L1");
							ApprovalLog aprvLogObj1 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv2);
							aprvLog.setAprvlevel("L2");
							ApprovalLog aprvLogObj2 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv3);
							aprvLog.setAprvlevel("L3");
							ApprovalLog aprvLogObj3 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv4);
							aprvLog.setAprvlevel("L4");
							ApprovalLog aprvLogObj4 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv5);
							aprvLog.setAprvlevel("L5");
							ApprovalLog aprvLogObj5 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv6);
							aprvLog.setAprvlevel("L6");
							ApprovalLog aprvLogObj6 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv7);
							aprvLog.setAprvlevel("L7");
							ApprovalLog aprvLogObj7 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv8);
							aprvLog.setAprvlevel("L8");
							ApprovalLog aprvLogObj8 = webService.createApprovalLog(aprvLog);

						} else if (aprvlevel == 9) {
							aprvLog.setAprv(Aprv1);
							aprvLog.setAprvlevel("L1");
							ApprovalLog aprvLogObj1 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv2);
							aprvLog.setAprvlevel("L2");
							ApprovalLog aprvLogObj2 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv3);
							aprvLog.setAprvlevel("L3");
							ApprovalLog aprvLogObj3 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv4);
							aprvLog.setAprvlevel("L4");
							ApprovalLog aprvLogObj4 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv5);
							aprvLog.setAprvlevel("L5");
							ApprovalLog aprvLogObj5 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv6);
							aprvLog.setAprvlevel("L6");
							ApprovalLog aprvLogObj6 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv7);
							aprvLog.setAprvlevel("L7");
							ApprovalLog aprvLogObj7 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv8);
							aprvLog.setAprvlevel("L8");
							ApprovalLog aprvLogObj8 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv9);
							aprvLog.setAprvlevel("L9");
							ApprovalLog aprvLogObj9 = webService.createApprovalLog(aprvLog);

						} else if (aprvlevel == 10) {
							aprvLog.setAprv(Aprv1);
							aprvLog.setAprvlevel("L1");
							ApprovalLog aprvLogObj1 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv2);
							aprvLog.setAprvlevel("L2");
							ApprovalLog aprvLogObj2 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv3);
							aprvLog.setAprvlevel("L3");
							ApprovalLog aprvLogObj3 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv4);
							aprvLog.setAprvlevel("L4");
							ApprovalLog aprvLogObj4 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv5);
							aprvLog.setAprvlevel("L5");
							ApprovalLog aprvLogObj5 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv6);
							aprvLog.setAprvlevel("L6");
							ApprovalLog aprvLogObj6 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv7);
							aprvLog.setAprvlevel("L7");
							ApprovalLog aprvLogObj7 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv8);
							aprvLog.setAprvlevel("L8");
							ApprovalLog aprvLogObj8 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv9);
							aprvLog.setAprvlevel("L9");
							ApprovalLog aprvLogObj9 = webService.createApprovalLog(aprvLog);
							aprvLog.setAprv(Aprv10);
							aprvLog.setAprvlevel("L10");
							ApprovalLog aprvLogObj10 = webService.createApprovalLog(aprvLog);
						}
					}

				}
				// for
//				count = true;
//				for (String workflowConfigListForAprv : list1) {
//
//					if (count && workflowConfigListForAprv != "") {
//						AprvForMailStatus = workflowConfigListForAprv;
//
//					} else if (count) {
//						js.put("code", "1001");
//						js.put("error", "Plant Is Not Found For This " + reqid + "");
//						json = js.toString();
//						return new ResponseEntity<String>(json, HttpStatus.OK);
//
//					}
//					count = false;
//				}

				count = true;

//				if (appid.equalsIgnoreCase("ASN") || appid == "ASN") {
//
//					ApprovalMatrix approvalMatrixForAprv = approvalMatrixRepository.findByPlant(plant);
//					if (approvalMatrixForAprv == null) {
//						js.put("code", "1001");
//						js.put("error", "According To This Plant " + plant + "  ApprovalMatrix Is Not Maintain");
//						json = js.toString();
//						return new ResponseEntity<String>(json, HttpStatus.OK);
//
//					}
//
//					to = masterRepo.findByAdrid((approvalMatrixForAprv.getMatspoc())).getEmail();
//					if (to.isEmpty() || to.equalsIgnoreCase("")) {
//						js.put("code", "1001");
//						js.put("error", "To is not found");
//						json = js.toString();
//						return new ResponseEntity<String>(json, HttpStatus.OK);
//
//					}
//
//					subject = "ASN no. " + reqid
//							+ " against the Purchase Orderd no.  dated __________ has been generated by"
//							+ " the vendor.\r\n" + "";
//					body = "ASN no. _____________ against the Purchase Orderd no. ________________ dated __________ has"
//							+ " been generated by the vendor. Kindly verify and confirm the same in revert.\r\n" + "";
//
//					emailconf1 = emailSending.emailSendService(to, subject, body, "");
//
//				}

				if (appid.equalsIgnoreCase("PR") || appid == "PR") {// prw
					subject = "Purchase Request no. " + reqid + "   has been initiated";
					body = "Purchase Request no. " + reqid
							+ " has been initiated. Request your kind verification and approval for the same.";

					List<PurchaseRequisition> purchaseRequisitionList = purchaseRequisitionRepository
							.findByBanfn(reqid);

					for (PurchaseRequisition purchaseRequisitionListPlant : purchaseRequisitionList) {

						if (count && purchaseRequisitionListPlant != null) {
							plant = (purchaseRequisitionListPlant.getWerks());

						} else if (count) {
							js.put("code", "1001");
							js.put("error", "Plant Is Not Found For This " + reqid + "");
							json = js.toString();
							return new ResponseEntity<String>(json, HttpStatus.OK);

						}
						count = false;
					}

					ApprovalMatrix approvalMatrixForAprv = approvalMatrixRepository.findByPlant(plant);
					if (approvalMatrixForAprv == null) {
						js.put("code", "1001");
						js.put("error", "According To This Plant " + plant + "  ApprovalMatrix Is Not Maintain");
						json = js.toString();
						return new ResponseEntity<String>(json, HttpStatus.OK);

					}
					count = true;
					// lab head
					to = masterRepo.findByAdrid((approvalMatrixForAprv.getLabhead())).getEmail();

					emailconf1 = emailSending.emailSendService(to, subject, body, "");
//				
				}

				// ************************************vm *********************

				else if (appid.equalsIgnoreCase("VM") || appid == "VM") {

					Vendor vendors = vendorRepository.findByVid(reqid);
					if (vendors == null) {
						js.put("code", "1001");
						js.put("error", "Vendor  is empty");
						json = js.toString();
						return new ResponseEntity<String>(json, HttpStatus.OK);

					}

					subject = "Details submitted for Vendor" + vendors.getOrgName() + "";
					body = "The required details have been submitted by the vendor" + vendors.getOrgName()
							+ " for the temporary registration purpose." + " Kindly do the needful.";


//					ApprovalMatrix approvalMatrixForAprv = approvalMatrixRepository.findByPlant(plant);
//					if (approvalMatrixForAprv == null) {
//						js.put("code", "1001");
//						js.put("error", "According To This Plant " + plant + "  ApprovalMatrix Is Not Maintain");
//						json = js.toString();
//						return new ResponseEntity<String>(json, HttpStatus.OK);
//
//					}
					count = true;

					to = masterRepo.findByAdrid(rfpCreator).getEmail();
					if (to.isEmpty() || to == null || to.equalsIgnoreCase("")) {
						js.put("code", "1001");
						js.put("error", "First Aprv is Not found");
						json = js.toString();
						return new ResponseEntity<String>(json, HttpStatus.OK);

					}

					emailconf1 = emailSending.emailSendService(to, subject, body, "");
//						

				}
				// RFPVS or TC
				else if (appid.equalsIgnoreCase("RFPVS") || appid == "RFPVS") {

					subject = "" + position + " has been maintained for vendor  against rfp no." + reqid + ". ";
					body = "	" + position + " has been maintained for vendor against rfp no." + reqid + "."
							+ " Kindly initiate the approval.";

					plant = "1000";// change in future after conformation

//					ApprovalMatrix approvalMatrixForAprv = approvalMatrixRepository.findByPlant(plant);
//					if (approvalMatrixForAprv == null) {
//						js.put("code", "1001");
//						js.put("error", "According To This Plant " + plant + "  ApprovalMatrix Is Not Maintain");
//						json = js.toString();
//						return new ResponseEntity<String>(json, HttpStatus.OK);
//
//					}
					count = true;

					to = masterRepo.findByAdrid(Aprv1).getEmail().trim();
					if (to.isEmpty() || to == "" || to.equalsIgnoreCase("") || to == null) {
						js.put("code", "1001");
						js.put("error", "TO Email ID IS NOT FOUND");
						json = js.toString();
						return new ResponseEntity<String>(json, HttpStatus.OK);

					}

					emailconf1 = emailSending.emailSendService(to, subject, body, "");
//						

				}else if (appid == "NONPOINV" || appid.equalsIgnoreCase("NONPOINV")) {

					subject = "NON PO BASED INVOICE fghjkl ";
					body = "	" + position + " has been maintained for vendor against rfp no." + reqid + "."
							+ " Kindly initiate the approval.";

					count = true;

					to = masterRepo.findByAdrid(Aprv1).getEmail().trim();
					if (to.isEmpty() || to == "" || to.equalsIgnoreCase("") || to == null) {
						js.put("code", "1001");
						js.put("error", "TO Email ID IS NOT FOUND");
						json = js.toString();
						return new ResponseEntity<String>(json, HttpStatus.OK);

					}

					emailconf1 = emailSending.emailSendService(to, subject, body, "");
//						

				}

				// *********************************

				if (emailconf1) {

					List<ApprovalLog> aprvList1 = null;
					// String a = approvalMatrixForAprv.getLabhead();
					// System.out.println(a);
					
						aprvList1 = sampleRepo.getApprovalLogByReqNoAndMailStatusaAndAprv(reqid, 3, Aprv1);
				

					for (ApprovalLog approvalLogForAprv : aprvList1) {

						if (count && approvalLogForAprv != null) {
							approvalLogForAprv.setMailstatus(1);
							ApprovalLog approvalLog = sampleRepo.save(approvalLogForAprv);
							System.out.println(approvalLog);

						} else if (count) {
							js.put("code", "1001");
							js.put("error", "Plant Is Not Found For This ");
							json = js.toString();
							return new ResponseEntity<String>(json, HttpStatus.OK);

						}

					}

					count = true;

					System.out.println("sent email***********");
				} else {
					System.out.println("not sent email***************");
				}

				js.put("workflowConfigList", ow.writeValueAsString(workflowConfigList));
				js.put("1000", "workflow started");
				json = js.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);

	
		
		
	}

	@RequestMapping(value = "/pendingAprvList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> pendingAprvList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ID") String userid) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (userid == "" || userid.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "USERID is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			int statusint = 3;
			List<ApprovalLog> pendingAprvList = webService.getPendingApprovalById(userid, statusint);
			if (pendingAprvList == null || pendingAprvList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "pending Approver List Is Empty");
				json = js.toString();
			} else {
				js.put("pendingAprvList", ow.writeValueAsString(pendingAprvList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/storageList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> storageList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("PLANTCODE") String plantcode) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (plantcode == "" || plantcode.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "PLANT CODE is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			int statusint = 0;
			List<Storage> storageList = webService.getStorageListByPlantcode(plantcode, statusint);
			if (storageList == null || storageList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Storage List Is Empty");
				json = js.toString();
			} else {
				js.put("storageList", ow.writeValueAsString(storageList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/uomList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> uomList(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			List<Uom> uomList = webService.getAllUom();
			if (uomList == null || uomList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "UOM List Is Empty");
				json = js.toString();
			} else {
				js.put("uomList", ow.writeValueAsString(uomList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/materialList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> materialList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("MATNR") String matnr, @RequestParam("WERKS") String werks,
			@RequestParam("MAKTX") String maktx, @RequestParam("MTART") String mtart) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (werks.isEmpty() || werks == "") {
				js.put("code", "1002");
				js.put("error", "WERKS is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			if (matnr != "" && werks != "") {

				try {
					List<MaterialMaster> materialList = webService.getAllMaterialsByMatnrAndWerks(matnr, werks);
					if (materialList == null || materialList.isEmpty()) {
						js.put("code", "1001");
						js.put("error", "MATERIAL List Is Empty");
						json = js.toString();
					} else {
						js.put("materialList", ow.writeValueAsString(materialList));
						js.put("code", "1000");
						json = js.toString();
					}
					try {
						json = js.toString();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			else if (matnr != "") {

				try {
					List<MaterialMaster> materialList = webService.getAllMaterialsByMatnrAndWerksAndMtart(matnr, werks,
							mtart);
					if (materialList == null || materialList.isEmpty()) {
						js.put("code", "1001");
						js.put("error", "MATERIAL List Is Empty");
						json = js.toString();
					} else {
						js.put("materialList", ow.writeValueAsString(materialList));
						js.put("code", "1000");
						json = js.toString();
					}
					try {
						json = js.toString();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			else if (maktx != "") {

				try {
					List<MaterialMaster> materialList = webService.getAllMaterialsByMaktxAndMtartAndWerks(maktx, mtart,
							werks);
					if (materialList == null || materialList.isEmpty()) {
						js.put("code", "1001");
						js.put("error", "MATERIAL List Is Empty");
						json = js.toString();
					} else {
						js.put("materialList", ow.writeValueAsString(materialList));
						js.put("code", "1000");
						json = js.toString();
					}
					try {
						json = js.toString();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			else {

				try {
					List<MaterialMaster> materialList = webService.getAllMaterialsByMtartAndWerks(mtart, werks);
					if (materialList == null || materialList.isEmpty()) {
						js.put("code", "1001");
						js.put("error", "MATERIAL List Is Empty");
						json = js.toString();
					} else {
						js.put("materialList", ow.writeValueAsString(materialList));
						js.put("code", "1000");
						json = js.toString();
					}
					try {
						json = js.toString();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>(json, HttpStatus.OK);

	}

	@RequestMapping(value = "/plantList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> plantList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("COMPCODE") String bukrs, @RequestParam("PURORG") String ekorg,
			@RequestParam("PLANT") String werks) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		String plantcode_1 = "";
		String plantcode_2 = "";
		List<Plant> deletionIndexes = new ArrayList<Plant>();
		List<Plant> plantList = new ArrayList<Plant>();
		try {
			if (bukrs == "" && ekorg == "" && werks == "") {

				try {
					plantList = webService.getPlantList();

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (bukrs == "" && ekorg == "" && werks != "") {
				try {
					plantList = webService.getPlantListByPlant(werks);
					//List<Plant> listWithoutDuplicates = plantList1.stream().distinct().collect(Collectors.toList());
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (bukrs != "" && ekorg == "" && werks == "") {
				try {
					plantList = webService.getPlantListByComp(bukrs);

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (bukrs != "" && ekorg != "" && werks == "") {
				try {
					plantList = webService.getPlantListByCompAndOrg(bukrs, ekorg);

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (bukrs != "" && ekorg != "" && werks != "") {
				try {
					plantList = webService.getPlantListByCompAndOrgAndPlant(bukrs, ekorg, werks);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		if (plantList == null || plantList.isEmpty()) {
			js.put("code", "1001");
			js.put("error", "Plant List Is Empty");
			json = js.toString();
		} else {
			
			for(Plant plantData: plantList)
			{
				
				plantcode_1 = plantData.getWerks();
				if (plantcode_1.equalsIgnoreCase(plantcode_2))
				{
					deletionIndexes.add(plantData);
				}
				plantcode_2 = plantcode_1;
				
			}
			plantList.removeAll(deletionIndexes);
			js.put("plantList", ow.writeValueAsString(plantList));
			js.put("code", "1000");
			json = js.toString();
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/storageLocationList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> storageLocationList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("COMPCODE") String bukrs, @RequestParam("PURORG") String ekorg,
			@RequestParam("PLANT") String werks) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		String plantcode_1 = "";
		String plantcode_2 = "";
		List<Plant> deletionIndexes = new ArrayList<Plant>();
		List<Plant> plantList = new ArrayList<Plant>();

		
		try {

		 if (bukrs != "" && ekorg != "" && werks != "") {
				try {
					plantList = webService.getPlantListByCompAndOrgAndPlant(bukrs, ekorg, werks);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		if (plantList == null || plantList.isEmpty()) {
			js.put("code", "1001");
			js.put("error", "Plant List Is Empty");
			json = js.toString();
		} else {
			
			js.put("plantList", ow.writeValueAsString(plantList));
			js.put("code", "1000");
			json = js.toString();
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/plantList1", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> plantList1(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;

		try {
			int statusint = 0;
			List<Plant> plantList = webService.getPlantList(statusint);
			if (plantList == null || plantList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Plant List Is Empty");
				json = js.toString();
			} else {
				js.put("plantList", ow.writeValueAsString(plantList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/purOrgList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> purOrgList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("COMPCODE") String bukrs) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (bukrs == "" || bukrs.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "COMPANY CODE is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			int statusint = 0;
			List<PurOrg> purOrgList = webService.getpurOrgListByComp(bukrs, statusint);
			if (purOrgList == null || purOrgList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Purchase Organisation List Is Empty");
				json = js.toString();
			} else {
				js.put("purOrgList", ow.writeValueAsString(purOrgList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/rfpHeaderList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> rfpHeaderList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("USERID") String usercode) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (usercode == "" || usercode.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "USER ID is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			int statusint = 0;
			List<RFPHeader> rfpHeaderList = webService.getrfpHeaderListByUser(usercode);
			if (rfpHeaderList == null || rfpHeaderList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "RFP Header List Is Empty");
				json = js.toString();
			} else {
				js.put("rfpHeaderList", ow.writeValueAsString(rfpHeaderList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/unReadProposalCount", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> unReadProposalCount(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("USERID") String usercode) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (usercode == "" || usercode.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "USER ID is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			int statusint = 0;
			String readstatus = "P";
			List<VPHeader> vpHeaderList = webService.getvpHeaderListByUser(usercode, readstatus, statusint);
			if (vpHeaderList == null || vpHeaderList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "VP Header List Is Empty");
				json = js.toString();
			} else {
				js.put("rfpHeaderList", ow.writeValueAsString(vpHeaderList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/displayRfpHeader", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> displayRfpHeader(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;

		try {
			List<RFPHeader> rfpHeaderList = webService.getAllRfpHeader();
			if (rfpHeaderList == null || rfpHeaderList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "RFP Header List Is Empty");
				json = js.toString();
			} else {
				js.put("rfpHeaderList", ow.writeValueAsString(rfpHeaderList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/bukrsList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> bukrsList(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			List<PurOrg> purOrgList = webService.getAllPurOrg();
			if (purOrgList == null || purOrgList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Company Code List Is Empty");
				json = js.toString();
			} else {
				js.put("uomList", ow.writeValueAsString(purOrgList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/purGrpList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> purGrpList(HttpServletRequest request, HttpServletResponse response) {
		// @RequestParam("COMPCODE") String bukrs,@RequestParam("PURORG") String ekorg)
		// {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			// int statusint = 0;
			// List<PurOrg> purGrpList =
			// webService.getpurGrpByCompAndOrg(bukrs,ekorg,statusint);
			List<PurOrg> purGrpList = webService.getAllPurOrg();
			if (purGrpList == null || purGrpList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Purchase Group List Is Empty");
				json = js.toString();
			} else {
				js.put("purGrpList", ow.writeValueAsString(purGrpList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/vendorList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> vendorList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("BUKRS") String bukrs, @RequestParam("LIFNR") String lifnr,
			@RequestParam("BEZEI") String bezei, @RequestParam("ORT01") String ort01,
			@RequestParam("NAME1") String name1) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (bukrs == "" || bukrs.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "COMPANY CODE is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (lifnr != "") {
				long lifnr1 = Long.parseLong(lifnr);
				List<VendorRegistration> vendorList = webService.getVendorsByBukrsAndLifnr(bukrs, lifnr1);
				if (vendorList == null || vendorList.isEmpty()) {
					js.put("code", "1001");
					js.put("error", "Vendor List Is Empty");
					json = js.toString();
				} else {
					js.put("vendorList", ow.writeValueAsString(vendorList));
					js.put("code", "1000");
					json = js.toString();
				}
				try {
					json = js.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (name1 != "") {
				List<VendorRegistration> vendorList = webService.getVendorsByBukrsAndName1AndRegionAndCity(name1, bukrs,
						bezei);
				if (vendorList == null || vendorList.isEmpty()) {
					js.put("code", "1001");
					js.put("error", "Vendor List Is Empty");
					json = js.toString();
				} else {
					js.put("vendorList", ow.writeValueAsString(vendorList));
					js.put("code", "1000");
					json = js.toString();
				}
				try {
					json = js.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			else if (ort01 != "") {

				List<VendorRegistration> vendorList = webService.getVendorsByBukrsRegion(bukrs, bezei, ort01);
				if (vendorList == null || vendorList.isEmpty()) {
					js.put("code", "1001");
					js.put("error", "Vendor List Is Empty");
					json = js.toString();
				} else {
					js.put("vendorList", ow.writeValueAsString(vendorList));
					js.put("code", "1000");
					json = js.toString();
				}
				try {
					json = js.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			else {

				List<VendorRegistration> vendorList = webService.getVendorsByBukrsRegion1(bukrs, bezei);
				if (vendorList == null || vendorList.isEmpty()) {
					js.put("code", "1001");
					js.put("error", "Vendor List Is Empty");
					json = js.toString();
				} else {
					js.put("vendorList", ow.writeValueAsString(vendorList));
					js.put("code", "1000");
					json = js.toString();
				}
				try {
					json = js.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			// List<VendorRegistration> vendorList = webService.getAllVendors();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	// ADD EXISTING VENDORS
	@RequestMapping(value = "/activeUsersList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> activeUsersList(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;

		try {
			// int statusint = 0;
			List<VendorRegistration> activeUsersList = webService.getActiveUserListByStatus();
			if (activeUsersList == null || activeUsersList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Active User List is Empty");
				json = js.toString();
			} else {
				js.put("activeUsersList", ow.writeValueAsString(activeUsersList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/listByRfpNo", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> listByRfpNo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("RFPNO") String rfpno) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		// response.headers["X-FRAME-OPTIONS"] = "ALLOW-FROM http://*.bar.com";
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (rfpno.isEmpty() || rfpno == "") {
				js.put("code", "1002");
				js.put("error", "REQNO is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long reqid = 0;
		try {
			reqid = Long.parseLong(rfpno);
		} catch (Exception e) {
			try {
				js.put("code", "1002");
				js.put("error", "RFPNO Is Not Proper");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} catch (Exception e2) {
				e.printStackTrace();
			}
			e.printStackTrace();
		}
		try {

			System.out.println("RFP No:" + reqid);
			RFPHeader rfpHeaderObject = null;
			try {
				rfpHeaderObject = webService.getRFPHeaderDatailsByReqid(reqid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			DocumentType documentType = webService.getDocumentType(rfpHeaderObject.getTransactiontype());
			rfpHeaderObject.setTransactiontype(documentType.getDescp());
			// List<DSItem> dsItemList = webService.getDocumetnItems(reqid);
			List<RFPItem> rfpList = webService.getRFPItemsByReqid(reqid);
			List<RFPInvitedVendors> rfpInvitedVendorsList = webService.getInvitedVendorsByReqid(reqid);
			List<RFPParticipant> rfpParticipantList = webService.getRfpParticipantListByReqid(reqid);
			List<RFPSection> rfpSectionList = webService.getRfpSectionListByReqid(reqid);
			if (rfpHeaderObject == null) {
				js.put("code", "1001");
				js.put("error", "Header Not Found");
				js.put("rfpHeaderObject", ow.writeValueAsString(rfpHeaderObject));
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} else {
				js.put("Header", "Success");
				js.put("rfpHeaderObject", ow.writeValueAsString(rfpHeaderObject));
				// js.put("dsItemList", ow.writeValueAsString(dsItemList));
				js.put("rfpItemObject", ow.writeValueAsString(rfpList));
				js.put("rfpInvitedVendorsObject", ow.writeValueAsString(rfpInvitedVendorsList));
				js.put("rfpParticipantObjectList", ow.writeValueAsString(rfpParticipantList));
				js.put("rfpSectionObject", ow.writeValueAsString(rfpSectionList));
				js.put("code", "1000");
				json = js.toString();
			}
			for (RFPSection rfpSection : rfpSectionList) {
				String fileName = rfpSection.getFilename();
				String pathact = request.getServletContext().getRealPath("/resources/signedPdf");
				File directory = new File(pathact);
				if (!directory.exists()) {
					directory.mkdir();
				}
				// byte[] bytes = rfpSection.getPdffile();
				File serverFile = new File(directory.getAbsolutePath() + File.separator + fileName);
				try {
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					// stream.write(bytes);
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// break;
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/listByProposalpNo", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> listByProposalpNo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("PROPOSALNO") String proposalno) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		// response.headers["X-FRAME-OPTIONS"] = "ALLOW-FROM http://*.bar.com";
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (proposalno.isEmpty() || proposalno == "") {
				js.put("code", "1002");
				js.put("error", "PROPOSALNO is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		VPHeader vpHeaderObject = null;
		long proposalid = 0;
		try {
			proposalid = Long.parseLong(proposalno);
		} catch (Exception e) {
			try {
				js.put("code", "1002");
				js.put("error", "PROPOSALNO Is Not Proper");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} catch (Exception e2) {
				e.printStackTrace();
			}
			e.printStackTrace();
		}
		try {
			vpHeaderObject = webService.getVPHeaderDetailsByProposalno(proposalid);

		} catch (Exception e) {
			e.printStackTrace();
		}
		DocumentType documentType = webService.getDocumentType(vpHeaderObject.getTransactiontype());
		vpHeaderObject.setTransactiontype(documentType.getDescp());
		// List<DSItem> dsItemList = webService.getDocumetnItems(reqid);
		List<VPItem> vpItemList = webService.getVPItemsByProposalno(proposalid);
		// List<VPSection> vpSectionList =
		// webService.getVPSectionListByProposalno(proposalid);
		List<VPGenProposal> vpGenProposalList = webService.getVPGenProposalListByProposalno(proposalid);
		List<VPTCProposal> vpTcProposalList = webService.getVPTCProposalListByProposalno(proposalid);

		try {
			if (vpHeaderObject == null) {
				js.put("code", "1001");
				js.put("error", "Header Not Found");
				js.put("vpHeaderObject", ow.writeValueAsString(vpHeaderObject));
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} else {
				js.put("Header", "Success");
				js.put("vpHeaderObject", ow.writeValueAsString(vpHeaderObject));
				js.put("vpItemObject", ow.writeValueAsString(vpItemList));
				// js.put("vpSectionObject", ow.writeValueAsString(vpSectionList));
				js.put("vpGenProposalObject", ow.writeValueAsString(vpGenProposalList));
				js.put("vpTCProposalObject", ow.writeValueAsString(vpTcProposalList));
				js.put("code", "1000");
				json = js.toString();
			}
			for (VPGenProposal vpGenProposal : vpGenProposalList) {
				String fileName = vpGenProposal.getFilename();
				String pathact = request.getServletContext().getRealPath("/resources/signedPdf");
				File directory = new File(pathact);
				if (!directory.exists()) {
					directory.mkdir();
				}
				// byte[] bytes = vpGenProposal.getPdfFile();
				File serverFile = new File(directory.getAbsolutePath() + File.separator + fileName);
				try {
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					// stream.write(bytes);
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// break;
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}
		try {
			for (VPTCProposal vptcProposal : vpTcProposalList) {
				String fileName = vptcProposal.getFilename();
				String pathact = request.getServletContext().getRealPath("/resources/signedPdf");
				File directory = new File(pathact);
				if (!directory.exists()) {
					directory.mkdir();
				}
				// byte[] bytes = vptcProposal.getPdfFile();
				File serverFile = new File(directory.getAbsolutePath() + File.separator + fileName);
				try {
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					// stream.write(bytes);
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// break;
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}

		return new ResponseEntity<String>(json, HttpStatus.OK);

	}

	@RequestMapping(value = "/proposalList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> proposalList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("RFPNO") String rfpno) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		// response.headers["X-FRAME-OPTIONS"] = "ALLOW-FROM http://*.bar.com";
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (rfpno.isEmpty() || rfpno == "") {
				js.put("code", "1002");
				js.put("error", "RFPNO is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		VPHeader vpHeaderObject = null;
		long rfpid = 0;
		try {
			rfpid = Long.parseLong(rfpno);
		} catch (Exception e) {
			try {
				js.put("code", "1002");
				js.put("error", "RFPNO Is Not Proper");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} catch (Exception e2) {
				e.printStackTrace();
			}
			e.printStackTrace();
		}
		try {
			vpHeaderObject = webService.getVPHeaderDetailsByProposalno(rfpid);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// DocumentType documentType =
		// webService.getDocumentType(vpHeaderObject.getTransactiontype());
		// vpHeaderObject.setTransactiontype(documentType.getDescp());
		// List<DSItem> dsItemList = webService.getDocumetnItems(reqid);
		List<VPItem> vpItemList = webService.getVPItemsByProposalno(rfpid);
		// List<VPSection> vpSectionList =
		// webService.getVPSectionListByProposalno(rfpid);
		List<VPGenProposal> vpGenProposalList = webService.getVPGenProposalListByProposalno(rfpid);
		List<VPTCProposal> vpTcProposalList = webService.getVPTCProposalListByProposalno(rfpid);

		try {
			if (vpHeaderObject == null) {
				js.put("code", "1001");
				js.put("error", "Header Not Found");
				js.put("vpHeaderObject", ow.writeValueAsString(vpHeaderObject));
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} else {
				js.put("Header", "Success");
				js.put("vpHeaderObject", ow.writeValueAsString(vpHeaderObject));
				js.put("vpItemObject", ow.writeValueAsString(vpItemList));
				// js.put("vpSectionObject", ow.writeValueAsString(vpSectionList));
				js.put("vpGenProposalObject", ow.writeValueAsString(vpGenProposalList));
				js.put("vpTCProposalObject", ow.writeValueAsString(vpTcProposalList));
				js.put("code", "1000");
				json = js.toString();
			}
			for (VPGenProposal vpGenProposal : vpGenProposalList) {
				String fileName = vpGenProposal.getFilename();
				String pathact = request.getServletContext().getRealPath("/resources/signedPdf");
				File directory = new File(pathact);
				if (!directory.exists()) {
					directory.mkdir();
				}
				// byte[] bytes = vpGenProposal.getPdfFile();
				File serverFile = new File(directory.getAbsolutePath() + File.separator + fileName);
				try {
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					// stream.write(bytes);
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// break;
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}
		try {
			for (VPTCProposal vptcProposal : vpTcProposalList) {
				String fileName = vptcProposal.getFilename();
				String pathact = request.getServletContext().getRealPath("/resources/signedPdf");
				File directory = new File(pathact);
				if (!directory.exists()) {
					directory.mkdir();
				}
				// byte[] bytes = vptcProposal.getPdfFile();
				File serverFile = new File(directory.getAbsolutePath() + File.separator + fileName);
				try {
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					// stream.write(bytes);
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// break;
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}

		return new ResponseEntity<String>(json, HttpStatus.OK);

	}

	@RequestMapping(value = "displayRfpList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> displayRfpList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("VENDOR") String vendor) {

		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {

			List<RFPHeader> rfpHeaderList1 = new ArrayList<>();
			List<RFPInvitedVendors> rfpInvitedVendorsList1 = webService.getRfpInvitedVendors(vendor);
			for (RFPInvitedVendors rfpInvitedVendors : rfpInvitedVendorsList1) {
				RFPHeader rfpHeader1 = webService.getRfpHeader(rfpInvitedVendors);
				rfpHeaderList1.add(rfpHeader1);
			}

			/*
			 * 
			 * List<DSItem> dsItemsApproversList =
			 * webService.getRequestedApproval(approverTo, signedStatus); List<RFPHeader>
			 * rfpHeaderList = new ArrayList<>();
			 * 
			 * //List<ApproverMaster> approverList = webService.getAllApprovers(status);
			 * List<RFPInvitedVendors> rfpInvitedVendorsList =
			 * webService.getRfpInvitedVendors(vendor); for (RFPInvitedVendors
			 * rfpInvitedItem : rfpInvitedVendorsList) { //dsHeadersList =
			 * webService.getDsHeaderListByReqid(dsItem.getReqid(), status);
			 * //dsHeadersAllReaquestList.addAll(dsHeadersList); //dsDocumentsItemsList =
			 * webService.getDsDocumentItemsByReqid(dsItem.getReqid()); RFPInvitedVendors
			 * rfpHeaderVendor = webService.getRfpHeaderbyRfpno(rfpInvitedItem);
			 * rfpHeaderList.add(rfpHeaderVendor);
			 * 
			 * // UserMaster user = webService.getUserByApproverMaster(approverMaster);
			 * //userMasters.add(user);
			 * 
			 * 
			 * //rfpHeaderList = webService.getRfpHeaderbyRfpno(); List<UserMaster>
			 * userMasters = new ArrayList<>(); List<ApproverMaster> approverList =
			 * webService.getAllApprovers(status); for (ApproverMaster approverMaster :
			 * approverList) { UserMaster user =
			 * webService.getUserByApproverMaster(approverMaster); userMasters.add(user); }
			 */

			if (rfpHeaderList1 == null || rfpHeaderList1.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "RFP List Not Found");
				json = js.toString();
			} else {
				js.put("rfpHeaderList", ow.writeValueAsString(rfpHeaderList1));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@SuppressWarnings("null")
	@RequestMapping(value = "/maillNotification", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> maillNotification(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("USERID") long userId, @RequestParam("REQID") long reqid) throws JSONException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		String res1 = null;
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String res = null;

		if (userId == 0) {

			js.put("code", "1001");
			js.put("error", "APPID is Empty");
			res = js.toString();
			return new ResponseEntity<String>(res, HttpStatus.OK);

		} else if (reqid == 0) {
			js.put("code", "1002");
			js.put("error", "REQID is Empty");
			res = js.toString();
			return new ResponseEntity<String>(res, HttpStatus.OK);

		} else {

			List<ApprovalLog> appList = sampleRepo.getApprovalLogListByReqidAndAprv(reqid, 3);
			ApprovalLog approvalLog = null;

			// String appr=masterRepo.findById(userId).get().getEmail();

			String appr = masterRepo.findOne(userId).getEmail();
			boolean econf = emailNotification(appr);

			for (ApprovalLog i : appList) {
				// for (int i = 1; i<=appList.size();i) {
				boolean econf1 = false;
				if (i.getMailstatus() == 3) {

//					 econf1=email.emailNotification(masterRepo.findById(i.getAprv()).get().getEmail());
					System.out.println("i:" + i);
					System.out.println("appList:" + appList);
					econf1 = emailNotification(masterRepo.findOne(i.getAprv()).getEmail());
					js.put("code", "1003");
					js.put("sucess", "EMAIL is sent");
					res = js.toString();
					return new ResponseEntity<String>(res, HttpStatus.OK);

				}

				if (econf || econf1) {
					approvalLog = sampleRepo.getApprovalLogByReqNoAndAprv(reqid, i.getAprv());

					approvalLog.setMailstatus(1);
					approvalLog = sampleRepo.save(approvalLog);

					js.put("code", "1001");
					js.put("sucess", "approve is success");
					res = js.toString();
					return new ResponseEntity<String>(res, HttpStatus.OK);
				}

			}
		}

		return new ResponseEntity<String>("Approved Successully", HttpStatus.OK);

	}

	private String sendUnAutheriedApproverMail(String requestId, String email) {

		System.out.println("not Autenticated Approver");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", "webmail.srl.in");// 10.1.4.100
		properties.put("mail.smtp.port", "587");
		javax.mail.Session session = javax.mail.Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("srl-intelligence@srl.in", "Inteli#007"); // //Welcome@123
			}
		});
		String subjectLine = "Digital Signature Approve Req.ID:" + requestId;
		try {
			String MsgText = "<html><body><font color=black>Hello,</b>" + "<br>"
					+ "<br>VMS has been rejected(Invalid Approver)." + "<br>" + "</body></html>";

			try {
				Message replyMessage = new MimeMessage(session);
				replyMessage.setSubject(subjectLine);
				replyMessage.setFrom(new InternetAddress("srl-intelligence@srl.in", "VMS"));
				replyMessage.setContent(MsgText, "text/html");
				replyMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));

				try {
					BodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setText(MsgText);
					// Create a multipar message
					Multipart multipart = new MimeMultipart();
					MimeBodyPart messageBodyP = new MimeBodyPart();
					messageBodyP.setContent(MsgText, "text/html");
					multipart.addBodyPart(messageBodyP);
					replyMessage.setContent(multipart);
					try {
						Transport.send(replyMessage);
						System.out.println("Reject Email Send successfully ....");
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			} catch (Exception e3) {
				e3.printStackTrace();
			}

		} catch (Exception e4) {
			e4.printStackTrace();
		}

		return "succcess";
	}

	public boolean emailNotification(String appr) {


		try {
			// String certificatesTrustStorePath = "<JAVA HOME>/jre/lib/security/cacerts";
			// System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "webmail.srl.in");
			props.put("mail.smtp.port", "587");
			props.put("mail.debug", "true");
			// props.put("mail.smtp.ssl", "false");
			props.put("mail.smtp.starttls.enable", "false");
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("srl-intelligence@srl.in", "inteli#007");
				}
			});

			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("srl-intelligence@srl.in"));
				msg.setRecipients(Message.RecipientType.TO, appr);// anuj.surana@ssll.in
				msg.setSubject(
						"Vendor Management System Req.ID:\"+requesId+\" \" + \"[\" + \" \" + uploader + \" \" + \"]For Approval");
				// System.out.println("****");
				msg.setSubject("Vendor Management System Req.ID:\"+requesId+\" [ Admin ]For Approval");
				msg.setSentDate(new Date());
				Multipart multipart = new MimeMultipart();
				MimeBodyPart htmlPart = new MimeBodyPart();
				String htmlContent = "<html><body><font color=black>Hello,</b>" + "<br>"
						+ "<br>+fileName has been sent for your approval, created by +uploader +senderMailId" + "<br>"
						+ "<br><b>Purpose : Vendor Management System</b>" + "<br>"
						+ "<br><b>You are requested to review the attached document and proceed on Approval process for Vendor Management System.</b>"
						+ "<br>"
						+ "<br><a href='mailLink'>Approve :</a></b>  The document will be approved and sent to next level approvers if any further level. If this final level, mail will be notified to concern person."
						+ "<br>"
						+ "<br><a href='mailLink'>Reject :</a></b>  Please note, this will reject the document and send it back to the creator. Approvals which have already happened will be reset. "
						+ "<br>"
						+ "<br><a href='mailLink'>Clarification :</a></b>  The document will be approved and sent to next level approvers if any further level. If this final level, mail will be notified to concern person."
						+ "<br>"
						+ "<br><a href='mailLink'>Accept & Forward :</a></b>  The document will be approved and sent to next level approvers if any further level. If this final level, mail will be notified to concern person."
						+ "<br>" + "</font></body></html>";
				htmlPart.setContent(htmlContent, "text/html");
				multipart.addBodyPart(htmlPart);
				msg.setContent(multipart);
				Transport.send(msg);
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		} finally {
			System.out.println("In final block!");
		}

	
	}

	@RequestMapping(value = "/criteriaListByRfpno", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> criteriaListByRfpno(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("RFPNO") String rfpno) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (rfpno == "" || rfpno.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "RFPNO is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long rfpnum = Long.parseLong(rfpno);
		try {
			int statusint = 0;
			List<TechnoCriteria> criteriaList = webService.getCriteriaListByRfpno(rfpnum);
			if (criteriaList == null || criteriaList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Techno commercial Criteria List Is Empty");
				json = js.toString();
			} else {
				js.put("criteriaList", ow.writeValueAsString(criteriaList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/invitedVendorList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> invitedVendorList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("RFPNO") String rfpno) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (rfpno == "" || rfpno.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "RFPNO is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long rfpnum = Long.parseLong(rfpno);
		try {
			int statusint = 0;
			List<RFPParticipant> invitedVendorList = webService.getInvitedVendorListByRfpno(rfpnum);
			if (invitedVendorList == null || invitedVendorList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Invited vendor List Is Empty");
				json = js.toString();
			} else {
				js.put("invitedVendorList", ow.writeValueAsString(invitedVendorList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/ratingListByRfpVend", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> ratingListByRfpVend(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("RFPNO") String rfpno, @RequestParam("VENDOR") String vendor,
			@RequestParam("USERID") String user) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (rfpno == "" || rfpno.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "RFPNO is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (vendor == "" || vendor.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "VENDOR is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		long rfpnum = Long.parseLong(rfpno);
		if(user == "" || user.isEmpty()){
			try {
				//int statusint = 0;
				List<TechnoRatings> ratingList = webService.getRatingListByRfpVend(rfpnum, vendor);
				if (ratingList == null || ratingList.isEmpty()) {
					js.put("code", "1001");
					js.put("error", "Techno commercial Rating List Is Empty");
					json = js.toString();
				} else {
					js.put("ratingList", ow.writeValueAsString(ratingList));
					js.put("code", "1000");
					json = js.toString();
				}
				try {
					json = js.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();

			}
		} else {
			try {
				//int statusint = 0;
				List<TechnoRatings> ratingList = webService.getRatingListByRfpVendCreatedBy(rfpnum, vendor, user);
				if (ratingList == null || ratingList.isEmpty()) {
					js.put("code", "1001");
					js.put("error", "Techno commercial Rating List Is Empty");
					json = js.toString();
				} else {
					js.put("ratingList", ow.writeValueAsString(ratingList));
					js.put("code", "1000");
					json = js.toString();
				}
				try {
					json = js.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@CrossOrigin("*")
	@RequestMapping(value = "/mailNotification", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> approver(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> aprvData)
			throws JSONException, JsonGenerationException, JsonMappingException, IOException

	{

		String userIdNo = (String) aprvData.get("USERID");
		String reqid1 = String.valueOf(aprvData.get("REQID"));
		String appid = String.valueOf(aprvData.get("APPID"));
		String remark = String.valueOf(aprvData.get("REMARK"));
		String subProcess = String.valueOf(aprvData.get("SUBPROC"));

		int status = Integer.parseInt(String.valueOf(aprvData.get("STATUS")));
		ArrayList<Map<String, String>> iteamList = (ArrayList<Map<String, String>>) aprvData.get("ITEMS");

		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		String res1 = null;
		boolean count = true;
		String dept = "";
		Status statusObj = null;
		ArrayList<String> emailList = new ArrayList<String>();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String res = null;
		Long reqid = Long.parseLong(reqid1);
		// System.out.println("************************"+sampleRepo.getWorkflowConfigByAppid(appid));

		if (userIdNo == null || userIdNo == "") {

			js.put("code", "1001");
			js.put("error", "APPID is Empty");
			res = js.toString();
			return new ResponseEntity<String>(res, HttpStatus.OK);

		} else if (appid == "" && appid.isEmpty() && appid.equals(null)) {
			js.put("code", "1002");
			js.put("error", "APPID is Empty");
			res = js.toString();
			return new ResponseEntity<String>(res, HttpStatus.OK);

		} else if (reqid == 0) {
			js.put("code", "1002");
			js.put("error", "REQID is Empty");
			res = js.toString();
			return new ResponseEntity<String>(res, HttpStatus.OK);

		} else if (status == 0) {
			js.put("code", "1002");
			js.put("error", "STATUS is Empty");
			res = js.toString();
			return new ResponseEntity<String>(res, HttpStatus.OK);

		} else {

			// PR ack item Level
			if (appid.equalsIgnoreCase("PR") || appid == "PR") {
				String returnPR = "not working";
				try {
					returnPR = approverServices.prAcknowledgementMail(userIdNo, reqid, appid, status, remark,
							subProcess);

				} catch (org.json.JSONException e) {

					e.printStackTrace();
				}
				return new ResponseEntity<String>(returnPR, HttpStatus.OK);

			}

			// ASN ACK
			else if (appid.equalsIgnoreCase("ASN") || appid == "ASN") {
				String returnASN = "not working";
				try {
					returnASN = approverServices.asnAcknowledgementMail(userIdNo, reqid, appid, status, remark);

				} catch (org.json.JSONException e) {

					e.printStackTrace();
				}
				return new ResponseEntity<String>(returnASN, HttpStatus.OK);

			}
			
			
			
			//
			else if (appid.equalsIgnoreCase("NONPOINV") || appid == "NONPOINV") {
				String returnASN = "not working";
				try {
					returnASN = approverServices.nonPoInvAcknowledgementMail(userIdNo, reqid, appid, status, remark);

				} catch (org.json.JSONException e) {

					e.printStackTrace();
				}
				return new ResponseEntity<String>(returnASN, HttpStatus.OK);

			}

			// PO ACk

			else if (appid.equalsIgnoreCase("PO") || appid == "PO") {
				String returnPo = "not working";
				try {
					returnPo = approverServices.poAcknowledgementMail(userIdNo, reqid, appid, status, remark);

				} catch (org.json.JSONException e) {

					e.printStackTrace();
				}
				return new ResponseEntity<String>(returnPo, HttpStatus.OK);

			}

			// VM ACK
			else if (appid.equalsIgnoreCase("VM") || appid == "VM") {
				String returnVM = "not working";
				try {
					returnVM = approverServices.vmAcknowledgementMail(userIdNo, reqid, appid, status,
							remark);

				} catch (org.json.JSONException e) {

					e.printStackTrace();
				}
				return new ResponseEntity<String>(returnVM, HttpStatus.OK);

			}
			// RFPVS OR TC
			else if (appid.equalsIgnoreCase("RFPVS") || appid == "RFPVS") {

				String returnVM = "not working";
				try {
					returnVM = approverServices.rfpvsAcknowledgementMail(userIdNo, reqid, appid, status, remark);

				} catch (org.json.JSONException e) {

					e.printStackTrace();
				}
				return new ResponseEntity<String>(returnVM, HttpStatus.OK);

			}

			else {
				js.put("code", "1001");
				js.put("error", "Invalid Entry you are entered");
				res = js.toString();
				return new ResponseEntity<String>(res, HttpStatus.OK);
			}

		}

	}

	public boolean emailNotification1(String appr, String body, String subject) {
		try {
//String certificatesTrustStorePath = "<JAVA HOME>/jre/lib/security/cacerts";
//System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);

			System.out.println(appr);
			long a = Long.parseLong(appr);
			String aprv = masterRepo.findOne(a).getEmail();
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "webmail.srl.in");
//			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.port", "25"); // 465

			props.put("mail.debug", "true");
//props.put("mail.smtp.ssl", "false");
			props.put("mail.smtp.starttls.enable", "false");
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("srl-intelligence@srl.in", "inteli#007");
				}
			});

			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("srl-intelligence@srl.in"));
				msg.setRecipients(Message.RecipientType.TO, aprv);// anuj.surana@ssll.in
				msg.setSubject(subject);
				System.out.println("****");
				// msg.setSubject("Vendor Management System Req.ID:10020 [ Admin ]For
				// Approval");
				msg.setSentDate(new Date());
				Multipart multipart = new MimeMultipart();

				MimeBodyPart htmlPart = new MimeBodyPart();

				String htmlContent = "<html><body><font color=black>Hello,</b>" + "<br>" + "<br>" + body + "<br>"
						+ "<br><a href='localhost:8080/mailNotification'>Login :</a> </b>" // change the ui according to
																							// the data \r\n" +

						+ "<br>+fileName has been sent for your approval, created by +uploader +senderMailId" + "<br>"
//						+ "<br><b>Purpose : Vendor MManagement System</b>" + "<br>"
//						+ "<br><b>You are requested to review the attached document and proceed on Approval process for Vendor Management System.</b>"
//						+ "<br>"
						+ "<br><a href='mailLink'>Login :</a></b>"
//						+ "The document will be approved and sent to next level approvers if any further level. If this final level, mail will be notified to concern person."
//						+ "<br>"
//						+ "<br><a href='mailLink'>Reject :</a></b>  Please note, this will reject the document and send it back to the creator. Approvals which have already happened will be reset. "
						+ "</font></body></html>";
				htmlPart.setContent(htmlContent, "text/html");
				multipart.addBodyPart(htmlPart);
				msg.setContent(multipart);
				Transport.send(msg);
				System.out.println("---yep mails send---");
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		} finally {
			System.out.println("runnnnnning *********");
		}
	}

	public boolean emailNotificationPO(String appr, String body, String subject, String ccEmail) {

		try {
//String certificatesTrustStorePath = "<JAVA HOME>/jre/lib/security/cacerts";
//System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
//			String aprv = masterRepo.findByAdrid((appr)).getEmail();
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "webmail.srl.in");
			props.put("mail.smtp.port", "587");
			props.put("mail.debug", "true");
//props.put("mail.smtp.ssl", "false");
			props.put("mail.smtp.starttls.enable", "false");
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("srl-intelligence@srl.in", "inteli#007");
				}
			});

			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("srl-intelligence@srl.in"));
				msg.setRecipients(Message.RecipientType.TO, appr);// anuj.surana@ssll.in
				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail));

				msg.setSubject(subject);
				System.out.println("****");
				// msg.setSubject("Vendor Management System Req.ID:10020 [ Admin ]For
				// Approval");
				msg.setSentDate(new Date());
				Multipart multipart = new MimeMultipart();

				MimeBodyPart htmlPart = new MimeBodyPart();

				String htmlContent = "<html><body><font color=black>Hello,</b>" + "<br>" + "<br>" + body + "<br>"
						+ "<br><a href='localhost:8080/mailNotification'>Login :</a> </b>" // change the ui according to
																							// the data \r\n" +

						+ "<br>+fileName has been sent for your approval, created by +uploader +senderMailId" + "<br>"
//						+ "<br><b>Purpose : Vendor MManagement System</b>" + "<br>"
//						+ "<br><b>You are requested to review the attached document and proceed on Approval process for Vendor Management System.</b>"
//						+ "<br>"
						+ "<br><a href='mailLink'>Login :</a></b>"
//						+ "The document will be approved and sent to next level approvers if any further level. If this final level, mail will be notified to concern person."
//						+ "<br>"
//						+ "<br><a href='mailLink'>Reject :</a></b>  Please note, this will reject the document and send it back to the creator. Approvals which have already happened will be reset. "
						+ "</font></body></html>";
				htmlPart.setContent(htmlContent, "text/html");
				multipart.addBodyPart(htmlPart);
				msg.setContent(multipart);
				Transport.send(msg);
				System.out.println("---yep mails send---");
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		} finally {
			System.out.println("runnnnnning *********");
		}

	}

	@GetMapping("/contreg/contract")
	@CrossOrigin("*")
	public List<Contract> getContracts() throws SQLException {
		return webService.getAllContracts();
	}

	@GetMapping("/contreg/contract/metadata")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getContractBasicDetails() throws SQLException {
		return webService.getAllContractsMetaData();
	}

	/*
	 * @GetMapping("/contreg/contract/metadata/{sessionid}")
	 * 
	 * @CrossOrigin("*") public List<HashMap<String, Object>>
	 * getContractBasicDetailsBySessionid(@PathVariable String sessionid) throws
	 * SQLException { return
	 * webService.getContractBasicDetailsBySessionid(sessionid); }
	 */
	@GetMapping("/contreg/contract/metadata/{sessionid}")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getContractHeaderBySessionid(@PathVariable String sessionid)
			throws SQLException {
		return webService.getContractHeaderBySessionid(sessionid);
	}

	@GetMapping("/contreg/contract/metadataForInvByAndCrdby/{sessionid}")
	@CrossOrigin("*")
	public List<HashMap<String, Object>> getContractBasicDetailsBySessionid(@PathVariable String sessionid)
			throws SQLException {
		return webService.getContractBasicDetailsBySessionid(sessionid);
	}

	@GetMapping("/contreg/contract/type/metadata")
	@CrossOrigin("*")
	public List<Object> getContractsListByTypes() throws SQLException {
		return webService.getMetaDataByType();
	}

	@GetMapping(value = "/contreg/contract/{contractId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public Contract getContract(@PathVariable long contractId) throws SQLException {
		return webService.getContract(contractId);
	}

	@GetMapping(value = "/contreg/contract/type/{contractType}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	public List<Contract> getContract(@PathVariable String contractType) throws SQLException {
		return webService.getContractsByType(contractType);
	}

	@PostMapping("/contreg/contract")
	@CrossOrigin("*")
	public HashMap<String, Object> addContract(@RequestBody Contract contract) throws SerialException, SQLException {
		return webService.addContract(contract);
	}

	@PutMapping("/contreg/contract")
	@CrossOrigin("*")
	public HashMap<String, Object> updateContract(@RequestBody Contract contract) throws SerialException, SQLException {
		return webService.updateContract(contract);
	}

	/*
	 * @RequestMapping(value = "/poItemList", method = RequestMethod.POST, produces
	 * = "application/json") public ResponseEntity<String>
	 * poItemList(HttpServletRequest request, HttpServletResponse response,
	 * 
	 * @RequestParam("ID") String purchaseOrderId) throws JSONException {
	 * 
	 * response.setHeader("Access-Control-Allow-Origin", "*"); JSONObject js = new
	 * JSONObject(); ObjectWriter ow = new
	 * ObjectMapper().writer().withDefaultPrettyPrinter(); String json = null;
	 * 
	 * try { PurchaseOrder purchaseOrder =
	 * webService.getpurchaseOrderById(Integer.parseInt(purchaseOrderId));
	 * 
	 * js.put("poNo",purchaseOrder.getEbeln());// Getting po no
	 * js.put("companyCode",purchaseOrder.getBukrs()); // Getting Company code
	 * js.put("cntr_no",purchaseOrder.getCntr_no()); //Getting contract no
	 * js.put("aedat",purchaseOrder.getAedat());// can not find PO creation date
	 * field js.put("vendCode",purchaseOrder.getLifnr());
	 * 
	 * js.put("pogrp",purchaseOrder.getEkgrp());// Getting Purchase org
	 * js.put("poDocType",purchaseOrder.getTransactiontype()); // Getting Doc Type
	 * 
	 * // Getting Vendor no this will only give Vendor No if vendor no and name is
	 * required please provide vendor relationship
	 * 
	 * List<POItemDetails> pOItemDetails =
	 * webService.pOItemDetailsListbasedOnPurchaseOrderId(Integer.parseInt(
	 * purchaseOrderId));
	 * 
	 * List<JSONObject> jsItemList = new ArrayList<JSONObject>(); for(POItemDetails
	 * pOItemDetails1:pOItemDetails){ JSONObject jsItem = new JSONObject();
	 * jsItem.put("itemno",pOItemDetails1.getIteamNo());
	 * jsItem.put("materialno",pOItemDetails1.getMatNo());
	 * jsItem.put("plant",pOItemDetails1.getPlant());
	 * jsItem.put("storageloc",pOItemDetails1.getStorageLoc());
	 * jsItem.put("materialgrp",pOItemDetails1.getMatGroup());
	 * jsItem.put("poqty",pOItemDetails1.getPoQty());
	 * jsItem.put("netprice",pOItemDetails1.getToNetPrice());
	 * jsItem.put("eindt",pOItemDetails1.getEindt()); jsItemList.add(jsItem); }
	 * js.put("jsItemList",jsItemList); json = js.toString(); return new
	 * ResponseEntity<String>(json, HttpStatus.OK);
	 * 
	 * } catch (Exception e) { js.put("code", "1001"); js.put("error",
	 * "Error Fetching Purachase Order"); json = js.toString(); return new
	 * ResponseEntity<String>(json, HttpStatus.OK); } }
	 * 
	 */

	@RequestMapping(value = "/firstPartyList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> firstPartyList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("PLANT") String werks) {
		// @RequestParam("PLANT") List<String> plant) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (werks == "" || werks.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "PLANT is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			int statusint = 0;
			List<FirstParty> firstPartyList = webService.getfirstPartyListByPlant(werks);
			if (firstPartyList == null || firstPartyList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "First Party List Is Empty");
				json = js.toString();
			} else {
				js.put("firstPartyList", ow.writeValueAsString(firstPartyList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/secondPartyList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> secondPartyList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("PARTNER") String lifnr) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (lifnr == "" || lifnr.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "PARTNER is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			int statusint = 0;
			List<SecondParty> secondPartyList = webService.getsecondPartyListByPartner(lifnr);
			if (secondPartyList == null || secondPartyList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Second Party List Is Empty");
				json = js.toString();
			} else {
				js.put("secondPartyList", ow.writeValueAsString(secondPartyList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/rfpItemListByMatnr", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> rfpItemListByMatnr(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("MATNR") String matnr) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (matnr == "" || matnr.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "MATNR is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			int statusint = 0;
			List<RFPItem> rfpItemListByMatnr = webService.getrfpItemListByMatnr(matnr);
			if (rfpItemListByMatnr == null || rfpItemListByMatnr.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "RFP Item List Is Empty");
				json = js.toString();
			} else {
				js.put("rfpItemListByMatnr", ow.writeValueAsString(rfpItemListByMatnr));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/getrfpItemListByWerks", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> getrfpItemListByWerks(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("WERKS") String werks) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (werks == "" || werks.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "PLANT is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			int statusint = 0;
			List<RFPItem> getrfpItemListByWerks = webService.getrfpItemListByWerks(werks);
			if (getrfpItemListByWerks == null || getrfpItemListByWerks.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "RFP Item List Is Empty");
				json = js.toString();
			} else {
				js.put("getrfpItemListByWerks", ow.writeValueAsString(getrfpItemListByWerks));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/vendorLedgerList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> vendorLedgerList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("LIFNR") String lifnr, @RequestParam("BUDAT") String budat,
			@RequestParam("GJAHR") String gjahr) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			if (lifnr == "" || lifnr.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "Company Code is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (budat == "" || budat.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "Posting Date is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (gjahr == "" || budat.isEmpty()) {
				js.put("code", "1002");
				js.put("error", "Fiscal year is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			int statusint = 0;
			List<VendorLedger> vendorLedgerList = webService.getvendorLedgerByLifnrGjahrBudat(lifnr, budat, gjahr);
			if (vendorLedgerList == null || vendorLedgerList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Vendor Ledger List Is Empty");
				json = js.toString();
			} else {
				js.put("vendorLedgerList", ow.writeValueAsString(vendorLedgerList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/createPlant", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> createPlant(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("COMPCODE") String bukrs, @RequestParam("PLANT") String werks,
			@RequestParam("ADR") String city1, @RequestParam("DESC") String desc, @RequestParam("PURORG") String ekorg,
			@RequestParam("LGORT") String lgort, @RequestParam("LGOBE") String lgobe,
			@RequestParam("EMAIL") String emailid, @RequestParam("ID") String plantid,
			@RequestParam("SESID") String sesId) throws JSONException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		Plant plant = new Plant();
		UserMaster createdBy = null;
		UserMaster changedBy = null;
		String ardid = null;
		String crby = null;
		String cdby = null;
		String cradrid = null;
		String cdadrid = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			if (bukrs.isEmpty() || bukrs == "") {
				js.put("code", "1002");
				js.put("error", "BUKRS is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (werks.isEmpty() || werks == "") {
				js.put("code", "1002");
				js.put("error", "WERKS Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (desc.isEmpty() || desc == "") {
				js.put("code", "1002");
				js.put("error", "DESC Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (city1.isEmpty() || city1 == "") {
				js.put("code", "1002");
				js.put("error", "ADDRESS Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			if (ekorg.isEmpty() || ekorg == "") {

				js.put("code", "1002");
				js.put("error", "Purchase Organisation Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
			if (lgort.isEmpty() || lgort == "") {

				js.put("code", "1002");
				js.put("error", "Storage Location Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Plant plantObject = null;

		if (plantid == null || plantid.isEmpty() || plantid == "") {
			try {

				try {
					long sesid = Long.parseLong(sesId);
					List<SessionsTable> sessionsTableList = webService.getAllSessions();
					for (SessionsTable sessionsTable2 : sessionsTableList) {
						if (sessionsTable2.getSesid() == sesid) {
							ardid = sessionsTable2.getAdrid();
						}
					}
					if (ardid == null) {
						js.put("error", "Session Expired");
						json = js.toString();
						return new ResponseEntity<String>(json, HttpStatus.OK);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				UserMaster approverTo = null;
				try {
					createdBy = webService.getUserDetails(ardid);
					System.out.println("CREATEDBY:" + createdBy.getId());
					crby = Long.toString(createdBy.getId());
					cradrid = createdBy.getAdrid();
					System.out.println("CREATEDDATE:" + today.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}

				plant.setBukrs(bukrs);
				plant.setWerks(werks);
				plant.setCity1(city1);
				plant.setName1(desc);
				plant.setEkorg(ekorg);
				plant.setLgort(lgort);
				// plant.setStatus(statusObject);
				// plant.setInactive(1);
				plant.setCrdat(today.toString());
				plant.setEmailid(emailid);
				plant.setCreatedBy(cradrid);
				plant.setLgobe(lgobe);
				plantObject = webService.createPlant(plant);

				try {
					if (plantObject == null) {
						js.put("code", "1001");
						js.put("error", "Plant not created");
						json = js.toString();
						return new ResponseEntity<String>(json, HttpStatus.OK);
					} else {
						js.put("success", "Plant created Successfully");
						js.put("plantObject", ow.writeValueAsString(plantObject));
						js.put("code", "1000");
						json = js.toString();
					}
					try {
						json = js.toString();
					} catch (Exception e) {
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e);
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {

				try {
					long sesid = Long.parseLong(sesId);
					List<SessionsTable> sessionsTableList = webService.getAllSessions();
					for (SessionsTable sessionsTable2 : sessionsTableList) {
						if (sessionsTable2.getSesid() == sesid) {
							ardid = sessionsTable2.getAdrid();
						}
					}
					if (ardid == null) {
						js.put("error", "Session Expired");
						json = js.toString();
						return new ResponseEntity<String>(json, HttpStatus.OK);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				UserMaster approverTo = null;
				try {
					changedBy = webService.getUserDetails(ardid);
					System.out.println("CHANGEDBY:" + changedBy.getId());
					cdby = Long.toString(changedBy.getId());

					cdadrid = changedBy.getAdrid();

					System.out.println("CHANGEDDATE:" + today.toString());
					Plant plantObject1 = webService.getPlantById(Integer.parseInt(plantid));
					Plant plant1 = new Plant();
					plant1.setId(Integer.parseInt(plantid));
					plant1.setBukrs(bukrs);
					plant1.setWerks(werks);
					plant1.setCity1(city1);
					plant1.setName1(desc);
					plant1.setEmailid(emailid);
					plant1.setEkorg(ekorg);
					plant1.setLgort(lgort);
					// plant.setStatus(statusObject);
					// plant1.setInactive(1);
					// Date date1 = formatter.parse(crdat);
					// System.out.println("Dateeeeeee:"+date1);
					plant1.setCrdat(plantObject1.getCrdat());
					plant1.setCddat(today.toString());
					plant1.setCreatedBy(plantObject1.getCreatedBy());
					plant1.setChangeddBy(cdadrid);
					plant1.setLgobe(lgobe);
					Plant plantObject2 = webService.createPlant(plant1);
					System.out.println("Plant is updated");
					js.put("Success", "Plant updated Successfully");
					js.put("PlantObject", ow.writeValueAsString(plant1));
					js.put("code", "1000");
					json = js.toString();

				} catch (Exception e) {
					js.put("code", "1001");
					js.put("error", "Plant is Not Updated");
					json = js.toString();
					return new ResponseEntity<String>(json, HttpStatus.OK);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/deptList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> deptList(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			List<Department> deptList = webService.getAllDepartments();
			if (deptList == null || deptList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Department List Is Empty");
				json = js.toString();
			} else {
				js.put("deptList", ow.writeValueAsString(deptList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/currencyList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> currencyList(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			List<Currency> currencyList = webService.getAllCurrency();
			if (currencyList == null || currencyList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "Currency List Is Empty");
				json = js.toString();
			} else {
				js.put("deptList", ow.writeValueAsString(currencyList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/userMasterListById", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> userMasterListById(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ID") String userid) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		long uid = Long.parseLong(userid);

		try {
			if (userid.isEmpty() || userid == "") {
				js.put("code", "1002");
				js.put("error", "UserId Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			List<UserMaster> userMasterList = webService.getUsersById(uid);
			if (userMasterList == null || userMasterList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "User List Is Empty");
				json = js.toString();
			} else {
				js.put("userMasterList", ow.writeValueAsString(userMasterList));
				js.put("code", "1000");
				json = js.toString();
			}
			try {
				json = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	@RequestMapping(value = "/userMasterList", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> userMasterList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("BPTYPE") String bptype) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		int status = 0;
		try {
			if (bptype.isEmpty() || bptype == "") {

				try {
					List<UserMaster> userMasterList = webService.getAllActiveUsers(status);
					if (userMasterList == null || userMasterList.isEmpty()) {
						js.put("code", "1001");
						js.put("error", "User master List Is Empty");
						json = js.toString();
					} else {
						js.put("userMasterList", ow.writeValueAsString(userMasterList));
						js.put("code", "1000");
						json = js.toString();
					}
					try {
						json = js.toString();
					} catch (Exception e) {
						e.printStackTrace();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (bptype != "") {
				try {
					List<UserMaster> userMasterList2 = webService.getUserListByBptype(bptype, status);
					if (userMasterList2 == null || userMasterList2.isEmpty()) {
						js.put("code", "1001");
						js.put("error", "User Master List Is Empty");
						json = js.toString();
					} else {
						js.put("userMasterList", ow.writeValueAsString(userMasterList2));
						js.put("code", "1000");
						json = js.toString();
					}
					try {
						json = js.toString();
					} catch (Exception e) {
						e.printStackTrace();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(value = "/participantsList", method = RequestMethod.POST,
	 * produces = "application/json") public ResponseEntity<String>
	 * participantsList(HttpServletRequest request, HttpServletResponse response,
	 * 
	 * @RequestParam("DEPT") String department) {
	 * 
	 * response.setHeader("Access-Control-Allow-Origin", "*"); JSONObject js = new
	 * JSONObject(); ObjectWriter ow = new
	 * ObjectMapper().writer().withDefaultPrettyPrinter(); String json = null; try {
	 * List<UserMaster> userMasterList = webService.getuserMasterByDept(department);
	 * if (userMasterList == null || userMasterList.isEmpty()) { js.put("code",
	 * "1001"); js.put("error", "User List Is Empty"); json = js.toString(); } else
	 * { js.put("userMasterList", ow.writeValueAsString(userMasterList));
	 * js.put("code", "1000"); json = js.toString(); } try { json = js.toString(); }
	 * catch (Exception e) { e.printStackTrace(); } } catch (Exception e) {
	 * e.printStackTrace(); } return new ResponseEntity<String>(json,
	 * HttpStatus.OK); }
	 */

}

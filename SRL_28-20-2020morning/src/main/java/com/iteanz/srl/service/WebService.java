package com.iteanz.srl.service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityNotFoundException;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.lang3.RandomStringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteanz.srl.domain.ASNHeader;
import com.iteanz.srl.domain.ASNItem;
import com.iteanz.srl.domain.ASNSection;
import com.iteanz.srl.domain.ApprovalLog;
import com.iteanz.srl.domain.ApprovalMatrix;
import com.iteanz.srl.domain.ApproverMaster;
import com.iteanz.srl.domain.Contract;
import com.iteanz.srl.domain.ContractDocument;
import com.iteanz.srl.domain.Currency;
import com.iteanz.srl.domain.Department;
import com.iteanz.srl.domain.Designation;
import com.iteanz.srl.domain.DocumentType;
import com.iteanz.srl.domain.FirstParty;
import com.iteanz.srl.domain.GRNHeader;
import com.iteanz.srl.domain.GRNItem;
import com.iteanz.srl.domain.GatePassItem;
import com.iteanz.srl.domain.Invite;
import com.iteanz.srl.domain.Invoice;
import com.iteanz.srl.domain.MaterialMaster;
import com.iteanz.srl.domain.NumberRange;
import com.iteanz.srl.domain.OrgMasterAndUserMapping;
import com.iteanz.srl.domain.PODocument;
import com.iteanz.srl.domain.POItemDetails;
import com.iteanz.srl.domain.POItemDetailsQty;
import com.iteanz.srl.domain.PasswordPolicy;
import com.iteanz.srl.domain.Payment;
import com.iteanz.srl.domain.Plant;
import com.iteanz.srl.domain.ProposalSummary;
import com.iteanz.srl.domain.PurOrg;
import com.iteanz.srl.domain.PurchaseOrder;
import com.iteanz.srl.domain.PurchaseOrderDetails;
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
import com.iteanz.srl.domain.ServerInfo;
import com.iteanz.srl.domain.SessionHistory;
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
import com.iteanz.srl.domain.VPSection;
import com.iteanz.srl.domain.VPTCProposal;
import com.iteanz.srl.domain.Vendor;
import com.iteanz.srl.domain.VendorBalance;
import com.iteanz.srl.domain.VendorDocument;
import com.iteanz.srl.domain.VendorLedger;
import com.iteanz.srl.domain.VendorRegistration;
import com.iteanz.srl.domain.WorkflowConfig;
import com.iteanz.srl.repositories.ASNHeaderRepository;
import com.iteanz.srl.repositories.ASNItemRepository;
import com.iteanz.srl.repositories.ASNRepository;
import com.iteanz.srl.repositories.ApprovalLogRepo;
import com.iteanz.srl.repositories.ApprovalMatrixRepository;
import com.iteanz.srl.repositories.ContractRepository;
import com.iteanz.srl.repositories.GRNHeaderRepository;
import com.iteanz.srl.repositories.GRNItemRepository;
import com.iteanz.srl.repositories.GatePassItemRepository;
import com.iteanz.srl.repositories.InviteRepository;
import com.iteanz.srl.repositories.InvoiceRepository;
import com.iteanz.srl.repositories.POHeaderRepository;
import com.iteanz.srl.repositories.PaymentRepository;
import com.iteanz.srl.repositories.PlantRepository;
import com.iteanz.srl.repositories.ProposalSummaryRepo;
import com.iteanz.srl.repositories.PurchaseOrderRepository;
import com.iteanz.srl.repositories.PurchaseRequisitionRepository;
import com.iteanz.srl.repositories.RFPHeaderRepository;
import com.iteanz.srl.repositories.RFPInvitedVendorRepo;
import com.iteanz.srl.repositories.RFPParticipantsRepository;
import com.iteanz.srl.repositories.RFPQueryRepository;
import com.iteanz.srl.repositories.TechnoCriteriaHeadRepository;
import com.iteanz.srl.repositories.TechnoRatingsHeadRepository;
import com.iteanz.srl.repositories.UserMasterRepo;
import com.iteanz.srl.repositories.VPHeaderRepository;
import com.iteanz.srl.repositories.VPItemRepo;
import com.iteanz.srl.repositories.VendorBalanceRepository;
import com.iteanz.srl.repositories.VendorLedgerRepository;
import com.iteanz.srl.repositories.VendorRepository;
import com.iteanz.srl.repositories.WebServiceRepository;
import com.iteanz.srl.repositories.WorkFlowRepo;
import com.iteanz.srl.utility.EmailSending;

//@Transactional
@Service
public class WebService {
	private static final String Array = null;
	@Autowired
	WebService webService;
	@Autowired
	private RFPHeaderRepository rfpheaderRepository;
	@Autowired
	private VPHeaderRepository vpheaderRepository;
	@Autowired
	WebServiceRepository sampleRepo;
	@Autowired
	private UserMasterRepo master;

	@Autowired
	private GatePassItemRepository gatePassItemRepository;

	@Autowired
	private ASNItemRepository asnItemRepository;

	@Autowired
	private GRNItemRepository grnItemRepository;

	@Autowired
	private RFPQueryRepository rfpQueryRepo;

	@Autowired
	private TechnoCriteriaHeadRepository technocriteriaRepository;

	@Autowired
	private ApprovalLogRepo approvalLogRepo;

	@Autowired
	private TechnoRatingsHeadRepository technoratingsRepository;

	@Autowired
	private VendorRepository vendorRepository;

	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private PlantRepository plantRepository;

	@Autowired
	private PurchaseRequisitionRepository purchaseRequisitionRepository;

	@Autowired
	private ASNHeaderRepository asnHeaderRepository;

	@Autowired
	private ProposalSummaryRepo proposalSummaryRepo;

	@Autowired
	private ASNRepository asnRepository;

	@Autowired
	private RFPParticipantsRepository rfpParticipantsRepository;

	@Autowired
	private GRNHeaderRepository grnheaderRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private InviteRepository inviteRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private VendorLedgerRepository vendorLedgerRepository;

	@Autowired
	private VendorBalanceRepository vendorBalanceRepository;

	@Autowired
	private VPHeaderRepository vpHeaderRepository;

	@Autowired
	private RFPInvitedVendorRepo rfpInvitedVendorRepo;

	@Autowired
	private POHeaderRepository poheaderrepository;

	@Autowired
	private PurchaseOrderRepository poRepository;

	@Autowired
	private UserMasterRepo userMasterRepo;

	@Autowired
	private VPItemRepo vpitemrepository;

	boolean count = true;

	@Autowired
	private ApprovalMatrixRepository approvalMatrixRepository;

	public List<HashMap<String, Object>> poQty(Long ebeln) throws SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();

		long poitemno = 0;
		long ponumber = 0;
		float menge = 0.0f;
		float asnmenge = 0.0f;
		float grnmenge = 0.0f;
		float gatepassmenge = 0;
		float gatepassmenge_returnable = 0;
		float balanceQty = 0;

		PurchaseOrderDetails podetails = new PurchaseOrderDetails();
		List<POItemDetailsQty> poitemdetailsqty = new ArrayList<POItemDetailsQty>();

		try {

			PurchaseOrder po = poheaderrepository.getPurchaseOrderByEbeln(ebeln);
			List<PurchaseOrder> myList = new CopyOnWriteArrayList<PurchaseOrder>();

			podetails.setEbeln(po.getEbeln());
			podetails.setBsart(po.getBsart());
			podetails.setBukrs(po.getBukrs());
			podetails.setLifnr(po.getLifnr());
			podetails.setEkorg(po.getEkorg());
			podetails.setAckno(po.getAckno());
			podetails.setCntr_no(po.getCntr_no());
			podetails.setName1(po.getName1());
			podetails.setStatus(po.getStatus());
			podetails.setAedat(po.getAedat());
			podetails.setCddat(po.getCddat());
			podetails.setErnam(po.getErnam());
			podetails.setChangeddBy(po.getChangeddBy());

			// for(PurchaseOrder po : poList)

			ponumber = po.getEbeln();
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			// List<POItemDetails> poitemList = sampleRepo.getPOItemDetailsByCp_fk(ebeln);
			for (POItemDetails poitem : po.getPoitem())
			// for(POItemDetails poitem : poitemList)
			{

				POItemDetailsQty poitemdetailsqty_i = new POItemDetailsQty();

				poitemdetailsqty_i.setPoitem(poitem);

//				ponumber = poitem.getCp_fk();
				poitemno = poitem.getEbelp();
				menge = menge + poitem.getMenge();

				System.out.println("EBELP:" + poitemno);

				List<ASNItem> asnitemList = asnItemRepository.getASNItemByEbelnAndEbelp(ebeln, poitemno);
				// List<ASNItem> asnitemList =
				// asnItemRepository.getASNItemByEbelnAndEbelpAndStatus(ebeln, poitemno);
				float asnmenge_i = 0.0f;
				for (ASNItem asnitem : asnitemList) {
					System.out.println("EBELP:" + poitemno);
					System.out.println("ASNQTY:" + asnmenge);
					Long asno = asnitem.getCp_fk();
					System.out.println("ASNO:" + asno);
					ASNHeader asnheader = asnHeaderRepository.findOne(asno);
					System.out.println("ASNO:" + asnheader.getStatus());
					if (asnheader.getStatus() != 2) {
						asnmenge = asnmenge + asnitem.getMenge();

						asnmenge_i = asnmenge_i + asnitem.getMenge();
					}

					/*
					 * try { totasnmenge = sampleRepo.sumASNMenges(ebeln); totasnitemmenge =
					 * sampleRepo.sumASNItemMenges(ebeln, poitemno);
					 * System.out.println("TOTALASNQTY:"+totasnmenge);
					 * System.out.println("TOTALASITEMNQTY:"+totasnitemmenge);
					 * po.setAsnitemmenge(totasnmenge); poitem.setAsnitemmenge(totasnitemmenge); }
					 * catch(Exception e) { e.printStackTrace(); continue; }
					 */
				}
				poitemdetailsqty_i.setAsnitemmenge(asnmenge_i);
				List<GRNItem> grnitemList = grnItemRepository.getGRNItemByEbelnAndEbelp(ebeln, poitemno);
				float grnmenge_i = 0.0f;
				for (GRNItem grnitem : grnitemList) {
					grnmenge = grnmenge + grnitem.getMenge1();
					System.out.println("EBELP:" + poitemno);
					System.out.println("GRNQTY:" + grnmenge);

					grnmenge_i = grnmenge_i + grnitem.getMenge1();

					/*
					 * try { totgrnmenge = sampleRepo.sumGRNMenges(ebeln); totgrnitemmenge =
					 * sampleRepo.sumGRNItemMenges(ebeln, poitemno);
					 * System.out.println("TOTALGRNQTY:"+totgrnmenge);
					 * System.out.println("TOTALGRNITEMQTY:"+totgrnitemmenge);
					 * po.setGrnitemmenge(totgrnmenge); poitem.setGrnitemmenge(totgrnitemmenge); }
					 * catch(Exception e) { e.printStackTrace(); continue; }
					 */
				}

				poitemdetailsqty_i.setGrnitemmenge(grnmenge_i);
				List<GatePassItem> gatepassitemList = gatePassItemRepository.getGatePassItemByEbelnAndEbelp(ebeln,
						poitemno);
				float gatepassmenge_i = 0;
				float gatepassmenge_returnable_i = 0;
				for (GatePassItem gatepassitem : gatepassitemList) {

					gatepassmenge = gatepassmenge + gatepassitem.getMenge();

					// returnable gatepass logic
					if (gatepassitem.getChec().equalsIgnoreCase("X")) {
						gatepassmenge_returnable_i = gatepassmenge_returnable_i + gatepassmenge;
					}

					System.out.println("EBELP:" + poitemno);
					System.out.println("GATEPASSQTY:" + gatepassmenge);
					gatepassmenge_i = gatepassmenge_i + gatepassitem.getMenge();

					/*
					 * try { totgatepassmenge = sampleRepo.sumGPassMenges(ebeln);
					 * totgatepassitemmenge = sampleRepo.sumGPassItemMenges(ebeln, poitemno);
					 * System.out.println("TOTALGATEPASSQTY:"+totgatepassmenge);
					 * System.out.println("TOTALGATEPASSITEMQTY:"+totgatepassitemmenge);
					 * po.setGatepassitemmenge(totgatepassmenge);
					 * poitem.setGatepassitemmenge(totgatepassitemmenge); } catch(Exception e) {
					 * e.printStackTrace(); continue; }
					 */
				}
				poitemdetailsqty_i.setGatepassitemmenge(gatepassmenge_i);

				System.out.println("POQTY:" + menge);
				/*
				 * try { totalpomenge = sampleRepo.sumPOMenges(ebeln); totalpoitemmenge =
				 * sampleRepo.sumPOItemMenges(ebeln,poitemno);
				 * System.out.println("TOTPOQTY:"+totalpomenge);
				 * System.out.println("TOTPOITEMQTY:"+totalpoitemmenge);
				 * po.setPoitemmenge(totalpomenge); poitem.setPoitemmenge(totalpoitemmenge); }
				 * catch(Exception e) { e.printStackTrace(); continue; }
				 */

				/*
				 * poitem.setAsnitemmenge(totasnitemmenge);
				 * poitem.setGrnitemmenge(totgrnitemmenge);
				 * poitem.setGatepassitemmenge(totgatepassitemmenge);
				 * poitem.setPoitemmenge(totalpoitemmenge);
				 */

				// PO Item Balance Qty = (PO Item - ASN Item) + Gatepass Item
				poitemdetailsqty_i.setBalanceQty(
						(poitemdetailsqty_i.getPoitem().getMenge() - poitemdetailsqty_i.getAsnitemmenge())
								+ poitemdetailsqty_i.getGatepassitemmenge());

				// Add item to header
				poitemdetailsqty.add(poitemdetailsqty_i);

			}

			podetails.setPoitemdetails(poitemdetailsqty);

			balanceQty = (menge - asnmenge) + gatepassmenge;
			podetails.setMenge(menge); // Total PO Qty
			podetails.setAsnmenge(asnmenge); // Total ASN for the PO
			podetails.setGrnmenge(grnmenge); // Total GRN for the PO
			podetails.setGatepassmenge(gatepassmenge); // Total Gatepass for the PO
			podetails.setBalanceQty(balanceQty); // Total Balance Qty for the PO

			map1.put("poheader", podetails);

			/*
			 * map1.put("ebeln", po.getEbeln()); map1.put("bsart", po.getBsart());
			 * map1.put("bukrs", po.getBukrs()); map1.put("lifnr", po.getLifnr());
			 * map1.put("ekorg", po.getEkorg()); map1.put("ackno", po.getAckno());
			 * map1.put("cntr_no", po.getCntr_no()); map1.put("status", po.getStatus());
			 * map1.put("aedat", po.getAedat()); map1.put("cddat", po.getCddat());
			 * map1.put("ernam", po.getErnam()); map1.put("changeddBy", po.getChangeddBy());
			 * map1.put("poitemmenge", po.getPoitemmenge()); map1.put("asnitemmenge",
			 * po.getAsnitemmenge()); map1.put("grnitemmenge", po.getGrnitemmenge());
			 * map1.put("gatepassitemmenge", po.getGatepassitemmenge()); //
			 * metaDataList.add(map1); map.put("poheaderList", map1);
			 */

			// map.put("poList", po);
			metaDataList.add(map1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return metaDataList;
	}

	public List<HashMap<String, Object>> getContractHeaderBySessionid(String sessionid) {
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		String cradrid = null;
		String ardid = null;
		String crby = null;
		UserMaster createdBy = null;
		RoleModel contractCreatedBy = null;
		try {
			long sesid = Long.parseLong(sessionid);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();

				}
			}
			if (ardid == null) {
				map1.put("error", "Session Expired");
				return (List<HashMap<String, Object>>) map1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			crby = Long.toString(createdBy.getId());
			cradrid = createdBy.getAdrid();
			// contracts.setCreatedby(crby);
			// contracts.setCreateddate(today.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Get Contract by CreatedBy
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<Contract> contracts = contractRepository.getContractByCreatedBy(cradrid);
		// List<RFPHeader> getRFPBySesid(String sesId);
		for (Contract contract : contracts) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ContractId", contract.getContractId());
			map.put("ContractStatus", contract.getContractStatus());
			map.put("ContractType", contract.getContractType());
			map.put("status", contract.getStatus());
			// Add on 22.02.2020 for get contract created by department
			contractCreatedBy = webService.getUserDetails(contract.getCreatedBy()).getUserRoll();
			map.put("department", contractCreatedBy.getId());
			metaDataList.add(map);
		}
		// Get Contract by Participants
		List<Invite> invites = inviteRepository.getInviteByUserid(cradrid);
		for (Invite invite : invites) {
			HashMap<String, Object> map = new HashMap<String, Object>();

			long contractid = invite.getContractId();
			Contract contract = contractRepository.getOne(contractid);
			map.put("ContractId", contract.getContractId());
			map.put("ContractStatus", contract.getContractStatus());
			map.put("ContractType", contract.getContractType());
			map.put("status", contract.getStatus());
			// Add on 22.02.2020 for get contract created by department
			contractCreatedBy = webService.getUserDetails(contract.getCreatedBy()).getUserRoll();
			map.put("department", contractCreatedBy.getId());
			metaDataList.add(map);
		}
		return metaDataList;
	}

	public List<HashMap<String, Object>> proposalSummary(Long rfpno) throws SQLException {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<VPHeader> myList = new CopyOnWriteArrayList<VPHeader>();
		List<VPHeader> vpheadersList = vpheaderRepository.getVPByRfpno(rfpno);

		myList = vpheadersList;
		long proposalno = 0;
		double totpreis = 0;
		double preis = 0;
		long cp_fk = 0;
		int x = 0;
		for (VPHeader vpheader : vpheadersList) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			x = x + 1;
			List<VPItem> vpitems = vpheader.getVpitem();
			for (VPItem vpitem : vpitems) {
				proposalno = vpheader.getProposalno();
				cp_fk = vpitem.getCp_fk();
				System.out.println("PROPOSAL NO:" + proposalno);
				preis = vpitem.getPreis();
				System.out.println("VPPREIS:" + preis);

				totpreis = sampleRepo.sumPreis(cp_fk);
				System.out.println("TOTPREIS:" + totpreis);
			}
			map.put("rfpno", vpheader.getRfpno());
			map.put("lifnr", vpheader.getLifnr());
			map.put("proposalno", vpheader.getProposalno());
			map.put("position", "L" + x);
			map.put("comments", vpheader.getComments());
			map.put("ratingsscore", "");
			map.put("supplierstatus", "");
			map.put("createdby", vpheader.getCreatedby());
			map.put("changedby", vpheader.getChangedby());
			map.put("createddate", vpheader.getCreateddate());
			map.put("changeddate", vpheader.getChangeddate());
			map.put("preis", totpreis);
			metaDataList.add(map);
		}

		return metaDataList;

	}

	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> proposalSummaryTest(Long rfpno) throws SQLException {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<VPHeader> vpheadersList = vpheaderRepository.getVPByRfpno(rfpno);

		long proposalno = 0;
		double totpreis = 0;
		double preis = 0;
		double valprice = 0;
		long cp_fk = 0;
		int x = 0;
		for (VPHeader vpheader : vpheadersList) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			x = x + 1;
			List<VPItem> vpitemsList = vpheader.getVpitem();
			for (VPItem vpitem : vpitemsList) {
				proposalno = vpheader.getProposalno();
				cp_fk = vpitem.getCp_fk();
				System.out.println("PROPOSAL NO:" + proposalno);
				// preis = vpitem.getPreis();
				valprice = valprice + vpitem.getValprice();
				System.out.println("VPPREIS:" + preis);

				// totpreis = sampleRepo.sumPreis(cp_fk);
				System.out.println("TOTPREIS:" + totpreis);
			}
			map.put("rfpno", vpheader.getRfpno());
			map.put("lifnr", vpheader.getLifnr());
			map.put("proposalno", vpheader.getProposalno());
			map.put("position", "L" + x);
			map.put("comments", vpheader.getComments());
			map.put("ratingsscore", "");
			map.put("supplierstatus", "");
			map.put("createdby", vpheader.getCreatedby());
			map.put("changedby", vpheader.getChangedby());
			map.put("createddate", vpheader.getCreateddate());
			map.put("changeddate", vpheader.getChangeddate());
			map.put("preis", valprice);

			metaDataList.add(map);
			metaDataList.sort(Comparator.comparing(o -> (Double) o.get("preis")));
			System.out.println(metaDataList);
		}

		return metaDataList;
	}

	public HashMap<String, Object> addProposalSummary(ProposalSummary proposalsummary)
			throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		NumberRange numberRangeObject = null;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = proposalsummary.getSessionid();
		System.out.println("SESSIONID:" + sesId);
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
				map.put("error", "Session Expired");
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CREATEDDATE:" + today.toString());
			proposalsummary.setCreatedBy(cradrid);
			proposalsummary.setCreateddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		long propsumid = 0;

		propsumid = getNumberRangeUId("PSID");

		if (propsumid > 0) {
			proposalsummary.setPropsumid(propsumid);
			// vendor.setVersion(1);
			proposalsummary = proposalSummaryRepo.save(proposalsummary);
			if (propsumid > 0) {
				map.put("Status", "Successfull");
				map.put("ProposalSummary Id", propsumid);

			} else {
				map.put("Status", "Failed");
				map.put("ProposalSummary Id", 0);
			}
		} else {
			map.put("Status", "Failed");
			map.put("ProposalSummary Id", 0);
		}
		return map;
	}

	public HashMap<String, Object> addPO(PurchaseOrder po) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		long ebeln = 0;
		ebeln = getNumberRangeUId("EBELN");
		if (ebeln > 0) {
			po.setEbeln(ebeln);

			PODocument pd = po.getPoDocument();
			System.out.println("doc : " + pd.getAttachment());
			System.out.println("doc length : " + pd.getAttachment().length());
			if (pd.getAttachment() != null) {
				if (pd.getAttachment().length() > 0) {
					String data = pd.getAttachment().split(",")[1];
					Decoder decoder = Base64.getDecoder();
					byte[] decodedByte = decoder.decode(data);
					Blob b = new SerialBlob(decodedByte);
					// Utility.writeUsingFiles(data);
					pd.setFileData(b);
					pd.setAttachment(pd.getAttachment().split(",")[0]);
				}
			}
			po = poheaderrepository.save(po);

			if (po.getEbeln() > 0) {
				map.put("code", "1000");
				map.put("ebeln", po.getEbeln());
				map.put("status", "ebeln created successfully");

			} else {
				map.put("code", "1001");
				map.put("ebeln", 0);
				map.put("error", "ebeln creation failed");
			}
		} else {
			map.put("Status", "Failed");
			map.put("Ebeln", 0);
		}
		System.out.println("response : " + map);
		return map;
	}

	@SuppressWarnings("null")
	public List<HashMap<String, Object>> incompleterequisition() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		int status = 1;
		String pritemno = null;
		long prnumber = 0L;
		float menge = 0.0f;
		float totalMenge = 0.0f;
		float totprmenge = 0.0f;

		try {
			List<PurchaseRequisition> incompPrList = webService.getPRByStatus(status);
			List<PurchaseRequisition> myList = new CopyOnWriteArrayList<PurchaseRequisition>();
			myList = incompPrList;
			List<PurchaseRequisition> deletionIndexes = new ArrayList<PurchaseRequisition>();
			int index = -1;
			for (PurchaseRequisition pr : incompPrList) {
				index = index + 1;
				prnumber = pr.getBanfn();
				pritemno = pr.getBnfpo();
				menge = pr.getMenge();
				System.out.println("PRQTY:" + menge);
				// sum RFP Item quantity for the given PR & ITEM.
				try {
					totprmenge = sampleRepo.sumMenges(prnumber, pritemno);
					System.out.println("TOTRFPQTY:" + totprmenge);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				menge = menge - totprmenge;
				System.out.println("UPDATEDPRQTY:" + menge);
				int x = 0;
				if (menge > 0) {
					pr.setMenge(menge);
				} else {
					System.out.println("NOOOOOOOOOOOOO");
					menge = 0;
					pr.setMenge(menge);
					deletionIndexes.add(pr);
					System.out.println(incompPrList.get(x));
					System.out.println("VALUE OF X:" + x);
				}
				float quantity = pr.getMenge();

				x = x + 1;
			}
			int y = incompPrList.size();
			// for(int i = 0; i<y; i++){
			// if((myList.get(i).getMenge() == 0 || myList.get(i).getMenge() == 0.0f ||
			// myList.get(i).getMenge() <= 0))
			// myList.remove(i);
			// myList.remove(i)
			// y=y-1;
			// }
			myList.removeAll(deletionIndexes);
			map.put("prList", myList);
			metaDataList.add(map);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return metaDataList;
	}

	public HashMap<String, Object> addInvoice(Invoice invoice) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		NumberRange numberRangeObject = null;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = invoice.getSessionid();
		System.out.println("SESSIONID:" + sesId);
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
				map.put("error", "Session Expired");
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CREATEDDATE:" + today.toString());
			invoice.setCreatedBy(cradrid);
			invoice.setCreateddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		long invid = 0;

		invid = getNumberRangeUId("INV");
		if (invid > 0) {
			invoice.setInvoiceId(invid);
			// vendor.setVersion(1);
			invoice = invoiceRepository.save(invoice);
			if (invoice.getInvoiceId() > 0) {
				map.put("code", "1000");
				map.put("Status", "Invoice created successfully");
				map.put("Invoice Id", invoice.getInvoiceId());

			} else {
				map.put("code", "1001");
				map.put("error", "Invoice Creation Failed");
				map.put("Invoice Id", 0);
			}
		} else {
			map.put("code", "1001");
			map.put("error", "Invoice Creation Failed");
			map.put("Invoice Id", 0);

		}
		return map;
	}

	public VPHeader getVPHeaderByRfpno1(long rfpno) throws SQLException {
		// Optional<Vendor> vendor = vendorRepository.findById(vid);
		// Vendor vendor = vendorRepository.findById(vid).get();
		VPHeader vpheader = vpheaderRepository.getOne(rfpno);
		if (vpheader.getRfpno() > 0) {
			List<VPSection> vpSections = vpheader.getVpsection();
			for (VPSection vp : vpSections) {
				Blob docBlob = vp.getData();
				byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts);
				vp.setAttachment(vp.getAttachment() + "," + encoded);
				vp.setData(null);
			}

			List<VPGenProposal> vpgenProposal = vpheader.getVpgenproposal();
			for (VPGenProposal vp1 : vpgenProposal) {
				Blob docBlob = vp1.getData();
				byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts);
				vp1.setAttachment(vp1.getAttachment() + "," + encoded);
				vp1.setData(null);
			}
			List<VPTCProposal> vptcpProposal = vpheader.getVptcproposal();
			for (VPTCProposal vp2 : vptcpProposal) {
				Blob docBlob = vp2.getData();
				byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts);
				vp2.setAttachment(vp2.getAttachment() + "," + encoded);
				vp2.setData(null);
			}
		}
		return vpheader;
	}

	public HashMap<String, Object> addVendorProposal(VPHeader vpheader) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = vpheader.getSessionid();
		System.out.println("SESSIONID:" + sesId);
		UserMaster createdBy = null;
		String createdByMailid = null;
		String ardid = null;
		String vpcreateddate = null;
		try {
			long sesid = Long.parseLong(sesId);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();

				}
			}
			if (ardid == null) {
				map.put("error", "Session Expired");
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			createdByMailid = createdBy.getEmail();
			System.out.println("CREATEDDATE:" + today.toString());
			vpheader.setCreatedby(cradrid);
			vpheader.setCreateddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		RFPHeader rfpheader = rfpheaderRepository.getOne(vpheader.getRfpno());
		long vpno = 0;
		if (rfpheader != null) {
			NumberRange numberRangeObject = null;
			System.out.println("VPNO9:" + vpno);
			try {
				numberRangeObject = webService.getNumberRageByDocumentType("VPNO");
			} catch (Exception e) {
				e.printStackTrace();
			}
			NumberRange saveNumberRangeObject = null;
			vpno = numberRangeObject.getFromNumber() + 1;
			System.out.println("VPNO:" + vpno);
			if (vpno <= numberRangeObject.getToNumber()) {
				NumberRange numberRange = new NumberRange();
				numberRange.setId(numberRangeObject.getId());
				numberRange.setFromNumber(vpno);
				numberRange.setToNumber(numberRangeObject.getToNumber());
				numberRange.setTransationType(numberRangeObject.getTransationType());
				numberRange.setYear(numberRangeObject.getYear());
				saveNumberRangeObject = webService.saveNumberRange(numberRange);
			}
			vpheader.setStatus("1");
			vpheader.setProposalno(vpno);
		}

		List<VPSection> vpSection = vpheader.getVpsection();
		for (VPSection vf : vpSection) {
			if (vf.getAttachment() != null) {
				if (vf.getAttachment().length() > 0) {
					String data = vf.getAttachment().split(",")[1];
					Decoder decoder = Base64.getDecoder();
					byte[] decodedByte = decoder.decode(data);
					Blob b = new SerialBlob(decodedByte);
					// Utility.writeUsingFiles(data);
					vf.setData(b);
					vf.setAttachment(vf.getAttachment().split(",")[0]);
				}
			}
		}
		List<VPGenProposal> vpgenProposal = vpheader.getVpgenproposal();
		for (VPGenProposal vgen : vpgenProposal) {
			String data = vgen.getAttachment().split(",")[1];
			Decoder decoder = Base64.getDecoder();
			byte[] decodedByte = decoder.decode(data);
			Blob b = new SerialBlob(decodedByte);
			// Utility.writeUsingFiles(data);
			vgen.setData(b);
			vgen.setAttachment(vgen.getAttachment().split(",")[0]);
		}
		List<VPTCProposal> vptcProposal = vpheader.getVptcproposal();
		for (VPTCProposal vtcp : vptcProposal) {
			String data = vtcp.getAttachment().split(",")[1];
			Decoder decoder = Base64.getDecoder();
			byte[] decodedByte = decoder.decode(data);
			Blob b = new SerialBlob(decodedByte);
			// Utility.writeUsingFiles(data);
			vtcp.setData(b);
			vtcp.setAttachment(vtcp.getAttachment().split(",")[0]);
		}

		List<VPItem> vpItems = vpheader.getVpitem();
		for (VPItem vp : vpItems) {
			vp.setCp_fk(vpno);
		}

		vpheader = vpheaderRepository.save(vpheader);
		if (vpheader.getProposalno() > 0) {
			map.put("code", "1000");
			map.put("vpno", vpheader.getProposalno());
			map.put("status", "VP created successfully");

			if (vpheader.getProposalno() > 0) {
				List<RFPParticipant> rfParticipants = rfpheader.getParticipant();
				System.out.println("STARTLIST");
				for (RFPParticipant rp : rfParticipants) {
					String participantmail = rp.getEmail();
					System.out.println("EMAILID:" + rp.getEmail());
					vpcreateddate = vpheader.getCreateddate();
					vpParticipantsCreatedByMail(participantmail, rfpheader.getRfpno(), vpheader.getProposalno(),
							vpcreateddate);
				}
				System.out.println("RFPPPPPPPPPPPPP:" + rfpheader.getRfpno());
				System.out.println("CREATED EMAIL:" + createdByMailid);
				vpParticipantsCreatedByMail(createdByMailid, rfpheader.getRfpno(), vpheader.getProposalno(),
						vpcreateddate);
				System.out.println("ENDLIST");
			}

		} else {
			map.put("code", "1001");
			map.put("vpno", 0);
			map.put("error", "VP creation failed");
		}
		System.out.println("response : " + map);
		return map;
	}

	public List<HashMap<String, Object>> getRFPEmailIdsByVP(long proposalno) throws SQLException {
		VPHeader vpheader = vpheaderRepository.getOne(proposalno);

		RFPHeader rfpheader = rfpheaderRepository.getOne(vpheader.getRfpno());
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		// List<Vendor> vendors = vendorRepository.findAll();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("rfpno", rfpheader.getRfpno());
		map.put("createdBy", rfpheader.getCreatedby());
		map.put("mode", rfpheader.getRfpmode());

		HashMap<String, Object> map1 = new HashMap<String, Object>();

		if (vpheader.getProposalno() > 0) {
			List<RFPParticipant> rfParticipants = rfpheader.getParticipant();
			System.out.println("STARTLIST");
			for (RFPParticipant rp : rfParticipants) {

				rp.getEmail();
				System.out.println("EMAILID:" + rp.getEmail());
			}
			System.out.println("ENDLIST");
		}
		map1.put("vpitemlist", vpheader);
		map1.put("rfpheader", map);
		metaDataList.add(map1);
		return metaDataList;
	}

	public List<Contract> getAllContracts() throws SQLException {
		List<ContractDocument> contractDocuments;
		List<Contract> contracts = contractRepository.findAll();
		for (Contract contract : contracts) {
			contractDocuments = contract.getContractDocuments();
			for (ContractDocument cd : contractDocuments) {
				if (cd.getAttachment() != null) {
					if (cd.getAttachment().length() > 0) {
						getBlobToBase64(cd);
					}
				}
			}
		}
		return contracts;
	}

	public HashMap<String, Object> addContract(Contract contract) throws SerialException, SQLException {

		HashMap<String, Object> map = new HashMap<String, Object>();
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = contract.getSessionid();
		System.out.println("SESSIONID:" + sesId);
		UserMaster createdBy = null;
		String ardid = null;
		RoleModel dept = null;
		String createdByMailid = null;
		String cradrid = null;
		try {
			long sesid = Long.parseLong(sesId);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();

				}
			}
			if (ardid == null) {
				map.put("error", "Session Expired");
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			cradrid = createdBy.getAdrid();
			dept = createdBy.getUserRoll();
			createdByMailid = createdBy.getEmail();
			// System.out.println("DEPARTMENT:"+dept);
			System.out.println("CREATEDDATE:" + today.toString());
			contract.setCreatedBy(cradrid);
			contract.setCreatedOn(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		String depart = "CID" + dept.getId();
		System.out.println("CID DEPARTMENT:" + depart);

		// Add number range id here
		long cid = getNumberRangeUId(depart);

		if (cid > 0) {
			contract.setContractId(cid);
			// System.out.println("\n contract : " + contract.toString());
			List<ContractDocument> contractDocument = contract.getContractDocuments();
			for (ContractDocument cd : contractDocument) {
				if (cd.getAttachment() != null) {
					if (cd.getAttachment().length() > 0) {
						getBase64ToBlob(cd);
					}
				}
			}

			List<Invite> rfpVendors = contract.getInvites();
			for (Invite rv : rfpVendors) {
				rv.setContractId(cid);

				rv.setCreatedBy(cradrid);
				rv.setCreateddate(today.toString());
			}

			// Saving First Party in Plant Table
			// addFirstPartyToPlant(contract.getFirstParty());
			contract = contractRepository.save(contract);

			if (contract.getContractId() > 0) {
				map.put("code", "1000");
				map.put("Status", "Contract created successfully");
				map.put("ContractId", contract.getContractId());

				List<Invite> invitees = contract.getInvites();
				for (Invite inv : invitees) {
					if (contract.getContractStatus().equals("S")) {
						long contractid = contract.getContractId();
						String currdt = contract.getContractStartDate();
						// contractMail(String email,String name, long cid, String currdf)
						// contractMail(inv.getEmailid(),inv.getUser1(),contractid,currdt);
						contractMail(inv.getEmail(), contractid, currdt);
						System.out.println("CREATED EMAIL:" + createdByMailid);
						contractMail(createdByMailid, contractid, currdt);
						System.out.println("CREATED EMAIL:" + createdByMailid);
						inv.setMailstatus("N");
					}
				}

			} else {
				map.put("code", "1001");
				map.put("error", "Contract creation failed");
				map.put("ContractId", 0);
			}
		} else {
			map.put("code", "1001");
			map.put("error", "Contract creation failed");
			map.put("ContractId", 0);
		}
		return map;
	}

	public HashMap<String, Object> updateContract(Contract contract) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = contract.getSessionid();
		System.out.println("SESSIONID:" + sesId);
		UserMaster createdBy = null;
		String ardid = null;
		String createdByMailid = null;
		String cradrid = null;

		try {
			long sesid = Long.parseLong(sesId);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();
				}
			}
			if (ardid == null) {
				map.put("error", "Session Expired");
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CHANGEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			cradrid = createdBy.getAdrid();
			System.out.println("CHANGEDDATE:" + today.toString());
			createdByMailid = createdBy.getEmail();

			contract.setChangedBy(cradrid);
			contract.setChangedOn(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		contract.setContractStatus("S");
		// System.out.println("\n contract : " + contract.toString());
		List<ContractDocument> contractDocument = contract.getContractDocuments();
		for (ContractDocument cd : contractDocument) {
			if (cd.getAttachment() != null) {
				if (cd.getAttachment().length() > 0) {
					getBase64ToBlob(cd);
				}
			}
		}

		List<Invite> rfpVendors = contract.getInvites();
		for (Invite rv : rfpVendors) {
			rv.setContractId(contract.getContractId());
			rv.setChangeddBy(cradrid);
			rv.setChangeddate(today.toString());
		}

		// addFirstPartyToPlant(contract.getFirstParty());

		contract = contractRepository.save(contract);

		contract = contractRepository.save(contract);

		if (contract.getContractId() > 0) {
			map.put("code", "1000");
			map.put("Status", "Contract updated successfully");
			map.put("ContractId", contract.getContractId());

			List<Invite> invitees = contract.getInvites();
			for (Invite inv : invitees) {
				if (contract.getContractStatus().equals("S")) {
					long contractid = contract.getContractId();
					String currdt = contract.getContractStartDate();
					// contractMail(String email,String name, long cid, String currdf)
					// contractMail(inv.getEmailid(),inv.getUser1(),contractid,currdt);
					contractMail(inv.getEmail(), contractid, currdt);
					System.out.println("CREATED EMAIL:" + createdByMailid);
					contractMail(createdByMailid, contractid, currdt);
					inv.setMailstatus("N");
				}
			}

		} else {
			map.put("code", "1001");
			map.put("error", "Contract updation failed");
			map.put("ContractId", 0);

		}
		return map;
	}

	public Contract getContract(long contractId) throws SQLException {

		Contract contract = contractRepository.getOne(contractId);

		if (contract.getContractId() > 0) {
			List<ContractDocument> contractDocuments = contract.getContractDocuments();
			for (ContractDocument cd : contractDocuments) {
				if (cd.getAttachment() != null) {
					if (cd.getAttachment().length() > 0) {
						getBlobToBase64(cd);
					}
				}
			}
		}
		return contract;
	}

	public List<HashMap<String, Object>> getAllContractsMetaData() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<Contract> contracts = contractRepository.findAll();
		for (Contract contract : contracts) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ContractId", contract.getContractId());
			map.put("ContractStatus", contract.getContractStatus());
			map.put("ContractType", contract.getContractType());
			map.put("status", contract.getStatus());
			metaDataList.add(map);
		}
		return metaDataList;
	}

	public List<HashMap<String, Object>> getContractBasicDetailsBySessionid(String sessionid) {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		List<Contract> contracts = contractRepository.getContractBySessionid(sessionid);
		String ardid = null;
		UserMaster createdBy = null;
		try {
			long sesid = Long.parseLong(sessionid);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();

				}
			}
			if (ardid == null) {
				map1.put("error", "Session Expired");
				return (List<HashMap<String, Object>>) map1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			List<Invite> lifnr = inviteRepository.getInviteByUserid(crby);
			for (Invite invite : lifnr) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				Contract contract = contractRepository.getOne(invite.getContractId());
				map.put("ContractId", contract.getContractId());
				map.put("ContractStatus", contract.getContractStatus());
				map.put("ContractType", contract.getContractType());
				map.put("status", contract.getStatus());
				metaDataList.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return metaDataList;
	}

	public ContractDocument getBase64ToBlob(ContractDocument cd) throws SerialException, SQLException {
		String data = cd.getAttachment().split(",")[1];
		Decoder decoder = Base64.getDecoder();
		byte[] decodedByte = decoder.decode(data);
		Blob b = new SerialBlob(decodedByte);
		cd.setData(b);
		cd.setAttachment(cd.getAttachment().split(",")[0]);
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		cd.setCreatedDate(today.toString());
		return cd;

	}

	public ContractDocument getBlobToBase64(ContractDocument cd) throws SerialException, SQLException {
		Blob docBlob = cd.getData();
		byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
		String encoded = Base64.getEncoder().encodeToString(bts);
		cd.setAttachment(cd.getAttachment() + "," + encoded);
		cd.setData(null);
		return cd;
	}

	public List<Contract> getContractsByType(String contractType) throws SerialException, SQLException {
		List<ContractDocument> contractDocuments;
		List<Contract> contracts = contractRepository.getContractsByContractType(contractType);
		for (Contract contract : contracts) {
			contractDocuments = contract.getContractDocuments();
			for (ContractDocument cd : contractDocuments) {
				if (cd.getAttachment() != null) {
					if (cd.getAttachment().length() > 0) {
						getBlobToBase64(cd);
					}
				}
			}
		}
		return contracts;
	}

	public List<Object> getMetaDataByType() throws SerialException, SQLException {

		List<String> contractTypes = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
		List<Contract> contracts;
		HashMap<String, Object> map;
		List<Object> metaDataList;
		List<Object> metaDataListFinal = new ArrayList<Object>();
		HashMap<String, Object> contTypeData;
		for (String contractType : contractTypes) {
			contracts = contractRepository.getContractsByContractType(contractType);
			if (contracts.size() > 0) {
				metaDataList = new ArrayList<Object>();
				contTypeData = new HashMap<String, Object>();
				contTypeData.put("ContractType", contractType);
				for (Contract contract : contracts) {
					map = new HashMap<String, Object>();
					map.put("ContractId", contract.getContractId());
					map.put("ContractStatus", contract.getContractStatus());
					map.put("ContractType", contractType);
					metaDataList.add(map);
				}
				contTypeData.put("Contracts", metaDataList);
				metaDataListFinal.add(contTypeData);
			}
		}
		return metaDataListFinal;
	}

	// Uncomment to save in DB
	public void addFirstPartyToPlant(FirstParty firstParty) throws SerialException, SQLException {

		Plant plant = new Plant();
		plant.setWerks(firstParty.getWerks());
		plant.setName1(firstParty.getName1());
		plant.setCity1(firstParty.getCity1());
		plant.setLgort(firstParty.getLgort());
		plant.setEmailid(firstParty.getEmailid());
		plant.setBukrs(firstParty.getBukrs());
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		plant.setCrdat(today.toString());
		plantRepository.save(plant);
	}

	public long getNumberRangeUId(String appType) {
		long uid = 0;
		NumberRange numberRangeObject = null;
		NumberRange saveNumberRangeObject = null;

		try {
			numberRangeObject = webService.getNumberRageByDocumentType(appType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (numberRangeObject == null) {
			System.out.println("\n No Row for Contarct in Number Range Table");
			return uid;
		}

		uid = numberRangeObject.getFromNumber() + 1;
		System.out.println("\n Contract Id : " + uid);

		if (uid <= numberRangeObject.getToNumber()) {

			NumberRange numberRange = new NumberRange();
			numberRange.setId(numberRangeObject.getId());
			numberRange.setFromNumber(uid);
			numberRange.setToNumber(numberRangeObject.getToNumber());
			numberRange.setTransationType(numberRangeObject.getTransationType());
			numberRange.setYear(numberRangeObject.getYear());
			saveNumberRangeObject = webService.saveNumberRange(numberRange);
		}
		return uid;
	}

	/*
	 * public List<PurchaseOrder> getRFPwithVPNO(String createdBy) throws
	 * SerialException, SQLException { List<PurchaseOrder> pos =
	 * poheaderrepository.getPurchaseOrderByCreatedBy(createdBy); return pos; }
	 * 
	 * public RFPHeader getRFPwithVPNO(String createdBy) throws SerialException,
	 * SQLException { //Optional<Vendor> vendor = vendorRepository.findById(vid);
	 * //Vendor vendor = vendorRepository.findById(vid).get(); RFPHeader rfpheader =
	 * rfpheaderRepository.getRFPByCreatedBy(createdBy); VPHeader vpHeader =
	 * vpheaderRepository.getVPByCreatedBy(createdBy); if(rfpheader.getRfpno() > 0)
	 * { return rfpheader; }
	 * 
	 * }
	 * 
	 */

	public HashMap<String, Object> createASN(ASNHeader asnheader) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		NumberRange numberRangeObject = null;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = asnheader.getSessionid();
		System.out.println("SESSIONID:" + sesId);
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
				map.put("error", "Session Expired");
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CREATEDDATE:" + today.toString());
			asnheader.setCreatedBy(cradrid);
			asnheader.setCreateddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		long asn = 0;

		try {
			numberRangeObject = webService.getNumberRageByDocumentType("ASN");
		} catch (Exception e) {
			e.printStackTrace();
		}

		NumberRange saveNumberRangeObject = null;
		if (numberRangeObject == null) {
			System.out.println(asn + " is not match please enter proper dctype");
			// js.put("DCTYP", transationType + ": is not match please enter proper
			// dctype");
			// json = js.toString();
			// return new ResponseEntity<String>(json, HttpStatus.OK);
		}

		asn = numberRangeObject.getFromNumber() + 1;
		System.out.println("ASN:" + asn);

		if (asn <= numberRangeObject.getToNumber()) {
			System.out.println("ASN:" + asn);
			NumberRange numberRange = new NumberRange();
			numberRange.setId(numberRangeObject.getId());
			numberRange.setFromNumber(asn);
			numberRange.setToNumber(numberRangeObject.getToNumber());
			numberRange.setTransationType(numberRangeObject.getTransationType());
			numberRange.setYear(numberRangeObject.getYear());
			saveNumberRangeObject = webService.saveNumberRange(numberRange);
		}

		asnheader.setStatus(3);
		asnheader.setDrfgrnno(asn);

		List<ASNItem> asnitems = asnheader.getAsnitem();
		for (ASNItem asn_i : asnitems) {
			asn_i.setCp_fk(asn);

		}
		List<ASNSection> asnsections = asnheader.getAsnsection();
		for (ASNSection as : asnsections) {
			System.out.println("doc : " + as.getAttachment());
			System.out.println("doc length : " + as.getAttachment().length());
			if (as.getAttachment() != null) {
				if (as.getAttachment().length() > 0) {
					String data = as.getAttachment().split(",")[1];
					Decoder decoder = Base64.getDecoder();
					byte[] decodedByte = decoder.decode(data);
					Blob b = new SerialBlob(decodedByte);
					// Utility.writeUsingFiles(data);
					as.setData(b);
					as.setAttachment(as.getAttachment().split(",")[0]);
				}
			}
		}
		asnheader = asnHeaderRepository.save(asnheader);
		if (asnheader.getDrfgrnno() > 0) {
			map.put("code", "1000");
			map.put("asno", asnheader.getDrfgrnno());
			map.put("status", "ASN created successfully");

		} else {
			map.put("code", "1001");
			map.put("asno", 0);
			map.put("error", "ASN creation failed");
		}

		return map;
	}

	public List<HashMap<String, Object>> getRFPwithVPNO(String lifnr) throws SQLException {
		List<RFPInvitedVendors> rfpinvite = rfpInvitedVendorRepo.getRFPInviteByLifnr(lifnr);
		// RFPInvitedVendors rfpinvite1 =
		// rfpInvitedVendorRepo.getRFPInviteByCpfk(lifnr);
		long rfpno = 0;
		String rfpmode = "S";
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();

		for (RFPInvitedVendors rfpinv : rfpinvite) {

			rfpno = rfpinv.getCp_fk();

			System.out.println("RFPNO:" + rfpno);
//			RFPHeader rpheader = rfpheaderRepository.getOne(rfpno);
			try {
				RFPHeader rpheader = rfpheaderRepository.findByRfpnoAndRfpmode(rfpno, rfpmode);//
				if (rpheader == null) {
					continue;
				}

			} catch (Exception e) {
				continue;
			}
			RFPHeader rpheader = rfpheaderRepository.findByRfpnoAndRfpmode(rfpno, rfpmode);//

			HashMap<String, Object> map1 = new HashMap<String, Object>();

			VPHeader vpheader = vpHeaderRepository.getVPByLifnrAndRfpno(lifnr, rfpno);

			// if((vpheader.getProposalno())!=0 || vpheader.getProposalno()!=null) {
			map1.put("rfpno", rpheader.getRfpno());
			map1.put("version", rpheader.getVersion());
			map1.put("versioninfo", rpheader.getVersioninfo());
			map1.put("bukrs", rpheader.getBukrs());
			map1.put("ekorg", rpheader.getEkorg());
			map1.put("status", rpheader.getStatus());
			map1.put("dt", rpheader.getDt());
			map1.put("df", rpheader.getDf());
			map1.put("transactiontype", rpheader.getTransactiontype());
			map1.put("prereq", rpheader.getPrereq());
			map1.put("changeddate", rpheader.getChangeddate());
			map1.put("createddate", rpheader.getCreateddate());
			map1.put("createdby", rpheader.getCreatedby());

			// if((vpheader.getProposalno())!=0 || (vpheader.getProposalno)!=null) {
			try {
				System.out.println("VPNO:" + vpheader.getProposalno());
				map1.put("proposalno", vpheader.getProposalno());

			} catch (NullPointerException e) {
				map1.put("proposalno", "");
			}
			metaDataList.add(map1);
		}
		return metaDataList;
	}

	public List<HashMap<String, Object>> getRFPwithVPNOWithSessionid(String sessionid) throws SQLException {

		HashMap<String, Object> map = new HashMap<String, Object>();
		String ardid = null;
		String crvendor = null;
		UserMaster vendor = null;
		String lifnrbysesid = null;
		try {
			long sesid = Long.parseLong(sessionid);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();

				}
			}
			if (ardid == null) {
				map.put("error", "Session Expired");
				return (List<HashMap<String, Object>>) map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			vendor = webService.getUserDetails(ardid);
			System.out.println("CREATEDVENDOR:" + vendor.getId());
			System.out.println("CREATEDVENDORID:" + vendor.getAdrid());
			crvendor = Long.toString(vendor.getId());
			System.out.println("CREATEDVENDORID:" + crvendor);
			lifnrbysesid = vendor.getAdrid();
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<RFPInvitedVendors> rfpinvite = rfpInvitedVendorRepo.getRFPInviteByLifnr(lifnrbysesid);
		// RFPInvitedVendors rfpinvite1 =
		// rfpInvitedVendorRepo.getRFPInviteByCpfk(lifnr);
		long rfpno = 0;
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();

		for (RFPInvitedVendors rfpinv : rfpinvite) {

			rfpno = rfpinv.getCp_fk();

			System.out.println("RFPNO:" + rfpno);
			RFPHeader rpheader = rfpheaderRepository.getOne(rfpno);
			HashMap<String, Object> map1 = new HashMap<String, Object>();

			VPHeader vpheader = vpHeaderRepository.getVPByLifnrAndRfpno(lifnrbysesid, rfpno);

			// if((vpheader.getProposalno())!=0 || vpheader.getProposalno()!=null) {
			map1.put("rfpno", rpheader.getRfpno());
			map1.put("version", rpheader.getVersion());
			map1.put("versioninfo", rpheader.getVersioninfo());
			map1.put("bukrs", rpheader.getBukrs());
			map1.put("ekorg", rpheader.getEkorg());
			map1.put("status", rpheader.getStatus());
			map1.put("dt", rpheader.getDt());
			map1.put("df", rpheader.getDf());
			map1.put("transactiontype", rpheader.getTransactiontype());
			map1.put("prereq", rpheader.getPrereq());
			map1.put("changeddate", rpheader.getChangeddate());
			map1.put("createddate", rpheader.getCreateddate());
			map1.put("createdby", rpheader.getCreatedby());

			// if((vpheader.getProposalno())!=0 || (vpheader.getProposalno)!=null) {
			try {
				System.out.println("VPNO:" + vpheader.getProposalno());
				map1.put("proposalno", vpheader.getProposalno());

			} catch (NullPointerException e) {
				map1.put("proposalno", "");
			}
			metaDataList.add(map1);
		}
		return metaDataList;
	}

	public List<Invoice> getAllInvoice() throws SQLException {

		List<Invoice> invoices = invoiceRepository.findAll();
		return invoices;
	}

	public HashMap<String, Object> updateProposalSummary(ProposalSummary proposalsummary)
			throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = proposalsummary.getSessionid();
		System.out.println("SESSIONID:" + sesId);
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
				map.put("error", "Session Expired");
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CHANGEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CHANGEDDATE:" + today.toString());
			proposalsummary.setChangeddBy(cradrid);
			proposalsummary.setChangeddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// invoice.setCurrentState("1");
		proposalsummary = proposalSummaryRepo.save(proposalsummary);
		if (proposalsummary.getPropsumid() > 0) {
			map.put("Status", "psid updated successfuully");
			map.put("ProposalSummaryId", proposalsummary.getPropsumid());

		} else {
			map.put("Status", "Failed");
			map.put("ProposalSummaryId", 0);
		}
		return map;
	}

	public HashMap<String, Object> updateInvoice(Invoice invoice) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = invoice.getSessionid();
		System.out.println("SESSIONID:" + sesId);
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
				map.put("error", "Session Expired");
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CHANGEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CHANGEDDATE:" + today.toString());
			invoice.setChangeddBy(cradrid);
			invoice.setChangeddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// invoice.setCurrentState("1");
		invoice = invoiceRepository.save(invoice);
		if (invoice.getInvoiceId() > 0) {

			map.put("code", "1000");
			map.put("status", "Invoice updated successfully");
			map.put("InvoiceId", invoice.getInvoiceId());

		} else {
			map.put("code", "1001");
			map.put("error", "Invoice updation failed");
			map.put("InvoiceId", 0);

		}
		return map;
	}

	public Invoice getInvoice(long invoiceId) throws SQLException {

		Invoice invoice = invoiceRepository.getOne(invoiceId);
		return invoice;
	}

	public ProposalSummary getProposalSummary(long rfpno) throws SQLException {
		ProposalSummary proposalSummary = proposalSummaryRepo.getProposalSummaryByRfpno(rfpno);
		return proposalSummary;
	}

	public List<Invoice> getInvByasnNoAndLifnr(String asnNo, String lifnr) throws SQLException {
		List<Invoice> invoice = null;
		// if ((asnNo != 0) && (lifnr != null)) {
		if ((!asnNo.isEmpty()) && (!lifnr.isEmpty())) {
			invoice = invoiceRepository.findAllByAsnNoAndVendorNo(asnNo, lifnr);
			// return invoice;
		}

		// else if ((asnNo != 0) && (lifnr == null)) {
		else if ((!asnNo.isEmpty()) && (lifnr == null)) {
			invoice = invoiceRepository.findByAsnNo(asnNo);
			// return invoice;
		} else if ((!lifnr.isEmpty()) && (asnNo.isEmpty())) {
			invoice = invoiceRepository.findByVendorNo(lifnr);
			// return invoice;
		}
		return invoice;

	}

	public List<HashMap<String, Object>> getMetaData() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<Invoice> invoices = invoiceRepository.findAll();
		for (Invoice invoice : invoices) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("InvoiceId", invoice.getInvoiceId());
			map.put("CurrentState", invoice.getCurrentState());
			metaDataList.add(map);
		}
		return metaDataList;
	}

	public List<Invoice> getInvoiceByCurrentState(String currentState) throws SerialException, SQLException {
		List<Invoice> invoices = invoiceRepository.getInvoicesByCurrentState(currentState);
		return invoices;
	}

	public List<VendorBalance> getVendorBalances() throws SQLException {
		List<VendorBalance> vendorbalances = vendorBalanceRepository.findAll();
		return vendorbalances;

	}

	public VendorBalance getVendorBalance(String lifnr) throws SQLException {
		VendorBalance vendorbalance = vendorBalanceRepository.getVendorBalanceByLifnr(lifnr);
		return vendorbalance;
	}

	public List<HashMap<String, Object>> getAllVendorBalanceMetaData() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<VendorBalance> vendorbalances = vendorBalanceRepository.findAll();
		for (VendorBalance vendorbalance : vendorbalances) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("vendorledgerid", vendorbalance.getVendorledgerid());
			map.put("vendorname", vendorbalance.getName1());
			map.put("vendor code", vendorbalance.getLifnr());
			map.put("documentno", vendorbalance.getBelnr());
			map.put("documentdate", vendorbalance.getBldat());
			map.put("invoicereference", vendorbalance.getXblnr());
			map.put("debitbalance", vendorbalance.getShkzgdb());
			map.put("creditbalance", vendorbalance.getShkzgcr());
			map.put("running amount", vendorbalance.getWrbtr());
			map.put("postingdate", vendorbalance.getBudat());
			map.put("status", vendorbalance.getStatus());
			map.put("createdby", vendorbalance.getCreatedBy());
			map.put("changedby", vendorbalance.getChangeddBy());
			map.put("changeddate", vendorbalance.getChangeddate());
			metaDataList.add(map);
		}
		return metaDataList;
	}

	public List<VendorLedger> getVendorLedgers() throws SQLException {
		List<VendorLedger> vendorledgers = vendorLedgerRepository.findAll();
		return vendorledgers;

	}

	public List<VendorLedger> getVendorLedger(String lifnr) throws SQLException {
		List<VendorLedger> vendorledgers = vendorLedgerRepository.getVendLedgByLifnr(lifnr);
		return vendorledgers;
	}

	public List<HashMap<String, Object>> getAllVendorLedgerMetaData(String sessionid) {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> map1 = new HashMap<String, Object>();
		String cradrid = null;
		String ardid = null;
		String crby = null;
		List<HashMap<String, Object>> json = new ArrayList<HashMap<String, Object>>();
		JSONObject js = new JSONObject();
		UserMaster createdBy = null;
		try {
			long sesid = Long.parseLong(sessionid);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();

				}
			}
			if (ardid == null) {
				map1.put("error", "Session Expired");
				return (List<HashMap<String, Object>>) map1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// get approver log table for ASN appid for the adrid

		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CHANGEDBY:" + createdBy.getId());
			crby = Long.toString(createdBy.getId());
			cradrid = createdBy.getAdrid();

		} catch (Exception e) {
			e.printStackTrace();
		}

		List<VendorLedger> vendorledgers = vendorLedgerRepository.getVendLedgByLifnr(cradrid);
		for (VendorLedger vendorledger : vendorledgers) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("vendorledgerid", vendorledger.getVendorledgerid());
			map.put("vendorname", vendorledger.getName1());
			map.put("vendor code", vendorledger.getLifnr());
			map.put("documentno", vendorledger.getBelnr());
			map.put("documentdate", vendorledger.getBldat());
			map.put("invoicereference", vendorledger.getXblnr());
			map.put("debitcreditindicator", vendorledger.getShkzg());
			map.put("running amount", vendorledger.getWrbtr());
			map.put("postingdate", vendorledger.getBudat());
			map.put("docstatus", vendorledger.getStatus());
			map.put("createdby", vendorledger.getCreatedBy());
			map.put("changedby", vendorledger.getChangeddBy());
			map.put("changeddate", vendorledger.getChangeddate());
			map.put("entrydat", vendorledger.getEntrydat());
			map.put("pstyp", vendorledger.getPstyp());
			map.put("ebeln", vendorledger.getEbeln());
			map.put("gjahr", vendorledger.getGjahr());
			map.put("monat", vendorledger.getMonat());
			map.put("waers", vendorledger.getWaers());
			map.put("zumsk", vendorledger.getZumsk());
			map.put("wrbtr1", vendorledger.getWrbtr1());
			map.put("augbl", vendorledger.getAugbl());
			map.put("augdt", vendorledger.getAugdt());
			map.put("sgtxt", vendorledger.getSgtxt());
			map.put("sno", vendorledger.getSno());
			map.put("belnr1", vendorledger.getBelnr1());
			map.put("lfbnr", vendorledger.getLfbnr());
			map.put("rebzg", vendorledger.getRebzg());
			metaDataList.add(map);
		}
		return metaDataList;
	}

	public List<Payment> getAllPayments() throws SQLException {
		List<Payment> payments = paymentRepository.findAll();
		return payments;

	}

	public Payment getPayment(String lifnr) throws SQLException {
		Payment payment = paymentRepository.getPaymentByLifnr(lifnr);
		return payment;
	}

	public List<HashMap<String, Object>> getAllPaymentMetaData() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<Payment> payments = paymentRepository.findAll();
		for (Payment payment : payments) {
			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("paymentid", payment.getPaymentid());
			map.put("vendorname", payment.getName1());
			map.put("vendor code", payment.getLifnr());
			map.put("paymentdocdate", payment.getBldat());
			map.put("paymentamount", payment.getWrbtr());
			map.put("currency", payment.getWaers());
			map.put("reference", payment.getXblnr());
			map.put("invoiceno", payment.getRebzg());
			map.put("invoiceamount", payment.getWrbtr1());
			map.put("paymentmethod", payment.getRzawe());
			map.put("bankaccountno", payment.getUbknt());
			map.put("chequeno", payment.getChect());
			map.put("entrydat", payment.getEntrydat());
			// map.put("status",payment.getStatus());
			map.put("createdby", payment.getCreatedBy());
			map.put("changedby", payment.getChangeddBy());
			map.put("changeddate", payment.getChangeddate());
			metaDataList.add(map);
		}
		return metaDataList;
	}

	public List<GRNHeader> getAllGRNHeaders() throws SQLException {
		List<GRNHeader> grnheaders = grnheaderRepository.findAll();
		return grnheaders;

	}

	public GRNHeader getGRNByAsno(long grn) throws SQLException {
		GRNHeader grnheader = grnheaderRepository.getOne(grn);
		return grnheader;
	}

	public GRNHeader getGRNHeader(String lifnr) throws SQLException {
		GRNHeader grnheader = grnheaderRepository.getGRNByLifnr(lifnr);
		return grnheader;
	}

	public List<HashMap<String, Object>> getAllGRNMetaData() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<GRNHeader> grnheaders = grnheaderRepository.findAll();
		for (GRNHeader grnheader : grnheaders) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("grn", grnheader.getGrn());
			map.put("po no", grnheader.getEbeln());
			map.put("vendor code", grnheader.getLifnr());
			map.put("vendor name", grnheader.getName1());
			map.put("status", grnheader.getStatus());
			map.put("createdby", grnheader.getCreatedBy());
			map.put("changedby", grnheader.getChangeddBy());
			map.put("budat", grnheader.getBudat());
			map.put("changeddate", grnheader.getChangeddate());
			metaDataList.add(map);
		}
		return metaDataList;
	}

	public HashMap<String, Object> addASN1(ASNHeader asnheader) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		// PurchaseOrder purchaseOrder =
		// poheaderrepository.getOne(asnheader.getEbeln());
		long asnno = 0;
		// System.out.println("purchaseOrder:"+purchaseOrder);
		int status = asnheader.getStatus();
		System.out.println("Status:" + status);
		// if((purchaseOrder!=null) && (status == 1)) {
		NumberRange numberRangeObject = null;
		try {
			numberRangeObject = webService.getNumberRageByDocumentType("ASN");
		} catch (Exception e) {
			e.printStackTrace();
		}
		NumberRange saveNumberRangeObject = null;
		asnno = numberRangeObject.getFromNumber() + 1;
		System.out.println("ASNNO2:" + asnno);
		if (asnno <= numberRangeObject.getToNumber()) {
			NumberRange numberRange = new NumberRange();
			numberRange.setId(numberRangeObject.getId());
			numberRange.setFromNumber(asnno);
			numberRange.setToNumber(numberRangeObject.getToNumber());
			numberRange.setTransationType(numberRangeObject.getTransationType());
			numberRange.setYear(numberRangeObject.getYear());
			saveNumberRangeObject = webService.saveNumberRange(numberRange);
		}
		// asnheader.setStatus(1);
		asnheader.setDrfgrnno(asnno);
		// }

		List<ASNSection> asnSection = asnheader.getAsnsection();
		for (ASNSection asn : asnSection) {
			if (asn.getAttachment() != null) {
				if (asn.getAttachment().length() > 0) {
					String data = asn.getAttachment().split(",")[1];
					Decoder decoder = Base64.getDecoder();
					byte[] decodedByte = decoder.decode(data);
					Blob b = new SerialBlob(decodedByte);
					// Utility.writeUsingFiles(data);
					asn.setData(b);
					asn.setAttachment(asn.getAttachment().split(",")[0]);
				}
			}
		}
		asnheader = asnHeaderRepository.save(asnheader);
		if (asnheader.getDrfgrnno() > 0) {
			map.put("code", "asnheader");
			map.put("asnno", asnheader.getDrfgrnno());
			map.put("status", "ASN created successfully");

		} else {
			map.put("code", "1001");
			map.put("asnno", 0);
			map.put("error", "ASN creation failed");
		}
		System.out.println("response : " + map);
		return map;
	}

	public HashMap<String, Object> updateASN(ASNHeader asnheader) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = asnheader.getSessionid();
		System.out.println("SESSIONID:" + sesId);
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
				map.put("error", "Session Expired");
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CHANGEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CHANGEDDATE:" + today.toString());
			asnheader.setChangeddBy(cradrid);
			asnheader.setChangeddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		// asnheader.setStatus(1);
		long asnno = asnheader.getDrfgrnno();

		List<ASNItem> asnitems = asnheader.getAsnitem();
		for (ASNItem asn_i : asnitems) {
			asn_i.setCp_fk(asnno);

		}

		List<ASNSection> asnSection = asnheader.getAsnsection();
		for (ASNSection asn : asnSection) {
			if (asn.getAttachment() != null) {
				if (asn.getAttachment().length() > 0) {

					String data = asn.getAttachment().split(",")[1];
					Decoder decoder = Base64.getDecoder();
					byte[] decodedByte = decoder.decode(data);
					Blob b = new SerialBlob(decodedByte);
					// Utility.writeUsingFiles(data);
					asn.setData(b);
					asn.setAttachment(asn.getAttachment().split(",")[0]);
				}
			}
		}
		asnheader = asnHeaderRepository.save(asnheader);
		if (asnno > 0) {
			map.put("code", "1000");
			map.put("asnno", asnno);
			map.put("status", "ASNNO updated successfully");

		} else {
			map.put("code", "1001");
			map.put("asnno", 0);
			map.put("error", "ASN updation failed");
		}
		System.out.println("response : " + map);
		return map;
	}

	public List<ASNHeader> getAllASNHeaders() throws SQLException {
		List<ASNSection> asnSections;
		List<ASNHeader> asnheaders = asnHeaderRepository.findAll();
		for (ASNHeader asnheader : asnheaders) {
			asnSections = asnheader.getAsnsection();
			for (ASNSection asn : asnSections) {
				Blob docBlob = asn.getData();
				byte[] bts1 = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts1);
				asn.setAttachment(asn.getAttachment() + "," + encoded);
				asn.setData(null);
			}

		}
		return asnheaders;

	}

	public List<HashMap<String, Object>> getAllASNMetaData() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<ASNHeader> asnheaders = asnHeaderRepository.findAll();
		for (ASNHeader asnheader : asnheaders) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("drfgrnno", asnheader.getDrfgrnno());
			map.put("status", asnheader.getStatus());
			map.put("ebeln", asnheader.getEbeln());
			map.put("shipmentdetail", asnheader.getShipmentdetail());
			map.put("shipmentno", asnheader.getShipmentno());
			map.put("banfn", asnheader.getBanfn());
			map.put("transportname", asnheader.getTransportname());
			map.put("vehicleno", asnheader.getVehicleno());
			map.put("ewaybillno", asnheader.getEwaybillno());
			map.put("vbeln", asnheader.getVbeln());
			map.put("discamt", asnheader.getDiscamt());
			map.put("bsamt", asnheader.getBsamt());
			map.put("taxamt", asnheader.getTaxamt());
			map.put("createdby", asnheader.getCreatedBy());
			map.put("changedby", asnheader.getChangeddBy());
			map.put("vendorinvdate", asnheader.getVendorinvdate());
			map.put("changeddate", asnheader.getChangeddate());
			metaDataList.add(map);
		}
		return metaDataList;
	}

	public List<HashMap<String, Object>> getAllASNMetaDataByApprover(String sessionid) {

		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();

		HashMap<String, Object> map1 = new HashMap<String, Object>();
		String cradrid = null;
		String ardid = null;
		String crby = null;
		List<HashMap<String, Object>> json = new ArrayList<HashMap<String, Object>>();
		JSONObject js = new JSONObject();
		UserMaster createdBy = null;
		try {
			long sesid = Long.parseLong(sessionid);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();

				}
			}
			if (ardid == null) {
				map1.put("error", "Session Expired");
				return (List<HashMap<String, Object>>) map1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// get approver log table for ASN appid for the adrid

		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CHANGEDBY:" + createdBy.getId());
			crby = Long.toString(createdBy.getId());
			cradrid = createdBy.getAdrid();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			// HashMap<String, Object> map = new HashMap<String, Object>();
			WorkflowConfig appid = workFlowRepo.findByAppid("ASN");
			if (appid == null) {

//				System.out.println("Workflow Config is Empty");
//				js.put("code", "1001");
//				js.put("error", "Workflow Config is Empty");
//			
//			
//				return (List<HashMap<String, Object>>) json;
			}

			List<ApprovalLog> approvalLogsList = approvalLogRepo.findByAprvAndAppid(cradrid, appid);

			for (ApprovalLog approvalLogOne : approvalLogsList) {

				// String lifnrId = String.valueOf(approvalLogOne.getReqNo());
				long asno = approvalLogOne.getReqNo();

				// List<ASNHeader> asnheaders = asnHeaderRepository.findAll();
				ASNHeader asnheader = asnHeaderRepository.findOne(asno);

				// for (ASNHeader asnheader : asnheaders) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("drfgrnno", asnheader.getDrfgrnno());
				map.put("status", approvalLogOne.getStatus());
				map.put("ebeln", asnheader.getEbeln());
				map.put("shipmentdetail", asnheader.getShipmentdetail());
				map.put("shipmentno", asnheader.getShipmentno());
				map.put("banfn", asnheader.getBanfn());
				map.put("transportname", asnheader.getTransportname());
				map.put("vehicleno", asnheader.getVehicleno());
				map.put("ewaybillno", asnheader.getEwaybillno());
				map.put("vbeln", asnheader.getVbeln());
				map.put("discamt", asnheader.getDiscamt());
				map.put("bsamt", asnheader.getBsamt());
				map.put("taxamt", asnheader.getTaxamt());
				map.put("createdby", asnheader.getCreatedBy());
				map.put("changedby", asnheader.getChangeddBy());
				map.put("vendorinvdate", asnheader.getVendorinvdate());
				map.put("changeddate", asnheader.getChangeddate());
				map.put("level", approvalLogOne.getAprvlevel());
				metaDataList.add(map);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return metaDataList;
	}

	public ASNHeader getASNHeader(long drfgrnno) throws SQLException {
		ASNHeader asnheader = asnRepository.getOne(drfgrnno);
		if (asnheader.getDrfgrnno() > 0) {
			List<ASNSection> asnSections = asnheader.getAsnsection();
			for (ASNSection asn : asnSections) {
				Blob docBlob = asn.getData();
				byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts);
				asn.setAttachment(asn.getAttachment() + "," + encoded);
				asn.setData(null);
			}
		}
		return asnheader;
	}

	public List<ASNHeader> getASNByEbeln(Long ebeln) throws SerialException, SQLException {
		List<ASNSection> asnSections;
		List<ASNHeader> asnheaders = asnRepository.getASNByEbeln(ebeln);
		for (ASNHeader asnheader : asnheaders) {
			asnSections = asnheader.getAsnsection();
			for (ASNSection asn : asnSections) {
				if (asn.getAttachment() != null) {
					if (asn.getAttachment().length() > 0) {
						getASNSectionBlobToBase64(asn);
					}
				}
			}
		}
		return asnheaders;
	}

	public List<Vendor> getAllVendors1() throws SQLException {
		List<VendorDocument> vendorDocuments;
		List<Vendor> vendors = vendorRepository.findAll();
		for (Vendor vendor : vendors) {
			vendorDocuments = vendor.getVenDocuments();
			for (VendorDocument vd : vendorDocuments) {
				if (vd.getAttachment() != null) {
					if (vd.getAttachment().length() > 0) {
						Blob docBlob = vd.getData();
						byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
						String encoded = Base64.getEncoder().encodeToString(bts);
						vd.setAttachment(vd.getAttachment() + "," + encoded);
						vd.setData(null);
					}
				}
			}
		}
		return vendors;
	}

	public List<PurchaseOrder> getAllPOs() throws SQLException {
		PODocument poDocuments;
//		List<PurchaseOrder> poheadersList = new ArrayList<PurchaseOrder>();
		List<PurchaseOrder> poheaders = poheaderrepository.findAll();
		for (PurchaseOrder poheader : poheaders) {
			poDocuments = poheader.getPoDocument();
			Blob docBlob = poDocuments.getFileData();
			byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
			String encoded = Base64.getEncoder().encodeToString(bts);
			poDocuments.setAttachment(poDocuments.getAttachment() + "," + encoded);
			poDocuments.setFileData(null);
//			poheadersList.add(poheader);
		}
		return poheaders;
	}

	public PurchaseOrder getPurchaseOrder(long ebeln) throws SQLException {
		// Optional<Vendor> vendor = vendorRepository.findById(vid);
		// Vendor vendor = vendorRepository.findById(vid).get();
		PODocument poDocuments;
		PurchaseOrder poheader = poheaderrepository.findByEbeln(ebeln);
		if (poheader != null) {
			poDocuments = poheader.getPoDocument();
			Blob docBlob = poDocuments.getFileData();
			byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
			String encoded = Base64.getEncoder().encodeToString(bts);
			poDocuments.setAttachment(poDocuments.getAttachment() + "," + encoded);
			poDocuments.setFileData(null);
		}

		
		return poheader;

	}

	public List<PurchaseOrder> getPurchaseOrderByVendor(String lifnr) throws SerialException, SQLException {
		List<PurchaseOrder> pos = poheaderrepository.getPurchaseOrderByLifnr(lifnr);

		PODocument poDocuments;
		for (PurchaseOrder poheader : pos) {
			poDocuments = poheader.getPoDocument();
			Blob docBlob = poDocuments.getFileData();
			byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
			String encoded = Base64.getEncoder().encodeToString(bts);
			poDocuments.setFileData(null);
			// poDocuments.getFileData();
		}

		return pos;
	}

	public List<HashMap<String, Object>> getAllPOMetaData() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<PurchaseOrder> poheaders = poheaderrepository.findAll();
		for (PurchaseOrder poheader : poheaders) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ebeln", poheader.getEbeln());
			map.put("status", poheader.getStatus());
			map.put("bsart", poheader.getBsart());
			map.put("bukrs", poheader.getBukrs());
			map.put("lifnr", poheader.getLifnr());
			map.put("ekorg", poheader.getEkorg());
			map.put("ackno", poheader.getAckno());
			map.put("cntr_no", poheader.getCntr_no());
			map.put("aedat", poheader.getAedat());
			map.put("cddat", poheader.getCddat());
			// map.put("createdBy",poheader.getCreatedBy());
			// map.put("changeddBy",poheader.getChangeddBy());
			map.put("name1", poheader.getName1());
			map.put("status", poheader.getStatus());
			map.put("ernam", poheader.getErnam());
			// map.put("pstyp",poheader.getPstyp());
			metaDataList.add(map);

		}
		return metaDataList;
	}

	public List<HashMap<String, Object>> getPOBasicDetailsBySessionid(String sessionid) {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		// List<PurchaseOrder> poheaders =
		// poheaderrepository.getPOBySessionid(sessionid);
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		String cradrid = null;
		String ardid = null;
		String crby = null;
		UserMaster createdBy = null;
		try {
			long sesid = Long.parseLong(sessionid);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();

				}
			}
			if (ardid == null) {
				map1.put("error", "Session Expired");
				return (List<HashMap<String, Object>>) map1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			crby = Long.toString(createdBy.getId());
			cradrid = createdBy.getAdrid();
			// contracts.setCreatedby(crby);
			// contracts.setCreateddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// List<PurchaseOrder> poheaders =
		// poheaderrepository.getPurchaseOrderByCreatedBy(crby);
		List<PurchaseOrder> poheaders = poheaderrepository.getPurchaseOrderByLifnr(cradrid);
		for (PurchaseOrder poheader : poheaders) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("pono", poheader.getEbeln());
			map.put("status", poheader.getStatus());
			map.put("bsart", poheader.getBsart());
			map.put("bukrs", poheader.getBukrs());
			map.put("lifnr", poheader.getLifnr());
			map.put("ekorg", poheader.getEkorg());
			map.put("ackno", poheader.getAckno());
			map.put("cntr_no", poheader.getCntr_no());
			map.put("aedat", poheader.getAedat());
			map.put("cddat", poheader.getCddat());
			// map.put("createdBy",poheader.getCreatedBy());
			map.put("changeddBy", poheader.getChangeddBy());
			map.put("vendorname", poheader.getName1());
			map.put("status", poheader.getStatus());
			map.put("ernam", poheader.getErnam());
			// map.put("pstyp",poheader.getPstyp());
			metaDataList.add(map);

		}
		return metaDataList;
	}

	@Autowired
	private WorkFlowRepo workFlowRepo;

	public Vendor getVendor(long vid) throws SQLException {

		// Optional<Vendor> vendor = vendorRepository.findById(vid);
		// Vendor vendor = vendorRepository.findById(vid).get();

		Vendor vendor = vendorRepository.getOne(vid);

		if (vendor.getVid() > 0) {
			List<VendorDocument> vendorDocuments = vendor.getVenDocuments();
			for (VendorDocument vd : vendorDocuments) {
				if (vd.getAttachment() != null) {
					if (vd.getAttachment().length() > 0) {
						Blob docBlob = vd.getData();
						byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
						String encoded = Base64.getEncoder().encodeToString(bts);
						vd.setAttachment(vd.getAttachment() + "," + encoded);
						vd.setData(null);
					}
				}
			}
		}

		return vendor;

	}

	public List<HashMap<String, Object>> getAllVendorsMetaData() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<Vendor> vendors = vendorRepository.findAll();
		for (Vendor vendor : vendors) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("vid", vendor.getVid());
			map.put("orgName", vendor.getOrgName());
			map.put("status", vendor.getStatus());
			metaDataList.add(map);
		}
		return metaDataList;
	}

	public ASNSection getASNSectionBlobToBase64(ASNSection asn) throws SerialException, SQLException {
		Blob docBlob = asn.getData();
		byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
		String encoded = Base64.getEncoder().encodeToString(bts);
		asn.setAttachment(asn.getAttachment() + "," + encoded);
		asn.setData(null);
		return asn;
	}

	public VPSection getVPSectionBlobToBase64(VPSection vp) throws SerialException, SQLException {
		Blob docBlob = vp.getData();
		byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
		String encoded = Base64.getEncoder().encodeToString(bts);
		vp.setAttachment(vp.getAttachment() + "," + encoded);
		vp.setData(null);
		return vp;
	}

	public VPGenProposal getVPGenSectionBlobToBase64(VPGenProposal vp1) throws SerialException, SQLException {
		Blob docBlob = vp1.getData();
		byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
		String encoded = Base64.getEncoder().encodeToString(bts);
		vp1.setAttachment(vp1.getAttachment() + "," + encoded);
		vp1.setData(null);
		return vp1;
	}

	public VPTCProposal getVPTCPSectionBlobToBase64(VPTCProposal vp2) throws SerialException, SQLException {
		Blob docBlob = vp2.getData();
		byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
		String encoded = Base64.getEncoder().encodeToString(bts);
		vp2.setAttachment(vp2.getAttachment() + "," + encoded);
		vp2.setData(null);
		return vp2;
	}

	public HashMap<String, Object> addTechRatingHeader(TechRatingHeader techratinghead)
			throws SerialException, SQLException {

		HashMap<String, Object> map = new HashMap<String, Object>();
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = techratinghead.getSessionid();
		System.out.println("SESSIONID:" + sesId);
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
				map.put("error", "Session Expired");
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CREATEDDATE:" + today.toString());
			techratinghead.setCreatedby(cradrid);
			techratinghead.setCreateddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		List<TechnoRatings> techratings = techratinghead.getRating();
		for (TechnoRatings tr : techratings) {
			createdBy = webService.getUserDetails(ardid);
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			tr.setCreatedBy(cradrid);
			tr.setCreateddate(today.toString());
		}

		techratinghead = technoratingsRepository.save(techratinghead);
		if (techratinghead.getRfpno() > 0) {
			map.put("code", "1000");
			map.put("status", "Rating created successfully");
			map.put("rfpno", techratinghead.getRfpno());
		}

		else {
			map.put("code", "1001");
			map.put("rfpno", 0);
			map.put("error", "Rating creation failed");
		}

		System.out.println("response : " + map);
		return map;
	}

	public HashMap<String, Object> updateTechRatingHeader(TechRatingHeader techratinghead)
			throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = techratinghead.getSessionid();
		System.out.println("SESSIONID:" + sesId);
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
				map.put("error", "Session Expired");
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CHANGEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CHANGEDDATE:" + today.toString());
			techratinghead.setChangeddBy(cradrid);
			techratinghead.setChangeddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		// techhead.setStatus("2");
//		List<TechnoRatings> techratings = techratinghead.getRating();

		List<TechnoRatings> techratings = getRatingListByRfpVend(techratinghead.getRfpno(),
				techratinghead.getRating().get(0).getLifnr());
		for (TechnoRatings tr : techratings) {
			createdBy = webService.getUserDetails(ardid);
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			tr.setChangeddBy(cradrid);
			tr.setChangeddate(today.toString());
		}

		techratinghead = technoratingsRepository.save(techratinghead);
		if (techratinghead.getRfpno() > 0) {
			map.put("code", "1000");
			map.put("rfpno", techratinghead.getRfpno());
			map.put("status", "Rating updated successfully");

		} else {
			map.put("code", "1001");
			map.put("rfpno", 0);
			map.put("error", "Rating updation failed");
		}
		System.out.println("response : " + map);
		return map;
	}

	public List<TechRatingHeader> getAllTechnoRatings() throws SQLException {

		List<TechRatingHeader> techratingsheads = technoratingsRepository.findAll();
		return techratingsheads;
	}

	public TechRatingHeader getTechnoRatings(long rfpno) throws SQLException {
		TechRatingHeader techratingshead = technoratingsRepository.getOne(rfpno);
		return techratingshead;
	}

	public HashMap<String, Object> addTechCriteriaHeader(TechCriteriaHeader techcriteriahead)
			throws SerialException, SQLException {

		HashMap<String, Object> map = new HashMap<String, Object>();
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = techcriteriahead.getSessionid();
		System.out.println("SESSIONID:" + sesId);
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
				map.put("error", "Session Expired");
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CREATEDDATE:" + today.toString());
			techcriteriahead.setCreatedby(cradrid);
			techcriteriahead.setCreateddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		List<TechnoCriteria> techcriterias = techcriteriahead.getCriteria();
		for (TechnoCriteria tc : techcriterias) {
			createdBy = webService.getUserDetails(ardid);
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			tc.setCreatedBy(cradrid);
			tc.setCreateddate(today.toString());
		}
		techcriteriahead = technocriteriaRepository.save(techcriteriahead);
		if (techcriteriahead.getRfpno() > 0) {
			map.put("code", "1000");
			map.put("Status", "Criteria created successfully");
			map.put("rfpno", techcriteriahead.getRfpno());
		}

		else {
			map.put("code", "1001");
			map.put("rfpno", 0);
			map.put("error", "Criteria creation failed");
		}

		System.out.println("response : " + map);
		return map;
	}

	public HashMap<String, Object> updateTechCriteriaHeader(TechCriteriaHeader techcriteriahead)
			throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = techcriteriahead.getSessionid();
		System.out.println("SESSIONID:" + sesId);
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
				map.put("error", "Session Expired");
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CHANGEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CHANGEDDATE:" + today.toString());
			techcriteriahead.setChangeddBy(cradrid);
			techcriteriahead.setChangeddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		// techhead.setStatus("2");

		List<TechnoCriteria> techcriterias = techcriteriahead.getCriteria();
		for (TechnoCriteria tc : techcriterias) {
			createdBy = webService.getUserDetails(ardid);
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			tc.setChangeddBy(cradrid);
			tc.setChangeddate(today.toString());
		}

		techcriteriahead = technocriteriaRepository.save(techcriteriahead);
		if (techcriteriahead.getRfpno() > 0) {
			map.put("code", "1000");
			map.put("rfpno", techcriteriahead.getRfpno());
			map.put("status", "Criteria updated successfully");

		} else {
			map.put("code", "1001");
			map.put("rfpno", 0);
			map.put("error", "Criteria updation failed");
		}
		System.out.println("response : " + map);
		return map;
	}

	public List<TechCriteriaHeader> getAllTechnoCriterias() throws SQLException {

		List<TechCriteriaHeader> techcriteriaheads = technocriteriaRepository.findAll();
		return techcriteriaheads;
	}

	public TechCriteriaHeader getTechnoCriterias(long rfpno) throws SQLException {
		TechCriteriaHeader techcriteriahead = technocriteriaRepository.getOne(rfpno);
		return techcriteriahead;
	}

	public List<HashMap<String, Object>> getVendorBasicDetailsBySessionid(String sessionid) {

		HashMap<String, Object> map1 = new HashMap<String, Object>();
		String cradrid = null;
		String ardid = null;
		String crby = null;
		UserMaster createdBy = null;
		try {
			long sesid = Long.parseLong(sessionid);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();

				}
			}
			if (ardid == null) {
				map1.put("error", "Session Expired");
				return (List<HashMap<String, Object>>) map1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			crby = Long.toString(createdBy.getId());
			cradrid = createdBy.getAdrid();
			// contracts.setCreatedby(crby);
			// contracts.setCreateddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<Vendor> vendors = vendorRepository.getVendorByCreatedBy(cradrid);
		for (Vendor vendor : vendors) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("vid", vendor.getVid());
			map.put("orgName", vendor.getOrgName());
			map.put("status", vendor.getStatus());
			metaDataList.add(map);
		}
		return metaDataList;
	}

	/*
	 * 
	 * 
	 * public HashMap<String, Object> addTechnocom(TechnocomHeader techhead) throws
	 * SerialException, SQLException {
	 * 
	 * HashMap<String, Object> map = new HashMap<String, Object>(); java.util.Date
	 * utilDate = new java.util.Date(); java.sql.Date today = new
	 * java.sql.Date(utilDate.getTime()); String sesId = techhead.getSessionid();
	 * System.out.println("SESSIONID:"+sesId); UserMaster createdBy = null; String
	 * ardid = null; try { long sesid = Long.parseLong(sesId); List<SessionsTable>
	 * sessionsTableList = webService.getAllSessions(); for (SessionsTable
	 * sessionsTable2 : sessionsTableList) { if (sessionsTable2.getSesid() == sesid)
	 * { ardid = sessionsTable2.getAdrid();
	 * 
	 * } } if (ardid == null) { map.put("error", "Session Expired"); return map; }
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } try { createdBy =
	 * webService.getUserDetails(ardid);
	 * System.out.println("CREATEDBY:"+createdBy.getId()); String crby =
	 * Long.toString(createdBy.getId());
	 * System.out.println("CREATEDDATE:"+today.toString());
	 * techhead.setCreatedBy(crby); techhead.setCreateddate(today.toString());
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * techhead.setStatus("1"); techhead = technocomRepository.save(techhead);
	 * if(techhead.getRfpno() > 0) { map.put("code", "1000"); map.put("Status",
	 * "TCR created successfully"); map.put("rfpno", techhead.getRfpno()); }
	 * 
	 * else { map.put("code", "1001"); map.put("rfpno", 0); map.put("error",
	 * "TCR creation failed"); }
	 * 
	 * 
	 * 
	 * System.out.println("response : " + map); return map; }
	 * 
	 * public HashMap<String, Object> updateTechnocom(TechnocomHeader techhead)
	 * throws SerialException, SQLException { HashMap<String, Object> map = new
	 * HashMap<String, Object>();
	 * 
	 * java.util.Date utilDate = new java.util.Date(); java.sql.Date today = new
	 * java.sql.Date(utilDate.getTime()); String sesId = techhead.getSessionid();
	 * System.out.println("SESSIONID:"+sesId); UserMaster createdBy = null; String
	 * ardid = null; try { long sesid = Long.parseLong(sesId); List<SessionsTable>
	 * sessionsTableList = webService.getAllSessions(); for (SessionsTable
	 * sessionsTable2 : sessionsTableList) { if (sessionsTable2.getSesid() == sesid)
	 * { ardid = sessionsTable2.getAdrid();
	 * 
	 * } } if (ardid == null) { map.put("error", "Session Expired"); return map; }
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } try { createdBy =
	 * webService.getUserDetails(ardid);
	 * System.out.println("CHANGEDBY:"+createdBy.getId()); String crby =
	 * Long.toString(createdBy.getId());
	 * System.out.println("CHANGEDDATE:"+today.toString());
	 * techhead.setChangeddBy(crby); techhead.setChangeddate(today.toString());
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * 
	 * techhead.setStatus("2"); techhead = technocomRepository.save(techhead);
	 * if(techhead.getRfpno() > 0) { map.put("Status", "Successfull");
	 * map.put("code", "1000"); map.put("rfpno", techhead.getRfpno());
	 * map.put("status", "TCR updated successfully");
	 * 
	 * } else { map.put("code", "1001"); map.put("rfpno", 0); map.put("error",
	 * "TCR updation failed"); } System.out.println("response : " + map); return
	 * map; }
	 * 
	 * public TechnocomHeader getTechnocom(long rfpno) throws SQLException {
	 * TechnocomHeader technohead = technocomRepository.getOne(rfpno); return
	 * technohead; }
	 * 
	 * 
	 */

	// @Transactional
	public List<RFPHeader> getAllRFPHeaders() throws SQLException {
		List<RFPSection> rfpSections;
		List<RFPHeader> rfpheaders = rfpheaderRepository.findAll();
		for (RFPHeader rfpheader : rfpheaders) {
			rfpSections = rfpheader.getSection();
			for (RFPSection rf : rfpSections) {
				Blob docBlob = rf.getData();
				byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts);
				rf.setAttachment(rf.getAttachment() + "," + encoded);
				rf.setData(null);
			}
		}
		return rfpheaders;
	}

	public HashMap<String, Object> addVendor(Vendor vendor) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		NumberRange numberRangeObject = null;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = vendor.getSessionid();
		System.out.println("SESSIONID:" + sesId);
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
				map.put("error", "Session Expired");
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CREATEDDATE:" + today.toString());
			vendor.setCreatedBy(cradrid);
			vendor.setCreateddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		long vid = 0;

		try {
			numberRangeObject = webService.getNumberRageByDocumentType("VID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		NumberRange saveNumberRangeObject = null;
		if (numberRangeObject == null) {
			System.out.println(vid + " is not match please enter proper dctype");
			// js.put("DCTYP", transationType + ": is not match please enter proper
			// dctype");
			// json = js.toString();
			// return new ResponseEntity<String>(json, HttpStatus.OK);
		}

		vid = numberRangeObject.getFromNumber() + 1;
		System.out.println("VID:" + vid);

		System.out.println("VIDDDDDDDDDD:" + vid);

		if (vid <= numberRangeObject.getToNumber()) {
			System.out.println("vid:" + vid);
			NumberRange numberRange = new NumberRange();
			numberRange.setId(numberRangeObject.getId());
			numberRange.setFromNumber(vid);
			numberRange.setToNumber(numberRangeObject.getToNumber());
			numberRange.setTransationType(numberRangeObject.getTransationType());
			numberRange.setYear(numberRangeObject.getYear());
			saveNumberRangeObject = webService.saveNumberRange(numberRange);
		}

		String[] array = vendor.getDfdt().split("-");
		vendor.setDf(array[0]);
		vendor.setDt(array[1]);
		vendor.setStatus("3");
		vendor.setVid(vid);
		List<VendorDocument> vendorDocuments = vendor.getVenDocuments();
		for (VendorDocument vd : vendorDocuments) {
			System.out.println("doc : " + vd.getAttachment());
			System.out.println("doc length : " + vd.getAttachment().length());
			if (vd.getAttachment() != null) {
				if (vd.getAttachment().length() > 0) {
					String data = vd.getAttachment().split(",")[1];
					Decoder decoder = Base64.getDecoder();
					byte[] decodedByte = decoder.decode(data);
					Blob b = new SerialBlob(decodedByte);
					// Utility.writeUsingFiles(data);
					vd.setData(b);
					vd.setAttachment(vd.getAttachment().split(",")[0]);
				}
			}
		}
		// vendor.setVersion(1);
		vendor = vendorRepository.save(vendor);
		if (vendor.getVid() > 0) {
			map.put("code", "1000");
			map.put("Vendor Id", vendor.getVid());
			map.put("status", "Vendor updated successfully");

		} else {
			map.put("code", "1001");
			map.put("Vendor Id", 0);
			map.put("error", "Vendor updation failed");
		}
		return map;
	}

	public HashMap<String, Object> addRFPHeader(RFPHeader rfpheader) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		// long id = new Date().getTime();
		UserMaster userMaster = new UserMaster();
		VendorRegistration venReg = new VendorRegistration();
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		long rfpno = 0;
		long templifnr = 0;
		String transationType = rfpheader.getTransactiontype();
		String rfpMode = rfpheader.getRfpmode();
		String vendType = "LIFNR";

		String sesId = rfpheader.getSessionid();
		System.out.println("SESSIONID:" + sesId);
		UserMaster createdBy = null;
		String ardid = null;
		String mob = null;
		String contperson = null;
		String emailid = null;
		String crdate = null;
		String lifnr = null;
		String name = null;
		String status = null;
		String tempflag = null;
		String mobile = null;
		try {
			long sesid = Long.parseLong(sesId);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();

				}
			}
			if (ardid == null) {
				map.put("error", "Session Expired");
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CREATEDDATE:" + today.toString());
			rfpheader.setCreatedby(cradrid);
			rfpheader.setCreateddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		NumberRange numberRangeObject = null;
		NumberRange numberRangeObject1 = null;
		try {
			numberRangeObject = webService.getNumberRageByDocumentType(transationType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		NumberRange saveNumberRangeObject = null;
		if (numberRangeObject == null) {
			System.out.println(transationType + " is not match please enter proper dctype");
			// js.put("DCTYP", transationType + ": is not match please enter proper
			// dctype");
			// json = js.toString();
			// return new ResponseEntity<String>(json, HttpStatus.OK);
		}

		rfpno = numberRangeObject.getFromNumber() + 1;

		System.out.println("RFPNO:" + rfpno);
		if (rfpno <= numberRangeObject.getToNumber()) {
			System.out.println("rfpnooooooooooo2:" + rfpno);
			NumberRange numberRange = new NumberRange();
			numberRange.setId(numberRangeObject.getId());
			numberRange.setFromNumber(rfpno);
			numberRange.setToNumber(numberRangeObject.getToNumber());
			numberRange.setTransationType(numberRangeObject.getTransationType());
			numberRange.setYear(numberRangeObject.getYear());
			saveNumberRangeObject = webService.saveNumberRange(numberRange);
		}
		rfpheader.setStatus("1");
		rfpheader.setRfpno(rfpno);

		List<RFPInvitedVendors> rfpVendors = rfpheader.getInvitedvendors();
		for (RFPInvitedVendors rv : rfpVendors) {
			rv.setCp_fk(rfpno);
			String mailflag = rv.getTempflag();
			if (rfpheader.getRfpmode().equals("S") && mailflag.equalsIgnoreCase("Y")) {
				mob = rv.getContactno();
				contperson = rv.getContactperson();
				emailid = rv.getEmailid();
				crdate = rv.getCreateddate();
				mobile = rv.getContactno();
				long rfpn = rfpno;
				lifnr = rv.getLifnr();
				name = rv.getName();
				status = rv.getStatus();
				tempflag = rv.getTempflag();
				String lifnrid = rv.getLifnr();
				String email = rv.getEmailid();
				// String actstatus = rv.getStatus();
				if (lifnr.equals("null") || lifnr.equals("") || lifnr.equals(null) || lifnr.isEmpty())
				// if(lifnrid.equals("") || lifnrid.equals("null") || lifnrid.equals(null) ||
				// lifnrid.isEmpty())
				{

					try {
						numberRangeObject1 = webService.getNumberRageByDocumentType(vendType);
					} catch (Exception e) {
						e.printStackTrace();
					}
					NumberRange saveNumberRangeObject1 = null;
					if (numberRangeObject1 == null) {
						System.out.println(vendType + " is not match please enter proper dctype");
					}
					templifnr = numberRangeObject1.getFromNumber() + 1;
					System.out.println("TEMP LIFNR:" + templifnr);
					if (templifnr <= numberRangeObject1.getToNumber()) {
						System.out.println("lifnrrrrrrrrrrrrr2:" + templifnr);
						NumberRange numberRange = new NumberRange();
						numberRange.setId(numberRangeObject1.getId());
						numberRange.setFromNumber(templifnr);
						numberRange.setToNumber(numberRangeObject1.getToNumber());
						numberRange.setTransationType(numberRangeObject1.getTransationType());
						numberRange.setYear(numberRangeObject1.getYear());
						saveNumberRangeObject1 = webService.saveNumberRange(numberRange);
					}
					System.out.println("LIFNR:" + templifnr);
					String venlifnr = Long.toString(templifnr);
					// String vendor = Long.toString(templifnr);
					// //RandomStringUtils.randomNumeric(8);
					System.out.println("TEMPORARY VENDOR CODE:" + venlifnr);
					rv.setLifnr("T" + venlifnr);
					String pwd = RandomStringUtils.randomAlphanumeric(8);
					System.out.println("TEMPORARY PASSWORD:" + pwd);
					UserMaster userMasterObject = null;
					try {
						userMaster.setAdrid("T" + venlifnr);
						userMaster.setBegda(today);
						userMaster.setAdflag("I");
						userMaster.setPmble(mobile);
						// userMaster.setDepartment(department);
						userMaster.setEmail(emailid);
						userMaster.setPswrd(pwd);
						userMaster.setEunam(name);
						userMaster.setSunam(name);
						userMaster.setInactive(0);

						/*
						 * String dept = "99";
						 * 
						 * int id = Integer.parseInt(dept); RoleModel roleModle =
						 * webService.getRoleByRoleId(id); userMaster.setUserRoll(roleModle);
						 */

						userMaster.setBptype("V");
						userMaster.setUserDesignation("VENDOR");
						int blockstatus = Integer.parseInt(status);

						// int blockstatus=1;

						/*
						 * Status status1 = new Status(); status1.setStatusId(blockstatus);
						 * userMaster.setStatus(status1);
						 */
						userMasterObject = webService.createUser(userMaster);
					} catch (Exception e) {
						System.out.println(e);
					}
					long rfpnum = rfpheader.getRfpno();
					String currdt = rfpheader.getDt();
					// tempVendorMail(String email, String password, String name, String
					// venlifnr,long rfpnum, String currdf) {
					tempVendorMail(emailid, pwd, name, venlifnr, rfpnum, currdt);
					rv.setTempflag("N");
				} else if (lifnr != "null" || lifnr != "" || lifnr != null)
				// else if(!lifnrid.equals("") || !lifnrid.equals(null) || !lifnrid.isEmpty())
				{
					System.out.println("Vendor already exists!");
					System.out.println("EXISTING LIFNR:" + lifnrid);
					long rfpnum = rfpheader.getRfpno();
					String currdt = rfpheader.getDt();
					invitedVendorMail(email, name, rfpnum, currdt);
					rv.setTempflag("N");
				}

			} else if (rfpheader.getRfpmode().equals("D")) {
				map.put("code", "1001");
				map.put("rfpno", 0);
				map.put("success", "RFP draft saved successfully");
			}

			// rv.setCp_fk(rv.getCp_fk());
		}
		List<RFPParticipant> rfpParticipants = rfpheader.getParticipant();
		for (RFPParticipant rf : rfpParticipants) {
			rf.setCp_fk(rfpno);
		}

		List<RFPSection> rfpSection = rfpheader.getSection();

		for (RFPSection rf : rfpSection) {

			if (rf.getAttachment() != null) {
				if (rf.getAttachment().length() > 0) {
					String data = rf.getAttachment().split(",")[1];
					Decoder decoder = Base64.getDecoder();
					byte[] decodedByte = decoder.decode(data);
					Blob b = new SerialBlob(decodedByte);
					// Utility.writeUsingFiles(data);
					rf.setData(b);
					rf.setAttachment(rf.getAttachment().split(",")[0]);
				}
			}
		}
		rfpheader.setVersion(1);
		rfpheader = rfpheaderRepository.save(rfpheader);

		if (rfpheader.getRfpno() > 0) {
			map.put("code", "1000");
			map.put("rfpno", rfpheader.getRfpno());
			map.put("status", "RFP created successfully");

		} else {
			map.put("code", "1001");
			map.put("rfpno", rfpheader.getRfpno());
			map.put("error", "RFP creation failed");
		}
		System.out.println("response : " + map);
		return map;
	}

	public HashMap<String, Object> updateRFPHeader(RFPHeader rfpheader) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String vendType = "LIFNR";
		UserMaster createdBy = null;
		String ardid = null;
		String mob = null;
		String contperson = null;
		String emailid = null;
		String crdate = null;
		String lifnr = null;
		String name = null;
		String status = null;
		String tempflag = null;
		String mobile = null;
		long templifnr = 0;
		NumberRange numberRangeObject1 = null;
		NumberRange saveNumberRangeObject1 = null;
		UserMaster userMaster = new UserMaster();
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		RFPHeader existingRFP = rfpheaderRepository.getOne(rfpheader.getRfpno());
		String sesId = rfpheader.getSessionid();
		System.out.println("SESSIONID:" + sesId);
		try {
			long sesid = Long.parseLong(sesId);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();
				}
			}
			if (ardid == null) {
				map.put("error", "Session Expired");
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CHANGEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CHANGEDDATE:" + today.toString());
			rfpheader.setChangeddBy(cradrid);
			rfpheader.setChangeddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		rfpheader.setStatus("1");
		if (!rfpheader.getDf().equals(existingRFP.getDf())) {
			String newdf = rfpheader.getDf();
			String olddt = existingRFP.getDt();
			rfpheader.setVersion(existingRFP.getVersion() + 1);
			if (rfpheader.getRfpmode().equals("S")) {
				long rfpnum = rfpheader.getRfpno();
				List<RFPInvitedVendors> invitees = rfpheader.getInvitedvendors();
				for (RFPInvitedVendors inv : invitees) {
					rfpValidityExtension(inv.getEmailid(), rfpnum, olddt, newdf, inv.getName());

					// rfpValidityExtension(String email,long rfpno,String olddt, String newdt,
					// String name)
				}
			} else if (rfpheader.getRfpmode().equals("D")) {
				System.out.println("Draft updated");
			}
		}

		List<RFPSection> rfpSection = rfpheader.getSection();
		for (RFPSection rf : rfpSection) {
			if (rf.getAttachment() != null) {
				if (rf.getAttachment().length() > 0) {

					String data = rf.getAttachment().split(",")[1];
					Decoder decoder = Base64.getDecoder();
					byte[] decodedByte = decoder.decode(data);
					Blob b = new SerialBlob(decodedByte);
					// Utility.writeUsingFiles(data);
					rf.setData(b);
					rf.setAttachment(rf.getAttachment().split(",")[0]);
				}
			}
		}

		List<RFPParticipant> rfpParticipants = rfpheader.getParticipant();
		for (RFPParticipant rf : rfpParticipants) {
			rf.setCp_fk(rfpheader.getRfpno());
		}

		List<RFPInvitedVendors> rfpVendors = rfpheader.getInvitedvendors();
		for (RFPInvitedVendors rv : rfpVendors) {
			// rv.setCp_fk(rv.getCp_fk());
			rv.setCp_fk(rfpheader.getRfpno());
			System.out.println("CP_FK:" + rv.getCp_fk());
			String mailflag = rv.getTempflag();
			if (rfpheader.getRfpmode().equals("S") && mailflag.equalsIgnoreCase("Y")) {
				mob = rv.getContactno();
				contperson = rv.getContactperson();
				emailid = rv.getEmailid();
				crdate = rv.getCreateddate();
				mobile = rv.getContactno();
				long rfpn = rfpheader.getRfpno();
				lifnr = rv.getLifnr();
				name = rv.getName();
				status = rv.getStatus();
				tempflag = rv.getTempflag();
				String lifnrid = rv.getLifnr();
				String email = rv.getEmailid();

				if (lifnr.equals("null") || lifnr.equals("") || lifnr.equals(null) || lifnr.isEmpty())
				// if(lifnrid.equals("") || lifnrid.equals("null") || lifnrid.equals(null) ||
				// lifnrid.isEmpty())
				{

					try {
						numberRangeObject1 = webService.getNumberRageByDocumentType(vendType);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (numberRangeObject1 == null) {
						System.out.println(vendType + " is not match please enter proper dctype");
					}
					templifnr = numberRangeObject1.getFromNumber() + 1;
					System.out.println("TEMP LIFNR:" + templifnr);
					if (templifnr <= numberRangeObject1.getToNumber()) {
						System.out.println("lifnrrrrrrrrrrrrr2:" + templifnr);
						NumberRange numberRange = new NumberRange();
						numberRange.setId(numberRangeObject1.getId());
						numberRange.setFromNumber(templifnr);
						numberRange.setToNumber(numberRangeObject1.getToNumber());
						numberRange.setTransationType(numberRangeObject1.getTransationType());
						numberRange.setYear(numberRangeObject1.getYear());
						saveNumberRangeObject1 = webService.saveNumberRange(numberRange);
					}
					System.out.println("LIFNR:" + templifnr);
					String venlifnr = Long.toString(templifnr);
					// String vendor = Long.toString(templifnr);
					// //RandomStringUtils.randomNumeric(8);
					System.out.println("TEMPORARY VENDOR CODE:" + venlifnr);
					rv.setLifnr("T" + venlifnr);
					String pwd = RandomStringUtils.randomAlphanumeric(8);
					System.out.println("TEMPORARY PASSWORD:" + pwd);
					UserMaster userMasterObject = null;
					try {
						userMaster.setAdrid("T" + venlifnr);
						userMaster.setBegda(today);
						userMaster.setAdflag("I");
						userMaster.setEmail(emailid);
						userMaster.setPswrd(pwd);
						userMaster.setEunam(name);
						userMaster.setSunam(name);
						userMaster.setInactive(0);
						userMaster.setBptype("V");
						userMaster.setPmble(mobile);

						String dept = "99";
						int id = Integer.parseInt(dept);
						RoleModel roleModle = webService.getRoleByRoleId(id);
						userMaster.setUserRoll(roleModle);

						int blockstatus = Integer.parseInt(status);

						Status status1 = new Status();
						status1.setStatusId(blockstatus);
						userMaster.setStatus(status1);

						userMaster.setUserDesignation("VENDOR");
						userMasterObject = webService.createUser(userMaster);
					} catch (Exception e) {
						System.out.println(e);
					}
					long rfpnum = rfpheader.getRfpno();
					String currdt = rfpheader.getDt();
					// tempVendorMail(String email, String password, String name, String
					// venlifnr,long rfpnum, String currdf) {
					tempVendorMail(emailid, pwd, name, venlifnr, rfpnum, currdt);
					rv.setTempflag("N");
				} else if (lifnr != "null" || lifnr != "" || lifnr != null)
				// else if(!lifnrid.equals("") || !lifnrid.equals(null) || !lifnrid.isEmpty())
				{
					System.out.println("Vendor already exists!");
					System.out.println("EXISTING LIFNR:" + lifnrid);
					long rfpnum = rfpheader.getRfpno();
					String currdt = rfpheader.getDt();
					invitedVendorMail(email, name, rfpnum, currdt);
					rv.setTempflag("N");
				}

			} else if (rfpheader.getRfpmode().equals("D")) {
				map.put("code", "1001");
				map.put("rfpno", 0);
				map.put("success", "RFP draft updated successfully");
			}

			// rv.setCp_fk(rv.getCp_fk());

		}
		rfpheader = rfpheaderRepository.save(rfpheader);

		if (rfpheader.getRfpno() > 0) {
			map.put("code", "1000");
			map.put("rfpno", rfpheader.getRfpno());
			map.put("status", "RFP updated successfully");

		} else {
			map.put("code", "1001");
			map.put("rfpno", 0);
			map.put("error", "RFP updation failed");
		}
		System.out.println("response : " + map);
		return map;
	}

	public List<VPHeader> getAllVPHeaders() throws SQLException {
		List<VPSection> vpSections;
		List<VPGenProposal> vpgenProposal;
		List<VPTCProposal> vptcProposal;
		List<VPHeader> vpheaders = vpheaderRepository.findAll();
		for (VPHeader vpheader : vpheaders) {
			vpSections = vpheader.getVpsection();
			vpgenProposal = vpheader.getVpgenproposal();
			vptcProposal = vpheader.getVptcproposal();
			for (VPSection vp : vpSections) {
				Blob docBlob = vp.getData();
				byte[] bts1 = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts1);
				vp.setAttachment(vp.getAttachment() + "," + encoded);
				vp.setData(null);
			}
			for (VPGenProposal vp1 : vpgenProposal) {
				Blob docBlob = vp1.getData();
				byte[] bts2 = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts2);
				vp1.setAttachment(vp1.getAttachment() + "," + encoded);
				vp1.setData(null);
			}
			for (VPTCProposal vp2 : vptcProposal) {
				Blob docBlob = vp2.getData();
				byte[] bts3 = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts3);
				vp2.setAttachment(vp2.getAttachment() + "," + encoded);
				vp2.setData(null);
			}
		}
		return vpheaders;
	}

	public List<VPHeader> getAllVPWithRFPHeader() throws SQLException {
		List<VPSection> vpSections;
		List<VPGenProposal> vpgenProposal;
		List<VPTCProposal> vptcProposal;
		List<VPHeader> vpheaders = vpheaderRepository.findAll();
		// List<RFPHeader> rfpheader;
		for (VPHeader vpheader : vpheaders) {
			vpSections = vpheader.getVpsection();
			vpgenProposal = vpheader.getVpgenproposal();
			vptcProposal = vpheader.getVptcproposal();
			// rfpheader = vpheader.getRfpheaderlist();
			for (VPSection vp : vpSections) {
				Blob docBlob = vp.getData();
				byte[] bts1 = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts1);
				vp.setAttachment(vp.getAttachment() + "," + encoded);
				vp.setData(null);
			}
			for (VPGenProposal vp1 : vpgenProposal) {
				Blob docBlob = vp1.getData();
				byte[] bts2 = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts2);
				vp1.setAttachment(vp1.getAttachment() + "," + encoded);
				vp1.setData(null);
			}
			for (VPTCProposal vp2 : vptcProposal) {
				Blob docBlob = vp2.getData();
				byte[] bts3 = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts3);
				vp2.setAttachment(vp2.getAttachment() + "," + encoded);
				vp2.setData(null);
			}
		}
		return vpheaders;
	}

	/*
	 * public String updateVendor(Vendor vendor) { vendorRepository.save(vendor);
	 * return "updated"; }
	 */

	public HashMap<String, Object> updateVendor(Vendor vendor) throws SerialException, SQLException {

		HashMap<String, Object> map = new HashMap<String, Object>();
		Vendor existingVendor = vendorRepository.getOne(vendor.getVid());

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = vendor.getSessionid();
		System.out.println("SESSIONID:" + sesId);
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
				map.put("error", "Session Expired");
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CHANGEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CHANGEDDATE:" + today.toString());
			vendor.setChangeddBy(cradrid);
			vendor.setChangeddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] array = vendor.getDfdt().split("-");
		vendor.setDf(array[0]);
		vendor.setDt(array[1]);
		vendor.setStatus("3");

		/*
		 * if(!vendor.getDt().equals(existingVendor.getDfdt())) {
		 * vendor.setVersion(existingVendor.getVersion()+1); }
		 */
		List<VendorDocument> vendorDocuments = vendor.getVenDocuments();
		for (VendorDocument vd : vendorDocuments) {
			if (vd.getAttachment() != null) {
				if (vd.getAttachment().length() > 0) {
					String data = vd.getAttachment().split(",")[1];
					Decoder decoder = Base64.getDecoder();
					byte[] decodedByte = decoder.decode(data);
					Blob b = new SerialBlob(decodedByte);
					vd.setData(b);
					vd.setAttachment(vd.getAttachment().split(",")[0]);
				}
			}
		}
		vendor = vendorRepository.save(vendor);
		if (vendor.getVid() > 0) {
			map.put("code", "1000");
			map.put("Status", "Vendor updated successfully");
			map.put("Vendor Id", vendor.getVid());
		} else {
			map.put("code", "1001");
			map.put("error", "Vendor updation failed");
			map.put("Vendor Id", 0);

		}
		return map;
	}

	public RFPHeader getRFPHeader(long rfpno) throws SQLException {
		// Optional<Vendor> vendor = vendorRepository.findById(vid);
		// Vendor vendor = vendorRepository.findById(vid).get();
		RFPHeader rfpheader = rfpheaderRepository.getOne(rfpno);
		if (rfpheader.getRfpno() > 0) {
			List<RFPSection> rfpSections = rfpheader.getSection();
			for (RFPSection rf : rfpSections) {
				Blob docBlob = rf.getData();
				byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts);
				rf.setAttachment(rf.getAttachment() + "," + encoded);
				rf.setData(null);
			}
		}
		return rfpheader;
	}

	public VPHeader getVPHeader(long proposalno) throws SQLException {
		VPHeader vpheader = vpheaderRepository.getOne(proposalno);
		if (vpheader.getProposalno() > 0) {
			List<VPSection> vpSections = vpheader.getVpsection();
			for (VPSection vp : vpSections) {
				Blob docBlob = vp.getData();
				byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts);
				vp.setAttachment(vp.getAttachment() + "," + encoded);
				vp.setData(null);
			}

			List<VPGenProposal> vpgenProposal = vpheader.getVpgenproposal();
			for (VPGenProposal vp1 : vpgenProposal) {
				Blob docBlob = vp1.getData();
				byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts);
				vp1.setAttachment(vp1.getAttachment() + "," + encoded);
				vp1.setData(null);
			}
			List<VPTCProposal> vptcpProposal = vpheader.getVptcproposal();
			for (VPTCProposal vp2 : vptcpProposal) {
				Blob docBlob = vp2.getData();
				byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts);
				vp2.setAttachment(vp2.getAttachment() + "," + encoded);
				vp2.setData(null);
			}
		}
		return vpheader;
	}

	/*
	 * public List<HashMap<String, Object>> getRFPWithVP(long rfpno) throws
	 * SQLException { //VPHeader vpheader = vpheaderRepository.getOne(proposalno);
	 * RFPHeader rfpheader = rfpheaderRepository.getOne(rfpno); //RFPHeader
	 * rfpheader = rfpheaderRepository.getOne(vpheader.getRfpno()); VPHeader
	 * vpheader = vpheaderRepository.getOne(rfpheader.getProposalno());
	 * 
	 * 
	 * List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String,
	 * Object>>(); //List<Vendor> vendors = vendorRepository.findAll();
	 * 
	 * HashMap<String, Object> map = new HashMap<String, Object>();
	 * map.put("proposalno",vpheader.getProposalno());
	 * map.put("version",vpheader.getVersion());
	 * map.put("versioninfo",vpheader.getVersioninfo());
	 * map.put("lifnr",vpheader.getLifnr()); map.put("rfpno",vpheader.getRfpno());
	 * map.put("rfpversion",vpheader.getRfpversion());
	 * map.put("requester",vpheader.getRequester());
	 * map.put("ekgrp",vpheader.getEkgrp()); map.put("ekorg",vpheader.getEkorg());
	 * map.put("status",vpheader.getStatus());
	 * map.put("addtext",vpheader.getAddtext());
	 * map.put("startdate",vpheader.getStartdate());
	 * map.put("enddate",vpheader.getEnddate());
	 * map.put("transactiontype",vpheader.getTransactiontype());
	 * map.put("bukrs",vpheader.getBukrs()); map.put("prereq",vpheader.getPrereq());
	 * map.put("commhistory",vpheader.getCommhistory());
	 * map.put("comments",vpheader.getComments());
	 * map.put("createdby",vpheader.getCreatedby());
	 * map.put("changedby",vpheader.getChangedby());
	 * map.put("createddate",vpheader.getCreateddate());
	 * map.put("changeddate",vpheader.getChangeddate());
	 * 
	 * map.put("vpitem", vpheader.getVpitem()); map.put("vpsection",
	 * vpheader.getVpsection()); map.put("vpgenproposal",
	 * vpheader.getVpgenproposal()); map.put("vptcproposal",
	 * vpheader.getVpgenproposal());
	 * 
	 * HashMap<String, Object> map1 = new HashMap<String, Object>();
	 * 
	 * if(rfpheader.getRfpno() > 0) { List<RFPSection> rfpSections =
	 * rfpheader.getSection(); for(RFPSection rfp : rfpSections) { Blob docBlob =
	 * rfp.getData(); byte[] bts = docBlob.getBytes(1,(int) docBlob.length());
	 * String encoded = Base64.getEncoder().encodeToString(bts);
	 * rfp.setAttachment(rfp.getAttachment()+","+encoded); rfp.setData(null); } }
	 * map1.put("rfplist",rfpheader); map1.put("vpheader",map)
	 * metaDataList.add(map1); return metaDataList; };
	 */

	public List<HashMap<String, Object>> getVPHeaderVPWithRFP(long proposalno) throws SQLException {
		VPHeader vpheader = vpheaderRepository.getOne(proposalno);

		RFPHeader rfpheader = rfpheaderRepository.getOne(vpheader.getRfpno());
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		// List<Vendor> vendors = vendorRepository.findAll();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("rfpno", rfpheader.getRfpno());
		map.put("version", rfpheader.getVersion());
		map.put("versioninfo", rfpheader.getVersioninfo());
		// map.put("ekgrp",rfpheader.getEkgrp());
		map.put("ekorg", rfpheader.getEkorg());
		map.put("status", rfpheader.getStatus());
		map.put("bukrs", rfpheader.getBukrs());
		map.put("dt", rfpheader.getDt());
		map.put("df", rfpheader.getDf());
		map.put("transactiontype", rfpheader.getTransactiontype());
		map.put("prereq", rfpheader.getPrereq());
		map.put("changeddate", rfpheader.getChangeddate());
		map.put("createddate", rfpheader.getCreateddate());
		map.put("createdBy", rfpheader.getCreatedby());
		map.put("changeddBy", rfpheader.getChangeddBy());
		map.put("mode", rfpheader.getRfpmode());

		HashMap<String, Object> map1 = new HashMap<String, Object>();

		if (vpheader.getProposalno() > 0) {
			List<VPSection> vpSections = vpheader.getVpsection();
			for (VPSection vp : vpSections) {
				Blob docBlob = vp.getData();
				byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts);
				vp.setAttachment(vp.getAttachment() + "," + encoded);
				vp.setData(null);
			}

			List<VPGenProposal> vpgenProposal = vpheader.getVpgenproposal();
			for (VPGenProposal vp1 : vpgenProposal) {
				Blob docBlob = vp1.getData();
				byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts);
				vp1.setAttachment(vp1.getAttachment() + "," + encoded);
				vp1.setData(null);
			}
			List<VPTCProposal> vptcpProposal = vpheader.getVptcproposal();
			for (VPTCProposal vp2 : vptcpProposal) {
				Blob docBlob = vp2.getData();
				byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts);
				vp2.setAttachment(vp2.getAttachment() + "," + encoded);
				vp2.setData(null);
			}

		}
		map1.put("vpitemlist", vpheader);
		map1.put("rfpheader", map);
		metaDataList.add(map1);
		return metaDataList;
	}

	public VPHeader getVPHeaderByRfpno(long rfpno) throws SQLException {
		// Optional<Vendor> vendor = vendorRepository.findById(vid);
		// Vendor vendor = vendorRepository.findById(vid).get();
		VPHeader vpheader = vpheaderRepository.getOne(rfpno);
		if (vpheader.getRfpno() > 0) {
			List<VPSection> vpSections = vpheader.getVpsection();
			for (VPSection vp : vpSections) {
				Blob docBlob = vp.getData();
				byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts);
				vp.setAttachment(vp.getAttachment() + "," + encoded);
				vp.setData(null);
			}

			List<VPGenProposal> vpgenProposal = vpheader.getVpgenproposal();
			for (VPGenProposal vp1 : vpgenProposal) {
				Blob docBlob = vp1.getData();
				byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts);
				vp1.setAttachment(vp1.getAttachment() + "," + encoded);
				vp1.setData(null);
			}
			List<VPTCProposal> vptcpProposal = vpheader.getVptcproposal();
			for (VPTCProposal vp2 : vptcpProposal) {
				Blob docBlob = vp2.getData();
				byte[] bts = docBlob.getBytes(1, (int) docBlob.length());
				String encoded = Base64.getEncoder().encodeToString(bts);
				vp2.setAttachment(vp2.getAttachment() + "," + encoded);
				vp2.setData(null);
			}
		}
		return vpheader;
	}

	public List<VPHeader> getVPByRfpno(Long rfpno) throws SerialException, SQLException {
		List<VPSection> vpSections;
		List<VPGenProposal> vpgenProposals;
		List<VPTCProposal> vptcpProposals;

		List<VPHeader> vpheaders = vpheaderRepository.getVPByRfpno(rfpno);

		for (VPHeader vpheader : vpheaders) {
			vpSections = vpheader.getVpsection();
			vpgenProposals = vpheader.getVpgenproposal();
			vptcpProposals = vpheader.getVptcproposal();

			for (VPSection vp : vpSections) {
				if (vp.getAttachment() != null) {
					if (vp.getAttachment().length() > 0) {
						getVPSectionBlobToBase64(vp);
					}
				}
			}
			for (VPGenProposal vp1 : vpgenProposals) {
				if (vp1.getAttachment() != null) {
					if (vp1.getAttachment().length() > 0) {
						getVPGenSectionBlobToBase64(vp1);
					}
				}
			}
			for (VPTCProposal vp2 : vptcpProposals) {
				if (vp2.getAttachment() != null) {
					if (vp2.getAttachment().length() > 0) {
						getVPTCPSectionBlobToBase64(vp2);
					}
				}
			}
		}

		return vpheaders;
	}

	public List<HashMap<String, Object>> getAllVPMetaData() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<VPHeader> vpheaders = vpheaderRepository.findAll();
		for (VPHeader vpheader : vpheaders) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("proposalno", vpheader.getProposalno());
			map.put("status", vpheader.getStatus());
			map.put("version", vpheader.getVersion());
			map.put("versioninfo", vpheader.getVersioninfo());
			map.put("lifnr", vpheader.getLifnr());
			map.put("rfpno", vpheader.getRfpno());
			map.put("rfpversion", vpheader.getRfpversion());
			map.put("requester", vpheader.getRequester());
			map.put("ekgrp", vpheader.getEkgrp());
			map.put("ekorg", vpheader.getEkorg());
			map.put("addtext", vpheader.getAddtext());
			map.put("startdate", vpheader.getStartdate());
			map.put("enddate", vpheader.getEnddate());
			map.put("transactiontype", vpheader.getTransactiontype());
			map.put("bukrs", vpheader.getBukrs());
			map.put("prereq", vpheader.getPrereq());
			map.put("commhistory", vpheader.getCommhistory());
			map.put("comments", vpheader.getComments());
			map.put("createdby", vpheader.getCreatedby());
			map.put("changedby", vpheader.getChangedby());
			map.put("createddate", vpheader.getCreateddate());
			map.put("changeddate", vpheader.getChangeddate());
			metaDataList.add(map);
		}
		return metaDataList;
	}

	public List<HashMap<String, Object>> getAllVPMetaDataBySessionid(String sessionid) {

		HashMap<String, Object> map1 = new HashMap<String, Object>();
		String cradrid = null;
		String ardid = null;
		String crby = null;
		UserMaster createdBy = null;
		try {
			long sesid = Long.parseLong(sessionid);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();

				}
			}
			if (ardid == null) {
				map1.put("error", "Session Expired");
				return (List<HashMap<String, Object>>) map1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			crby = Long.toString(createdBy.getId());
			cradrid = createdBy.getAdrid();
			// contracts.setCreatedby(crby);
			// contracts.setCreateddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<VPHeader> vpheaders = vpheaderRepository.getVPByCreatedby(cradrid);
		for (VPHeader vpheader : vpheaders) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("proposalno", vpheader.getProposalno());
			map.put("status", vpheader.getStatus());
			map.put("version", vpheader.getVersion());
			map.put("versioninfo", vpheader.getVersioninfo());
			map.put("lifnr", vpheader.getLifnr());
			map.put("rfpno", vpheader.getRfpno());
			map.put("rfpversion", vpheader.getRfpversion());
			map.put("requester", vpheader.getRequester());
			map.put("ekgrp", vpheader.getEkgrp());
			map.put("ekorg", vpheader.getEkorg());
			map.put("addtext", vpheader.getAddtext());
			map.put("startdate", vpheader.getStartdate());
			map.put("enddate", vpheader.getEnddate());
			map.put("transactiontype", vpheader.getTransactiontype());
			map.put("bukrs", vpheader.getBukrs());
			map.put("prereq", vpheader.getPrereq());
			map.put("commhistory", vpheader.getCommhistory());
			map.put("comments", vpheader.getComments());
			map.put("createdby", vpheader.getCreatedby());
			map.put("changedby", vpheader.getChangedby());
			map.put("createddate", vpheader.getCreateddate());
			map.put("changeddate", vpheader.getChangeddate());
			metaDataList.add(map);
		}
		return metaDataList;
	}

	public List<HashMap<String, Object>> getAllRFPMetaData() {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<RFPHeader> rfpheaders = rfpheaderRepository.findAll();
		for (RFPHeader rfpheader : rfpheaders) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("rfpno", rfpheader.getRfpno());
			map.put("version", rfpheader.getVersion());
			map.put("versioninfo", rfpheader.getVersioninfo());
			map.put("bukrs", rfpheader.getBukrs());
			// map.put("ekgrp",rfpheader.getEkgrp());
			map.put("ekorg", rfpheader.getEkorg());
			map.put("startdate", rfpheader.getDt());
			map.put("enddate", rfpheader.getDf());
			map.put("transactiontype", rfpheader.getTransactiontype());
			map.put("prereq", rfpheader.getPrereq());
			map.put("changeddate", rfpheader.getChangeddate());
			map.put("createddate", rfpheader.getCreateddate());
			map.put("createdBy", rfpheader.getCreatedby());
			map.put("changeddBy", rfpheader.getChangeddBy());
			map.put("status", rfpheader.getStatus());
			map.put("mode", rfpheader.getRfpmode());
			metaDataList.add(map);
		}
		return metaDataList;
	}

	public List<RFPHeader> getRFPByCreatedby(String createdby) {
		return sampleRepo.getRFPByCreatedby(createdby);
	}

	public List<HashMap<String, Object>> rfpQuery(long rfpno, long proposalno) {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<RFPQuery> rfpquerys = new ArrayList<RFPQuery>();
		if (proposalno != 0) {
			rfpquerys = rfpQueryRepo.getRFPQueryByRfpnoAndProposalno(rfpno, proposalno);
		} else {
			rfpquerys = rfpQueryRepo.getRFPQueryByRfpno(rfpno);

		}

		// List<RFPHeader> getRFPBySesid(String sesId);
		for (RFPQuery rfpquery : rfpquerys) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("rfpno", rfpquery.getRfpno());
			map.put("proposalno", rfpquery.getProposalno());
			map.put("mailbody", rfpquery.getMailbody());
			map.put("createdBy", rfpquery.getCreatedBy());
			map.put("createdDate", rfpquery.getCreatedDate());
			metaDataList.add(map);
		}
		return metaDataList;

	}

	public List<HashMap<String, Object>> rfpQueryByRfpno(long rfpno) {
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<RFPQuery> rfpquerys = new ArrayList<RFPQuery>();

		rfpquerys = rfpQueryRepo.getRFPQueryByRfpno(rfpno);

		// List<RFPHeader> getRFPBySesid(String sesId);
		for (RFPQuery rfpquery : rfpquerys) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("rfpno", rfpquery.getRfpno());
			map.put("proposalno", rfpquery.getProposalno());
			map.put("mailbody", rfpquery.getMailbody());
			map.put("createdBy", rfpquery.getCreatedBy());
			map.put("createdDate", rfpquery.getCreatedDate());
			metaDataList.add(map);
		}
		return metaDataList;

	}

	public List<HashMap<String, Object>> getRFPBasicDetailsBySessionid(String sessionid) {
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		String cradrid = null;
		String ardid = null;
		String crby = null;
		UserMaster createdBy = null;
		try {
			long sesid = Long.parseLong(sessionid);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();

				}
			}
			if (ardid == null) {
				map1.put("error", "Session Expired");
				return (List<HashMap<String, Object>>) map1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			crby = Long.toString(createdBy.getId());
			cradrid = createdBy.getAdrid();
			// contracts.setCreatedby(crby);
			// contracts.setCreateddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Get RFPs by CreatedBy
		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
		List<RFPHeader> rfpheaders = webService.getRFPByCreatedby(cradrid);

		// List<RFPHeader> getRFPBySesid(String sesId);
		for (RFPHeader rfpheader : rfpheaders) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("rfpno", rfpheader.getRfpno());
			map.put("version", rfpheader.getVersion());
			map.put("versioninfo", rfpheader.getVersioninfo());
			map.put("bukrs", rfpheader.getBukrs());
			// map.put("ekgrp",rfpheader.getEkgrp());
			map.put("ekorg", rfpheader.getEkorg());
			map.put("startdate", rfpheader.getDt());
			map.put("enddate", rfpheader.getDf());
			map.put("transactiontype", rfpheader.getTransactiontype());
			map.put("prereq", rfpheader.getPrereq());
			map.put("changeddate", rfpheader.getChangeddate());
			map.put("createddate", rfpheader.getCreateddate());
			map.put("createdBy", rfpheader.getCreatedby());
			map.put("changeddBy", rfpheader.getChangeddBy());
			map.put("status", rfpheader.getStatus());
			map.put("mode", rfpheader.getRfpmode());

			metaDataList.add(map);
		}

		// Get RFPs by Participants
		List<RFPParticipant> rfpParticipants = rfpParticipantsRepository.getRFPParticipantByUserid(cradrid);
		for (RFPParticipant invite : rfpParticipants) {

			HashMap<String, Object> map = new HashMap<String, Object>();

			long rfpno = invite.getCp_fk();
			RFPHeader rfpheader = rfpheaderRepository.getOne(rfpno);
			map.put("rfpno", rfpheader.getRfpno());
			map.put("version", rfpheader.getVersion());
			map.put("versioninfo", rfpheader.getVersioninfo());
			map.put("bukrs", rfpheader.getBukrs());
			// map.put("ekgrp",rfpheader.getEkgrp());
			map.put("ekorg", rfpheader.getEkorg());
			map.put("startdate", rfpheader.getDt());
			map.put("enddate", rfpheader.getDf());
			map.put("transactiontype", rfpheader.getTransactiontype());
			map.put("prereq", rfpheader.getPrereq());
			map.put("changeddate", rfpheader.getChangeddate());
			map.put("createddate", rfpheader.getCreateddate());
			map.put("createdBy", rfpheader.getCreatedby());
			map.put("changeddBy", rfpheader.getChangeddBy());
			map.put("status", rfpheader.getStatus());
			map.put("mode", rfpheader.getRfpmode());
			metaDataList.add(map);

		}

		// get for RFPVendor Selection based on approver from approver log table
		try {

			HashMap<String, Object> map = new HashMap<String, Object>();
			WorkflowConfig appid = workFlowRepo.findByAppid("RFPVS");
			if (appid == null) {

				// System.out.println("Workflow Config is Empty");
				// map.put("code", "1001");
				// map.put("error", "Workflow Config is Empty");

				// return (List<HashMap<String, Object>>) map;
			}

			List<ApprovalLog> approvalLogsList = approvalLogRepo.findByAprvAndAppid(cradrid, appid);

			for (ApprovalLog approvalLogOne : approvalLogsList) {

				// String lifnrId = String.valueOf(approvalLogOne.getReqNo());
				long rfpno = approvalLogOne.getReqNo();

				// HashMap<String, Object> map = new HashMap<String, Object>();

				RFPHeader rfpheader = rfpheaderRepository.getOne(rfpno);
				map.put("rfpno", rfpheader.getRfpno());
				map.put("version", rfpheader.getVersion());
				map.put("versioninfo", rfpheader.getVersioninfo());
				map.put("bukrs", rfpheader.getBukrs());
				// map.put("ekgrp",rfpheader.getEkgrp());
				map.put("ekorg", rfpheader.getEkorg());
				map.put("startdate", rfpheader.getDt());
				map.put("enddate", rfpheader.getDf());
				map.put("transactiontype", rfpheader.getTransactiontype());
				map.put("prereq", rfpheader.getPrereq());
				map.put("changeddate", rfpheader.getChangeddate());
				map.put("createddate", rfpheader.getCreateddate());
				map.put("createdBy", rfpheader.getCreatedby());
				map.put("changeddBy", rfpheader.getChangeddBy());
				map.put("status", approvalLogOne.getStatus());
				map.put("mode", rfpheader.getRfpmode());
				map.put("level", approvalLogOne.getAprvlevel());
				metaDataList.add(map);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		metaDataList.sort(Comparator.comparing(o -> (String) o.get("createddate")));
		// metaDataList.revers
		/*
		 * metaDataList.lastIndexOf(o) metaDataList.stream()
		 * .sorted(Comparator.comparing(String createddate).reversed())
		 * .collect(Collectors.toList());
		 * 
		 */
		return metaDataList;
	}

	// get rfp data by using Session Id
	public String getRFPBasicDetailsByAprvSessionid(String sessionid) {
//		HashMap<String, Object> map1 = new HashMap<String, Object>();
		String cradrid = null;
		String ardid = null;
		String crby = null;
		String json = "";
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		JSONObject js = new JSONObject();
		UserMaster createdBy = null;
		try {
			long sesid = Long.parseLong(sessionid);
			List<SessionsTable> sessionsTableList = webService.getAllSessions();
			for (SessionsTable sessionsTable2 : sessionsTableList) {
				if (sessionsTable2.getSesid() == sesid) {
					ardid = sessionsTable2.getAdrid();

				}
			}
			if (ardid == null) {
//				map1.put("error", "Session Expired");
				js.put("code", "1001");
				js.put("error", "Session Expired");
				json = js.toString();
				return json;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			crby = Long.toString(createdBy.getId());
			cradrid = createdBy.getAdrid();
			// contracts.setCreatedby(crby);
			// contracts.setCreateddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			WorkflowConfig appid = workFlowRepo.findByAppid("RFPVS");

			if (appid == null) {
				js.put("code", "1001");
				js.put("error", "appid is Empty");
				json = js.toString();
				return json;
			}

			List<RFPHeader> rfpHeadersList = new ArrayList<RFPHeader>();
			List<ApprovalLog> approvalLogList = approvalLogRepo.findByAprvAndAppid(cradrid, appid);
			if (approvalLogList.isEmpty()) {
				js.put("code", "1001");
				js.put("error", "appid is Empty");
				json = js.toString();
				return json;
			}
			for (ApprovalLog approvalLogOne : approvalLogList) {

				if (approvalLogOne == null) {
					continue;
				} else {
					RFPHeader rfpHeaderOne = rfpheaderRepository.findOne(approvalLogOne.getReqNo());
					if (rfpHeaderOne == null) {
						continue;
					} else {
						rfpHeadersList.add(rfpHeaderOne);
					}
				}
			}

			js.put("code", "1000");
			js.put("sucess", ow.writeValueAsString(rfpHeadersList));
			json = js.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;

		// Get RFPs by CreatedBy
//		List<HashMap<String, Object>> metaDataList = new ArrayList<HashMap<String, Object>>();
//		List<RFPHeader> rfpheaders = webService.getRFPByCreatedby(cradrid);
//		

//		// List<RFPHeader> getRFPBySesid(String sesId);
//		for (RFPHeader rfpheader : rfpheaders) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("rfpno", rfpheader.getRfpno());
//			map.put("version", rfpheader.getVersion());
//			map.put("versioninfo", rfpheader.getVersioninfo());
//			map.put("bukrs", rfpheader.getBukrs());
//			// map.put("ekgrp",rfpheader.getEkgrp());
//			map.put("ekorg", rfpheader.getEkorg());
//			map.put("startdate", rfpheader.getDt());
//			map.put("enddate", rfpheader.getDf());
//			map.put("transactiontype", rfpheader.getTransactiontype());
//			map.put("prereq", rfpheader.getPrereq());
//			map.put("changeddate", rfpheader.getChangeddate());
//			map.put("createddate", rfpheader.getCreateddate());
//			map.put("createdBy", rfpheader.getCreatedby());
//			map.put("changeddBy", rfpheader.getChangeddBy());
//			map.put("status", rfpheader.getStatus());
//			map.put("mode", rfpheader.getRfpmode());
//			metaDataList.add(map);
//		}
//
//		// Get RFPs by Participants
//		List<RFPParticipant> rfpParticipants = rfpParticipantsRepository.getRFPParticipantByUserid(cradrid);
//		for (RFPParticipant invite : rfpParticipants) {
//
//			HashMap<String, Object> map = new HashMap<String, Object>();
//
//			long rfpno = invite.getCp_fk();
//			RFPHeader rfpheader = rfpheaderRepository.getOne(rfpno);
//			map.put("rfpno", rfpheader.getRfpno());
//			map.put("version", rfpheader.getVersion());
//			map.put("versioninfo", rfpheader.getVersioninfo());
//			map.put("bukrs", rfpheader.getBukrs());
//			// map.put("ekgrp",rfpheader.getEkgrp());
//			map.put("ekorg", rfpheader.getEkorg());
//			map.put("startdate", rfpheader.getDt());
//			map.put("enddate", rfpheader.getDf());
//			map.put("transactiontype", rfpheader.getTransactiontype());
//			map.put("prereq", rfpheader.getPrereq());
//			map.put("changeddate", rfpheader.getChangeddate());
//			map.put("createddate", rfpheader.getCreateddate());
//			map.put("createdBy", rfpheader.getCreatedby());
//			map.put("changeddBy", rfpheader.getChangeddBy());
//			map.put("status", rfpheader.getStatus());
//			map.put("mode", rfpheader.getRfpmode());
//			metaDataList.add(map);
//
//		}
//
//		return metaDataList;
	}

	public HashMap<String, Object> addASN(ASNHeader asnheader) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		long asno = 0;
		// PurchaseOrder purchaseOrder =
		// poheaderrepository.getOne(asnheader.getEbeln());
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = asnheader.getSessionid();
		System.out.println("SESSIONID:" + sesId);
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
				map.put("error", "Session Expired");
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CREATEDDATE:" + today.toString());
			asnheader.setCreatedBy(cradrid);
			asnheader.setCreateddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		int status = asnheader.getStatus();
		// if((purchaseOrder!=null) && (status == 3)) {
		NumberRange numberRangeObject = null;
		try {
			numberRangeObject = webService.getNumberRageByDocumentType("ASN");
		} catch (Exception e) {
			e.printStackTrace();
		}
		NumberRange saveNumberRangeObject = null;
		asno = numberRangeObject.getFromNumber() + 1;
		System.out.println("ASN:" + asno);

		System.out.println("STATUS:" + status);
		if (asno <= numberRangeObject.getToNumber()) {
			NumberRange numberRange = new NumberRange();
			numberRange.setId(numberRangeObject.getId());
			numberRange.setFromNumber(asno);
			numberRange.setToNumber(numberRangeObject.getToNumber());
			numberRange.setTransationType(numberRangeObject.getTransationType());
			numberRange.setYear(numberRangeObject.getYear());
			saveNumberRangeObject = webService.saveNumberRange(numberRange);
		}
		// asnheader.setEbeln(ebeln);
		asnheader.setDrfgrnno(asno);
		// }
		List<ASNSection> asnSection = asnheader.getAsnsection();
		for (ASNSection as : asnSection) {
			if (as.getAttachment() != null) {
				if (as.getAttachment().length() > 0) {
					String data = as.getAttachment().split(",")[1];
					Decoder decoder = Base64.getDecoder();
					byte[] decodedByte = decoder.decode(data);
					Blob b = new SerialBlob(decodedByte);
					// Utility.writeUsingFiles(data);
					as.setData(b);
					as.setAttachment(as.getAttachment().split(",")[0]);
				}
			}
		}
		asnheader = asnRepository.save(asnheader);

		if (asnheader.getDrfgrnno() > 0) {
			map.put("code", "1000");
			map.put("asno", asnheader.getDrfgrnno());
			map.put("status", "ASN created successfully");

		} else {
			map.put("code", "1001");
			map.put("asno", 0);
			map.put("error", "ASN creation failed");
		}

		System.out.println("response : " + map);
		return map;
	}

	public HashMap<String, Object> addVendorProposalWithoutMail(VPHeader vpheader)
			throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = vpheader.getSessionid();
		System.out.println("SESSIONID:" + sesId);
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
				map.put("error", "Session Expired");
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CREATEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CREATEDDATE:" + today.toString());
			vpheader.setCreatedby(cradrid);
			vpheader.setCreateddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		RFPHeader rfpheader = rfpheaderRepository.getOne(vpheader.getRfpno());
		long vpno = 0;
		if (rfpheader != null) {
			NumberRange numberRangeObject = null;
			System.out.println("VPNO9:" + vpno);
			try {
				numberRangeObject = webService.getNumberRageByDocumentType("VPNO");
			} catch (Exception e) {
				e.printStackTrace();
			}
			NumberRange saveNumberRangeObject = null;
			vpno = numberRangeObject.getFromNumber() + 1;
			System.out.println("VPNO:" + vpno);
			if (vpno <= numberRangeObject.getToNumber()) {
				NumberRange numberRange = new NumberRange();
				numberRange.setId(numberRangeObject.getId());
				numberRange.setFromNumber(vpno);
				numberRange.setToNumber(numberRangeObject.getToNumber());
				numberRange.setTransationType(numberRangeObject.getTransationType());
				numberRange.setYear(numberRangeObject.getYear());
				saveNumberRangeObject = webService.saveNumberRange(numberRange);
			}
			vpheader.setStatus("1");
			vpheader.setProposalno(vpno);
		}

		List<VPSection> vpSection = vpheader.getVpsection();
		for (VPSection vf : vpSection) {
			if (vf.getAttachment() != null) {
				if (vf.getAttachment().length() > 0) {
					String data = vf.getAttachment().split(",")[1];
					Decoder decoder = Base64.getDecoder();
					byte[] decodedByte = decoder.decode(data);
					Blob b = new SerialBlob(decodedByte);
					// Utility.writeUsingFiles(data);
					vf.setData(b);
					vf.setAttachment(vf.getAttachment().split(",")[0]);
				}
			}
		}
		List<VPGenProposal> vpgenProposal = vpheader.getVpgenproposal();
		for (VPGenProposal vgen : vpgenProposal) {
			String data = vgen.getAttachment().split(",")[1];
			Decoder decoder = Base64.getDecoder();
			byte[] decodedByte = decoder.decode(data);
			Blob b = new SerialBlob(decodedByte);
			// Utility.writeUsingFiles(data);
			vgen.setData(b);
			vgen.setAttachment(vgen.getAttachment().split(",")[0]);
		}
		List<VPTCProposal> vptcProposal = vpheader.getVptcproposal();
		for (VPTCProposal vtcp : vptcProposal) {
			String data = vtcp.getAttachment().split(",")[1];
			Decoder decoder = Base64.getDecoder();
			byte[] decodedByte = decoder.decode(data);
			Blob b = new SerialBlob(decodedByte);
			// Utility.writeUsingFiles(data);
			vtcp.setData(b);
			vtcp.setAttachment(vtcp.getAttachment().split(",")[0]);
		}
		vpheader = vpheaderRepository.save(vpheader);
		if (vpheader.getProposalno() > 0) {
			map.put("code", "1000");
			map.put("vpno", vpheader.getProposalno());
			map.put("status", "VP created successfully");

		} else {
			map.put("code", "1001");
			map.put("vpno", 0);
			map.put("error", "VP creation failed");
		}
		System.out.println("response : " + map);
		return map;
	}

	/*
	 * public HashMap<String, Object> addASN(ASNHeader asnheader) throws
	 * SerialException, SQLException { HashMap<String, Object> map = new
	 * HashMap<String, Object>(); //PurchaseOrder poheader =
	 * poheaderrepository.getOne(asnheader.getEbeln()); long asno = 0; int status =
	 * asnheader.getStatus(); if(status == 1){ NumberRange numberRangeObject = null;
	 * System.out.println("ASNO:"+asno); try { numberRangeObject =
	 * webService.getNumberRageByDocumentType("ASN"); } catch (Exception e) {
	 * e.printStackTrace(); } NumberRange saveNumberRangeObject = null; asno =
	 * numberRangeObject.getFromNumber() + 1; System.out.println("ASNO:"+asno); if
	 * (asno <= numberRangeObject.getToNumber()) { NumberRange numberRange = new
	 * NumberRange(); numberRange.setId(numberRangeObject.getId());
	 * numberRange.setFromNumber(asno);
	 * numberRange.setToNumber(numberRangeObject.getToNumber());
	 * numberRange.setTransationType(numberRangeObject.getTransationType());
	 * numberRange.setYear(numberRangeObject.getYear()); saveNumberRangeObject =
	 * webService.saveNumberRange(numberRange); } //asnheader.setStatus("1");
	 * asnheader.setDrfgrnno(asno); } List<ASNSection> asnSection =
	 * asnheader.getAsnsection(); for(ASNSection as : asnSection) {
	 * if(as.getAttachment()!=null) { if(as.getAttachment().length() > 0) { String
	 * data = as.getAttachment().split(",")[1]; Decoder decoder =
	 * Base64.getDecoder(); byte[] decodedByte = decoder.decode(data); Blob b = new
	 * SerialBlob(decodedByte); //Utility.writeUsingFiles(data); as.setData(b);
	 * as.setAttachment(as.getAttachment().split(",")[0]); } } }
	 * 
	 * asnheader = asnRepository.save(asnheader); if(asnheader.getDrfgrnno() > 0) {
	 * map.put("code", "1000"); map.put("asno", asnheader.getDrfgrnno());
	 * map.put("status", "ASN created successfully");
	 * 
	 * }else { map.put("code", "1001"); map.put("asno", 0); map.put("error",
	 * "ASN creation failed"); } System.out.println("response : " + map); return
	 * map; }
	 * 
	 */
	public HashMap<String, Object> updateVendorProposal(VPHeader vpheader) throws SerialException, SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		String sesId = vpheader.getSessionid();
		System.out.println("SESSIONID:" + sesId);
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
				map.put("error", "Session Expired");
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createdBy = webService.getUserDetails(ardid);
			System.out.println("CHANGEDBY:" + createdBy.getId());
			String crby = Long.toString(createdBy.getId());
			String cradrid = createdBy.getAdrid();
			System.out.println("CHANGEDDATE:" + today.toString());
			vpheader.setChangedby(cradrid);
			vpheader.setChangeddate(today.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		vpheader.setStatus("1");
		long vpno = vpheader.getProposalno();

		List<VPSection> vpSection = vpheader.getVpsection();
		for (VPSection vf : vpSection) {
			if (vf.getAttachment() != null) {
				if (vf.getAttachment().length() > 0) {

					String data = vf.getAttachment().split(",")[1];
					Decoder decoder = Base64.getDecoder();
					byte[] decodedByte = decoder.decode(data);
					Blob b = new SerialBlob(decodedByte);
					// Utility.writeUsingFiles(data);
					vf.setData(b);
					vf.setAttachment(vf.getAttachment().split(",")[0]);
				}
			}
		}
		List<VPGenProposal> vpgenProposal = vpheader.getVpgenproposal();
		for (VPGenProposal vgen : vpgenProposal) {
			String data = vgen.getAttachment().split(",")[1];
			Decoder decoder = Base64.getDecoder();
			byte[] decodedByte = decoder.decode(data);
			Blob b = new SerialBlob(decodedByte);
			// Utility.writeUsingFiles(data);
			vgen.setData(b);
			vgen.setAttachment(vgen.getAttachment().split(",")[0]);
		}
		List<VPTCProposal> vptcProposal = vpheader.getVptcproposal();
		for (VPTCProposal vtcp : vptcProposal) {
			String data = vtcp.getAttachment().split(",")[1];
			Decoder decoder = Base64.getDecoder();
			byte[] decodedByte = decoder.decode(data);
			Blob b = new SerialBlob(decodedByte);
			// Utility.writeUsingFiles(data);
			vtcp.setData(b);
			vtcp.setAttachment(vtcp.getAttachment().split(",")[0]);
		}

		vpheader = vpheaderRepository.save(vpheader);
		if (vpno > 0) {
			map.put("code", "1000");
			map.put("vpno", vpno);
			map.put("status", "VP updated successfully");

		} else {
			map.put("code", "1001");
			map.put("vpno", 0);
			map.put("error", "VP updation failed");
		}
		System.out.println("response : " + map);
		return map;
	}

	public List<SampleEntity> getAll() {
		return sampleRepo.getAll();
	}

	public SampleEntity createSample(SampleEntity sampleEntity) {
		return sampleRepo.save(sampleEntity);
	}

	public NumberRange getNumberRageByDocumentType(String transationType) {
		return sampleRepo.getNumberRageByDocumentType(transationType);
	}

	public NumberRange saveNumberRange(NumberRange numberRange) {
		return sampleRepo.save(numberRange);
	}

	public SampleEntity updateSample(int Id, SampleEntity sampleEntity) {
		SampleEntity updatedSample;
		Optional<SampleEntity> searchEntity = sampleRepo.findById(Id);

		if (searchEntity.isPresent()) {
			SampleEntity sample = searchEntity.get();
			sample.setAge(sampleEntity.getAge());
			sample.setName(sampleEntity.getName());
			updatedSample = sampleRepo.save(sample);
		} else {
			throw new EntityNotFoundException();
		}
		return updatedSample;
	}

//  po AcknowledgementMail
	public String poAcknowledgementMail(Long userIdNo, Long reqid, String appid, int status, String remark)
			throws JSONException {

		JSONObject js = new JSONObject();

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String res = null;
		String plant = null;
		Boolean mailSent;
		String to = null, body = null, sub = null, ccEmail = null;
		EmailSending emailSending = new EmailSending();
		PurchaseOrder purchaseOrderForPlant = poRepository.findByEbeln(reqid);

		if (purchaseOrderForPlant != null) {

			List<POItemDetails> poItemDetailsForPlant = purchaseOrderForPlant.getPoitem();

			for (POItemDetails poItemDetailsForPlant1 : poItemDetailsForPlant) {// 123
				if (count && poItemDetailsForPlant1 != null) {
					plant = poItemDetailsForPlant1.getWerks();
					count = false;
				} else if (count) {
					js.put("code", "1001");
					js.put("error", "PurchaseRequisition list is empty");
					res = js.toString();
					return res;
				}
			}
			count = true;
		} else {
			js.put("code", "1001");
			js.put("error", "Plant is not found ");
			res = js.toString();
			return res;
		}
		ApprovalMatrix approvalMatrixForAprv = approvalMatrixRepository.findByPlant(plant);
		if (approvalMatrixForAprv == null) {
			js.put("code", "1001");
			js.put("error", "According To This Plant " + plant + "  ApprovalMatrix Is Not Maintain");
			res = js.toString();
			return res;
		}
		to = userMasterRepo.findOne(Long.parseLong(approvalMatrixForAprv.getProchead())).getEmail();
//	      ccEmail = userMasterRepo.findOne(Long.parseLong(approvalMatrixForAprv.getLabhead())).getEmail();
		ccEmail = "";

		sub = "Purchase Orderd no. " + reqid + " dated " + Calendar.getInstance().getTime()
				+ " has been acknowledged by the vendor.";
		body = "Purchase Orderd no. " + reqid + " dated " + Calendar.getInstance().getTime()
				+ " has been acknowledged by the vendor.";

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

	public UserADIntegration saveADIntegration(UserADIntegration userADIntegration) {
		return sampleRepo.save(userADIntegration);
	}

	public UserMaster createUser(UserMaster userMaster) {
		return sampleRepo.save(userMaster);
	}

	public UserMaster getUserDetails(String adrid) {
		return sampleRepo.findByadrid(adrid);
	}

	public PasswordPolicy createPasswordPolicy(PasswordPolicy passwordPolicy) {
		return sampleRepo.save(passwordPolicy);
	}

	public List<Currency> getAllCurrency() {
		return sampleRepo.getAllCurrency();
	}

	public List<UserMaster> getAllActiveUsers(int inactive) {
		return sampleRepo.getAllActiveUsers(inactive);
	}

	public List<UserMaster> getAllUsers() {
		return sampleRepo.getAllUsers();
	}
	public List<UserMaster> getUsersByAdrid(String adrid) {
		return sampleRepo.getUsersByAdrid(adrid);
	}
	public UserMaster saveMasterAndUserMapping(OrgMasterAndUserMapping orgMasterAndUserMapping) {
		return sampleRepo.save(orgMasterAndUserMapping);
	}

	public SessionHistory saveSessionHistory(SessionHistory sessionHistory) {
		return sampleRepo.save(sessionHistory);
	}

	public SessionsTable saveSessionTable(SessionsTable sessionTable) {
		return sampleRepo.save(sessionTable);
	}

	public List<SessionsTable> findBySesid(long sesid) {
		return sampleRepo.findBySesid(sesid);
	}

	public List<SessionsTable> getAllSessions() {
		return sampleRepo.getAllSessions();
	}

	public ApproverMaster saveApprover(ApproverMaster approverMaster) {
		return sampleRepo.save(approverMaster);
	}

	public List<ApproverMaster> getAllApprovers(Status status) {
		return sampleRepo.getAllApprovels(status);
	}

	public List<UserMaster> getAllUserMasterApprovers(RoleModel userRoll) {
		return sampleRepo.getAllUserMasterApprovers(userRoll);
	}

	public List<UserADIntegration> getAllUserIntegration() {
		return sampleRepo.getAllUserIntegration();
	}

	public ApprovalLog getDetailsByRequestIdAndApproverId(long reqid, UserMaster aprv) {
		return sampleRepo.getDetailsByRequestIdAndApproverId(reqid, aprv);
	}

	public List<Designation> getAllDesignation() {
		return sampleRepo.getAllDesignation();
	}

	public UserMaster checkUserAdmin(RoleModel userRoll, String adrid) {
		return sampleRepo.checkUserAdmin(userRoll, adrid);
	}

	public UserMaster getADCheck(String adrid, String adflag) {
		return sampleRepo.getADCheck(adrid, adflag);
	}

	public UserMaster getUserAuthenitcation(String adrid, String pswrd) {
		return sampleRepo.getUserAuthenitcation(adrid, pswrd);
	}

	public ServerInfo getServerInfo() {
		return sampleRepo.getServerInfo();
	}

	public UserMaster getUserByEmail(String email) {
		return sampleRepo.getUserByEmail(email);
	}

	public UserMaster getApproverAutenticationByEmail(String email) {
		return sampleRepo.getApproverAutenticationByEmail(email);
	}

	public List<Department> getAllDepartment() {
		return sampleRepo.getAllDepartment();
	}

	public List<RoleModel> getAllRole() {
		return sampleRepo.getAllRole();
	}

	public List<RoleModel> getByRoleFlag(String roleflag) {
		return sampleRepo.getByRoleFlag(roleflag);
	}

	public UserRolesAssignment saveUserAssignment(UserRolesAssignment userRolesAssignment) {
		return sampleRepo.save(userRolesAssignment);
	}

	public RoleModel getRoleByRoleId(int id) {
		return sampleRepo.getRoleByRoleId(id);
	}

	public List<UserRolesAssignment> getAssignedRolls(UserMaster userMasterObject, Status status) {
		return sampleRepo.getAssignedRolls(userMasterObject, status);
	}

	public UserMaster getUserByApproverMaster(ApproverMaster approverMaster) {
		return sampleRepo.getUserByApproverMaster(approverMaster);
	}

	public List<DocumentType> getAllDocumentType() {
		return sampleRepo.getAllDocumentType();
	}

	public DocumentType saveDocumentType(DocumentType documenntType) {
		return sampleRepo.save(documenntType);
	}

	public DocumentType getDocumentType(String dctyp) {
		return sampleRepo.getDocumentType(dctyp);
	}

	public List<UserRolesAssignment> getUserRolesAssignment(UserMaster userMasterObject, Status status) {
		return sampleRepo.getUserRolesAssignment(userMasterObject, status);
	}

	public Status getstatusById(int statusId) {
		return sampleRepo.getstatusById(statusId);
	}

	public ApproverMaster getApprover(String adrid, Status status) {
		return sampleRepo.getApprover(adrid, status);
	}

	public List<SessionsTable> getUsersSession(long sesid) {
		return sampleRepo.getUsersSession(sesid);
	}

	public List<SessionHistory> getSessionHistoryList() {
		return sampleRepo.getSessionHistoryList();
	}

	public SessionsTable getSessionStatus(long sesid) {
		return sampleRepo.getSessionStatus(sesid);
	}

	public List<SessionsTable> getFindSessionById(long sesid) {
		return sampleRepo.getFindSessionById(sesid);
	}

//plant.prno,status
	public List<PurchaseRequisition> getPurchaseRequisitionByPlant(String werks, long banfn, int status) {
		// TODO Auto-generated method stub
		return sampleRepo.getPurchaseRequisitionByPlant(werks, banfn, status);
	}

	public List<PurchaseRequisition> gePRStatusByUserid(String aprv) {
		// TODO Auto-generated method stub
		return sampleRepo.gePRStatusByUserid(aprv);
	}

	public PurchaseRequisition gePRStatusByPrnoItemno(long banfn, String bnfpo, String aprv) {
		return sampleRepo.gePRStatusByPrnoItemno(banfn, bnfpo, aprv);
	}

	public List<PurchaseRequisition> getPurchaseRequisitionByAprv(String aprv) {
		// TODO Auto-generated method stub
		return sampleRepo.getPurchaseRequisitionByAprv(aprv);
	}

	public ApprovalMatrix createApproval(ApprovalMatrix aprvMatrix) {
		return sampleRepo.save(aprvMatrix);
	}

	public Plant createPlant(Plant plant) {
		return sampleRepo.save(plant);
	}

	public WorkflowConfig createWorkflow(WorkflowConfig workflowConfig) {
		return sampleRepo.save(workflowConfig);
	}

	public ApprovalLog createApprovalLog(ApprovalLog aprvLog) {
		return sampleRepo.save(aprvLog);
	}

	/*
	 * public List<WorkflowConfig> getActiveWorkflowConfigByStatus(Status status) {
	 * return sampleRepo.getActiveWorkflowConfigByStatus(status); }
	 */

	public WorkflowConfig getActiveWorkflowConfigByStatus(String appid, String subprocess, Status status) {
		return sampleRepo.getActiveWorkflowConfigByStatus(appid, subprocess, status);
	}
	/*
	 * public List<ApproverMaster> getAllApprovers(Status status) { return
	 * sampleRepo.getAllApprovels(status); }
	 */

	public ApprovalMatrix getApprovalMatrixById(int id) {
		// TODO Auto-generated method stub
		return sampleRepo.getApprovalMatrixById(id);
	}

	public Status getStatusById(int statusId) {
		// TODO Auto-generated method stub
		return sampleRepo.getStatusById(statusId);
	}

	public WorkflowConfig getWorkflowConfigById(int id) {
		// TODO Auto-generated method stub
		return sampleRepo.getWorkflowConfigById(id);
	}

	/*
	 * public List<DSItem> getDocumetnItems(long reqid) { return
	 * sampleRepo.getDocumetnItems(reqid); }
	 */
	public List<ApprovalMatrix> getAllApprovalMatrix() {
		return sampleRepo.getAllApprovalMatrix();
	}

	public List<WorkflowConfig> getAllWorkflowConfig() {
		return sampleRepo.getAllWorkflowConfig();
	}

	public List<ApprovalLog> getPendingApprovalById(String approvedBy, int mailstatus) {
		// TODO Auto-generated method stub
		return sampleRepo.getPendingApprovalById(approvedBy, mailstatus);
	}

	public List<ApprovalLog> getApprovalLogListByReqid(long reqNo) {
		// TODO Auto-generated method stub
		return sampleRepo.getApprovalLogListByReqid(reqNo);
	}

	public List<ApprovalLog> getApprovalLogListByReqidAndAprv(long reqNo, int mailstatus) {
		// TODO Auto-generated method stub
		return sampleRepo.getApprovalLogListByReqidAndAprv(reqNo, mailstatus);
	}

	public ApprovalLog getApprovalLogByReqNoAndAprv(long reqNo, String aprv) {
		return sampleRepo.getApprovalLogByReqNoAndAprv(reqNo, aprv);
	}

	public List<VendorRegistration> getActiveUserListByStatus() {
		// TODO Auto-generated method stub
		return sampleRepo.getActiveUserListByStatus();
	}

	public RFPHeader saveRfpHeader(RFPHeader rfpHeader) {
		return sampleRepo.save(rfpHeader);
	}

	public RFPItem saveRfpItem(RFPItem rfpItem) {
		return sampleRepo.save(rfpItem);
	}

	public RFPSection saveRfpSection(RFPSection rfpSection) {
		return sampleRepo.save(rfpSection);
	}

	public PurOrg savePurOrg(PurOrg purOrg) {
		return sampleRepo.save(purOrg);
	}

	public VPHeader saveVpHeader(VPHeader vpHeader) {
		return sampleRepo.save(vpHeader);
	}

	public VPItem saveVpItem(VPItem vpItem) {
		return sampleRepo.save(vpItem);
	}

	public VPSection saveVpSection(VPSection vpSection) {
		return sampleRepo.save(vpSection);
	}

	public VPGenProposal saveVpGenProposal(VPGenProposal vpGenProposal) {
		return sampleRepo.save(vpGenProposal);
	}

	public VPTCProposal saveVptcProposal(VPTCProposal vptcProposal) {
		return sampleRepo.save(vptcProposal);
	}

	public RFPInvitedVendors saveRfpInvitedVendors(RFPInvitedVendors rfpInvitedVendors) {
		return sampleRepo.save(rfpInvitedVendors);
	}

	public RFPParticipant saveRfpParticipantsn(RFPParticipant rfpParticipants) {
		return sampleRepo.save(rfpParticipants);
	}

	public List<Storage> getStorageListByPlantcode(String werks, int mailstatus) {
		// TODO Auto-generated method stub
		return sampleRepo.getStorageListByPlantcode(werks, mailstatus);
	}

	public List<Plant> getPlantList() {
		// TODO Auto-generated method stub
		return sampleRepo.getPlantList();
	}

	public List<Plant> getPlantListByComp(String bukrs) {
		// TODO Auto-generated method stub
		return sampleRepo.getPlantListByComp(bukrs);
	}

	public List<Plant> getPlantListByPlant(String werks) {
		// TODO Auto-generated method stub
		return sampleRepo.getPlantListByPlant(werks);
	}

	public List<Plant> getPlantListByCompAndOrgAndPlant(String bukrs, String ekorg, String werks) {
		// TODO Auto-generated method stub
		return sampleRepo.getPlantListByCompAndOrgAndPlant(bukrs, ekorg, werks);
	}

	public List<Plant> getPlantListByCompAndOrg(String bukrs, String ekorg) {
		// TODO Auto-generated method stub
		return sampleRepo.getPlantListByCompAndOrg(bukrs, ekorg);
	}

	public List<PurOrg> getPurOrgListByCompAndOrg(String bukrs, String ekorg, int mailstatus) {
		// TODO Auto-generated method stub
		return sampleRepo.getPurOrgListByCompAndOrg(bukrs, ekorg, mailstatus);
	}

	public List<Plant> getPlantList(int mailstatus) {
		// TODO Auto-generated method stub
		return sampleRepo.getPlantList(mailstatus);
	}

	public List<PurOrg> getpurOrgListByComp(String bukrs, int inactive) {
		// TODO Auto-generated method stub
		return sampleRepo.getpurOrgListByComp(bukrs, inactive);
	}

	public List<PurOrg> getpurGrpByCompAndOrg(String bukrs, String ekorg, int inactive) {
		// TODO Auto-generated method stub
		return sampleRepo.getpurGrpByCompAndOrg(bukrs, ekorg, inactive);
	}

	public List<RFPHeader> getrfpHeaderListByUser(String createdBy) {
		// TODO Auto-generated method stub
		return sampleRepo.getrfpHeaderListByUser(createdBy);
	}

	public List<VPHeader> getvpHeaderListByUser(String createdby, String readstatus, int inactive) {
		// TODO Auto-generated method stub
		return sampleRepo.getvpHeaderListByUser(createdby, readstatus, inactive);
	}

	public List<PurchaseRequisition> getincompPrListByReqStatus(String reqstatus, int inactive) {
		// TODO Auto-generated method stub
		return sampleRepo.getincompPrListByReqStatus(reqstatus, inactive);
	}

	public List<Uom> getAllUom() {
		return sampleRepo.getAllUom();
	}

	public List<MaterialMaster> getAllMaterials() {
		return sampleRepo.getAllMaterials();
	}

	public List<MaterialMaster> getAllMaterialsByMatnrAndWerksAndMtart(String matnr, String werks, String mtart) {
		return sampleRepo.getAllMaterialsByMatnrAndWerksAndMtart(matnr, werks, mtart);
	}

	public List<MaterialMaster> getAllMaterialsByMatnrAndWerks(String matnr, String werks) {
		return sampleRepo.getAllMaterialsByMatnrAndWerks(matnr, werks);
	}

	public List<MaterialMaster> getAllMaterialsByMaktxAndMtartAndWerks(String maktx, String mtart, String werks) {
		return sampleRepo.getAllMaterialsByMaktxAndMtartAndWerks(maktx, mtart, werks);
	}

	public List<MaterialMaster> getAllMaterialsByMtartAndWerks(String mtart, String werks) {
		return sampleRepo.getAllMaterialsByMtartAndWerks(mtart, werks);
	}

	public List<VendorRegistration> getAllVendors() {
		return sampleRepo.getAllVendors();
	}

	public List<RFPHeader> getAllRfpHeader() {
		return sampleRepo.getAllRfpHeader();
	}

	public List<PurOrg> getAllPurOrg() {
		return sampleRepo.getAllPurOrg();
	}

	public List<RFPInvitedVendors> getRfpInvitedVendors(String name) {
		// TODO Auto-generated method stub
		return sampleRepo.getRfpInvitedVendors(name);
	}

	/*
	 * public List<RFPHeader> getRfpHeaderbyRfpno(List rfpInvitedItem) { // TODO
	 * Auto-generated method stub return sampleRepo.getRfpHeaderbyRfpno(); }
	 * 
	 */

	public RFPHeader getRfpHeader(RFPInvitedVendors rfpInvitedVendors) {
		return sampleRepo.getRfpHeader(rfpInvitedVendors);
	}

	public List<RFPItem> getRFPItemsByReqid(long rfpno) {
		return sampleRepo.getRFPItemsByReqid(rfpno);
	}

	public RFPHeader getRFPHeaderDatailsByReqid(long rfpno) {
		return sampleRepo.getRFPHeaderDatailsByReqid(rfpno);
	}

	public List<RFPInvitedVendors> getInvitedVendorsByReqid(long rfpno) {
		return sampleRepo.getInvitedVendorsByReqid(rfpno);
	}

	public List<RFPParticipant> getRfpParticipantListByReqid(long rfpno) {
		return sampleRepo.getRfpParticipantListByReqid(rfpno);
	}

	public List<RFPSection> getRfpSectionListByReqid(long rfpno) {
		return sampleRepo.getRfpSectionListByReqid(rfpno);
	}

	/*
	 * public List<RFPHeader> getRfpHeaderbyRfpno(long rfpno) { // TODO
	 * Auto-generated method stub return sampleRepo.getRfpHeaderbyRfpno(rfpno); }
	 */
	/*
	 * public List<RFPHeader> getRfpHeaderbyRfpno(List<RFPInvitedVendors>
	 * rfpInvitedVendorsList) { // TODO Auto-generated method stub return
	 * sampleRepo.getRfpHeaderbyRfpno(rfpInvitedVendorsList); }
	 * 
	 */

	public VPHeader getVPHeaderDetailsByProposalno(long proposalno) {
		return sampleRepo.getVPHeaderDetailsByProposalno(proposalno);
	}

	public List<VPItem> getVPItemsByProposalno(long proposalno) {
		return sampleRepo.getVPItemsByProposalno(proposalno);
	}

	public List<VPSection> getVPSectionListByProposalno(long proposalno) {
		return sampleRepo.getVPSectionListByProposalno(proposalno);
	}

	public List<VPGenProposal> getVPGenProposalListByProposalno(long proposalno) {
		return sampleRepo.getVPGenProposalListByProposalno(proposalno);
	}

	public List<VPTCProposal> getVPTCProposalListByProposalno(long proposalno) {
		return sampleRepo.getVPTCProposalListByProposalno(proposalno);
	}

	public List<PurchaseRequisition> getAllPurchaseRequisition() {
		return sampleRepo.getAllPurchaseRequisition();

	}

	public List<PurchaseRequisition> getPRByStatus(int status) {
		return sampleRepo.getPRByStatus(status);

	}

	public VPHeader getVPHeaderDetailsByRfpno(long rfpno) {
		return sampleRepo.getVPHeaderDetailsByRfpno(rfpno);
	}

	public List<VPItem> getVPItemsByRfpno(long rfpno) {
		return sampleRepo.getVPItemsByRfpno(rfpno);
	}

	public List<VPSection> getVPSectionListByRfpno(long rfpno) {
		return sampleRepo.getVPSectionListByRfpno(rfpno);
	}

	public List<VPGenProposal> getVPGenProposalListRfpno(long rfpno) {
		return sampleRepo.getVPGenProposalListRfpno(rfpno);
	}

	public List<VPTCProposal> getVPTCProposalListByRfpno(long rfpno) {
		return sampleRepo.getVPTCProposalListByRfpno(rfpno);
	}

	public List<TechnoRatings> getRatingListByRfpVendCreatedBy(long cp_fk, String lifnr, String createdBy) {
		// TODO Auto-generated method stub
		return sampleRepo.getRatingListByRfpVendCreatedBy(cp_fk, lifnr, createdBy);
	}

	public List<TechnoRatings> getRatingListByRfpVend(long cp_fk, String lifnr) {
		// TODO Auto-generated method stub
		return sampleRepo.getRatingListByRfpVend(cp_fk, lifnr);
	}

	public List<TechnoCriteria> getCriteriaListByRfpno(long cp_fk) {
		// TODO Auto-generated method stub
		return sampleRepo.getCriteriaListByRfpno(cp_fk);
	}

	public List<RFPParticipant> getInvitedVendorListByRfpno(long cp_fk) {
		// TODO Auto-generated method stub
		return sampleRepo.getInvitedVendorListByRfpno(cp_fk);
	}

	public List<PurchaseRequisition> getPurchaseRequisitionByReqid(long banfn) {
		// TODO Auto-generated method stub
		return sampleRepo.getPurchaseRequisitionByReqid(banfn);
	}

	public List<ApprovalLog> getApprovalLogByAprv(String aprv) {
		// TODO Auto-generated method stub
		return sampleRepo.getApprovalLogByAprv(aprv);
	}

	public WorkflowConfig findByAppidAndStatus(String appid, Status status) {
		// TODO Auto-generated method stub
		return workFlowRepo.findByAppidAndStatus(appid, status);
	}

	public List<RFPItem> getRfpByPrAndPrItemNo(long prnumber, String pritemno) {
		// TODO Auto-generated method stub
		return sampleRepo.getRfpByPrAndPrItemNo(prnumber, pritemno);
	}

	public List<ApprovalLog> getListApprovalLogByReqnoAprvStatus(long reqNo, String dept, Status status) {
		// TODO Auto-generated method stub
		return sampleRepo.getListApprovalLogByReqnoAprvStatus(reqNo, dept, status);
	}
	/*
	 * public List<POItemDetails> pOItemDetailsListbasedOnPurchaseOrderId(int id){
	 * return sampleRepo.pOItemDetailsListbasedOnPurchaseOrderId(id); }
	 */

	public PurchaseOrder getpurchaseOrderById(int id) {
		return sampleRepo.getpurchaseOrderById(id);
	}

	public List<FirstParty> getfirstPartyListByPlant(String werks) {
		// TODO Auto-generated method stub
		return sampleRepo.getfirstPartyListByPlant(werks);
	}

	/*
	 * public List<FirstParty> getfirstPartyListByPlant(List<String> plant) { //
	 * TODO Auto-generated method stub return
	 * sampleRepo.getfirstPartyListByPlant(plant); }
	 */

	public List<SecondParty> getsecondPartyListByPartner(String lifnr) {
		// TODO Auto-generated method stub
		return sampleRepo.getsecondPartyListByPartner(lifnr);
	}

	public List<RFPItem> getrfpItemListByMatnr(String matnr) {
		// TODO Auto-generated method stub
		return sampleRepo.getrfpItemListByMatnr(matnr);
	}

	public List<RFPItem> getrfpItemListByWerks(String werks) {
		// TODO Auto-generated method stub
		return sampleRepo.getrfpItemListByWerks(werks);
	}

	public List<VendorLedger> getvendorLedgerByLifnrGjahrBudat(String lifnr, String budat, String gjahr) {
		// TODO Auto-generated method stub
		return sampleRepo.getvendorLedgerByLifnrGjahrBudat(lifnr, budat, gjahr);
	}

	public Plant getPlantById(int id) {
		// TODO Auto-generated method stub
		return sampleRepo.getPlantById(id);
	}

	public List<Department> getAllDepartments() {
		return sampleRepo.getAllDepartments();
	}

	public List<VendorRegistration> getVendorsByBukrs(String bukrs) {
		// TODO Auto-generated method stub
		return sampleRepo.getVendorsByBukrs(bukrs);
	}

	public List<VendorRegistration> getVendorsByBukrsAndName1AndRegionAndCity(String name1, String bukrs,
			String bezei) {
		// TODO Auto-generated method stub
		return sampleRepo.getVendorsByBukrsAndName1AndRegionAndCity(name1, bukrs, bezei);
	}

	public List<VendorRegistration> getVendorsByBukrsAndLifnr(String bukrs, long lifnr) {
		// TODO Auto-generated method stub
		return sampleRepo.getVendorsByBukrsAndLifnr(bukrs, lifnr);
	}

	public List<VendorRegistration> getVendorsByBukrsRegion(String bukrs, String bezei, String ort01) {
		// TODO Auto-generated method stub
		return sampleRepo.getVendorsByBukrsRegion(bukrs, bezei, ort01);
	}

	public List<VendorRegistration> getVendorsByBukrsRegion1(String bukrs, String bezei) {
		// TODO Auto-generated method stub
		return sampleRepo.getVendorsByBukrsRegion1(bukrs, bezei);
	}

	public List<UserMaster> getUserListByBptype(String bptype, int inactive) {
		// TODO Auto-generated method stub
		return sampleRepo.getUserListByBptype(bptype, inactive);
	}

	public List<UserMaster> getUsersById(long id) {
		// TODO Auto-generated method stub
		return sampleRepo.getUsersById(id);
	}

	public VendorRegistration createVenReg(VendorRegistration venreg) {
		return sampleRepo.save(venreg);
	}

	public boolean vpParticipantsCreatedByMail(String email, long rfppno, long vpnum, String currdt) {
		try {
			String subject = "Please find the Proposal no. " + vpnum + " against RFP no. " + rfppno + " Dated "
					+ currdt;
			// String subject ="Please find the Proposal no. "+vpnum+" Dated "+currdt;
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
				msg.setRecipients(Message.RecipientType.TO, email);
				msg.setSubject(subject);
				// msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail));

				System.out.println("****");
				Multipart multipart = new MimeMultipart();

				MimeBodyPart htmlPart = new MimeBodyPart();

				String htmlContent = "<html><body><font color=black>proposal no. " + vpnum + " against RFP no. "
						+ rfppno + " dated " + currdt
						+ " has been created, kindly initiate the techno-commercial rating." + "<br>"
						// + "<br>. Thank You" + "<br>"
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

	public boolean invitedVendorMail(String email, String name, long rfpnum, String currdt) {
		try {
			String subject = "Please find the RFP no. " + rfpnum + " Dated " + currdt;
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
				msg.setRecipients(Message.RecipientType.TO, email);
				msg.setSubject(subject);
				// msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail));

				System.out.println("****");
				Multipart multipart = new MimeMultipart();

				MimeBodyPart htmlPart = new MimeBodyPart();

				String htmlContent = "<html><body><font color=black> Please login now with your credentials." + "<br>"
						+ "<br>Link: http://192.168.0.56:8080/ZSRL_FIORI" + "<br>" + "<br>Thank You" + "<br>"
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

	public boolean tempVendorMail(String email, String password, String name, String venlifnr, long rfpnum,
			String currdt) {
		try {
			String subject = "Please find the RFP no. " + rfpnum + " Dated " + currdt;
			// String subject ="Provide details of temporay registration purpose";
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
				msg.setRecipients(Message.RecipientType.TO, email);
				msg.setSubject(subject);
				// msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail));

				System.out.println("****");
				Multipart multipart = new MimeMultipart();

				MimeBodyPart htmlPart = new MimeBodyPart();

				String htmlContent = "<html><body><font color=black> Greetings, Your account has been created. You can login now with following credentials."
						+ "<br>" + "<br>Username: " + "T" + venlifnr + "<br>" + "<br>Pasword: " + password + "<br>"
						+ "<br>Link: http://192.168.0.56:8080/ZSRL_FIORI" + "<br>"
						+ "<br>Kindly submit the required details for your temporary registration purpose at the earliest. Thank You"
						+ "<br>" + "</font></body></html>";
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

	public boolean contractMail(String email, long cid, String currdt) {
		try {

			String subject = "Contract no. " + cid + " dated " + currdt + " has been created";
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
				msg.setRecipients(Message.RecipientType.TO, email);
				msg.setSubject(subject);
				// msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail));

				System.out.println("****");
				Multipart multipart = new MimeMultipart();

				MimeBodyPart htmlPart = new MimeBodyPart();

				String htmlContent = "<html><body><font color=black>Contract no. " + cid + " dated " + currdt
						+ " has been created, kindly verify and revert your kind acknowledgement for the same, through the link login provided."
						+ "<br>" + "<br>Link: http://192.168.0.56:8080/ZSRL_FIORI" + "<br>"
						// + "<br>. Thank You" + "<br>"
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

	// rfpValidityExtension(inv.getEmailid(), rfpnum, olddf, newdf,inv.getName());
	public boolean rfpValidityExtension(String email, long rfpno, String olddt, String newdf, String name) {
		try {
			String subject = " RFP no. " + rfpno + " Dated " + olddt + " has been extended to " + newdf;
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
				msg.setRecipients(Message.RecipientType.TO, email);
				msg.setSubject(subject);
				// msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail));

				System.out.println("****");
				Multipart multipart = new MimeMultipart();

				MimeBodyPart htmlPart = new MimeBodyPart();

				String htmlContent = "<html><body><font color=black>Kindly note that the due date for submitting your lowest offer against the RFP no. "
						+ rfpno + " Dated " + olddt + " has been extended to " + newdf + "<br>" + "<br>"
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

}
package com.iteanz.srl.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iteanz.srl.domain.ApprovalLog;
import com.iteanz.srl.domain.ApprovalMatrix;
import com.iteanz.srl.domain.ApproverMaster;
import com.iteanz.srl.domain.Currency;
import com.iteanz.srl.domain.Department;
import com.iteanz.srl.domain.Designation;
import com.iteanz.srl.domain.DocumentType;
import com.iteanz.srl.domain.FirstParty;
import com.iteanz.srl.domain.MaterialMaster;
import com.iteanz.srl.domain.NumberRange;
import com.iteanz.srl.domain.OrgMasterAndUserMapping;
import com.iteanz.srl.domain.PasswordPolicy;
import com.iteanz.srl.domain.Plant;
import com.iteanz.srl.domain.PurOrg;
import com.iteanz.srl.domain.PurchaseOrder;
import com.iteanz.srl.domain.PurchaseRequisition;
import com.iteanz.srl.domain.RFPHeader;
import com.iteanz.srl.domain.RFPInvitedVendors;
import com.iteanz.srl.domain.RFPItem;
import com.iteanz.srl.domain.RFPParticipant;
import com.iteanz.srl.domain.RFPSection;
import com.iteanz.srl.domain.RoleModel;
import com.iteanz.srl.domain.SampleEntity;
import com.iteanz.srl.domain.SecondParty;
import com.iteanz.srl.domain.ServerInfo;
import com.iteanz.srl.domain.SessionHistory;
import com.iteanz.srl.domain.SessionsTable;
import com.iteanz.srl.domain.Status;
import com.iteanz.srl.domain.Storage;
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
import com.iteanz.srl.domain.VendorLedger;
import com.iteanz.srl.domain.VendorRegistration;
import com.iteanz.srl.domain.WorkflowConfig;
@Repository
public interface WebServiceRepository extends CrudRepository<SampleEntity, Long> {
 @Query("FROM SampleEntity")
    public List<SampleEntity> getAll();
    @Query("FROM NumberRange where transationType=:transationType")
   	public NumberRange getNumberRageByDocumentType(@Param("transationType")String transationType);
   	@Query("FROM SampleEntity where id=:id")
	public Optional<SampleEntity> findById(@Param("id") int id);
	public UserADIntegration save(UserADIntegration userADIntegration);
	public UserMaster save(UserMaster userMaster);
	@Query("FROM UserADIntegration where adrid=:adrid and pswrd=:pswrd")
	public Optional<UserADIntegration> findByadrid(@Param("adrid") String adrid, @Param("pswrd") String pswrd);
	@Query("FROM UserMaster where adrid=:adrid")
	
	public UserMaster findByadrid(@Param("adrid") String adrid);
	public PasswordPolicy save(PasswordPolicy passwordPolicy);
	
	@Query("FROM UserMaster where inactive=:inactive")
	public List<UserMaster> getAllActiveUsers(@Param("inactive") int inactive);
	 	 
	@Query("FROM UserMaster")
		public List<UserMaster> getAllUsers();
	 
	@Query("FROM UserMaster where adrid=:adrid")
	public List<UserMaster> getUsersByAdrid(@Param("adrid") String adrid);
 
	  @Query("FROM Currency")
		public List<Currency> getAllCurrency();
	  
	  @Query("FROM WorkflowConfig where appid=:appid")
		public WorkflowConfig getWorkflowConfigByAppid(@Param("appid") String appid);
	  
	  @Query("FROM ApprovalLog where appid=:appid and  aprv=:aprv and reqNo=:reqNo and status=:status and mailstatus=:mailstatus")
		public List<ApprovalLog> getApprovalLogByAprvAndAppidAndReqnoAndStatusMailStatus(
				@Param("appid") WorkflowConfig appid, @Param("aprv") String userIdNo, @Param("reqNo") long reqNo,
				@Param("status") Status status, @Param("mailstatus") int mailstatus);

	  @Query("FROM ApprovalLog where appid=:appid and  aprv=:aprv and reqNo=:reqNo and status=:status and mailstatus=:mailstatus")
			public List<ApprovalLog> getApprovalLogByAprvAndAppidAndReqnoAndStatusMailStatus(
					@Param("appid") WorkflowConfig appid, @Param("aprv") String userIdNo, @Param("reqNo") String reqNo,
					@Param("status") Status status, @Param("mailstatus") int mailstatus);

	  
	  @Query("FROM PurchaseRequisition where banfn=:banfn")
		public List<PurchaseRequisition> getPurchaseRequisitionByReqid(@Param("banfn") long banfn);

	  @Query("FROM ApprovalLog where appid=:appid and status=:status and reqNo=:reqNo")
		public List<ApprovalLog> getApprovalLogByAppIdAndReqNO(@Param("appid") WorkflowConfig appid1,
				@Param("status") Status statusObj, @Param("reqNo") long reqNo);
	  
	  @Query("FROM ApprovalLog where appid=:appid and status=:status and reqNo=:reqNo")
			public List<ApprovalLog> getApprovalLogByAppIdAndReqNO(@Param("appid") WorkflowConfig appid1,
					@Param("status") Status statusObj, @Param("reqNo") String reqNo);


	  @Query("FROM ApprovalLog where reqNo=:reqNo and aprv=:aprv and status=:status")
		public List<ApprovalLog> getListApprovalLogByReqnoAprvStatus(@Param("reqNo") long reqNo,@Param("aprv") String aprv,
				@Param("status") Status status);

	  @Query("FROM ApprovalLog where appid=:appid and  aprv=:aprv and reqNo=:reqNo")
			public List<ApprovalLog> getApprovalLogByAprvAndAppidAndReqno(@Param("appid") WorkflowConfig appid,@Param("aprv") String aprv, @Param("reqNo") long reqNo);
		  
		  @Query("FROM ApprovalLog where aprv=:aprv")
			public List<ApprovalLog> getApprovalLogByAprv(@Param("aprv") String aprv);

		  
	  
	  @Query("FROM ApprovalLog where reqNo=:reqNo and mailstatus=:mailstatus and aprv=:aprv")
		public List<ApprovalLog> getApprovalLogByReqNoAndMailStatusaAndAprv(@Param("reqNo") long reqNo,
				@Param("mailstatus") int mailstatus, @Param("aprv") String aprv);
	  
	  
	  @Query("FROM ApprovalLog where reqNo=:reqNo and mailstatus=:mailstatus and aprv=:aprv")
		public List<ApprovalLog> getApprovalLogByReqNoAndMailStatusaAndAprv(@Param("reqNo") String reqNo,
				@Param("mailstatus") int mailstatus, @Param("aprv") String aprv);

	  @Query("FROM ApprovalLog where reqNo=:reqNo and mailstatus=:mailstatus")
		public List<ApprovalLog> getApprovalLogByReqNoAndMailStatus(@Param("reqNo") long reqNo,
				@Param("mailstatus") int mailstatus);
	  @Query("FROM ApprovalLog where reqNo=:reqNo and mailstatus=:mailstatus")
			public List<ApprovalLog> getApprovalLogByReqNoAndMailStatus(@Param("reqNo") String reqNo,
					@Param("mailstatus") int mailstatus);
	  
	  @Query("FROM ApprovalLog where reqNo=:reqNo and mailstatus=:mailstatus and aprv=:aprv")
		public List<ApprovalLog> getApprovalLogByReqNoAndMailStatusAndAprv(@Param("reqNo") long reqNo,
				@Param("mailstatus") int mailstatus, @Param("aprv") String aprv);
	  
	  @Query("FROM ApprovalLog where reqNo=:reqNo and mailstatus=:mailstatus and aprv=:aprv")
		public List<ApprovalLog> getApprovalLogByReqNoAndMailStatusAndAprv(@Param("reqNo") String reqNo,
				@Param("mailstatus") int mailstatus, @Param("aprv") String aprv);
	  
	public UserMaster save(OrgMasterAndUserMapping orgMasterAndUserMapping);
	public SessionHistory save(SessionHistory sessionHistory);
	public SessionsTable save(SessionsTable sessionTable);
	 @Query("FROM SessionsTable")
	public List<SessionsTable> findBySesid(@Param("sesid") long sesid);
	 @Query("FROM SessionsTable")
	public List<SessionsTable> getAllSessions();
	public ApproverMaster save(ApproverMaster approverMaster);
	 @Query("FROM ApproverMaster where status=:status")
	public List<ApproverMaster> getAllApprovels(@Param("status") Status status);
	 @Query("FROM NumberRange")
		public NumberRange getNumberRage();
		public NumberRange save(NumberRange numberRange);
	 @Query("FROM UserMaster where userRoll=:userRoll")
	public List<UserMaster> getAllUserMasterApprovers(@Param("userRoll") RoleModel userRoll);
	 @Query("FROM UserADIntegration")
	public List<UserADIntegration> getAllUserIntegration();
	 @Query("FROM ApprovalLog where reqid=:reqid and aprv=:aprv")
	public ApprovalLog getDetailsByRequestIdAndApproverId(@Param("reqid")long reqid, @Param("aprv") UserMaster aprv);
	 @Query("FROM Designation")
	public List<Designation> getAllDesignation();
	 @Query("FROM UserMaster where adrid=:adrid and userRoll=:userRoll")
	public UserMaster checkUserAdmin(@Param("userRoll")RoleModel userRoll, @Param("adrid") String adrid);
	 @Query("FROM UserMaster where adrid=:adrid and adflag=:adflag")
	public UserMaster getADCheck(@Param("adrid")String adrid, @Param("adflag")String adflag);
	 @Query("FROM UserMaster where adrid=:adrid and pswrd=:pswrd")
	public UserMaster getUserAuthenitcation(@Param("adrid") String adrid,@Param("pswrd") String pswrd);
	 @Query("FROM ServerInfo")
	public ServerInfo getServerInfo();
	 @Query("FROM UserMaster where email=:email")
	public UserMaster getUserByEmail(@Param("email") String email);
	 @Query("FROM Department")
	 public List<Department> getAllDepartment();
	 @Query("FROM RoleModel")
	public List<RoleModel> getAllRole();
	 
	 
	 @Query("FROM RoleModel where roleflag=:roleflag")
		public List<RoleModel> getByRoleFlag(@Param("roleflag") String roleflag);
	 
	public UserRolesAssignment save(UserRolesAssignment userRolesAssignment);
	@Query("FROM RoleModel where id=:id")
	public RoleModel getRoleByRoleId(@Param("id") int id);
	
	@Query("FROM UserRolesAssignment where userMasterObject=:userMasterObject and status=:status")
		public List<UserRolesAssignment> getAssignedRolls(@Param("userMasterObject") UserMaster userMasterObject, @Param("status") Status status);
		
	 
	 @Query("FROM UserMaster where approverMaster=:approverMaster")
	public UserMaster getUserByApproverMaster(@Param("approverMaster") ApproverMaster approverMaster);
	 @Query("FROM DocumentType")
	public List<DocumentType> getAllDocumentType();
	public DocumentType save(DocumentType documenntType);
	 @Query("FROM DocumentType where dctyp=:dctyp")
	public DocumentType getDocumentType(@Param("dctyp") String dctyp);
	 @Query("FROM UserRolesAssignment where userMasterObject=:userMasterObject and status=:status")
	public List<UserRolesAssignment> getUserRolesAssignment(@Param("userMasterObject") UserMaster userMasterObject, @Param("status") Status status);
		
	 
	 
	 @Query("FROM Status where statusId=:statusId")
	public Status getstatusById(@Param("statusId") int statusId);
	 @Query("FROM ApproverMaster where adrid=:adrid and status=:status")
	public ApproverMaster getApprover(@Param("adrid") String adrid, @Param("status") Status status);
	 @Query("FROM SessionsTable where sesid=:sesid")
	public List<SessionsTable> getUsersSession(@Param("sesid") long sesid);
	
	@Query("FROM PurchaseRequisition where werks=:werks and banfn=:banfn and status=:status")
	public List<PurchaseRequisition> getPurchaseRequisitionByPlant(@Param("werks") String werks, @Param("banfn") long banfn, @Param("status") int status);
	
	@Query("FROM PurchaseRequisition where banfn=:banfn and bnfpo=:bnfpo and aprv=:aprv")
	public PurchaseRequisition gePRStatusByPrnoItemno(@Param("banfn") long banfn, @Param("bnfpo") String bnfpo, @Param("aprv") String aprv);
	
	@Query("FROM PurchaseRequisition where aprv=:aprv")
	public List<PurchaseRequisition> getPurchaseRequisitionByAprv(@Param("aprv") String aprv);
	
	@Query("FROM PurchaseRequisition where aprv=:aprv")
	public List<PurchaseRequisition> gePRStatusByUserid(@Param("aprv") String aprv);
	
	
	public PurchaseRequisition save(PurchaseRequisition purchaseRequisition);
	
	
	public ApprovalMatrix save(ApprovalMatrix aprvMatrix);
	
	public WorkflowConfig save(WorkflowConfig workflowConfig);
	
	public ApprovalLog save(ApprovalLog aprvLog);
	
	
	
	@Query("FROM ApprovalMatrix where id=:id")
	public ApprovalMatrix getApprovalMatrixById(@Param("id") int id);
	
	@Query("FROM WorkflowConfig where id=:id")
	public WorkflowConfig getWorkflowConfigById(@Param("id") int id);
	// appid:=appid 
	
	/*@Query("FROM WorkflowConfig where status=:status")
	public List<WorkflowConfig> getActiveWorkflowConfigByStatus(@Param("status") Status status);*/
	
	@Query("FROM WorkflowConfig where appid=:appid and subprocess=:subprocess and status=:status")
	public WorkflowConfig getActiveWorkflowConfigByStatus(@Param("appid") String appid, @Param("subprocess") String subprocess, @Param("status") Status status);
	
	
	@Query("FROM Status where statusId=:statusId")
	public Status getStatusById(@Param("statusId") int statusId);
	
	
	 @Query("FROM ApprovalMatrix")
	public List<ApprovalMatrix> getAllApprovalMatrix();
	
	 
	 @Query("FROM WorkflowConfig")
		public List<WorkflowConfig>getAllWorkflowConfig();
	
	 @Query("FROM ApprovalLog where approvedBy=:approvedBy and mailstatus=:mailstatus")
	public List<ApprovalLog> getPendingApprovalById(@Param("approvedBy") String approvedBy, @Param("mailstatus") int mailstatus);

	 @Query("FROM ApprovalLog where reqNo=:reqNo")
	public List<ApprovalLog> getApprovalLogListByReqid(@Param("reqNo") long reqNo);
	 
	 @Query("FROM ApprovalLog where reqNo=:reqNo and mailstatus=:mailstatus")
		public List<ApprovalLog> getApprovalLogListByReqidAndAprv(@Param("reqNo") long reqNo, @Param("mailstatus") int mailstatus);
	
	//public List <PurchaseRequisition> save(PurchaseRequisition <purchaseRequisition>);
	
	//public PurchaseRequisition gePRStatusByUserid(String aprv);
	 
	 @Query("FROM UserMaster where email=:email")
		public UserMaster getApproverAutenticationByEmail(@Param("email") String email);
	 
	 
	 @Query("FROM ApprovalLog where reqNo=:reqNo and aprv=:aprv")
		public ApprovalLog getApprovalLogByReqNoAndAprv(@Param("reqNo") long reqNo,@Param("aprv") String aprv);
		
	@Query("FROM UserMaster where id=:id")
	 public UserMaster getUserMasterById(@Param("id") String id);
	 
	@Query("FROM VendorRegistration")
	public List<VendorRegistration> getActiveUserListByStatus();
	
	public RFPHeader save(RFPHeader rfpHeader);
	
	public RFPItem save(RFPItem rfpItem);
	
	public RFPSection save(RFPSection rfpSection);

	public RFPInvitedVendors save(RFPInvitedVendors rfpInvitedVendors);
	
	public RFPParticipant save(RFPParticipant rfpParticipant);
	
	public PurOrg save(PurOrg purOrg);
	
	public VPHeader save(VPHeader vpHeader);
	
	public VPItem save(VPItem vpItem);
	
	public VPSection save(VPSection vpSection);
	
	public VPGenProposal save(VPGenProposal vpGenProposal);
	
	public VPTCProposal save(VPTCProposal vptcProposal);
	
	 @Query("FROM Storage where werks=:werks and mailstatus=:mailstatus")
		public List<Storage> getStorageListByPlantcode(@Param("werks") String werks, @Param("mailstatus") int mailstatus);
	
	 @Query("FROM PurOrg where bukrs=:bukrs and ekorg=:ekorg and mailstatus=:mailstatus")
		public List<PurOrg> getPurOrgListByCompAndOrg(@Param("bukrs") String bukrs,@Param("ekorg") String ekorg,@Param("mailstatus") int mailstatus);
	
	
	 @Query("FROM Plant where mailstatus=:mailstatus")
		public List<Plant> getPlantList(@Param("mailstatus") int mailstatus);
	
	  @Query("FROM PurOrg where bukrs=:bukrs and inactive=:inactive")
		public List<PurOrg> getpurOrgListByComp(@Param("bukrs") String bukrs,@Param("inactive") int inactive);
	  
		
		@Query("FROM PurOrg where bukrs=:bukrs and ekorg=:ekorg")
			public List<PurOrg> getpurOrgListByComp(@Param("bukrs") String bukrs,@Param("ekorg") String ekorg);
		 
	  
	  @Query("FROM Plant")
		public List<Plant> getPlantList();
	
	 @Query("FROM Plant where bukrs=:bukrs")
		public List<Plant> getPlantListByComp(@Param("bukrs") String bukrs);
	
	 @Query("FROM Plant where werks=:werks")
		public List<Plant> getPlantListByPlant(@Param("werks") String werks);
	 
	 // @Query("SELECT DISTINCT werks FROM Plant where bukrs=:bukrs and ekorg=:ekorg and werks=:werks")
	@Query("FROM Plant where bukrs=:bukrs and ekorg=:ekorg and werks=:werks")
		public List<Plant> getPlantListByCompAndOrgAndPlant(@Param("bukrs") String bukrs, @Param("ekorg") String ekorg, @Param("werks") String werks);
	 

		 @Query("FROM Plant where bukrs=:bukrs and ekorg=:ekorg")
			public List<Plant> getPlantListByCompAndOrg(@Param("bukrs") String bukrs,@Param("ekorg") String ekorg);
		
			@Query("FROM SessionHistory")
			public List<SessionHistory> getSessionHistoryList();
	
			@Query("FROM SessionsTable where sesid=:sesid")
			public List<SessionsTable> getFindSessionById(@Param("sesid") long sesid);
			
			@Query("FROM SessionsTable where sesid=:sesid")
			public SessionsTable getSessionStatus(@Param("sesid") long sesid);
	  
	  @Query("FROM TechnoRatings where cp_fk=:cp_fk and lifnr=:lifnr and createdBy=:createdBy")
		public List<TechnoRatings> getRatingListByRfpVendCreatedBy(@Param("cp_fk") long cp_fk,@Param("lifnr") String lifnr, @Param("createdBy") String createdBy);
	  
	  
	  @Query("FROM TechnoRatings where cp_fk=:cp_fk and lifnr=:lifnr")
		public List<TechnoRatings> getRatingListByRfpVend(@Param("cp_fk") long cp_fk,@Param("lifnr") String lifnr);
	 
	  
	  @Query("FROM TechnoCriteria where cp_fk=:cp_fk")
		public List<TechnoCriteria> getCriteriaListByRfpno(@Param("cp_fk") long rfpno);
	  
	  @Query("FROM RFPParticipant where cp_fk=:cp_fk")
		public List<RFPParticipant> getInvitedVendorListByRfpno(@Param("cp_fk") long rfpno);
	  
	 @Query("FROM PurOrg where bukrs=:bukrs and ekorg=:ekorg and inactive=:inactive")
		public List<PurOrg> getpurGrpByCompAndOrg(@Param("bukrs") String bukrs, @Param("ekorg") String ekorg, @Param("inactive") int inactive);
	 
	 @Query("FROM RFPHeader where createdBy=:createdBy")
		public List<RFPHeader> getrfpHeaderListByUser(@Param("createdBy") String createdBy);
	 
	 @Query("FROM VPHeader where createdby=:createdby and readstatus=:readstatus and inactive=:inactive")
		public List<VPHeader> getvpHeaderListByUser(@Param("createdby") String createdby,@Param("readstatus") String readstatus,@Param("inactive") int inactive);

	 @Query("FROM PurchaseRequisition where reqstatus=:reqstatus and inactive=:inactive")
		public List<PurchaseRequisition> getincompPrListByReqStatus(@Param("reqstatus") String reqstatus,@Param("inactive") int inactive);
	 
	 @Query("FROM Uom")
		public List<Uom> getAllUom();
	 
	 @Query("FROM MaterialMaster")
		public List<MaterialMaster>getAllMaterials();
	 
	  
	 @Query("FROM MaterialMaster where matnr =:matnr and werks =:werks and mtart=:mtart")
		public List<MaterialMaster>getAllMaterialsByMatnrAndWerksAndMtart(@Param("matnr") String matnr, @Param("werks") String werks, @Param("mtart") String mtart);
	 
	 
	 @Query("FROM MaterialMaster where matnr =:matnr and werks =:werks")
		public List<MaterialMaster>getAllMaterialsByMatnrAndWerks(@Param("matnr") String matnr, @Param("werks") String werks);
	
	 
	 @Query("FROM VendorRegistration")
		public List<VendorRegistration> getAllVendors();
	 
	 @Query("FROM RFPHeader")
		public List<RFPHeader> getAllRfpHeader();
	 
	 
	  @Query("FROM PurOrg")
		public List<PurOrg> getAllPurOrg();
	 
	 @Query("FROM RFPInvitedVendors where name=:name")
	 public List<RFPInvitedVendors> getRfpInvitedVendors(@Param("name") String name);
	 
	 
	 @Query("FROM RFPHeader where rfpInvitedVendors=:rfpInvitedVendors")
		public RFPHeader getRfpHeader(@Param("rfpInvitedVendors") RFPInvitedVendors rfpInvitedVendors);
	 
	 @Query("FROM RFPItem where rfpno=:rfpno")
		public List<RFPItem> getRFPItemsByReqid(@Param("rfpno") long rfpno);
		
	 @Query("FROM RFPHeader where rfpno=:rfpno")
		public RFPHeader getRFPHeaderDatailsByReqid(@Param("rfpno") long rfpno);
	 
	 @Query("FROM RFPInvitedVendors where rfpno=:rfpno")
		public List<RFPInvitedVendors> getInvitedVendorsByReqid(@Param("rfpno") long rfpno);
	 
	 @Query("FROM RFPParticipant where rfpno=:rfpno")
		public List<RFPParticipant> getRfpParticipantListByReqid(@Param("rfpno") long rfpno);
	 
	 @Query("FROM RFPSection where rfpno=:rfpno")
		public List<RFPSection> getRfpSectionListByReqid(@Param("rfpno") long rfpno);
	 
	 @Query("FROM VPHeader where proposalno=:proposalno")
		public VPHeader getVPHeaderDetailsByProposalno(@Param("proposalno") long proposalno);
	 
	 @Query("FROM VPItem where proposalno=:proposalno")
		public List<VPItem> getVPItemsByProposalno(@Param("proposalno") long proposalno);
	 
	 @Query("FROM VPSection where proposalno=:proposalno")
		public List<VPSection> getVPSectionListByProposalno(@Param("proposalno") long proposalno);
	 
	 @Query("FROM VPGenProposal where proposalno=:proposalno")
		public List<VPGenProposal> getVPGenProposalListByProposalno(@Param("proposalno") long proposalno);
	 
	 @Query("FROM VPTCProposal where proposalno=:proposalno")
		public List<VPTCProposal> getVPTCProposalListByProposalno(@Param("proposalno") long proposalno);
	
	@Query("FROM PurchaseRequisition")
		public List<PurchaseRequisition> getAllPurchaseRequisition();
	 
	
	
	@Query("FROM PurchaseRequisition where status=:status")
	public List<PurchaseRequisition> getPRByStatus(@Param("status") int status);
 
	
	 @Query("FROM VPHeader where rfpno=:rfpno")
		public VPHeader getVPHeaderDetailsByRfpno(@Param("rfpno") long rfpno);
	 
	@Query("FROM VPItem where rfpno=:rfpno")
	public List<VPItem> getVPItemsByRfpno(@Param("rfpno") long rfpno);
	
	@Query("FROM VPSection where rfpno=:rfpno")
	public List<VPSection> getVPSectionListByRfpno(@Param("rfpno") long rfpno);
	
	 @Query("FROM VPGenProposal where rfpno=:rfpno")
	public List<VPGenProposal> getVPGenProposalListRfpno(@Param("rfpno") long rfpno);
	
	@Query("FROM VPTCProposal where rfpno=:rfpno")
	public List<VPTCProposal> getVPTCProposalListByRfpno(@Param("rfpno") long rfpno);
	 
	@Query("FROM PurchaseOrder where id =:id")
		public PurchaseOrder getpurchaseOrderById(@Param("id") int id);

	/*@Query("FROM POItemDetails where purchaseOrder.id =:id")
		public List<POItemDetails>pOItemDetailsListbasedOnPurchaseOrderId(@Param("id") int id);
	@Query("FROM RFPHeader where rfpno=:rfpno")
	 public List<RFPHeader> getRfpHeaderbyRfpno(@Param("rfpno") long rfpno);*/
	 
	
	 //public List<RFPHeader> getRfpHeaderbyRfpno(@Param("rfpno") List<RFPInvitedVendors> rfpInvitedVendorsList);
	
	 @Query("FROM FirstParty where werks=:werksS")
		public List<FirstParty> getfirstPartyListByPlant(@Param("werks") String werks);
	 /*
	  	@Query("SELECT DISTINCT FROM FirstParty where plant=:plant")
		public List<FirstParty> getfirstPartyListByPlant(@Param("plant") List<String> plant);
	  */
	 
	  @Query("FROM SecondParty where lifnr=:lifnr")
		public List<SecondParty> getsecondPartyListByPartner(@Param("lifnr") String lifnr);
	  
	  @Query("FROM RFPItem where matnr=:matnr")
		public List<RFPItem> getrfpItemListByMatnr(@Param("matnr") String matnr);

	  @Query("FROM RFPItem where werks=:werks")
		public List<RFPItem> getrfpItemListByWerks(@Param("werks") String werks);
	  
	  
	  
	  
	  @Query("FROM RFPItem where prnumber=:prnumber and pritemno=:pritemno")
		public List<RFPItem> getRfpByPrAndPrItemNo(@Param("prnumber") long prnumber, @Param("pritemno") String pritemno);

	  
	   @Query("FROM VendorLedger where lifnr=:lifnr and budat=:budat and gjahr=:gjahr")
		public List<VendorLedger> getvendorLedgerByLifnrGjahrBudat(@Param("lifnr") String lifnr, @Param("budat") String budat, @Param("gjahr") String gjahr);

	   public Plant save(Plant plant);
	   
	   @Query("FROM Plant where id=:id")
		public Plant getPlantById(@Param("id") int id);
		
	   @Query("FROM UserMaster where department=:department")
		public UserMaster getuserMasterByDept(@Param("department") String department);
		
	 @Query("FROM Department")
		public List<Department> getAllDepartments();
		 
	 @Query("FROM VendorRegistration where bukrs=:bukrs")
		public List<VendorRegistration> getVendorsByBukrs(@Param("bukrs") String bukrs);
	
	 //LIKE CONCAT('%',:maktx,'%') 
	 
	// %:maktx%
	@Query("FROM MaterialMaster where maktx LIKE  %:maktx% and mtart=:mtart and werks=:werks")
		public List<MaterialMaster> getAllMaterialsByMaktxAndMtartAndWerks(@Param("maktx") String maktx, @Param("mtart") String mtart, @Param("werks") String werks);
		
	 
	 @Query("FROM MaterialMaster where mtart=:mtart and werks=:werks")
		public List<MaterialMaster> getAllMaterialsByMtartAndWerks(@Param("mtart") String mtart, @Param("werks") String werks);
		
	 
	 public VendorRegistration save(VendorRegistration venreg);
	 
	 
	 @Query("FROM UserMaster where bptype=:bptype and inactive=:inactive")
		public List<UserMaster> getUserListByBptype(@Param("bptype") String bptype, @Param("inactive") int inactive);
	 
	 @Query("FROM UserMaster where id=:id")
		public List<UserMaster> getUsersById(@Param("id") long id);
	 
	 
	 @Query("FROM VendorRegistration where bukrs=:bukrs and lifnr=:lifnr")
		public List<VendorRegistration> getVendorsByBukrsAndLifnr(@Param("bukrs") String bukrs, @Param("lifnr") long lifnr);
	 
	 @Query("FROM ApprovalLog where appid=:appid and  aprv=:aprv and reqNo=:reqNo and mailstatus=:mailstatus")
		public List<ApprovalLog> getApprovalLogByAprvAndAppidAndReqnoAndMailStatus(
				@Param("appid") WorkflowConfig appid, @Param("aprv") String userIdNo, @Param("reqNo") long reqNo,
			/* @Param("status") Status status, */ @Param("mailstatus") int mailstatus);
	 
	 @Query(value = "SELECT sum(menge) FROM RFPItem where prnumber=:prnumber and  pritemno=:pritemno")
	   public float sumMenges(@Param("prnumber") long prnumber, @Param("pritemno") String pritemno);
		
	 
		@Query(value = "SELECT sum(preis) FROM VPItem where cp_fk=:cp_fk")
	    public double sumPreis(@Param("cp_fk") long cp_fk);
		
		@Query(value = "SELECT sum(menge) FROM POItemDetails where cp_fk=:cp_fk and ebelp=:ebelp")
	    public float sumPOItemMenges(@Param("cp_fk") long cp_fk, @Param("ebelp") long ebelp);
	
		@Query(value = "SELECT sum(menge) FROM ASNItem where ebeln =:ebeln and ebelp=:ebelp")
	    public float sumASNItemMenges(@Param("ebeln") long ebeln, @Param("ebelp") long ebelp);
		
		@Query(value = "SELECT sum(menge1) FROM GRNItem where ebeln =:ebeln and ebelp=:ebelp")
	    public float sumGRNItemMenges(@Param("ebeln") long ebeln, @Param("ebelp") long ebelp);
		
		@Query(value = "SELECT sum(menge) FROM GatePassItem where ebeln =:ebeln and ebelp=:ebelp")
	    public float sumGPassItemMenges(@Param("ebeln") long ebeln, @Param("ebelp") long ebelp);
		
		
		
		@Query(value = "SELECT sum(menge) FROM POItemDetails where cp_fk=:cp_fk")
	    public float sumPOMenges(@Param("cp_fk") long cp_fk);
	
		@Query(value = "SELECT sum(menge) FROM ASNItem where ebeln =:ebeln")
	    public float sumASNMenges(@Param("ebeln") long ebeln);
		
		@Query(value = "SELECT sum(menge1) FROM GRNItem where ebeln =:ebeln")
	    public float sumGRNMenges(@Param("ebeln") long ebeln);
		
		@Query(value = "SELECT sum(menge) FROM GatePassItem where ebeln =:ebeln")
	    public float sumGPassMenges(@Param("ebeln") long ebeln);
		
		
		@Query("FROM RFPHeader where createdby=:createdby")
		public List<RFPHeader> getRFPByCreatedby(@Param("createdby") String createdby);
		
		//LIKE ' %:name1%'
		
		@Query("FROM VendorRegistration where name1 LIKE %:name1% and bukrs=:bukrs and bezei=:bezei")
		public List<VendorRegistration> getVendorsByBukrsAndName1AndRegionAndCity(@Param("name1") String name1, @Param("bukrs") String bukrs, @Param("bezei") String bezei);
		
		
		@Query("FROM VendorRegistration where bukrs=:bukrs and bezei=:bezei and ort01 LIKE %:ort01%")
		public List<VendorRegistration> getVendorsByBukrsRegion(@Param("bukrs") String bukrs, @Param("bezei") String bezei, @Param("ort01") String ort01);
		

		@Query("FROM VendorRegistration where bukrs=:bukrs and bezei=:bezei")
		public List<VendorRegistration> getVendorsByBukrsRegion1(@Param("bukrs") String bukrs, @Param("bezei") String bezei);
		
		
		
		
		
		
}

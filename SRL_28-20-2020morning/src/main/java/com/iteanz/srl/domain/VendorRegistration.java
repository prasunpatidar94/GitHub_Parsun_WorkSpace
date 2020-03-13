package com.iteanz.srl.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "VendorRegistration")
public class VendorRegistration {

	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "VendorRegistration_seq", allocationSize = 1, name = "VendorRegistration_s")
	@Column(name = "id")
	private Long id;

	@Column(name = "lifnr") // Vendor Code***********
	long lifnr;

	@Column(name = "ecclifnr")
	String ecclifnr;

	@Column(name = "name1") // Vendor Name*************
	String name1;

	@Column(name = "land1") // Country*************
	String land1;

	@Column(name = "ort01") // City
	String ort01;

	@Column(name = "ort02") // District
	String ort02;
	
	@Column(name = "bezei") // region name 
	String bezei;

	@Column(name = "pfach") // PO Box
	String pfach;

	@Column(name = "pstl2") // PO Box Pcode
	String pstl2;

	@Column(name = "pstlz") // Postal Code
	String pstlz;

	@Column(name = "regio") // Region****************
	String regio;

	@Column(name = "stras") // Street
	String stras;

	@Column(name = "emailid") // email id **********
	String emailid;

	@Column(name = "panno")
	String panno;

	@Column(name = "stceg") // VAT Reg.No
	String stceg;

	@Column(name = "banks")
	String banks;

	@Column(name = "bankl")
	String bankl;

	@Column(name = "bankn")
	String bankn;

	@Column(name = "koinh") //
	String koinh;

	@Column(name = "ktokk") // Account Group*********
	String ktokk;

	@Column(name = "sortl") // Search term
	String sortl;

	@Column(name = "bukrs") // Company Code******************
	String bukrs;

	@Column(name = "akont") // Reconcillation Account in general ledger
	String akont;

	@Column(name = "qsskz") //
	String qsskz;

	@Column(name = "ekorg") // Purchase Organization********************
	String ekorg;

	@Column(name = "zterm") // Payment Term
	String zterm;

	@Column(name = "inco1") // Incoterms
	String inco1;

	@Column(name = "inco2") // Incoterma 2
	String inco2;

	@Column(name = "waers") // Currancy
	String waers;

	@Column(name = "blockIndicator")
	String blockIndicator;

	@Column(name = "kalsk")
	String kalsk;

	@Column(name = "werks") // Plant
	String werks;

	@Column(name = "strsuppl1")
	String strsuppl1;

	@Column(name = "strsuppl2")
	String strsuppl2;

	@Column(name = "namev")
	String namev;

	@Column(name = "mobnumber") /// contect no *************
	String mobnumber;

	@Column(name = "j1isern")
	String j1isern;

	@Column(name = "stcd3")
	String stcd3;

	@Column(name = "banklz")
	String banklz;

	@Column(name = "bankadd")
	String bankadd;

	@Column(name = "ifsccode")
	String ifsccode;

	@Column(name = "natjob")
	String natjob;

	@Column(name = "natentity")
	String natentity;

	@Column(name = "foriegnentity")
	String foriegnentity;

	@Column(name = "msmed")
	String msmed;

	@Column(name = "zwels")
	String zwels;

	@Column(name = "reprf")
	String reprf;

	@Column(name = "witht")
	String witht;

	@Column(name = "qland")
	String qland;

	@Column(name = "telnumber")
	String telnumber;

	@Column(name = "bahns")
	String bahns;

	@Column(name = "approver1")
	String approver1;

	@Column(name = "approver1status")
	String approver1status;

	@Column(name = "approver1comments")
	String approver1comments;

	@Column(name = "approver2")
	String approver2;

	@Column(name = "approver2status")
	String approver2status;

	@Column(name = "approver2comments")
	String approver2comments;

	@Column(name = "createdby")
	String createdby;

	@Column(name = "changedby")
	String changedby;

	@Column(name = "createddate")
	Date createddate;

	@Column(name = "changeddate")
	Date changeddate;

	@Column(name = "emailflag")
	String emailflag;





	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLifnr(long lifnr) {
		this.lifnr = lifnr;
	}

	public Long getLifnr() {
		return lifnr;
	}

	public void setLifnr(Long lifnr) {
		this.lifnr = lifnr;
	}

	public String getEcclifnr() {
		return ecclifnr;
	}

	public void setEcclifnr(String ecclifnr) {
		this.ecclifnr = ecclifnr;
	}


	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getLand1() {
		return land1;
	}

	public void setLand1(String land1) {
		this.land1 = land1;
	}

	public String getOrt01() {
		return ort01;
	}

	public void setOrt01(String ort01) {
		this.ort01 = ort01;
	}

	public String getOrt02() {
		return ort02;
	}

	public void setOrt02(String ort02) {
		this.ort02 = ort02;
	}

	public String getPfach() {
		return pfach;
	}

	public void setPfach(String pfach) {
		this.pfach = pfach;
	}

	public String getPstl2() {
		return pstl2;
	}

	public void setPstl2(String pstl2) {
		this.pstl2 = pstl2;
	}

	public String getPstlz() {
		return pstlz;
	}

	public void setPstlz(String pstlz) {
		this.pstlz = pstlz;
	}

	public String getRegio() {
		return regio;
	}

	public void setRegio(String regio) {
		this.regio = regio;
	}

	public String getStras() {
		return stras;
	}

	public void setStras(String stras) {
		this.stras = stras;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getPanno() {
		return panno;
	}

	public void setPanno(String panno) {
		this.panno = panno;
	}

	public String getStceg() {
		return stceg;
	}

	public void setStceg(String stceg) {
		this.stceg = stceg;
	}

	public String getBanks() {
		return banks;
	}

	public void setBanks(String banks) {
		this.banks = banks;
	}

	public String getBankl() {
		return bankl;
	}

	public void setBankl(String bankl) {
		this.bankl = bankl;
	}

	public String getBankn() {
		return bankn;
	}

	public void setBankn(String bankn) {
		this.bankn = bankn;
	}

	public String getKoinh() {
		return koinh;
	}

	public void setKoinh(String koinh) {
		this.koinh = koinh;
	}

	public String getKtokk() {
		return ktokk;
	}

	public void setKtokk(String ktokk) {
		this.ktokk = ktokk;
	}

	public String getSortl() {
		return sortl;
	}

	public void setSortl(String sortl) {
		this.sortl = sortl;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public String getAkont() {
		return akont;
	}

	public void setAkont(String akont) {
		this.akont = akont;
	}

	public String getQsskz() {
		return qsskz;
	}

	public void setQsskz(String qsskz) {
		this.qsskz = qsskz;
	}

	public String getEkorg() {
		return ekorg;
	}

	public void setEkorg(String ekorg) {
		this.ekorg = ekorg;
	}

	public String getZterm() {
		return zterm;
	}

	public void setZterm(String zterm) {
		this.zterm = zterm;
	}

	public String getInco1() {
		return inco1;
	}

	public void setInco1(String inco1) {
		this.inco1 = inco1;
	}

	public String getInco2() {
		return inco2;
	}

	public void setInco2(String inco2) {
		this.inco2 = inco2;
	}

	public String getWaers() {
		return waers;
	}

	public void setWaers(String waers) {
		this.waers = waers;
	}

	public String getBlockIndicator() {
		return blockIndicator;
	}

	public void setBlockIndicator(String blockIndicator) {
		this.blockIndicator = blockIndicator;
	}

	public String getKalsk() {
		return kalsk;
	}

	public void setKalsk(String kalsk) {
		this.kalsk = kalsk;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public String getStrsuppl1() {
		return strsuppl1;
	}

	public void setStrsuppl1(String strsuppl1) {
		this.strsuppl1 = strsuppl1;
	}

	public String getStrsuppl2() {
		return strsuppl2;
	}

	public void setStrsuppl2(String strsuppl2) {
		this.strsuppl2 = strsuppl2;
	}

	public String getNamev() {
		return namev;
	}

	public void setNamev(String namev) {
		this.namev = namev;
	}

	public String getMobnumber() {
		return mobnumber;
	}

	public void setMobnumber(String mobnumber) {
		this.mobnumber = mobnumber;
	}

	public String getJ1isern() {
		return j1isern;
	}

	public void setJ1isern(String j1isern) {
		this.j1isern = j1isern;
	}

	public String getStcd3() {
		return stcd3;
	}

	public void setStcd3(String stcd3) {
		this.stcd3 = stcd3;
	}

	public String getBanklz() {
		return banklz;
	}

	public void setBanklz(String banklz) {
		this.banklz = banklz;
	}

	public String getBankadd() {
		return bankadd;
	}

	public void setBankadd(String bankadd) {
		this.bankadd = bankadd;
	}

	public String getIfsccode() {
		return ifsccode;
	}

	public void setIfsccode(String ifsccode) {
		this.ifsccode = ifsccode;
	}

	public String getNatjob() {
		return natjob;
	}

	public void setNatjob(String natjob) {
		this.natjob = natjob;
	}

	public String getNatentity() {
		return natentity;
	}

	public void setNatentity(String natentity) {
		this.natentity = natentity;
	}

	public String getForiegnentity() {
		return foriegnentity;
	}

	public void setForiegnentity(String foriegnentity) {
		this.foriegnentity = foriegnentity;
	}

	public String getMsmed() {
		return msmed;
	}

	public void setMsmed(String msmed) {
		this.msmed = msmed;
	}

	public String getZwels() {
		return zwels;
	}

	public void setZwels(String zwels) {
		this.zwels = zwels;
	}

	public String getReprf() {
		return reprf;
	}

	public void setReprf(String reprf) {
		this.reprf = reprf;
	}

	public String getWitht() {
		return witht;
	}

	public void setWitht(String witht) {
		this.witht = witht;
	}

	public String getQland() {
		return qland;
	}

	public void setQland(String qland) {
		this.qland = qland;
	}

	public String getTelnumber() {
		return telnumber;
	}

	public void setTelnumber(String telnumber) {
		this.telnumber = telnumber;
	}

	public String getBahns() {
		return bahns;
	}

	public void setBahns(String bahns) {
		this.bahns = bahns;
	}

	public String getApprover1() {
		return approver1;
	}

	public void setApprover1(String approver1) {
		this.approver1 = approver1;
	}

	public String getApprover1status() {
		return approver1status;
	}

	public void setApprover1status(String approver1status) {
		this.approver1status = approver1status;
	}

	public String getApprover1comments() {
		return approver1comments;
	}

	public void setApprover1comments(String approver1comments) {
		this.approver1comments = approver1comments;
	}

	public String getApprover2() {
		return approver2;
	}

	public void setApprover2(String approver2) {
		this.approver2 = approver2;
	}

	public String getApprover2status() {
		return approver2status;
	}

	public void setApprover2status(String approver2status) {
		this.approver2status = approver2status;
	}

	public String getApprover2comments() {
		return approver2comments;
	}

	public void setApprover2comments(String approver2comments) {
		this.approver2comments = approver2comments;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getChangedby() {
		return changedby;
	}

	public void setChangedby(String changedby) {
		this.changedby = changedby;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Date getChangeddate() {
		return changeddate;
	}

	public void setChangeddate(Date changeddate) {
		this.changeddate = changeddate;
	}

	public String getEmailflag() {
		return emailflag;
	}

	public void setEmailflag(String emailflag) {
		this.emailflag = emailflag;
	}
	
	

	public String getBezei() {
		return bezei;
	}

	public void setBezei(String bezei) {
		this.bezei = bezei;
	}

	
}

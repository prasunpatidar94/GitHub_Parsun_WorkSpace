package com.iteanz.srl.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Proxy;


@Entity
@Proxy(lazy = false)
public class VPItem {
	@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "VPItem_seq", allocationSize = 1, name = "VPItem_s")
	@Column(name = "vpgenid")
	private Long vpitemid;
	
	private int srno;
	private String version;
	private String itemno;
	private String lifnr;
	private String itemdesc;
	private String prnumber;
	private String pritemno;
	private String requsitior;
	private long rfpno;
	private String rfpversion;
	private String matnr;
	public String maktx;
	private String menge;
	private String meins;
	private String werks;
	private String lgort;
	private String matmake;
	private String packsize;
	private String matmodel;
	private String manufby;
	private String matmakep;
	private String packsizep;
	private String manufbyp;
	private double valprice;
	private String value;
	private String waers;
	private double preis;
	private String deliverydate;
	private String plantdelivloc;
	private String plantdelivcode;
	private String warranty;
	private String buyback;
	private String localimport;
	private String country;
	private String storagecond;
	private String shippinginst;
	private String shipmentmode;
	private String batchno;
	private String clearance;
	private String catalogue;
	private String freight;
	private String kitinsert;
	private String expiry;
	private String delivlead;
	private String hsncode;
	private String process;
	private String opttestpm;
	private String thruput;
	private String shelfperiod;
	private String equip;
	private String contractperiod;
	private String rrequipreturn;
	private String equipdimension;
	private String accessories;
	private String preinstall;
	private double spareprice;
	private double baserate;
	private double discount;
	private String gst;
	private String anyother;
	private String landedrate;
	private String cpt;
	private String cmc;
	private String amc;
	private String storageinstr;
	private String paymentreturns;
	private String others;
	private String hazardous;
	private String brandname;
	private String oem;
	private String oemproduct;
	private String discvalidity;
	private String tempsensitive;
	private String gsm;
	private String bindleave;
	private String colorno;
	private String aftersales;
	private String delivpacksize;
	private String govtlicense;
	private String licensename;
	private String authority;
	private String createdby;
	private String changedby;
	private String changeddate;
	private String createddate;
	private double rrPrice;
	private long cp_fk;
	public Long getVpitemid() {
		return vpitemid;
	}
	public void setVpitemid(Long vpitemid) {
		this.vpitemid = vpitemid;
	}
	public int getSrno() {
		return srno;
	}
	public void setSrno(int srno) {
		this.srno = srno;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getItemno() {
		return itemno;
	}
	public void setItemno(String itemno) {
		this.itemno = itemno;
	}
	public String getLifnr() {
		return lifnr;
	}
	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}
	public String getItemdesc() {
		return itemdesc;
	}
	public void setItemdesc(String itemdesc) {
		this.itemdesc = itemdesc;
	}
	public String getPrnumber() {
		return prnumber;
	}
	public void setPrnumber(String prnumber) {
		this.prnumber = prnumber;
	}
	public String getPritemno() {
		return pritemno;
	}
	public void setPritemno(String pritemno) {
		this.pritemno = pritemno;
	}
	public String getRequsitior() {
		return requsitior;
	}
	public void setRequsitior(String requsitior) {
		this.requsitior = requsitior;
	}
	public long getRfpno() {
		return rfpno;
	}
	public void setRfpno(long rfpno) {
		this.rfpno = rfpno;
	}
	public String getRfpversion() {
		return rfpversion;
	}
	public void setRfpversion(String rfpversion) {
		this.rfpversion = rfpversion;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	public String getMenge() {
		return menge;
	}
	public void setMenge(String menge) {
		this.menge = menge;
	}
	public String getMeins() {
		return meins;
	}
	public void setMeins(String meins) {
		this.meins = meins;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getLgort() {
		return lgort;
	}
	public void setLgort(String lgort) {
		this.lgort = lgort;
	}
	public String getMatmake() {
		return matmake;
	}
	public void setMatmake(String matmake) {
		this.matmake = matmake;
	}
	public String getPacksize() {
		return packsize;
	}
	public void setPacksize(String packsize) {
		this.packsize = packsize;
	}
	public String getMatmodel() {
		return matmodel;
	}
	public void setMatmodel(String matmodel) {
		this.matmodel = matmodel;
	}
	public String getManufby() {
		return manufby;
	}
	public void setManufby(String manufby) {
		this.manufby = manufby;
	}
	public String getMatmakep() {
		return matmakep;
	}
	public void setMatmakep(String matmakep) {
		this.matmakep = matmakep;
	}
	public String getPacksizep() {
		return packsizep;
	}
	public void setPacksizep(String packsizep) {
		this.packsizep = packsizep;
	}
	public String getManufbyp() {
		return manufbyp;
	}
	public void setManufbyp(String manufbyp) {
		this.manufbyp = manufbyp;
	}
	public double getValprice() {
		return valprice;
	}
	public void setValprice(double valprice) {
		this.valprice = valprice;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getWaers() {
		return waers;
	}
	public void setWaers(String waers) {
		this.waers = waers;
	}
	public double getPreis() {
		return preis;
	}
	public void setPreis(double preis) {
		this.preis = preis;
	}
	public String getDeliverydate() {
		return deliverydate;
	}
	public void setDeliverydate(String deliverydate) {
		this.deliverydate = deliverydate;
	}
	public String getPlantdelivloc() {
		return plantdelivloc;
	}
	public void setPlantdelivloc(String plantdelivloc) {
		this.plantdelivloc = plantdelivloc;
	}
	public String getPlantdelivcode() {
		return plantdelivcode;
	}
	public void setPlantdelivcode(String plantdelivcode) {
		this.plantdelivcode = plantdelivcode;
	}
	public String getWarranty() {
		return warranty;
	}
	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}
	public String getBuyback() {
		return buyback;
	}
	public void setBuyback(String buyback) {
		this.buyback = buyback;
	}
	public String getLocalimport() {
		return localimport;
	}
	public void setLocalimport(String localimport) {
		this.localimport = localimport;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStoragecond() {
		return storagecond;
	}
	public void setStoragecond(String storagecond) {
		this.storagecond = storagecond;
	}
	public String getShippinginst() {
		return shippinginst;
	}
	public void setShippinginst(String shippinginst) {
		this.shippinginst = shippinginst;
	}
	public String getShipmentmode() {
		return shipmentmode;
	}
	public void setShipmentmode(String shipmentmode) {
		this.shipmentmode = shipmentmode;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public String getClearance() {
		return clearance;
	}
	public void setClearance(String clearance) {
		this.clearance = clearance;
	}
	public String getCatalogue() {
		return catalogue;
	}
	public void setCatalogue(String catalogue) {
		this.catalogue = catalogue;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getKitinsert() {
		return kitinsert;
	}
	public void setKitinsert(String kitinsert) {
		this.kitinsert = kitinsert;
	}
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	public String getDelivlead() {
		return delivlead;
	}
	public void setDelivlead(String delivlead) {
		this.delivlead = delivlead;
	}
	public String getHsncode() {
		return hsncode;
	}
	public void setHsncode(String hsncode) {
		this.hsncode = hsncode;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getOpttestpm() {
		return opttestpm;
	}
	public void setOpttestpm(String opttestpm) {
		this.opttestpm = opttestpm;
	}
	public String getThruput() {
		return thruput;
	}
	public void setThruput(String thruput) {
		this.thruput = thruput;
	}
	public String getShelfperiod() {
		return shelfperiod;
	}
	public void setShelfperiod(String shelfperiod) {
		this.shelfperiod = shelfperiod;
	}
	public String getEquip() {
		return equip;
	}
	public void setEquip(String equip) {
		this.equip = equip;
	}
	public String getContractperiod() {
		return contractperiod;
	}
	public void setContractperiod(String contractperiod) {
		this.contractperiod = contractperiod;
	}
	public String getRrequipreturn() {
		return rrequipreturn;
	}
	public void setRrequipreturn(String rrequipreturn) {
		this.rrequipreturn = rrequipreturn;
	}
	public String getEquipdimension() {
		return equipdimension;
	}
	public void setEquipdimension(String equipdimension) {
		this.equipdimension = equipdimension;
	}
	public String getAccessories() {
		return accessories;
	}
	public void setAccessories(String accessories) {
		this.accessories = accessories;
	}
	public String getPreinstall() {
		return preinstall;
	}
	public void setPreinstall(String preinstall) {
		this.preinstall = preinstall;
	}
	public double getSpareprice() {
		return spareprice;
	}
	public void setSpareprice(double spareprice) {
		this.spareprice = spareprice;
	}
	public double getBaserate() {
		return baserate;
	}
	public void setBaserate(double baserate) {
		this.baserate = baserate;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public String getAnyother() {
		return anyother;
	}
	public void setAnyother(String anyother) {
		this.anyother = anyother;
	}
	public String getLandedrate() {
		return landedrate;
	}
	public void setLandedrate(String landedrate) {
		this.landedrate = landedrate;
	}
	public String getCpt() {
		return cpt;
	}
	public void setCpt(String cpt) {
		this.cpt = cpt;
	}
	public String getCmc() {
		return cmc;
	}
	public void setCmc(String cmc) {
		this.cmc = cmc;
	}
	public String getAmc() {
		return amc;
	}
	public void setAmc(String amc) {
		this.amc = amc;
	}
	public String getStorageinstr() {
		return storageinstr;
	}
	public void setStorageinstr(String storageinstr) {
		this.storageinstr = storageinstr;
	}
	public String getPaymentreturns() {
		return paymentreturns;
	}
	public void setPaymentreturns(String paymentreturns) {
		this.paymentreturns = paymentreturns;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	public String getHazardous() {
		return hazardous;
	}
	public void setHazardous(String hazardous) {
		this.hazardous = hazardous;
	}
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	public String getOem() {
		return oem;
	}
	public void setOem(String oem) {
		this.oem = oem;
	}
	public String getOemproduct() {
		return oemproduct;
	}
	public void setOemproduct(String oemproduct) {
		this.oemproduct = oemproduct;
	}
	public String getDiscvalidity() {
		return discvalidity;
	}
	public void setDiscvalidity(String discvalidity) {
		this.discvalidity = discvalidity;
	}
	public String getTempsensitive() {
		return tempsensitive;
	}
	public void setTempsensitive(String tempsensitive) {
		this.tempsensitive = tempsensitive;
	}
	public String getGsm() {
		return gsm;
	}
	public void setGsm(String gsm) {
		this.gsm = gsm;
	}
	public String getBindleave() {
		return bindleave;
	}
	public void setBindleave(String bindleave) {
		this.bindleave = bindleave;
	}
	public String getColorno() {
		return colorno;
	}
	public void setColorno(String colorno) {
		this.colorno = colorno;
	}
	public String getAftersales() {
		return aftersales;
	}
	public void setAftersales(String aftersales) {
		this.aftersales = aftersales;
	}
	public String getDelivpacksize() {
		return delivpacksize;
	}
	public void setDelivpacksize(String delivpacksize) {
		this.delivpacksize = delivpacksize;
	}
	public String getGovtlicense() {
		return govtlicense;
	}
	public void setGovtlicense(String govtlicense) {
		this.govtlicense = govtlicense;
	}
	public String getLicensename() {
		return licensename;
	}
	public void setLicensename(String licensename) {
		this.licensename = licensename;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
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
	public String getChangeddate() {
		return changeddate;
	}
	public void setChangeddate(String changeddate) {
		this.changeddate = changeddate;
	}
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	public double getRrPrice() {
		return rrPrice;
	}
	public void setRrPrice(double rrPrice) {
		this.rrPrice = rrPrice;
	}
	public long getCp_fk() {
		return cp_fk;
	}
	public void setCp_fk(long cp_fk) {
		this.cp_fk = cp_fk;
	}
	
	
	
}

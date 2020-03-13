package com.pp.iteanz.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pp.iteanz.XmlParsingStandalone1Application;
import com.pp.iteanz.entity.UserMaster;
import com.pp.iteanz.entity.VendorRegistration;
import com.pp.iteanz.repository.DepartmentRepo;
import com.pp.iteanz.repository.StatusRepo;
import com.pp.iteanz.repository.UserMasterRepo;
import com.pp.iteanz.repository.VendorRegistrationRepository;
import com.pp.iteanz.utility.PasswordGenrator;

@Component("VendorRegistrationRead")
public class VendorRegistrationReader {
	
	private VendorRegistration vendorReg;
	private String filePath;
	@Autowired
	private UserMasterRepo userMasterRepo;
	@Autowired
	private StatusRepo statusRepo;
	@Autowired
	private DepartmentRepo departmentRepo;
	
	private VendorRegistration vendorRegistration;
	
	private UserMaster userMaster1;
	
	private UserMaster userMaster ;
	
	private EmailSendingService emailSendingService = new EmailSendingService();


	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(XmlParsingStandalone1Application.class);


	@Autowired
	VendorRegistrationRepository vendorRegistrationRepository;

	public String VendorRegistrationRead(Document doc, String filePath) {

		this.filePath = filePath;

		NodeList nodeList = doc.getElementsByTagName("VMS_VendorMaster");
		System.out.println("child name " + nodeList);
		// now XML is loaded as Document in memory, lets convert it to Object List
		List<Object> prList = new ArrayList<Object>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			prList.add(getVendorRegistration(nodeList.item(i)));
		}
		return "1000   ::    sucess";
	}
	private VendorRegistration getVendorRegistration(Node node) {
		// XMLReaderDOM domReader = new XMLReaderDOM();
		vendorReg = new VendorRegistration();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			
			PasswordGenrator passwordGenrator = new PasswordGenrator();
			
			
			VendorRegistration vendorRegistrationCheck =vendorRegistrationRepository.findByLifnr(Long.parseLong(getTagValue("VendorCode", element)));

			try {
				
			
			
			if (vendorRegistrationCheck != null) {
				vendorReg = vendorRegistrationCheck;
				
				
			}else {
				
				vendorReg = new  VendorRegistration();
				vendorReg.setLifnr(Long.parseLong(getTagValue("VendorCode", element)));
				
			}
			
			
			vendorReg.setName1(getTagValue("VendorName", element));
			vendorReg.setLand1(getTagValue("Country", element));
			vendorReg.setBukrs(getTagValue("CompanyCode", element));
			vendorReg.setKtokk(getTagValue("AccountGroup", element));
			vendorReg.setEkorg(getTagValue("PurchaseOrg", element));
			vendorReg.setRegio(getTagValue("Region", element));
			vendorReg.setBezei(getTagValue("RegionName", element));
			vendorReg.setOrt01(getTagValue("City", element));
//			vendorReg.setEmailid((getTagValue("MailId", element)).toLowerCase());
			vendorReg.setEmailid("vineet12345srl@gmail.com");
//			vendorReg.setEmailid("sunpatidar94@gmail.com");

			vendorReg.setMobnumber(getTagValue("ContactNo", element));
			vendorReg.setBlockIndicator(getTagValue("BlockIndicator", element));
//			getTagValue("RegionName", element));
			
			vendorRegistration=vendorRegistrationRepository.save(vendorReg);

			
			
			
			//String input = "{\"ADRID\":\"0\",\"BEGDA\":\"0\",\"ENDDA\":\"0\",\"SUNAME\":\"0\",\"ENAME\":\"0\",\"EMAIL\":\"0\",\"MOBILE\":\"0\",\"BPTYPE\":\"0\",\"CRNAM\":\"0\",\"CRDAT\":\"0\",\"CRTIM\":\"0\",\"CDNAM\":\"0\",\"CDDAT\":\"0\",\"CDTIM\":\"0\",\"ROLE\":\"0\",\"ALEVL\":\"0\",\"DEGFLG\":\"0\",\"ADFLG\":\"0\",\"BPVALE\":\"0\",\"DEPT\":\"0\",\"ID\":\"0\",\"SAPID\":\"0\",\"PSWRD\":\"0\"}";
			
			
		
			
		if (vendorRegistration != null) {
			
			System.out.println("DATA INSERTED SUCCESSFULLY :: 1000  ::      "+vendorRegistration.toString());
			//return vendorReg;
			
		} else {
			
			System.out.println("DATA NOT INSERTED   :: 1001  ");			
		}	
		
		
		long  id= Long.parseLong(getTagValue("VendorCode", element));
		//================================
		 userMaster1 = userMasterRepo.findByAdrid(String.valueOf(id));
		 
		if (userMaster1 != null) {
			System.out.println("zdghkljcxxfh");
			userMaster=userMaster1;
			
		}else {
			System.out.println("dfklkhfxgj");
			// userMaster =userMaster1.get();
			userMaster =new UserMaster();
			userMaster.setAdrid(""+vendorRegistration.getLifnr());	
			userMaster.setPswrd(passwordGenrator.passGenretor());
		}
		
		userMaster.setEmail(vendorRegistration.getEmailid());
		userMaster.setPnam(vendorRegistration.getName1());
		userMaster.setUserDesignation("Vendor");
		int a= 1;
//		Status status = statusRepo.findOne(a);
		userMaster.setInactive(0);
	//	userMaster.setRegio(""+vendorRegistration.getRegio());
//		userMaster.setStatus(statusRepo.findById(6).get());
		userMaster.setPmble(vendorRegistration.getMobnumber());
		userMaster.setAdflag("I");
		userMaster.setBptype("V");
	
		

		long date2 = Calendar.getInstance().getTimeInMillis();
//		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-mm-dd");
//		String strDate2 = dateFormat2.format(date2);
		java.sql.Date date=new java.sql.Date(date2);

		userMaster.setCddat(date);
		
		UserMaster userMaster2 = userMasterRepo.save(userMaster);
		if (userMaster2 !=null) {
			System.out.println("USER CREATED");
			
			String subject = "Welcome To SRL VMS System";
			
			
			
			
			StringBuffer stringBuffer = new StringBuffer();
			
			
			stringBuffer.append("<html>");	
			stringBuffer.append("<body>");
			stringBuffer.append("<per>Dear Vendor "
					+ "<br>"
					+ "<br>"
					+ "Your account is registreted successfully in VMS system "
					+ "<br>"
					+ "please find below mentioned User ID  and Password to Login in VMS System. "
					+ "<br><br>"
					+ "User Id ::      "+userMaster2.getAdrid()+"<br>"
					+ "Password ::     "+userMaster2.getPswrd()+"<br><br>"
					
					+ "<a href=\" http://192.168.0.56:8080/ZSRL_FIORI/\">Login</a> </per>");
				
			stringBuffer.append("</body>");		
			stringBuffer.append("</html>");	
			
			
//			<a href=" http://192.168.0.56:8080/ZSRL_FIORI/">Login</a>
			
			String body = stringBuffer.toString();
			
			
			
			Boolean emailcon = emailSendingService.emailSend((userMaster2.getEmail()), subject, body, "");
			
			
			if (emailcon) {
				System.out.println("Email is sent to vendor "+userMaster2.getAdrid());
			}else {
				System.out.println("Email is  not sent to vendor "+userMaster2.getAdrid());
			}
			
		}else {
			System.out.println("USER NOT CREATED");

			
		}
		
		
		
		
		
		
		
//		==================================================
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		}

		System.out.println(vendorReg.toString());
		return vendorReg;
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

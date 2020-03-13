package com.iteanz.srl.adintegration;

import java.io.File;
import java.net.InetAddress;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
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
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iteanz.srl.domain.ApprovalLog;
import com.iteanz.srl.domain.SessionHistory;
import com.iteanz.srl.domain.SessionsTable;
import com.iteanz.srl.domain.Status;
import com.iteanz.srl.domain.UserMaster;
import com.iteanz.srl.repositories.WebServiceRepository;
import com.iteanz.srl.service.WebService;
@RestController
public class ADIntegration {
	 private static Logger logger  = Logger.getLogger(ADIntegration.class);
	@Autowired
	WebService webService;
	@Autowired
	WebServiceRepository sampleRepo;

	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("ADRID") String adrid, @RequestParam("PSWRD") String pswrd) {
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
			if (pswrd.isEmpty() || pswrd == "") {
				js.put("code", "1002");
				js.put("error", "PSWRD Is Empty");
				json = js.toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long id = new Date().getTime();
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		Time currentTime = new Time(utilDate.getTime());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 3);
		java.util.Date dt = cal.getTime();
		java.sql.Date endDate = new java.sql.Date(dt.getTime());
		SessionHistory sessionHistoryObject = null;
		try {
			String adflag = "A";
			UserMaster userMaster = null;
	        try {
	        	
	        	// First level authentication from Local database without Password
	        	userMaster = webService.getADCheck(adrid,adflag);
	        	js.put("userMaster", ow.writeValueAsString(userMaster));
	        	if(userMaster != null)
	        	{
	        		//Second level authentication from AD Server with AD Password
	        		// AD Authentication check of the AD Integrated Login User
	        		// service user
	        		// String serviceUserDN = "cn=Mister Service,ou=Users,dc=example,dc=com";
	        		String serviceUserDN ="CN=vms, OU=Integration, DC=srlnt, DC=com";
	        		// String serviceUserDN ="sdigital@cairnenergy.com";
	        		String serviceUserPassword = "Temp#2019";
	        		// user to authenticate
	        		String identifyingAttribute = "sAMAccountName";
	        		String identifier = adrid;   //"70000619";
	        		String password = pswrd;     //"Welcome@1234";
	        		// String base = "ou=CairnIndia,dc=cairnenergy,dc=com";
	        		String base ="DC=srlnt,DC=com";
	        		// LDAP connection info
	        		/*String ldap = "localhost";
	     		    int port = 10389;
	     		    String ldapUrl = "ldap://" + ldap + ":" + port;*/
	        		String ldapUrl="ldap://srlnt.com:389";
	        		// first create the service context
	        		DirContext serviceCtx = null;
	        		try {
	        			// use the service user to authenticate
	        			Properties serviceEnv = new Properties();
	        			serviceEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	        			serviceEnv.put(Context.PROVIDER_URL, ldapUrl);
	        			serviceEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
	        			serviceEnv.put(Context.SECURITY_PRINCIPAL, serviceUserDN);
	        			serviceEnv.put(Context.SECURITY_CREDENTIALS, serviceUserPassword);
	        			serviceCtx = new InitialDirContext(serviceEnv);

	        			// we don't need all attributes, just let it get the identifying one
	        			String[] attributeFilter = { identifyingAttribute };
	        			SearchControls sc = new SearchControls();
	        			sc.setReturningAttributes(attributeFilter);
	        			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

	        			// use a search filter to find only the user we want to authenticate
	        			String searchFilter = "(" + identifyingAttribute + "=" + identifier + ")";
	        			NamingEnumeration<SearchResult> results = serviceCtx.search(base, searchFilter, sc);
	        			System.out.println(results);
	        			System.out.println(serviceCtx);
	        			if (results.hasMore()) {
	        				// get the users DN (distinguishedName) from the result
	        				SearchResult result = results.next();
	        				String distinguishedName = result.getNameInNamespace();

	        				// attempt another authentication, now with the user
	        				Properties authEnv = new Properties();
	        				authEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	        				authEnv.put(Context.PROVIDER_URL, ldapUrl);
	        				authEnv.put(Context.SECURITY_PRINCIPAL, distinguishedName);
	        				authEnv.put(Context.SECURITY_CREDENTIALS, password);

	        				try
	        				{
	        					new InitialDirContext(authEnv);
	        					System.out.println("Authentication successful");
	        					SessionHistory sessionHistory = new SessionHistory();
	        					sessionHistory.setAdrid(adrid);
	        					sessionHistory.setLogindt(today);
	        					sessionHistory.setLogintm(currentTime);
	        					sessionHistoryObject = webService.saveSessionHistory(sessionHistory);
	        					System.out.println("**");
	        					SessionsTable sessionTable = new SessionsTable();
	        					sessionTable.setAdrid(adrid);
	        					sessionTable.setCrdat(today);
	        					sessionTable.setCrtim(currentTime);
	        					sessionTable.setSesid(id);
	        					SessionsTable sessionTableObject = webService.saveSessionTable(sessionTable);
	        					js.put("success", "AD Authenticated User");
	        					js.put("SessionId", id);
	        					List<UserMaster> usersList = webService.getAllUsers();
	        					js.put("userMaster", ow.writeValueAsString(usersList));
	        					js.put("code", "1000");
	        					json = js.toString();
	        					System.out.println("Success AD");
	        					return new ResponseEntity<String>(json, HttpStatus.OK);
	        					
	        				}
	        				catch (Exception e)
	        				{
	        					System.out.println("Authentication failed");
	        					try {
	        						js.put("code", "1001");
	        						js.put("error", "Invalid AD Credential");
	        						json = js.toString();
	        						return new ResponseEntity<String>(json, HttpStatus.OK);
	        					} catch (Exception er) {
	        						er.printStackTrace();
	        					} 		    
	        				}
	        			}
	        		} catch (Exception e) {
	        			e.printStackTrace();
	        		} 

	        		finally {
	        			if (serviceCtx != null) {
	        				try {
	        					serviceCtx.close();
	        				} catch (NamingException e) {
	        					e.printStackTrace();
	        				}
	        			}
	        		}        		
	        		json = js.toString();
	        		return new ResponseEntity<String>(json, HttpStatus.OK);
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        //Authentication with Local database for without AD Integrated users
	        UserMaster userMasterObject = null;
	        if(userMaster == null)
	        {
	        	userMasterObject = webService.getUserAuthenitcation(adrid,pswrd);
	        	if(userMasterObject == null)
	        	{
	        		System.out.println("1");
	        		js.put("error", "Invalid User");
	    			js.put("code", "1001");
	    			json = js.toString();
	    			return new ResponseEntity<String>(json, HttpStatus.OK);
	        	}
	        	if(userMaster == null) 
	        	{
	        		System.out.println("2");
	        		try {
	        			SessionHistory sessionHistory = new SessionHistory();
						sessionHistory.setAdrid(adrid);
						sessionHistory.setLogindt(today);
						sessionHistory.setLogintm(currentTime);
						sessionHistoryObject = webService.saveSessionHistory(sessionHistory);
						System.out.println("**");
						SessionsTable sessionTable = new SessionsTable();
						sessionTable.setAdrid(adrid);
						sessionTable.setCrdat(today);
						sessionTable.setCrtim(currentTime);
						sessionTable.setSesid(id);
						SessionsTable sessionTableObject = webService.saveSessionTable(sessionTable);
						//List<UserMaster> usersList = webService.getAllUsers();
						
						UserMaster usersList = webService.getUserDetails(adrid);
						
						js.put("userMaster", ow.writeValueAsString(usersList));
		        		js.put("success", "Authenticated User");
		        		js.put("SessionId", id);
		    			js.put("code", "1000");
		    			json = js.toString();
					} catch (Exception e) {
						e.printStackTrace();
					}
	        		
	    			logger.debug("Login Success");
					
	    			return new ResponseEntity<String>(json, HttpStatus.OK);
	        	}
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

	

	@RequestMapping(value = "/ADSearch", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> ADSearch(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "ADRID") String adrid) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		System.out.println("In Document Search");
		JSONObject js = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			ADIntegration ldapExaminer = new ADIntegration();
			// NOTE: replace theUserName below with the Active Directory/LDAP user whose
			// attribites you want printed.
			String adDetails = ldapExaminer.printUserBasicAttributes(adrid, ldapExaminer.getLdapContext(), js);

			js.put("adDetails", adDetails);
			js.put("AD", "Success");
			js.put("code", "1000");
			json = js.toString();
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

	public LdapContext getLdapContext() {
		LdapContext ctx = null;
		try {
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.SECURITY_AUTHENTICATION, "Simple");
			// NOTE: replace user@domain.com with a User that is present in your Active
			// Directory/LDAP
			// env.put(Context.SECURITY_PRINCIPAL,
			// "<strong>sdigital@cairnindia.com</strong>");
			// NOTE: replace userpass with passwd of this user.
			env.put(Context.SECURITY_CREDENTIALS, "Temp#2019");
			// NOTE: replace ADorLDAPHost with your Active Directory/LDAP Hostname or IP.
			env.put(Context.SECURITY_PRINCIPAL,
					"CN=vms, OU=Integration, DC=srlnt, DC=com");
			env.put(Context.PROVIDER_URL, "ldap://srlnt.com:389");
			System.out.println("Attempting to Connect...");
			ctx = new InitialLdapContext(env, null);
			System.out.println("Connection Successful.");
		} catch (NamingException nex) {
			System.out.println("LDAP Connection: FAILED");
			nex.printStackTrace();
		}
		return ctx;
	}

	private String printUserBasicAttributes(String username, LdapContext ctx, JSONObject js) {
		try {
			System.out.println("Print users details");
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			// NOTE: The attributes mentioned in array below are the ones that will be
			// retrieved, you can add more.
			String[] attrIDs = { "distinguishedName", "sn", "cn", "mail", "mobile", "canonicalName",
					"userAccountControl", "accountExpires" ,"title"};
			constraints.setReturningAttributes(attrIDs);
			// NOTE: replace DC=domain,DC=com below with your domain info. It is essentially
			// the Base Node for Search.
			NamingEnumeration answer = ctx.search("DC=srlnt,DC=com", "sAMAccountName=" + username, constraints);
			if (answer.hasMore()) {
				Attributes attrs = ((SearchResult) answer.next()).getAttributes();
				System.out.println(attrs.get("distinguishedName"));
				System.out.println(attrs.get("cn"));
				System.out.println(attrs.get("sn"));
				System.out.println(attrs.get("mail"));
				System.out.println(attrs.get("mobile"));
				System.out.println(attrs.get("canonicalName"));
				System.out.println(attrs.get("userAccountControl"));
				System.out.println(attrs.get("accountExpires"));
				System.out.println(attrs.get("title"));

				js.put("distinguishedName", "" + attrs.get("distinguishedName"));
				js.put("displayName", "" + attrs.get("cn"));
				js.put("sn", "" + attrs.get("sn"));
				js.put("mail", "" + attrs.get("mail"));
				js.put("telephoneNumber", "" + attrs.get("mobile"));
				js.put("canonicalName", "" + attrs.get("canonicalName"));
				js.put("userAccountControl", "" + attrs.get("userAccountControl"));
				js.put("accountExpires", "" + attrs.get("accountExpires"));
				js.put("title", attrs.get("title"));
				js.put("code", "1000");
			} else {
				throw new Exception("InvalidUser");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return js.toString();
	}

	@RequestMapping(value = "/sendMail", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> senMail(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("MAILID") String mailId) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject js = new JSONObject();
		String json = null;
		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "webmail.srl.in");
			props.put("mail.smtp.port", "25");
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
				msg.setRecipients(Message.RecipientType.TO, "sumit.sourav@cairnindia.com");
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
						+ "<br><b>Sign Type : Organizational</b> " + "<br><b>Document Type : </b>+transationType"
						+ "<br><b>Document Number : </b>+docnr" + "<br><b>Document Name : </b>+fileName"
						+ "<br><b>Purpose : Digital Signature</b>" + "<br>"
						+ "<br><b>You are requested to review the attached document and proceed on Approval process for Digital Signature on document.</b>"
						+ "<br>"
						+ "<br><a href='mailLink'>Approve :</a></b>  The document will be approved and sent to next level approvers if any further level. If this final level, document will be Digitally Signed and notified to concern person."
						+ "<br>"
						+ "<br><a href='mailLink'>Reject :</a></b>  Please note, this will reject the document and send it back to the creator. Approvals which have already happened will be reset. Post correction the document will be resent to all approvers."
						+ "</font></body></html>";
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

	
	
	private String sendUnAutheriedApproverMail(String requestId, String email) {

		System.out.println("not Autenticated Approver");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host","webmail.srl.in");//10.1.4.100
		properties.put("mail.smtp.port", "587");
		javax.mail.Session session = javax.mail.Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("srl-intelligence@srl.in", "VMS");//70000619@cairnindia.com //Welcome@123
			}
		});
		String subjectLine="Digital Signature Approve Req.ID:"+requestId;
		try {
			String	MsgText = "<html><body><font color=black>Hello,</b>"
		    		+"<br>"
		    		+"<br>Digital Signature request has been rejected(Invalid Approver)."
		    		+"<br>"
		    		+"</body></html>";
			
			try {
				Message replyMessage = new MimeMessage(session);
				replyMessage.setSubject(subjectLine);
				replyMessage.setFrom(new InternetAddress("srl-intelligence@srl.in", "VMS"));
				replyMessage.setContent(MsgText, "text/html");
				replyMessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email, false));
									
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
						System.out.println(" Reject Email Send successfully ....");
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

	
}

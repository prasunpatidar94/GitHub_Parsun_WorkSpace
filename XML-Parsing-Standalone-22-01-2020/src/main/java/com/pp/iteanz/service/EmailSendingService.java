package com.pp.iteanz.service;

import java.util.Date;
import java.util.Properties;

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

public class EmailSendingService {

	private final static String senderMailId = "srl-intelligence@srl.in";
	private final static String senderMailPassword = "Inteli#007";

	public Boolean contractMailNotification(String to, String subject, String body, String ccEmail) {

		try {

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
					return new PasswordAuthentication(senderMailId, senderMailPassword);
				}
			});

			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(senderMailId));

				msg.setRecipients(Message.RecipientType.TO, to);// anuj.surana@ssll.in
//				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
				if (ccEmail == null || ccEmail.isEmpty() || ccEmail == "") {
					System.out.println("ccemail is not there ");
				} else {
					msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail));

				}

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
	
	
	
	public Boolean grnMailNotification(String to, String subject, String body, String ccEmail) {

		try {

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
					return new PasswordAuthentication(senderMailId, senderMailPassword);
				}
			});

			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(senderMailId));

//				msg.setRecipients(Message.RecipientType.TO, to);// anuj.surana@ssll.in
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
				if (ccEmail == null || ccEmail.isEmpty() || ccEmail == "") {
					System.out.println("ccemail is not there ");
				} else {
					msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail));

				}

				msg.setSubject(subject);
				System.out.println("****");
				// msg.setSubject("Vendor Management System Req.ID:10020 [ Admin ]For
				// Approval");
				msg.setSentDate(new Date());
				Multipart multipart = new MimeMultipart();

				MimeBodyPart htmlPart = new MimeBodyPart();

				String htmlContent = "<html><body><font color=black>Hello,</b>" + "<br>" + "<br>" + body + "<br>"
																							
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

//	public static void main(String[] args) {
//		EmailSendingService emailSendingService = new EmailSendingService();
//		boolean result = emailSendingService.contractMailNotification("sunpatidar94@gmail.com", "sirf tere liye",
//				"chal rahi he baba", "prasunpatidar94@gmail.com");
//		System.out.println(result);
//	}

	
public Boolean emailSend(String to ,String subject ,String body ,String ccEmail) {
		
		
		
		try {


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
					return new PasswordAuthentication("srl-intelligence@srl.in", "Inteli#007");
				}
			});

			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("srl-intelligence@srl.in"));
				msg.setRecipients(Message.RecipientType.TO, to);// anuj.surana@ssll.in
				if (ccEmail==null || ccEmail.isEmpty() || ccEmail == "") {
					System.out.println("ccemail is not there ");
				}else {
					msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail));

				}
			

				msg.setSubject(subject);
				System.out.println("****");
				// msg.setSubject("Vendor Management System Req.ID:10020 [ Admin ]For
				// Approval");
				msg.setSentDate(new Date());
				Multipart multipart = new MimeMultipart();

				MimeBodyPart htmlPart = new MimeBodyPart();

//				String htmlContent = "<html><body><font color=black>Hello,</b>" + "<br>" + "<br>" + body + "<br>"
//						+ "<br><a href='localhost:8080/mailNotification'>Login :</a> </b>" // change the ui according to
//																							// the data \r\n" +
//
//						+ "<br>+fileName has been sent for your approval, created by +uploader +senderMailId" + "<br>"
////						+ "<br><b>Purpose : Vendor MManagement System</b>" + "<br>"
////						+ "<br><b>You are requested to review the attached document and proceed on Approval process for Vendor Management System.</b>"
////						+ "<br>"
//						+ "<br><a href='mailLink'>Login :</a></b>"
////						+ "The document will be approved and sent to next level approvers if any further level. If this final level, mail will be notified to concern person."
////						+ "<br>"
////						+ "<br><a href='mailLink'>Reject :</a></b>  Please note, this will reject the document and send it back to the creator. Approvals which have already happened will be reset. "
//						+ "</font></body></html>";
				htmlPart.setContent(body, "text/html");
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

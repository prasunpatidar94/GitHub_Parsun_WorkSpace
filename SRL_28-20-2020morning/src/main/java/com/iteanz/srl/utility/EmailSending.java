package com.iteanz.srl.utility;

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

public class EmailSending {
	
	public Boolean emailSendService(String to ,String subject ,String body ,String ccEmail ) {
		
		
		
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
					msg.setRecipients(Message.RecipientType.TO, to);
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

					String htmlContent = "<html><body><br>" + body + "<br></body></html>";
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

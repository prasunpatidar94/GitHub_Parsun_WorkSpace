package com.pp.iteanz.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pp.iteanz.ReminderMailExeApplication;
import com.pp.iteanz.entity.Contract;
import com.pp.iteanz.entity.Invite;
import com.pp.iteanz.entity.SecondParty;
import com.pp.iteanz.entity.UserMaster;
import com.pp.iteanz.repository.ContractRepository;
import com.pp.iteanz.repository.UserMasterRepo;

@Component("contractMailNotificationSend")

public class ContractMailNotificationSender {

	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReminderMailExeApplication.class);
	@Autowired
	private ContractRepository contractRepository;
	@Autowired
	private UserMasterRepo userMasterRepo;
	
	boolean check = true ;

	// contract expire 90 days reminder main notification
	public Boolean ContractReminderMail() {
		EmailSendingService emailSendingService = new EmailSendingService();
		Boolean dateCompareReturn = false;
		String endDate = "";
		String to = "";
		String ccEmail = "";
		UserMaster userMaster;
		Integer expireReminder = 0;
		String inviteMail = "";
		Boolean EmailConfriguration = false;
		List<Contract> contractAllList = contractRepository.findAll();
		if (contractAllList.isEmpty()) {
			log.info("1001  :  Contract List Is Empty");
			System.out.println("1001  :  Contract List Is Empty");
			return false;

		} else {
			for (Contract oneContract : contractAllList) {
				if (oneContract != null) {

					endDate = oneContract.getContractExpiryDate();
					expireReminder = Integer.parseInt(oneContract.getExpiryReminderdays().trim());

					dateCompareReturn = dateCompare(endDate, expireReminder);
					if (dateCompareReturn) {

						List<Invite> invites = oneContract.getInvites();
						try {
							userMaster = userMasterRepo.findBySapId(oneContract.getCreatedBy());

							if (userMaster != null) {
								ccEmail = userMaster.getEmail().trim().toLowerCase();

							} else {
								System.out.println("****************ccEmail in not found ******************");
							}
						} catch (Exception e) {

							System.out.println("***************some problem to fetch created bY********************");
							System.out.println(e.getMessage());

						}
						if (invites.isEmpty()) {
							System.out.println("*************** invites List is Empty********************");

						} else {
							for (Invite invitesOne : invites) {

								inviteMail = inviteMail + "," + (invitesOne.getEmail().trim().toLowerCase());

							}

//							SecondParty secondParty = oneContract.getSecondParty();
//							if (secondParty == null) {
//								log.info("1001  :  secondParty  Is Not Found");
//								System.out.println("1001  :  secondParty  Is Not Found");
//							} else {
//								to = secondParty.getEmail();
							//
//							}

							to = inviteMail;

							String subject = "Contract period of the Contract no. " + oneContract.getContractId()
									+ " dated " + oneContract.getContractStartDate() + " will get expired on "
									+ oneContract.getContractExpiryDate() + " .";
							String body = "Contract period of the Contract no. " + oneContract.getContractId()
									+ " dated " + oneContract.getContractStartDate() + " will get expired on "
									+ oneContract.getContractExpiryDate() + " ."
									+ "Kindly verify and take the necessary action.";

							EmailConfriguration = emailSendingService.contractMailNotification(to, subject, body,
									ccEmail);
							if (EmailConfriguration) {
								log.info("1000  :  Email  Is Sent");
								System.out.println("1000  :  Email  Is Sent");

							} else {
								log.info("1001  :  Email  Is Not Sent");
								System.out.println("1001  :  Email  Is Not Sent");

							}
						}

					} else {
						check = false;

					}

				} else {
					log.info("1001  :  One Contract  Is Empty");
					System.out.println("1001  : One  Contract  Is Empty");
					return false;
				}

			}
			return check;
		}

	}

	// contract expire main notification
	public Boolean ContractExpireReminderMail() {
		EmailSendingService emailSendingService = new EmailSendingService();

		List<Contract> contractAllList = contractRepository.findAll();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String endDate = "";
		Date expDateIn = null;
		String ccEmail = "";

		String inviteMail = "";

		Date currentDate = null;
		String to = "", sub = "", body = "";
		Boolean EmailConfriguration = false;
		if (contractAllList.isEmpty()) {
			log.info("1001  :  Contract List Is Empty");
			System.out.println("1001  :  Contract List Is Empty");
			return false;

		} else {
			for (Contract oneContract : contractAllList) {

				if (oneContract != null) {

					endDate = oneContract.getContractExpiryDate();
					try {
						expDateIn = sdf.parse(endDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					currentDate = Calendar.getInstance().getTime();
					if (currentDate.after(expDateIn) || currentDate.equals(expDateIn)) {
						System.out.println("currentDate is after expDateIn");// send

						List<Invite> invites = oneContract.getInvites();
						try {
							SecondParty secondParty = oneContract.getSecondParty();

							if (secondParty != null) {
								ccEmail = secondParty.getEmail().trim().toLowerCase();

							} else {
								System.out.println("****************ccEmail in not found ******************");
							}
						} catch (Exception e) {

							System.out.println("***************some problem to fetch created bY********************");
							System.out.println(e.getMessage());

						}
						if (invites.isEmpty()) {
							System.out.println("*************** invites List is Empty********************");

						} else {
							for (Invite invitesOne : invites) {

								inviteMail = inviteMail + "," + (invitesOne.getEmail().trim().toLowerCase());

							}

							to = inviteMail;
							sub = "Your Contract no. " + oneContract.getContractId() + " dated "
									+ Calendar.getInstance().getTime() + " has expired on "
									+ oneContract.getContractExpiryDate() + " 	";
							body = "Your Contract no. " + oneContract.getContractId() + " dated "
									+ Calendar.getInstance().getTime() + " has expired on "
									+ oneContract.getContractExpiryDate()
									+ " . Kindly verify and take the necessary action.";

							EmailConfriguration = emailSendingService.contractMailNotification(to, sub, body, ccEmail);
							if (EmailConfriguration) {
								log.info("1000  :  Email  Is Sent");
								System.out.println("1000  :  Email  Is Sent");

							} else {
								log.info("1001  :  Email  Is Not Sent");
								System.out.println("1001  :  Email  Is Not Sent");

							}
						}

					} else {

						System.out.println("contract is not expired . expire date is "
								+ oneContract.getContractExpiryDate() + ".");// send
						log.error("1001  ::  To Email Is Empty");

					}

				} else {
					System.out.println("contract is not found.");// send
					log.error("1001  ::  contract is not found.");

				}

			}

			return true;
		}

	}

	// date compare for 90 days reminder
	public Boolean dateCompare(String expDate, Integer expireReminder) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date expDateIn = null;
		Date dateBefore90days = null;
		Date currentDate = null;
		try {
			expDateIn = sdf.parse(expDate);

			// Date date2 = sdf.parse("2010-01-31");

		} catch (ParseException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		try {

		} catch (Exception e) {
			// TODO: handle exception
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(expDateIn);
		cal.add(Calendar.DATE, (-(expireReminder)));
		dateBefore90days = cal.getTime();
		currentDate = Calendar.getInstance().getTime();
		System.out.println("30 din pahale ki date     " + dateBefore90days);

		if (currentDate.after(dateBefore90days)) {
			System.out.println("Date1 is after Date2");// send
			return true;
		}

		else if (currentDate.before(dateBefore90days)) {
			System.out.println("Date1 is before Date2");
			return false;
		}

		else {// equal
			System.out.println("Date1 is equal Date2");
			return true;

		} /*
			 * if (currentDate.equals(dateBefore90days)) {
			 * System.out.println("Date1 is equal Date2"); return true;
			 * 
			 * }
			 */

	}

}

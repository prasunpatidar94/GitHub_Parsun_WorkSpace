package com.pp.iteanz.services;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.pp.iteanz.entity.ApprovalLog;
import com.pp.iteanz.repository.ApprovalLogRepository;
import com.pp.iteanz.repository.UserMasterRepo;

@Component(value = "AprvReminderMail")
@Service
public class AprvReminderMailNotification {

	@Autowired
	private ApprovalLogRepository approvalLogRepository;

	@Autowired
	private UserMasterRepo userMasterRepository;

	public Boolean aprvReminder() {

		long count = 1L;

		EmailSendingService emailSendingService = new EmailSendingService();

		List<ApprovalLog> approvalLogList = approvalLogRepository.findByStatus(3);
		if (approvalLogList.isEmpty()) {
			return false;
		}
		for (ApprovalLog approvalLogOne : approvalLogList) {

			String to = userMasterRepository.findByAdrid(approvalLogOne.getAprv()).getEmail().trim();
			if (to.isEmpty() || to.equalsIgnoreCase("") || to == null) {

				System.out.println("Aprv Email is  empty for " + approvalLogOne.getReqNo());
				continue;
			}

			String subject = "Approval Reminder Notification no Date " + Calendar.getInstance().getTime();
			String body = mailBodyGentor(approvalLogList);
			String ccEmail = "";

			if (body.isEmpty() || body.equalsIgnoreCase("") || body == null) {
				System.out.println(
						"1001 :: something is wrong in email Body genration for reqId " + approvalLogOne.getReqNo());
				continue;
			}

			Boolean mailconf = emailSendingService.aprvMailNotification(to, subject, body, ccEmail);

			if (mailconf) {
				System.out.println("mail is sent for this reqNo  " + approvalLogOne.getReqNo() + "count is : " + count);
			} else {
				System.out.println("1001  ::  something is wrong for  " + approvalLogOne.getReqNo());
			}
			count++;

		}

		return null;
	}

	public String mailBodyGentor(List<ApprovalLog> approvalLogList) {

		StringBuffer stringBuffer = new StringBuffer();
		StringBuffer stringBufferloop = null;

		for (ApprovalLog approvalLogOne : approvalLogList) {

			stringBufferloop = new StringBuffer();

			stringBufferloop.append(" <tr> ");
			stringBufferloop.append(" <td>" + approvalLogOne.getReqNo() + "</td> ");
			stringBufferloop.append(" <td>" + approvalLogOne.getAppid().getAppid() + "</td> ");
			stringBufferloop.append(" <td>" + approvalLogOne.getSubProcess() + "</td> ");
//			stringBufferloop.append(" <td>" + quantity + "</td> ");
			stringBufferloop.append(" <td>" + approvalLogOne.getStatus() + "</td> ");
			stringBufferloop.append(" </tr>");

		}

		stringBuffer.append("<html>");
		stringBuffer.append("<body>");
		stringBuffer.append("<p>Prasun</p>");
		stringBuffer.append("<table border = '1' width = '100%'  font color='black'> ");

		stringBuffer.append("<caption><font color=\"red\"><b>SRL Diagnostics</b></font></caption> ");

		stringBuffer.append("<thead>");

		stringBuffer.append(" <tr> ");
		stringBuffer.append(" <th>REQUEST_NO</th> ");
		stringBuffer.append("<th>APP_ID</th> ");
		stringBuffer.append(" <th>SUB_PROCESS</th>");
//		stringBuffer.append(" <th>QUNTITY</th>");
		stringBuffer.append(" <th>STATUS</th>");
		stringBuffer.append(" </tr>");

		stringBuffer.append(" </thead> ");
		stringBuffer.append("<tbody>");
		stringBuffer.append(stringBufferloop);

		stringBuffer.append(" </tbody>");
		stringBuffer.append("<tfoot> ");
		stringBuffer.append(" <tr> ");
		stringBuffer.append(" <td colspan=\"5\">");
//		stringBuffer.append("<em>REMARK : - </em> " + remark + "</em>");
		stringBuffer.append("<br><em><a href=\"http://192.168.0.56:8080/ZSRL_FIORI\" target=\"_blank\">Login</a> ");

		stringBuffer.append("</tr> ");
		stringBuffer.append("</tfoot> ");
		stringBuffer.append("</table>");
		stringBuffer.append("</body>");
		stringBuffer.append("</html>");

		return String.valueOf(stringBuffer);

	}

}

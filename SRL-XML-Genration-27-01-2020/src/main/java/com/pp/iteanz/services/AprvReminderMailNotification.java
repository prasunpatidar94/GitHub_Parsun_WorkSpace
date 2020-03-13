package com.pp.iteanz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.pp.iteanz.entity.ApprovalLog;
import com.pp.iteanz.entity.UserMaster;
import com.pp.iteanz.repository.ApprovalLogRepository;
import com.pp.iteanz.repository.UserMasterRepository;

@Component(value = "AprvReminderMail")
@Service
public class AprvReminderMailNotification {
	
	@Autowired 
	private ApprovalLogRepository approvalLogRepository ;
	
	@Autowired 
	private UserMasterRepository userMasterRepository ;


	public Boolean aprvReminder() {
		
		
	List<ApprovalLog> approvalLogList = approvalLogRepository.findByStatus(3);
	if (approvalLogList.isEmpty()) {
		return false ;
	}
	for (ApprovalLog approvalLogOne : approvalLogList) {
		
		
		String to = userMasterRepository.findByAdrid(approvalLogOne.getAprv()).getEmail().trim();
		if (to.isEmpty() || to.equalsIgnoreCase("") || to==null) {
			
			System.out.println("Aprv Email is  empty for "+approvalLogOne.getReqNo());
			continue ; 
		}
		
		
		
		
		
		
		
	}
		
		
		
		
		return null;
	}

}

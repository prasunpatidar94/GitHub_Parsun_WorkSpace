package com.iteanz.srl.repositories;

import java.sql.Time;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iteanz.srl.domain.RoleModel;
import com.iteanz.srl.domain.Status;
import com.iteanz.srl.domain.User;
import com.iteanz.srl.domain.UserMaster;
import com.iteanz.srl.service.WebService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
	WebService webService;
    @Before
    public void setUp() throws Exception {
    	for(int i = 1; i<=3; i++ )
    	{
    		RoleModel roleModel = new RoleModel();
    		roleModel.setId(i);
    		if(i == 1)
    		{
    			roleModel.setRole("AD");
    			System.out.println("AD");
    		}
    		if(i == 2)
    		{
    			roleModel.setRole("UR");
    			System.out.println("UR");
    		}
    		if(i == 3)
    		{
    			roleModel.setRole("AR");
    			System.out.println("AR");
    		}
    	}
    	for(int i = 1; i<=3; i++ )
    	{
    		Status status = new Status();
    		status.setStatusId(i);
    		if(i == 1)
    		{
    			status.setStatus("Approved");
    			System.out.println("Approved");
    		}
    		if(i == 2)
    		{
    			status.setStatus("Rejected");
    			System.out.println("Rejected");
    		}
    		if(i == 3)
    		{
    			status.setStatus("Received");
    			System.out.println("Received");
    		}
    	}
    	java.util.Date utilDate = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilDate.getTime());
		Time currentTime = new Time(utilDate.getTime());
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MONTH, 3);
		java.util.Date dt = cal.getTime();
		java.sql.Date endDate = new java.sql.Date(dt.getTime());
    	UserMaster userMaster = new UserMaster();
    	userMaster.setId(1L);
    	userMaster.setAdrid("admin");
		userMaster.setBegda(today);
		//userMaster.setBptype("");
		userMaster.setCddat(today);
		userMaster.setCdtim(currentTime);
		userMaster.setCrdat(today);
		// userMaster.setCrnam(crnam);
		userMaster.setEmail("mail.iteanz@gmail.com");
		userMaster.setEndda(endDate);
		//userMaster.setPmble("9049888990");
		userMaster.setPswrd("admin");
		userMaster.setEunam("Admin User");
		com.iteanz.srl.domain.Status status = new com.iteanz.srl.domain.Status();
		status.setStatusId(1);
		userMaster.setStatus(status);
		RoleModel roleModelObject = new RoleModel();
		roleModelObject.setId(1);
		userMaster.setUserRoll(roleModelObject);
		//userMaster.setApproverMaster(approverMasterObject);
		//UserMaster userMasterObject = sampleService.createUser(userMaster);
        User user1= new User("Alice", 23);
        User user2= new User("Bob", 38);
       
    }

    @Test
    public void testFetchData(){
        /*Test data retrieval*/
        User userA = userRepository.findByName("Bob");
     
    }
    
}
package com.pp.iteanz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.pp.iteanz.services.AprvReminderMailNotification;
import com.pp.iteanz.services.AsnXmlCreator;
import com.pp.iteanz.services.ContractXmlCreator;
import com.pp.iteanz.services.PrXMLGenrator;

@SpringBootApplication
public class SrlXmlGenration27012020Application /* implements CommandLineRunner */ {

	@Autowired
	ContractXmlCreator contractXmlCreate;
	@Autowired
	AsnXmlCreator asnXmlCreator;
	@Autowired
	PrXMLGenrator prXMLGenrator;
	
	@Autowired
	AprvReminderMailNotification aprvReminderMailNotification;


	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(SrlXmlGenration27012020Application.class, args);

		System.out.println("Please Wait a min");

	}

	@Configuration
	@EnableScheduling
	@ConditionalOnProperty(name = "sceduling", matchIfMissing = true)
	class SchdulingConfiguration {

	}

//	@Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
	@Scheduled(fixedDelay = 10000L, initialDelay = 5000L)
	public void autoRunner() {
		System.out.println("running ");

////		Contract 
//		Boolean resultContract = contractXmlCreate.contractXmlCreation();
//		if (resultContract)
//			System.out.println("contractXmlCreate is runnning "+resultContract);
//		else
//			System.out.println("contractXmlCreate is not runnning "+resultContract);
////		
////		
////		PR
//		Boolean resultPR = prXMLGenrator.prXMLGenrator();
//		if (resultPR)
//			System.out.println("contractXmlCreate is runnning "+resultPR);
//		else
//			System.out.println("contractXmlCreate is not runnning "+resultPR);
////		

		Boolean resultASN = asnXmlCreator.asnGenration();

		if (resultASN)
			System.out.println("asnXmlCreator is runnning " + resultASN);
		else
			System.out.println("asnXmlCreator is not runnning " + resultASN);

		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ************************Aprv Reminder mail ***********************

////	@Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
//	@Scheduled(cron = "0 30 8 * * * *")
//
////	@Scheduled(fixedDelay = 10000L, initialDelay = 5000L )
//	public void aprvReminderNotification() {
//
//		Boolean resultAprv = aprvReminderMailNotification.aprvReminder();
//
//		if (resultAprv)
//			System.out.println("Aprv Reminder  is runnning " + resultAprv);
//		else
//			System.out.println("Aprv Reminder  is not runnning " + resultAprv);
//
//	}

	// ********************************************************************

//
//	@Override
//	@Scheduled(fixedDelay = 6000L, initialDelay = 5000L)
//	public void run(String... args) throws Exception {
//		ContractXmlCreator contractXmlCreate = new ContractXmlCreator();
//		AsnXmlCreator asnXmlCreator = new AsnXmlCreator();
//		System.out.println("running ");
//
//		Boolean result = contractXmlCreate.contractXmlCreation();
//		if (result)
//			System.out.println("contractXmlCreate is runnning "+result);
//		else
//			System.out.println("contractXmlCreate is not runnning "+result);
//		
//		
//		Boolean resultASN=asnXmlCreator.asnGenration();
//		
//		if (resultASN)
//			System.out.println("asnXmlCreator is runnning "+result);
//		else
//			System.out.println("asnXmlCreator is not runnning "+result);
//		try {
//			Thread.sleep(6000L);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

//	@Scheduled(fixedDelay = 6000L, initialDelay = 5000L)

}

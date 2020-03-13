//package com.pp.iteanz.services;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component("autoRun")
//public class AutoRunner {
//	@Scheduled(fixedDelay = 60000, initialDelay = 5000L)
//	public Boolean autoRunning() {
//		ContractXmlCreator contractXmlCreator = new ContractXmlCreator();
////		AsnToSapConveter asnToSapConveter = new AsnToSapConveter();
//
//		if (contractXmlCreator.contractXmlCreation())
//
//			System.out.println("1000 :  ContractXmlCreator run successfull");
//
//		else
//			System.out.println("1001 :  ContractXmlCreator run unsuccessfull");
//
////		if (asnToSapConveter.asnGenration())
////			System.out.println("1000 :  AsnToSapConveter run successfull");
////		else
////			System.out.println("1001 :  AsnToSapConveter run successfull");
//
//		try {
//			Thread.sleep(6000L);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return true;
//
//	}
//
//}

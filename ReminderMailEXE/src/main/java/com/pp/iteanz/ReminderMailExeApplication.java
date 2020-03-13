package com.pp.iteanz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.pp.iteanz.services.AprvReminderMailNotification;
import com.pp.iteanz.services.ContractMailNotificationSender;

@SpringBootApplication
public class ReminderMailExeApplication {

	@Autowired
	private ContractMailNotificationSender contractMailNotificationSender;
	
	@Autowired
	private AprvReminderMailNotification aprvReminderMailNotification;

	public static void main(String[] args) {
		SpringApplication.run(ReminderMailExeApplication.class, args);
	}

	@Configuration
	@EnableScheduling
	@ConditionalOnProperty(name = "sceduling", matchIfMissing = true)
	class SchdulingConfiguration {

	}

	@Scheduled(fixedDelay = 10000L, initialDelay = 5000L)
	public void autoRunner() {
		System.out.println("running ");

		if (contractMailNotificationSender.ContractReminderMail()) {
			System.out.println("**********contract day reminder mail is successfull***********");
		} else {
			System.out.println("**********contract day reminder mail is unsuccessfull***********");
		}

		if (contractMailNotificationSender.ContractExpireReminderMail()) {
			System.out.println("**********contract  expire day  mail is successfull***********");
		} else {
			System.out.println("**********contract expire day mail is unsuccessfull***********");
		}

		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	@Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
	@Scheduled(cron = "0 30 8 * * * *")

//	@Scheduled(fixedDelay = 10000L, initialDelay = 5000L )
	public void aprvReminderNotification() {

		Boolean resultAprv = aprvReminderMailNotification.aprvReminder();

		if (resultAprv)
			System.out.println("Aprv Reminder  is runnning " + resultAprv);
		else
			System.out.println("Aprv Reminder  is not runnning " + resultAprv);

	}

}

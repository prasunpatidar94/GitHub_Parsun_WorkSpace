package com.iteanz.srl.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteanz.srl.domain.ASNHeader;
import com.iteanz.srl.domain.ASNItem;
import com.iteanz.srl.domain.MaterialMaster;
import com.iteanz.srl.repositories.ASNHeaderRepository;
import com.iteanz.srl.repositories.MaterialMasterRepository;

@Service
public class DateCompare {

	@Autowired
	private MaterialMasterRepository materialMasterRepository;
	@Autowired
	private ASNHeaderRepository asnHeaderRepository;

	public String dateDiffrence(String werks, long asn) {

		MaterialMaster materialMaster = null;
		ASNHeader asnHeader = null;

		Date createDateInDate = null;
		Boolean count = true;
		List<ASNItem> asnItems = null;

		// asnItemA.getMatnr()

		try {

			asnHeader = asnHeaderRepository.findOne(asn);

			if (asnHeader == null) {
				return " asnHeader  is empty";
			}

			asnItems = asnHeader.getAsnitem();
			if (asnItems.isEmpty()) {
				return " ASNItem List is empty";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Something is Wrong In material master" + e.getMessage();
		}

		String createDate = asnHeader.getCreateddate();
		if (createDate.isEmpty() || createDate == "" || createDate.equalsIgnoreCase("")) {
			return "create date is empty ";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfForExp = new SimpleDateFormat("yyyy-MM-dd");// for item exp date

		try {
			createDateInDate = sdf.parse(createDate);
		} catch (ParseException e) {
			return e.getMessage();
		}

		for (ASNItem asnItem : asnItems) {
			try {
				materialMaster = materialMasterRepository.findbyMatnrAndWerks(asnItem.getMatnr(), asnItem.getWerks());
				if (materialMaster == null)
					return "Something is Wrong In material master in date ";

				long lifeSpan = Long.parseLong(materialMaster.getMhdrz().trim());
				if (lifeSpan == 0L)
					return "Something is Wrong In file spon days ,it may may be empty ";

				long dateOfAdd = createDateInDate.getTime() + (1000 * 60 * 60 * 24 * lifeSpan);
				if (dateOfAdd == 0L)
					return "adding date id empty ";

				String ExpDate = asnItem.getExdate().trim();
				if (ExpDate.isEmpty() || ExpDate.equalsIgnoreCase(""))
					return "Something  is wrong in Exp ";
				long expDateInDate = 0L;
				try {
					expDateInDate = sdfForExp.parse(ExpDate).getTime();
				} catch (Exception e) {
					return "Something in parsing date for expire date  " + e.getMessage();
				}

				if (dateOfAdd < expDateInDate) {
					count = true;

				} else if (dateOfAdd > expDateInDate) {
					count = false;
					return "ex";
				} else if (dateOfAdd == expDateInDate) {
					count = false;
					return "ex";
				}

			} catch (Exception e) {

			}

		}
		if (count)

		{
			return "act";
		} else {
			return "ex";
		}
	}

}

// ********************************************

//		try {
//			dayLife = Integer.parseInt(materialMaster.getMhdrz().trim());
//			long dateOfAdd = createDateInDate.getTime() + (1000 * 60 * 60 * 24 * dayLife);
//
//		} catch (Exception e) {
//			return "Something is Wrong In material master in Life spon in fetch" + e.getMessage();
//		}
//
//		if (dayLife == 0L) {
//			return "Something is Wrong In material master in Life spon ";
//		}

//		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

//		try {
//			date = sdf.parse(createDate);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

//		long dateOfAdd = date.getTime() + (1000 * 60 * 60 * 24 * dayLife);

//		List<ASNItem> asnItemsList = asnHeader.getAsnitem();
//		if (asnItemsList.isEmpty()) {
//			return " ASNItem List is empty";
//		}
//		long expDateMiliSec = 0L;
////		for (ASNItem asnItem : asnItemsList) {
//			try {
//				expDateMiliSec = sdf1.parse(asnItem.getExdate()).getTime();
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}

//			if (dateOfAdd < expDateMiliSec) {
//				count = true;
//
//			} else if (dateOfAdd > expDateMiliSec) {
//				count = false;
//				return "ex";
//			} else if (dateOfAdd == expDateMiliSec) {
//				count = false;
//				return "ex";
//			}

//		}

//	if(count)
//
//	{
//		return "act";
//	}else
//	{
//		return "ex";
//	}

//		Calendar c = Calendar.getInstance();
//		c.setTime(date); // Now use today date.
//
//		c.add(Calendar.DATE,  (int) dayLife); 
//		
//		c.getTime();
//		

//		String output = sdf.format(c.getTime());
//		System.out.println(output);

//		long createDateMillis = date.getTime();
//
//		long diffrenceDays = (long) ((((Calendar.getInstance().getTimeInMillis()) - createDateMillis)
//				/ (1000 * 60 * 60 * 24)) % 7);
//
//		Boolean check = diffrenceDays > dayLife;
//
//		if (check) {
//			return "ex";
//		} else {
//			return "act";
//		}

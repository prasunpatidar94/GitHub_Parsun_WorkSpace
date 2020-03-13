package com.iteanz.srl.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteanz.srl.domain.Plant;
import com.iteanz.srl.repositories.PlantRepository;

@Service
public class WebGetServices {

	@Autowired
	private PlantRepository plantRepository;

	public Map<String, Object> getStorageLocationBasedOnPlant(String werks) {

		Map<String, Object> mapdata = new HashMap<String, Object>();
		Set<Plant> plantSet = plantRepository.findByWerks(werks);

		if (plantSet.isEmpty()) {
			mapdata.put("code", "1001");
			mapdata.put("error", "Plant List Is Empty");
			return mapdata;
		} else {
			mapdata.put("code", "1000");
			mapdata.put("succes", plantSet);
			return mapdata;
		}

	}

	

}

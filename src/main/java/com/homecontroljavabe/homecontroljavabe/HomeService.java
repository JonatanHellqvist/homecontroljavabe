package com.homecontroljavabe.homecontroljavabe;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class HomeService {
	
	private final MongoOperations mongoOperations;

	public HomeService(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	// public List<HomeDevice> addHomeDevices(List <HomeDevice> devices) {
	// 	List <HomeDevice> savedDevices = new ArrayList<>();
	// 	for (HomeDevice device : devices) {
	// 		mongoOperations.save(device);
	// 		savedDevices.add(device);
    //     }
	// 	return savedDevices;
	// }

	public List <HomeDevice> getAllHomeDevices() {
		return mongoOperations.findAll(HomeDevice.class);
	}
}

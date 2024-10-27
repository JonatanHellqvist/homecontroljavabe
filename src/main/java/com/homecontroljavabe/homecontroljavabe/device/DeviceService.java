package com.homecontroljavabe.homecontroljavabe.device;

import org.springframework.data.mongodb.core.MongoOperations;

import org.springframework.stereotype.Service;

import com.homecontroljavabe.homecontroljavabe.user.User;

import java.util.List;
import java.util.ArrayList;

@Service
public class DeviceService {
	

	private final MongoOperations mongoOperations;

	public DeviceService(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	public Device addNewDevice (String id, Device device) {
		User user = mongoOperations.findById(id, User.class);
			if (user != null) {
			List<Device> deviceList = user.getDeviceList();
				if (deviceList == null) {
					deviceList = new ArrayList<>();
				}
				deviceList.add(device);
				user.setDeviceList(deviceList);
				mongoOperations.insert(device);
				mongoOperations.save(user);
				return device;
				}
			return null;
	}
	
	public List<Device> getUserDevices(String userId) {
        User user = mongoOperations.findById(userId, User.class);
        if (user != null) {
            return user.getDeviceList();
        }
        return null;
    }
}

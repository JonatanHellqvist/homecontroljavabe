package com.homecontroljavabe.homecontroljavabe.device;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
public class DeviceController {

	private DeviceService deviceService;

	public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

	// @PostMapping("/post-new-device")
	// public Device postNewUserDeviceToDb(@RequestBody Device device) {
	// 	return deviceService.addNewDevice(device);
	// }
	
	// @GetMapping("/get-all-devices")
	// public List<Device> getAllDevicesFromDb() {
	// 	return deviceService.getAllUserDevices();
	// }

	@PostMapping("/{userId}/list/adddevice")
    public Device addUserDevice(@PathVariable String userId, @RequestBody Device device) {
        return deviceService.addNewDevice(userId, device);
    }

	//personliga listan
	@GetMapping ("/{userId}/list")
	public List<Device> getUserDevices(@PathVariable String userId) {
		return deviceService.getUserDevices(userId);
	}
}




// @RestController
// @CrossOrigin(origins = "*")
// public class TempController {
	
// 	private TempService tempService;

// 	public TempController(TempService tempService) {
// 		this.tempService = tempService;
// 	}

// 	@PostMapping("/post-dht11-sensor-data")
// 	public Temp postDht11SensorDataToDb(@RequestBody Temp temp) {
// 		return tempService.addDht11SensorData(temp);
// 	}

// 	@GetMapping("/get-all-dht11-sensor-data")
// 	public List<Temp> getAllDht11SensorDataFromDb() {
// 		return tempService.getAllDht11SensorData();
// 	}

// 	@GetMapping("/get-latest-dht11-sensor-data")
// 	public Temp getLatestDht11SensorData() {
//     	return tempService.getLatestDht11SensorData();
// }
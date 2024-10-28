package com.homecontroljavabe.homecontroljavabe.device;

import org.springframework.beans.factory.annotation.Value;

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

	@Value("${hue.api.key}")
	private String hueApiKey;

	@Value("${hue.bridge.ip}")
	private String hueBridgeIp;

	private DeviceService deviceService;

	public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
		
    }

	@PostMapping("/{userId}/list/adddevice")
    public Device addUserDevice(@PathVariable String userId, @RequestBody Device device) {
        return deviceService.addNewDevice(userId, device);
    }

	//personliga listan
	@GetMapping ("/{userId}/list")
	public List<Device> getUserDevices(@PathVariable String userId) {
		return deviceService.getUserDevices(userId);
	}

	//getbyid
	@GetMapping("/device/{deviceId}/{userId}")
	public Device getDevice(@PathVariable String deviceId, @PathVariable String userId) {
		return deviceService.getDeviceById(deviceId, userId);
	}
	//getbyhueindex
	@GetMapping("/device/hue/{hueIndex}/{userId}")
	public Device getDeviceByHueIndexFromDb(@PathVariable int hueIndex, @PathVariable String userId) {
		return deviceService.getDeviceByHueIndex(hueIndex, userId);
	}

	//Toggle Device On/Off
    // @PutMapping("/{userId}/list/{hueIndex}/state")
    // public ResponseEntity<String> toggleDevice(@PathVariable String userId, @PathVariable int hueIndex, @RequestBody Map<String, Boolean> state) {
    //     return deviceService.toggleDevice(userId, hueIndex, state);
    // }
	
}

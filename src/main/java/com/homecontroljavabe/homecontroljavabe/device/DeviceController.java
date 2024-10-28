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

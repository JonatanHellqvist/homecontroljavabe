package com.homecontroljavabe.homecontroljavabe;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "*")
public class HomeController {

	private HomeService homeService;

	
	public HomeController(HomeService homeService) {
		this.homeService = homeService;
	}

	@GetMapping("/test")
	public String getTest() {
		return "{ Test123 }";
	}
	@GetMapping()
	public String getTest2() {
		return "{ root Test123 }";
	}

	@GetMapping("/get-all-home-devices")
	public List<HomeDevice>getAllHomeDevicesFromDb() {
		return homeService.getAllHomeDevices();
	}

	@PostMapping("/add-all-home-devices")
	public List<HomeDevice> postAllHomeDevicesToDb(@RequestBody List<HomeDevice> devices) {
		return homeService.addHomeDevices(devices);
	}
	
	
	
	
}

package com.homecontroljavabe.homecontroljavabe;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.homecontroljavabe.homecontroljavabe.user.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/lights")
public class HueController {

	@Value("${hue.api.key}")
	private String hueApiKey;

	@Value("${hue.bridge.ip}")
	private String hueBridgeIp;

	private final RestTemplate restTemplate = new RestTemplate();

	@Autowired
	UserService userService;
	
	//HÄMTA alla lampor kopplade till bridge ipt
	@GetMapping
	public ResponseEntity <String> getAllLights() {
		String url = "http://" + hueBridgeIp + "/api/" + hueApiKey + "/lights";

		//Get förfrågan från Hue API
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
			
	return ResponseEntity.ok(response.getBody());
	} 

	

	//Uppdatera state värdet mot Hues API
	@PutMapping("/{lightId}/state")
	//tar emot light idt och dess state från frontenden
	public ResponseEntity<String> toggleLight(@PathVariable String lightId, @RequestBody Map<String, Boolean> state) {
		System.out.println("State för Id " + lightId + ": " + state);
		String url = "http://" + hueBridgeIp + "/api/" + hueApiKey + "/lights/" + lightId + "/state";

		//Skapa HttpEntity med state som kropp
		HttpEntity<Map<String, Boolean>> requestEntity = new HttpEntity<>(state, createHeaders());

		//Skicka PUT mot Hue API
		ResponseEntity<String> response;
		try {
			response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
		} catch (RestClientException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fel vid kommunikation : " + e.getMessage());
		}

    return ResponseEntity.ok(response.getBody());
	}

	//metod för att skapa headers
	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	@GetMapping("/user/{userId}/devices")
	public ResponseEntity<String> getAllDevices(@PathVariable String userId) {
    //Hämta användarens bridge IP från databasen
    String hueBridgeIp = userService.getBridgeIp(userId);
	System.out.println("Hämtat ip: "+ hueBridgeIp);
    
    if (hueBridgeIp == null || hueApiKey == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bridge IP or API key not found for user");
    }

    String url = "http://" + hueBridgeIp + "/api/" + hueApiKey + "/lights";
    
    //Gör en GET-förfrågan till Hue API med det specifika hueBridgeIp
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    
    return ResponseEntity.ok(response.getBody());
}
		
}
	


package com.homecontroljavabe.homecontroljavabe.hueauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

////////////////////////////////////PILLA INTE DÅ SMÄLLER DET///////////////////////////////////////////////////////////////////
   /// 

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hue")
public class HueAuthController {

    @Value("${hue.client.id}")
    private String clientId;

    @Value("${hue.client.secret}")
    private String clientSecret;

	@Value("${hue.redirect.uri}")
	private String hueRedirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    //Temporär lagring för access token och användarnamn
    private static final Map<String, String> userTokens = new HashMap<>();

    /**
     * Hanterar callback från Philips Hue efter autentisering.
     * 
     * @param authorizationCode Koden som skickas från Hue API.
     * @return En response som innehåller access token.
     */
	@GetMapping("/callback")
	public ResponseEntity<HueTokenResponse> handleHueCallback(@RequestParam("code") String authorizationCode) {
		String tokenUrl = "https://api.meethue.com/v2/oauth2/token";
		String authHeader = "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
		System.out.println("Redirect URI: " + hueRedirectUri);
		String body = "grant_type=authorization_code&code=" + authorizationCode + "&redirect_uri=" + hueRedirectUri;

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authHeader);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<String> request = new HttpEntity<>(body, headers);

		try {
			ResponseEntity<HueTokenResponse> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, HueTokenResponse.class);
			
			if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
				//Hämta access token
				String accessToken = response.getBody().getAccessToken();
				
				userTokens.put("access_token", accessToken);
				
				//Registrera applikationen och spara användarnamnet TRYCK på bridgeknappen om det behövs för ny
				String username = registerApplication(accessToken);
				userTokens.put("username", username);
				
				HueTokenResponse hueTokenResponse = response.getBody();
    			hueTokenResponse.setUsername(username); 

				System.out.println("Registered username: " + username);
				System.out.println(hueTokenResponse);
				return ResponseEntity.ok(hueTokenResponse);
				
			} else {
				
				return ResponseEntity.status(response.getStatusCode()).body(null);
			}
			
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			return ResponseEntity.status(ex.getStatusCode()).body(null);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	private String registerApplication(String accessToken) {
		String registrationUrl = "https://api.meethue.com/route/api";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		String requestBody = "{\"devicetype\":\"<homecontrol>\"}"; 
		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(registrationUrl, HttpMethod.POST, request, String.class);
			
			if (response.getStatusCode() == HttpStatus.OK) {
				
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonResponse = objectMapper.readTree(response.getBody());
				System.out.println("Registration Response: " + response.getBody());
				return jsonResponse.get(0).get("success").get("username").asText(); 
				
			} else {
				System.out.println("Failed to register application: " + response.getBody());
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null; 
	}
    
	@GetMapping("/devices")
	public ResponseEntity<?> getHueDevices() {
		String accessToken = userTokens.get("access_token");
		String username = userTokens.get("username");

		if (accessToken == null || username == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token or username is missing");
		}

		String devicesUrl = "https://api.meethue.com/route/clip/v2/resource/device";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		headers.set("hue-application-key", username);

		HttpEntity<String> request = new HttpEntity<>(headers);
		System.out.println(accessToken+username+request);
		try {
			ResponseEntity<String> response = restTemplate.exchange(devicesUrl, HttpMethod.GET, request, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				return ResponseEntity.ok(response.getBody());
			} else {
				return ResponseEntity.status(response.getStatusCode()).body("Failed to fetch devices");
			}

		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			return ResponseEntity.status(ex.getStatusCode()).body("Error fetching devices: " + ex.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
		}
	}  

	@GetMapping("/device/{deviceId}")
	public ResponseEntity<?> getDeviceInfo(@PathVariable String deviceId) {
		String accessToken = userTokens.get("access_token");
		String username = userTokens.get("username");
		
		System.out.println("Device ID: " + deviceId); 
		
		String devicesUrl = "https://api.meethue.com/route/clip/v2/resource/light/" + deviceId;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		headers.set("hue-application-key", username);
		
		System.out.println("Request URL: " + devicesUrl);
		System.out.println("Headers: " + headers);
		
		try {
			HttpEntity<String> request = new HttpEntity<>(headers);
			ResponseEntity<String> response = restTemplate.exchange(devicesUrl, HttpMethod.GET, request, String.class);
			System.out.println(response.getBody().toString());
			return ResponseEntity.ok(response.getBody());
		} catch (HttpClientErrorException e) {
			System.out.println("Error response: " + e.getResponseBodyAsString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found: " + e.getMessage());
		}
	}

	@PutMapping("/device/{deviceId}")
	public ResponseEntity<String> putDeviceOnOff(@PathVariable String deviceId, @RequestBody boolean isON) {
		String accessToken = userTokens.get("access_token");
		String username = userTokens.get("username");

		String devicesUrl = "https://api.meethue.com/route/clip/v2/resource/light/" + deviceId;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		headers.set("hue-application-key", username);
		headers.setContentType(MediaType.APPLICATION_JSON);
		String requestBody = "{\"on\":{\"on\":" + isON + "}}";

		//Gör PUT-anropet
		try {
			HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
			restTemplate.exchange(devicesUrl, HttpMethod.PUT, request, String.class);

			// Returnera svar från API:t
			return ResponseEntity.ok("Device " + (isON ? "turned ON" : "turned OFF") + " successfully.");
		} catch (HttpClientErrorException e) {
			//Hantera fel och logga meddelande om det finns något
			System.out.println("Error response: " + e.getResponseBodyAsString());
			return ResponseEntity.status(e.getStatusCode()).body("Failed to change device state: " + e.getMessage());
		}
	}
	

}
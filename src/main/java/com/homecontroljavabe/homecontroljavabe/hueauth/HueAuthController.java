package com.homecontroljavabe.homecontroljavabe.hueauth;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/hue")
public class HueAuthController {

    @Value("${hue.client.id}")
    private String clientId;

    @Value("${hue.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/callback")
    public ResponseEntity<String> handleHueCallback(@RequestParam("code") String authorizationCode) {
        String tokenUrl = "https://api.meethue.com/oauth2/token";
        String authHeader = "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

        String body = "grant_type=authorization_code&code=" + authorizationCode;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            //Hantera access_token-svar här, t.ex. spara det i databasen
            return ResponseEntity.ok("Access token received and processed successfully.");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Failed to retrieve access token.");
        }
    }
}
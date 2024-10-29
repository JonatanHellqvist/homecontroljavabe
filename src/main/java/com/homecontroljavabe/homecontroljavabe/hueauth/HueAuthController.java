package com.homecontroljavabe.homecontroljavabe.hueauth;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "*")
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

        try {
            ResponseEntity<HueTokenResponse> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, HueTokenResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                // Hämta access token och andra data från responsen
                HueTokenResponse tokenResponse = response.getBody();
                String accessToken = tokenResponse.getAccessToken();
                String refreshToken = tokenResponse.getRefreshToken();
                int expiresIn = tokenResponse.getExpiresIn();

                // TODO: Spara token i databasen eller annan säker plats

                return ResponseEntity.ok("Access token received successfully: " + accessToken);
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Failed to retrieve access token.");
            }

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Fångar fel vid anrop
            return ResponseEntity.status(ex.getStatusCode()).body("Error occurred: " + ex.getResponseBodyAsString());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
        }
    }
}
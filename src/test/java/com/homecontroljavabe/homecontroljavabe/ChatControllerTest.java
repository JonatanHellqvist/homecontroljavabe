package com.homecontroljavabe.homecontroljavabe;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

	@Test
	//kolla så svaret innehåller "smarta hem lösningar"
	public void testPostChat() {
		
		String prompt = "Vad är en smart hem lösning?";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(prompt, headers);
	
		ResponseEntity<String> response = restTemplate.postForEntity("/chat", request, String.class);

		assertEquals(OK, response.getStatusCode());
		assertNotNull(response.getBody());
	
		System.out.println("Response Body: " + response.getBody());
	
		assertTrue(response.getBody().contains("smart"), "Svaret innehåller inte 'smart'");
	}
}
package com.homecontroljavabe.homecontroljavabe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.homecontroljavabe.homecontroljavabe.user.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {
	
	@Autowired
    	private TestRestTemplate restTemplate;

	@LocalServerPort
    	private int port;

	@Test
	  	public void testAddUser() {
			User user = new User();
			user.setUsername("testUser");
			user.setPassword("testPassword");

			ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + port + "/user/register", user, User.class);

			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertNotNull(response.getBody());
			assertEquals("testUser", response.getBody().getUsername());
    }

	@Test
    public void testLogin() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        restTemplate.postForEntity("http://localhost:" + port + "/user/register", user, User.class);

        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + port + "/user/login", user, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateTempSensSettings() {
        String userId = "123";
        int tempSensitivity = 5;
        String tempIndex = "1";

        ResponseEntity<String> response = restTemplate.exchange(
            "http://localhost:" + port + "/user/tempsens/" + userId + "/" + tempSensitivity + "/" + tempIndex,
            HttpMethod.PUT,
            null,
            String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

	@Test
	public void testGetTempSensSettings() {
		
		String userId = "123";
		String username = "testUser";
		String password = "testPassword";
		String tempIndex = "1";
		int tempSensitivity = 5;

		User user = new User();
		user.setId(userId);
		user.setUsername(username);
		user.setPassword(password);
		user.setBridgeIp("Please configure Bridge connection");

		restTemplate.postForEntity("http://localhost:" + port + "/user/register", user, User.class);

		ResponseEntity<String> putResponse = restTemplate.exchange(
			"http://localhost:" + port + "/user/tempsens/" + userId + "/" + tempSensitivity + "/" + tempIndex,
			HttpMethod.PUT,
			null,
			String.class
		);

		assertEquals(HttpStatus.OK, putResponse.getStatusCode());

		//get
		ResponseEntity<String> response = restTemplate.getForEntity(
			"http://localhost:" + port + "/user/tempsens/" + userId, String.class
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

    @Test
    public void testUpdateLightSensSettings() {
        String userId = "123";
        int lightSensitivity = 8;
        String lightIndex = "2";

        ResponseEntity<String> response = restTemplate.exchange(
            "http://localhost:" + port + "/user/lightsens/" + userId + "/" + lightSensitivity + "/" + lightIndex,
            HttpMethod.PUT,
            null,
            String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
	public void testGetLightSensSettings() {
		
		String userId = "123";
		String username = "testUser";
		String password = "testPassword";
		String lightIndex = "1";
		int lightSensitivity = 10;

		User user = new User();
		user.setId(userId);
		user.setUsername(username);
		user.setPassword(password);
		user.setBridgeIp("Please configure Bridge connection");

		restTemplate.postForEntity("http://localhost:" + port + "/user/register", user, User.class);

		ResponseEntity<String> putResponse = restTemplate.exchange(
			"http://localhost:" + port + "/user/lightsens/" + userId + "/" + lightSensitivity + "/" + lightIndex,
			HttpMethod.PUT,
			null,
			String.class
    );

    assertEquals(HttpStatus.OK, putResponse.getStatusCode());

    ResponseEntity<String> response = restTemplate.getForEntity(
        "http://localhost:" + port + "/user/lightsens/" + userId, String.class
    );

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
	}
}

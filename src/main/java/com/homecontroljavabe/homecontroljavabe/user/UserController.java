package com.homecontroljavabe.homecontroljavabe.user;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.mongodb.core.aggregation.VariableOperators.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.homecontroljavabe.homecontroljavabe.user.UserService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;



@RestController
@CrossOrigin(origins = "*")
public class UserController {
	
	UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	@GetMapping("/user/{id}")
	public User getUser(@PathVariable String id) {
		return userService.getUser(id);
	}

	@PostMapping("/user/register")
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}

	@PostMapping("/user/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User loggedInUser = userService.getUserByUsername(user.getUsername());   
        //kolla om user inte är null och password stämmer med getpassword för usern. veryfypassword metod för att kolla mot bcrypt
        if (loggedInUser != null && userService.verifyPassword(user.getPassword(), loggedInUser.getPassword())) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

	@PutMapping("/user/tempsens/{userId}/{tempSensitivity}/{tempIndex}")
	public ResponseEntity<String> updateTempSensSettings(@PathVariable String userId, @PathVariable String tempIndex, @PathVariable int tempSensitivity) {
		System.out.println("Received PUT request for user ID: " + userId + " with new temp settings: RID: " + tempIndex + "SENS: " + tempSensitivity);

		userService.setTempSettings(userId, tempIndex, tempSensitivity);
		System.out.println("RID: " + tempIndex);
		System.out.println("Sens " + tempSensitivity);

		return ResponseEntity.ok("{\"message\": \"Temp settings Updated successfully!\"}");
	}

	@GetMapping("/user/tempsens/{userId}")
	public String getTempSensSettings(@PathVariable String userId) {
		return userService.getTempSettings(userId);
	}

	@PutMapping("/user/lightsens/{userId}/{lightSensitivity}/{lightIndex}")
	public ResponseEntity<String> updateLightSensSettings(@PathVariable String userId, @PathVariable String lightIndex, @PathVariable int lightSensitivity) {
		System.out.println("Received PUT request for user ID: " + userId + " with new temp settings: RID: " + lightIndex + "SENS: " + lightSensitivity);
		
		userService.setLightSettings(userId, lightIndex, lightSensitivity);
		System.out.println("RID: " + lightIndex);
		System.out.println("Sens " + lightSensitivity);

		return ResponseEntity.ok("{\"message\": \"Light settings Updated successfully!\"}");
	}

	@GetMapping("/user/lightsens/{userId}")
	public String getLightSensSettings(@PathVariable String userId) {
		return userService.getLightSettings(userId);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////|METODER FÖR LOKAL ANVÄNDING AV BRIDGE|//////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// 
/// 	@GetMapping("/user/bridgeip/{id}")
// 	public String getBridgeIp(@PathVariable String id) {
// 		return userService.getBridgeIp(id);
// 	}

// 	@PutMapping("/user/bridgeip/{id}")
// 	public ResponseEntity<String> updateBridgeIp(@PathVariable String id, @RequestBody String newBridgeIp) {
// 		System.out.println("Received PUT request for user ID: " + id + " with new Bridge IP: " + newBridgeIp);
// 		userService.setBridgeIp(id, newBridgeIp);
// 		return ResponseEntity.ok("{\"message\": \"Bridge IP updated successfully\"}");
// }
// @PutMapping("/user/lightsens/{userId}")
	// public ResponseEntity<String> updateLightSensSettings(@PathVariable String userId, @RequestBody Map <String, Integer> newLightSensSettings) {
	// 	System.out.println("Received PUT request for user ID: " + userId + " with new light settings: " + newLightSensSettings);

	// 	int lightSensitivity = newLightSensSettings.getOrDefault("lightSensitivity", 0);
    // 	int lightIndex = newLightSensSettings.getOrDefault("lightIndex", 0);
	// 	userService.setLightSettings(userId, lightIndex, lightSensitivity);
		
	// 	return ResponseEntity.ok("{\"message\": \"Light settings Updated successfully!\"}");
	// }
		
}
	
	



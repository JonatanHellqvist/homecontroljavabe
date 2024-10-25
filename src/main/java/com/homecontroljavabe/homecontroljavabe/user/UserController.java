package com.homecontroljavabe.homecontroljavabe.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.homecontroljavabe.homecontroljavabe.user.UserService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "*")
public class UserController {
	
	UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}

	// @PostMapping
	// public ResponseEntity<String> registerUser(@RequestBody User user) {
	// 	try {
	// 		userService.registerUser(user);
	// 		return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
	// 	} catch (Exception e) {
	// 		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	// 	}
	// }
	
}

package com.homecontroljavabe.homecontroljavabe.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	
	
}

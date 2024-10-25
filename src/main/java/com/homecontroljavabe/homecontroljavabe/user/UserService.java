package com.homecontroljavabe.homecontroljavabe.user;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private final MongoOperations mongoOperations;

	
	PasswordEncoder passwordEncoder;

	
	public UserService(MongoOperations mongoOperations) {
		this.mongoOperations= mongoOperations;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}


	public User addUser(User user) {
		String passwordHash = passwordEncoder.encode(user.getPassword());
		user.setPassword(passwordHash);
		return mongoOperations.insert(user);
	}
}

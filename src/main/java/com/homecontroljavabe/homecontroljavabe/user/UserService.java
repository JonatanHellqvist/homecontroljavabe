package com.homecontroljavabe.homecontroljavabe.user;


import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
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
		user.setBridgeIp("Please configure Bridge connection");
		user.setPassword(passwordHash);
		return mongoOperations.insert(user);
	}

	public boolean verifyPassword(String inputPassword, String storedHashedPassword) {
        return BCrypt.checkpw(inputPassword, storedHashedPassword);
    }

	public User getUser(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		
		return mongoOperations.findOne(query, User.class);
	}

	public User getUserByUsername(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		return mongoOperations.findOne(query, User.class);
	}

	// public String getUserBridgeIp(String id) {
	// 	User user = mongoOperations.findById(id, User.class);
	// 	return (user != null) ? user.getBridgeIp() : null;
	// }

	public String getBridgeIp(String id) {
		User user = mongoOperations.findById(id, User.class);
		if (user != null) {
			System.out.println(user.getBridgeIp());
			return user.getBridgeIp();
		}
		return null; 
	}

	public void setBridgeIp(String id, String newBridgeIp) {
		User user = mongoOperations.findById(id, User.class);
		
		System.out.println("Fetching user: " + id); 
		System.out.println("New Bridge IP: " + newBridgeIp); 
		if (user != null) {
			System.out.println("User found: " + user); 
			user.setBridgeIp(newBridgeIp);
			mongoOperations.save(user); 
		} else {
			System.out.println("User not found with ID: " + id); 
		}
	}

	
}

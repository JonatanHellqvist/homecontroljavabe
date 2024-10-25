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

	
}

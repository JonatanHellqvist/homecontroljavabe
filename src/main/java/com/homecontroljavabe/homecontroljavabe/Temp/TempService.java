package com.homecontroljavabe.homecontroljavabe.Temp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class TempService {

	private final MongoOperations mongoOperations;

	public TempService(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	public Temp addDht11SensorData (Temp temp) {
		temp.setTimeStamp(LocalDateTime.now());
		return mongoOperations.insert(temp);
	}

	public List<Temp> getAllDht11SensorData () {
		return mongoOperations.findAll(Temp.class);
	}

	public Temp getLatestDht11SensorData() {
		Query query = new Query();
		query.with(Sort.by(Sort.Order.desc("timeStamp"))); 
		query.limit(1); //Hämta bara den senaste avläsningen
		return mongoOperations.findOne(query, Temp.class);
	}		
}


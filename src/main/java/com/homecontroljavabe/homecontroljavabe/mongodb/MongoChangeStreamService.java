package com.homecontroljavabe.homecontroljavabe.mongodb;

import com.mongodb.client.MongoClient;

import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
public class MongoChangeStreamService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MongoClient mongoClient;


    // public MongoChangeStreamService() {
    //     startChangeStreamListener();
    // }

	@Scheduled(initialDelay =1000, fixedRate = Long.MAX_VALUE)
    public void startChangeStreamListener() {
        Thread thread = new Thread(() -> {
            var database = mongoClient.getDatabase("Cluster0Extra-homeControl");
            var collection = database.getCollection("temp");
            
            var changeStream = collection.watch().fullDocument(com.mongodb.client.model.changestream.FullDocument.UPDATE_LOOKUP).iterator();
            
            while (changeStream.hasNext()) {
                ChangeStreamDocument<Document> change = changeStream.next();
                
                //skicka till WebSocket
                messagingTemplate.convertAndSend("/topic/changes", change.getFullDocument());
            }
        });

        thread.start();
    }
}
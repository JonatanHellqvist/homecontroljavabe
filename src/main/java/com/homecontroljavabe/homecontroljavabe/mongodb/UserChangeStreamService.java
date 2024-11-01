// package com.homecontroljavabe.homecontroljavabe.mongodb;

// import org.bson.Document;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.scheduling.annotation.EnableScheduling;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Service;

// import com.mongodb.client.MongoClient;
// import com.mongodb.client.model.changestream.ChangeStreamDocument;

// @EnableScheduling
// @Service
// public class UserChangeStreamService {

//     @Autowired
//     private SimpMessagingTemplate messagingTemplate;

//     @Autowired
//     private MongoClient mongoClient;

//     @Scheduled(initialDelay = 1000, fixedRate = Long.MAX_VALUE)
//     public void startUserChangeStreamListener() {
//         Thread thread = new Thread(() -> {
//             var database = mongoClient.getDatabase("Cluster0Extra-homeControl");
//             var collection = database.getCollection("users");

//             var changeStream = collection.watch()
//                 .fullDocument(com.mongodb.client.model.changestream.FullDocument.UPDATE_LOOKUP)
//                 .iterator();

//             while (changeStream.hasNext()) {
//                 ChangeStreamDocument<Document> change = changeStream.next();

//                 // Skicka `bridgeip`-specifika uppdateringar
//                 Document fullDocument = change.getFullDocument();
//                 if (fullDocument != null && fullDocument.containsKey("bridgeip")) {
//                     String bridgeIP = fullDocument.getString("bridgeip");
//                     messagingTemplate.convertAndSend("/topic/bridgeip", bridgeIP);
//                 }
//             }
//         });

//         thread.start();
//     }
// }
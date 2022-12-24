package com.example.poc.mongodb.database;

import com.example.poc.mongodb.models.Transaction;
import com.example.poc.mongodb.service.CallbackService;
import com.example.poc.mongodb.service.DatabaseService;
import com.google.gson.Gson;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.FullDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class MongoDB {

    @Autowired
    private CallbackService callbackService;
    @Autowired
    private DatabaseService databaseService;

    @PostConstruct
    public void init() {
        MongoClient mongoClient = MongoClients.create("mongodb+srv://{user}:{password}@cluster0.mfwx35k.mongodb.net/?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("transaction");
        MongoCollection dbStatus = database.getCollection("status");
//        System.out.println("MONGODB SETUP COMPLETED");
        List<Bson> filter = Arrays.asList(Aggregates.match(Filters.in("operationType", Arrays.asList("update"))));
        ChangeStreamIterable<Document> changes = dbStatus.watch(filter).fullDocument(FullDocument.UPDATE_LOOKUP);
        Thread listener = new Thread(() -> {
            changes.forEach(event -> {
                Gson gson = new Gson();
                Transaction transaction = gson.fromJson(event.getFullDocument().toJson(), Transaction.class);
                if (transaction.getIsDone()) {
//                    System.out.println(transaction.getPaymentId());
                    callbackService.callback(transaction);
                    databaseService.stopTime(transaction,System.currentTimeMillis());
                }
            });
        });
        listener.start();
//        System.out.println("MONGODB CHANGE STREAM SETUP COMPLETED");
    }
}

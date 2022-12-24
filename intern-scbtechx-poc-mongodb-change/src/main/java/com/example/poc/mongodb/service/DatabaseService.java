package com.example.poc.mongodb.service;

import com.example.poc.mongodb.models.Transaction;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    private MongoCollection dbStatus;

    public DatabaseService() {
        MongoClient mongoClient = MongoClients.create("mongodb+srv://{user}:{password}@cluster0.mfwx35k.mongodb.net/?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("transaction");
        this.dbStatus = database.getCollection("status");
    }

    private Document find(Transaction transaction) {
        return (Document) dbStatus.find(new Document("_id",transaction.getPaymentId())).first();
    }

    public void write(Transaction transaction) {
        Document sample = new Document("_id",transaction.getPaymentId())
                .append("paymentId",transaction.getPaymentId())
                .append("userId",transaction.getUserId())
                .append("isDone",transaction.getIsDone());
        dbStatus.insertOne(sample);
    }

    public void update(Transaction transaction) {
        try {
            Document object = find(transaction);
            if (object != null) {
                Bson updateValue = new Document("isDone", true);
                Bson updateOps = new Document("$set", updateValue);
                dbStatus.updateOne(object, updateOps);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Transaction transaction) {
        // TODO : DELETE DATA FROM DATABASE
    }

    public void startTime(Transaction transaction, long time) {
        DatabaseReference dbFirebase = FirebaseDatabase.getInstance().getReference().child("mongoPoon");
        dbFirebase.child(transaction.getPaymentId()).setValue(transaction,null);
        dbFirebase.child(transaction.getPaymentId()).child("startTime").setValue(time,null);
        dbFirebase.child(transaction.getPaymentId()).child("isDone").setValue(true,null);
    }

    public void stopTime(Transaction transaction, long time) {
        DatabaseReference dbFirebase = FirebaseDatabase.getInstance().getReference().child("mongoPoon");
        dbFirebase.child(transaction.getPaymentId()).child("stopTime").setValue(time,null);
    }

    public void clean() {
        DatabaseReference dbFirebase = FirebaseDatabase.getInstance().getReference().child("mongoPoon");
        dbStatus.deleteMany(new Document());
        dbFirebase.setValue(null,null);
    }

}

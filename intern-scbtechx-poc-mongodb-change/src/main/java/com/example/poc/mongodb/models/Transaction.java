package com.example.poc.mongodb.models;

import lombok.Data;

@Data
public class Transaction {

    private String userId;
    private String paymentId;
    private Boolean isDone;
    private long startTime;
    private long stopTime;

    public Transaction() {
    }

    public Transaction(String userId, String paymentId, Boolean isDone) {
        this.userId = userId;
        this.paymentId = paymentId;
        this.isDone = isDone;
    }

}

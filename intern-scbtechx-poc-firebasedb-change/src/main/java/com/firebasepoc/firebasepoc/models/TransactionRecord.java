package com.firebasepoc.firebasepoc.models;

import lombok.Data;

@Data
public class TransactionRecord {

    private String paymentId;
    private String userId;
    private Boolean done;
    private long startTime;
    private long stopTime;

    public TransactionRecord() {
    }

    public TransactionRecord(String paymentId, String userId, Boolean done, long startTime, long stopTime) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.done = done;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }
}

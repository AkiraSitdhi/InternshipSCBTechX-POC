package com.firebasepoc.firebasepoc.models;

import com.google.firebase.database.IgnoreExtraProperties;
import lombok.Data;

@Data
@IgnoreExtraProperties
public class Transaction {

    private String userId;
    private String paymentId;
    private boolean isDone;

    public Transaction() {
    }

    public Transaction(String userId, String paymentId, boolean isDone) {
        this.userId = userId;
        this.paymentId = paymentId;
        this.isDone = isDone;
    }
}

package com.example.poc.mongodb.service;

import com.example.poc.mongodb.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class RunnableTask implements Runnable {

    private long runTime;
    @Autowired
    TransactionService transactionService;

    public RunnableTask(TransactionService transactionService, long runTime) {
        this.transactionService = transactionService;
        this.runTime = runTime;
    }

    @Override
    public void run() {
        try {
            transactionService.demoTransaction(
                    new Transaction(UUID.randomUUID().toString(), UUID.randomUUID().toString(), false),
                    runTime
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

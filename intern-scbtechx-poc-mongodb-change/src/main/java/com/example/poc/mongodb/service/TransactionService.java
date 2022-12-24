package com.example.poc.mongodb.service;

import com.example.poc.mongodb.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    DatabaseService databaseService;

    public void demoTransaction(Transaction transaction, long runTime) throws InterruptedException {
        databaseService.write(transaction);
        Thread.sleep(runTime);
        databaseService.update(transaction);
        databaseService.startTime(transaction,System.currentTimeMillis());
    }
}

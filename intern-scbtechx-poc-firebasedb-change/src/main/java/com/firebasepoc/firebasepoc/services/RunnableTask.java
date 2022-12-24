package com.firebasepoc.firebasepoc.services;

import com.firebasepoc.firebasepoc.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class RunnableTask implements Runnable {

    Transaction transaction;

    long runTime;

    @Autowired
    private TransactionService transactionService;

    public RunnableTask(TransactionService transactionService, long runTime) {
        this.transactionService = transactionService;
        this.runTime = runTime;
    }

    @Override
    public void run() {
        try {
            transactionService.addTransaction(
                    new Transaction(UUID.randomUUID().toString(), UUID.randomUUID().toString(), false),
                    runTime
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

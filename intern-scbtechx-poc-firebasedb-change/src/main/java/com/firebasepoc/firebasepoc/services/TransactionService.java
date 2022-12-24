package com.firebasepoc.firebasepoc.services;

import com.firebasepoc.firebasepoc.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
public class TransactionService {


    @Autowired
    private WriteService writeService;

    public void addTransaction(Transaction transaction, long randomTime) throws InterruptedException {
        writeService.add(transaction);
        Thread.sleep(randomTime);
        writeService.update(transaction);
    }

}

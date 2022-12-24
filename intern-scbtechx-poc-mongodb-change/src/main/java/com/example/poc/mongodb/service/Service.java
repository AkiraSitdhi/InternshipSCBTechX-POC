package com.example.poc.mongodb.service;

import com.example.poc.mongodb.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private TestService testService;

    public void test() {
        Transaction transaction = new Transaction(UUID.randomUUID().toString(), UUID.randomUUID().toString(), false);
//        databaseService.write(transaction);
//        databaseService.update(transaction);
        try {
            transactionService.demoTransaction(transaction,2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stress(int iterations, boolean isFixedTime) {
        List<Runnable> tasks = new ArrayList<Runnable>();
        int numThreads = 100;
        ExecutorService pool = Executors.newFixedThreadPool(numThreads);

        if (isFixedTime){
            for (int i = 0; i < iterations; ++i) {
                Runnable r = new RunnableTask(transactionService, 2000);
                tasks.add(r);
            }
            for (Runnable task : tasks) {
                pool.execute(task);
            }
        }
        else {
            for (int i = 0; i < iterations; ++i) {
                Runnable r = new RunnableTask(transactionService, ThreadLocalRandom.current().nextLong(1000,10000));
                tasks.add(r);
            }
            for (Runnable task : tasks) {
                pool.execute(task);
            }
        }
    }

    public void clean() {
        databaseService.clean();
    }

    public void export(String fileName) {
        testService.getTestResult(fileName);
    }
}

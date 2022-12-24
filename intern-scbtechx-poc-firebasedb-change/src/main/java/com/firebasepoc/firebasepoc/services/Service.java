package com.firebasepoc.firebasepoc.services;

import com.firebasepoc.firebasepoc.models.Transaction;
import com.firebasepoc.firebasepoc.models.TransactionRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TestService testService;

    public void stressedTest(int iterations, boolean isFixedTime) {
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
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("trans");
        Transaction transaction = new Transaction("DEMO","DEMO",true);
        TransactionRecord transactionRecord = new TransactionRecord("DEMO","DEMO",true,0,0);
        mDatabase.child("status").setValue(null,null);
        mDatabase.child("time").setValue(null,null);
        mDatabase.child("status").child("demo").setValue(transaction,null);
        mDatabase.child("time").child("demo").setValue(transactionRecord,null);
    }

    public void exportResult(String fileName) {
        testService.getTestResult(fileName);
    }

}

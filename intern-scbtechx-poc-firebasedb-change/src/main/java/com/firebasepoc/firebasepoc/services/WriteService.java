package com.firebasepoc.firebasepoc.services;

import com.firebasepoc.firebasepoc.models.Transaction;
import com.google.firebase.database.*;
import com.google.firebase.internal.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriteService {

    @Autowired
    private CallbackService callbackService;

    public void add(Transaction transaction) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("trans");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("done").getValue(Boolean.class).equals(true)) {
                    //callbackService.callbackTransactionCompleted(transaction);
                    mDatabase.child("status").child(transaction.getUserId()).setValue(null,null);
                    //stop time
                    long stopTime = System.currentTimeMillis();
                    mDatabase.child("time").child(transaction.getUserId()).child("stopTime").setValue(stopTime,null);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        mDatabase.child("status").child(transaction.getUserId()).setValue(transaction,null);
        mDatabase.child("status").child(transaction.getUserId()).addValueEventListener(valueEventListener);
        mDatabase.child("time").child(transaction.getUserId()).setValue(transaction,null);
    }

    public void update(Transaction transaction) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("trans");
        mDatabase.child("status").child(transaction.getUserId()).child("done").setValue(true,null);
        mDatabase.child("time").child(transaction.getUserId()).child("done").setValue(true,null);
        //start time
        long startTime = System.currentTimeMillis();
        mDatabase.child("time").child(transaction.getUserId()).child("startTime").setValue(startTime,null);
    }

}

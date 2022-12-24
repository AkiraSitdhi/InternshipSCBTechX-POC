package com.firebasepoc.firebasepoc.services;

import com.firebasepoc.firebasepoc.models.Transaction;
import org.springframework.stereotype.Service;

@Service
public class CallbackService {

    public long callbackTransactionCompleted(Transaction transaction) {
        // DO SOMETHING EX.SENDING NOTIFICATIONS ETC.
        return System.currentTimeMillis();
    }
}

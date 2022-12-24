package com.firebasepoc.firebasepoc.services;

import com.firebasepoc.firebasepoc.models.TransactionRecord;
import com.google.firebase.database.*;
import com.google.firebase.internal.NonNull;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.ArrayList;

@Service
public class TestService {

    public void getTestResult(String fileName) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("trans").child("time");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<TransactionRecord> records = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    records.add(ds.getValue(TransactionRecord.class));
                }
                writeExcel(records, fileName);
                mDatabase.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        mDatabase.addValueEventListener(valueEventListener);
    }

    private void writeExcel(ArrayList<TransactionRecord> records, String fileName) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Result");

        int rowCount = 0;
        Row row = sheet.createRow(rowCount);
        Cell cell = row.createCell(0);
        cell.setCellValue("STATUS");
        cell = row.createCell(1);
        cell.setCellValue("paymentId");
        cell = row.createCell(2);
        cell.setCellValue("userId");
        cell = row.createCell(3);
        cell.setCellValue("startTime");
        cell = row.createCell(4);
        cell.setCellValue("stopTime");
        cell = row.createCell(5);
        cell.setCellValue("totalTime(ms)");

        for (TransactionRecord record : records) {
            row = sheet.createRow(++rowCount);
            writeRow(record, row);
        }

        try (FileOutputStream outputStream = new FileOutputStream("./src/main/resources/result/"+fileName+".xls")) {
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("EXPORT PROCESS FINISHED");
    }

    private void writeRow(TransactionRecord transactionRecord, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(transactionRecord.getDone());

        cell = row.createCell(1);
        cell.setCellValue(transactionRecord.getPaymentId());

        cell = row.createCell(2);
        cell.setCellValue(transactionRecord.getUserId());

        cell = row.createCell(3);
        cell.setCellValue(transactionRecord.getStartTime());

        cell = row.createCell(4);
        cell.setCellValue(transactionRecord.getStopTime());

        cell = row.createCell(5);
        cell.setCellValue(transactionRecord.getStopTime() - transactionRecord.getStartTime());
    }
}

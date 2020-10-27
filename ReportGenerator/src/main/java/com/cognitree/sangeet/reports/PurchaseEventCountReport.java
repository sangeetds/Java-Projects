package com.cognitree.sangeet.reports;

import com.cognitree.sangeet.ReportData;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

class PurchaseEventCountReport implements Report {
    HashMap<Integer, Integer> itemCount;
    String fileName;
    int count;

    public PurchaseEventCountReport() {
        this.count = 0;
        this.itemCount = new HashMap<>();
        this.fileName = "PurchaseReport.txt";
    }

    @Override
    public void aggregate(ReportData reportData) {
        count++;
        itemCount.put(reportData.getItemId(), itemCount.getOrDefault(reportData.getItemId(), 0) + 1);
    }

    @Override
    public void generateReport() {
        FileChannel file;
        ByteBuffer byteBuffer;

        try {
            file = new RandomAccessFile(fileName, "rw").getChannel();
            byteBuffer = file.map(FileChannel.MapMode.READ_WRITE, 0, count * 100);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (Map.Entry<Integer, Integer> itemCountEntry: itemCount.entrySet()) {
            byte[] buffer = (itemCountEntry.getKey() + " " + itemCountEntry.getValue()).getBytes();
            byteBuffer.put(buffer);
            byteBuffer.put("\n".getBytes());
        }
    }

    @Override
    public void openReport() {

    }
}

package com.cognitree.sangeet.reports;

import com.cognitree.sangeet.ReportData;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

class AverageQuantityReport implements Report {
    HashMap<Integer, List<Integer>> averageQuantity;
    String fileName;
    int count;

    public AverageQuantityReport() {
        this.count = 0;
        this.averageQuantity = new HashMap<>();
        this.fileName = "AverageSessionQuantityReport.txt";
    }

    @Override
    public void aggregate(ReportData reportData) {
        count++;
        averageQuantity.computeIfAbsent(reportData.getItemId(), k -> new ArrayList<>());
        averageQuantity.get(reportData.getItemId()).add(reportData.getSessionId());
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

        for (Map.Entry<Integer, List<Integer>> itemCountEntry: averageQuantity.entrySet()) {
            Double avgQuantity = new HashSet<>(itemCountEntry.getValue()).size() / (double) itemCountEntry.getValue().size();
            byte[] buffer = (itemCountEntry.getKey() + " " + avgQuantity).getBytes();
            byteBuffer.put(buffer);
            byteBuffer.put("\n".getBytes());
        }
    }

    @Override
    public void openReport() {

    }
}

package com.cognitree.sangeet.reports;

import com.cognitree.sangeet.ReportData;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DistinctSessionReport implements Report{
    HashMap<Integer, List<Integer>> sessionList;
    String fileName;
    int count;

    public DistinctSessionReport() {
        this.count = 0;
        this.sessionList = new HashMap<>();
        this.fileName = "SessionVsItemId.txt";
    }

    @Override
    public void aggregate(ReportData reportData) {
        count++;
        sessionList.computeIfAbsent(reportData.getItemId(), k -> new ArrayList<>());
        //Time Taking
        sessionList.get(reportData.getItemId()).add(reportData.getSessionId());
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

        for (Map.Entry<Integer, List<Integer>> itemCountEntry: sessionList.entrySet()) {
            byte[] buffer = (itemCountEntry.getKey() + " " + itemCountEntry.getValue()).getBytes();
            byteBuffer.put(buffer);
            byteBuffer.put("\n".getBytes());
        }
    }

    @Override
    public void openReport() {
    }
}

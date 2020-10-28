package com.cognitree.sangeet.reports;

import com.cognitree.sangeet.BuyData;

import java.nio.ByteBuffer;
import java.util.*;

class AverageQuantityReport extends FileBufferUtil implements Report {
    HashMap<Integer, List<Integer>> averageQuantity;
    String fileName;
    int count;

    public AverageQuantityReport() {
        this.count = 0;
        this.averageQuantity = new HashMap<>();
        this.fileName = "AverageSessionQuantityReport.txt";
    }

    @Override
    public void aggregate(BuyData buyData) {
        count++;
        averageQuantity.computeIfAbsent(buyData.getItemId(), k -> new ArrayList<>());
        averageQuantity.get(buyData.getItemId()).add(buyData.getSessionId());
    }

    @Override
    public void generate() {
        ByteBuffer byteBuffer = FileBufferUtil.getByteBuffer(fileName, count * 14);

        averageQuantity.forEach((key, value) -> {
            Double avgQuantity = new HashSet<>(value).size() / (double) value.size();
            byteBuffer.put((key + " " + avgQuantity + "\n").getBytes());
        });

        FileBufferUtil.closeFile();
    }
}

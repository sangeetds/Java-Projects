package com.cognitree.sangeet.reports;

import com.cognitree.sangeet.ReportData;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

class PurchaseEventCountReport extends FileBufferUtil implements Report {
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
    public void generate() {
        ByteBuffer byteBuffer = FileBufferUtil.getByteBuffer(fileName, count);

        for (Map.Entry<Integer, Integer> itemCountEntry: itemCount.entrySet()) {
            byte[] buffer = (itemCountEntry.getKey() + " " + itemCountEntry.getValue()).getBytes();
            byteBuffer.put(buffer);
            byteBuffer.put("\n".getBytes());
        }
    }
}

package com.cognitree.sangeet.reports;

import com.cognitree.sangeet.BuyData;

import java.nio.ByteBuffer;
import java.util.HashMap;

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
    public void aggregate(BuyData buyData) {
        count++;
        itemCount.put(buyData.getItemId(), itemCount.getOrDefault(buyData.getItemId(), 0) + 1);
    }

    @Override
    public void generate() {
        ByteBuffer byteBuffer = FileBufferUtil.getByteBuffer(fileName, count * 12);

        itemCount.forEach((key, value) -> byteBuffer.put((key + " " + value + "\n").getBytes()));

        FileBufferUtil.closeFile();
    }
}

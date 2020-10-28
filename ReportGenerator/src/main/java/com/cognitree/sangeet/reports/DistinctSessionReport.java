package com.cognitree.sangeet.reports;

import com.cognitree.sangeet.BuyData;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class DistinctSessionReport implements Report {
    HashMap<Integer, List<Integer>> sessionList;
    String fileName;
    int count;

    public DistinctSessionReport() {
        this.count = 0;
        this.sessionList = new HashMap<>();
        this.fileName = "SessionVsItemId.txt";
    }

    @Override
    public void aggregate(BuyData buyData) {
        count++;
        sessionList.computeIfAbsent(buyData.getItemId(), k -> new ArrayList<>());
        sessionList.get(buyData.getItemId()).add(buyData.getSessionId());
    }

    @Override
    public void generate() {
        ByteBuffer byteBuffer = FileBufferUtil.getByteBuffer(fileName, count * 20);

        sessionList.forEach((key, value) -> byteBuffer.put((key + " " + value + "\n").getBytes()));

        FileBufferUtil.closeFile();
    }
}

package com.cognitree.sangeet.reports;

import com.cognitree.sangeet.BuyData;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

class DistinctSessionReport implements Report {
    HashMap<Integer, Set<Integer>> sessionList;
    String fileName;
    int count;

    public DistinctSessionReport() {
        this.count = 0;
        this.sessionList = new HashMap<Integer, java.util.Set<Integer>>();
        this.fileName = "SessionVsItemId.txt";
    }

    @Override
    public void aggregate(List<BuyData> buyData) {
        count++;
        sessionList.putAll(buyData.stream().collect(groupingBy(BuyData::getItemId, mapping(BuyData::getSessionId, Collectors.toSet()))));
    }

    @Override
    public void generate() {
        ByteBuffer byteBuffer = FileBufferUtil.getByteBuffer(fileName, count * 20000);

        sessionList.forEach((key, value) -> byteBuffer.put((key + " " + value + "\n").getBytes()));

        FileBufferUtil.closeFile();
    }
}

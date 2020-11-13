package com.cognitree.sangeet.reports;

import com.cognitree.sangeet.BuyData;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

class AverageQuantityReport extends FileBufferUtil implements Report {
    private final HashMap<Integer, List<Integer>> averageQuantity;
    private final String fileName;
    private int count;

    public AverageQuantityReport() {
        this.count = 0;
        this.averageQuantity = new HashMap<>();
        this.fileName = "AverageSessionQuantityReport.txt";
    }

    @Override
    public void aggregate(List<BuyData> buyData) {
        count++;
        averageQuantity.putAll(buyData.stream().collect(groupingBy(BuyData::getItemId, mapping(BuyData::getSessionId, Collectors.toList()))));
    }

    @Override
    public void saveToOutput() {
        try {
            ByteBuffer byteBuffer = FileBufferUtil.getByteBuffer(fileName, count * 20000);

            averageQuantity.forEach((key, value) -> {
                Double avgQuantity = new HashSet<>(value).size() / (double) value.size();
                byteBuffer.put((key + " " + avgQuantity + "\n").getBytes());
            });
        }
        catch(Exception e) {
            System.out.println("Problems writing to the file");
        }
        finally {
            FileBufferUtil.closeFile();
        }
    }
}

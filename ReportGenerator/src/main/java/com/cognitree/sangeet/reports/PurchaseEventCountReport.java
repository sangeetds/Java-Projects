package com.cognitree.sangeet.reports;

import com.cognitree.sangeet.BuyData;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

class PurchaseEventCountReport extends FileBufferUtil implements Report {
    HashMap<Integer, Long> itemCount;
    String fileName;
    int count;

    public PurchaseEventCountReport() {
        this.count = 0;
        this.itemCount = new HashMap<>();
        this.fileName = "PurchaseReport.txt";
    }

    @Override
    public void aggregate(List<BuyData> buyData) {
        count++;
        Map<Integer,Long> temp = buyData.stream().collect(groupingBy(BuyData::getItemId, counting()));
        itemCount.putAll(temp);
    }

    @Override
    public void generate() {
        ByteBuffer byteBuffer = FileBufferUtil.getByteBuffer(fileName, count * 14000);

        itemCount.forEach((key, value) -> byteBuffer.put((key + " " + value + "\n").getBytes()));

        FileBufferUtil.closeFile();
    }
}

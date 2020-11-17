package com.cognitree.sangeet.reports;

import com.cognitree.sangeet.BuyData;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

class PurchaseEventCountReport extends FileBufferUtil implements Report {
    private final HashMap<Integer, Long> itemCount;
    private final String fileName;
    private int count;

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
    public void saveOutput() {
        try {
            ByteBuffer byteBuffer = FileBufferUtil.getByteBuffer(fileName, count * 14000);

            itemCount.forEach((key, value) -> byteBuffer.put((key + " " + value + "\n").getBytes()));
        }
        catch (Exception e) {
            System.out.println("Problems writing to the file");
        }
        finally {
            FileBufferUtil.closeFile();
        }
    }
}

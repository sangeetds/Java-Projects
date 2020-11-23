package com.cognitree.sangeet.threads;

import com.cognitree.sangeet.DataBatch;
import com.cognitree.sangeet.WordFrequency;
import com.cognitree.sangeet.exceptions.SearchWordInvalidException;
import com.cognitree.sangeet.processExecutor.ProcessExecutor;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class WordFrequencyThread extends WordFrequency {
    private final ProcessExecutor processExecutor;
    private final Map<String, Long> wordCountMap;

    public WordFrequencyThread() {
        this.processExecutor = new ProcessExecutor();
        this.wordCountMap = new HashMap<>();
    }

    @Override
    public Long getFrequency(String word) throws Exception {
        Long frequency = this.get(word);

        if (frequency == null) {
            throw new SearchWordInvalidException();
        }

        return frequency;
    }

    @Override
    public void countEveryWords(DataBatch dataBatch) {
        long time = System.nanoTime();
        long secondTime;
        long total = 0;
        for (int index = 0; index < dataBatch.getSize(); index++) {
            String dataLine = dataBatch.take(index);
            secondTime = System.nanoTime();
            Map<String, Long> a = super.calculateFrequency(dataLine);
            total += System.nanoTime() - secondTime;
            a.forEach(dataBatch::put);
        }
        System.out.println("Time taken for just calculating and storing the frequency for " + dataBatch.getSize() + " " + (System.nanoTime() - time) / 1_000_000_000d + " " + Thread.currentThread().getName());
        System.out.println("Time taken just for the calculation part: " + total / 1_000_000_000d);
    }

    public void processFile(BufferedReader fileScanner) throws InterruptedException {
        processExecutor.startThreadProcess(fileScanner, this);
    }

    public void put(String word, Long frequency) {
        this.wordCountMap.put(word, this.wordCountMap.getOrDefault(word, 0L) + frequency);
    }

    protected Long get(String word) {
        return wordCountMap.get(word);
    }

    protected ProcessExecutor getProcessExecutor() {
        return processExecutor;
    }
}

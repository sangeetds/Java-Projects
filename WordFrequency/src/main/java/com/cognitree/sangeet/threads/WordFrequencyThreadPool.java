package com.cognitree.sangeet.threads;

import com.cognitree.sangeet.DataBatch;
import com.cognitree.sangeet.threads.WordFrequencyThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.*;

public class WordFrequencyThreadPool extends WordFrequencyThread {

    public WordFrequencyThreadPool() {
        super();
    }

    public Long getFrequency(String word) throws Exception {
        return super.getFrequency(word);
    }

    public void processFile(BufferedReader fileScanner) {
        try {
            super.getProcessExecutor().startThreadPoolProcess(fileScanner, this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void put(String word, Long frequency) {
        super.put(word, frequency);
    }

    protected Long get(String word) {
        return super.get(word);
    }

    public void countEveryWords(DataBatch dataBatch) {
        super.countEveryWords(dataBatch);
    }
}

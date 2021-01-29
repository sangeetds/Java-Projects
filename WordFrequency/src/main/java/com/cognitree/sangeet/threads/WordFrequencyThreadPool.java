package com.cognitree.sangeet.threads;

import com.cognitree.sangeet.DataBatch;

import java.io.BufferedReader;

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

    public void countEveryWords(DataBatch<String> dataBatch) {
        super.countEveryWords(dataBatch);
    }
}

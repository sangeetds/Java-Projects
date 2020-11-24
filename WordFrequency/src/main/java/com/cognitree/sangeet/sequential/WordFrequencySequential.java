package com.cognitree.sangeet.sequential;

import com.cognitree.sangeet.DataBatch;
import com.cognitree.sangeet.WordFrequency;
import com.cognitree.sangeet.exceptions.SearchWordInvalidException;
import com.cognitree.sangeet.processExecutor.ProcessExecutor;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordFrequencySequential extends WordFrequency {
    private final Map<String, Long> wordCountMap;
    private final List<String> hay;
    private final ProcessExecutor processExecutor;

    public WordFrequencySequential() {
        this.hay = new ArrayList<>();
        this.wordCountMap = new HashMap<>();
        this.processExecutor = new ProcessExecutor();
    }

    public void storeLine(String newLine) {

        this.hay.add(newLine);
    }

    @Override
    public Long getFrequency(String word) throws Exception {
        Long frequency = wordCountMap.get(word);
        this.wordCountMap.forEach((w, value) -> System.out.println(w + " " + value));

        if (frequency == null) {
            throw new SearchWordInvalidException();
        }

        return frequency;
    }

    @Override
    public void countEveryWords(DataBatch dataBatch) {
        this.hay.forEach((dataLine) -> super.calculateFrequency(dataLine).forEach((word, frequency) -> this.wordCountMap.put(word, this.wordCountMap.getOrDefault(word, 0L) + frequency)));
    }

    public void processFile(BufferedReader fileScanner) {
        processExecutor.sequentialProcess(fileScanner, this);
    }

    public int getSize() {
        return this.hay.size();
    }
}

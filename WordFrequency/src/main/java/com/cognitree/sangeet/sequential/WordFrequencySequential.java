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
    private final List<String> hay;
    private final Map<String, Long> frequencyMap;

    public WordFrequencySequential() {
        this.hay = new ArrayList<>();
        this.frequencyMap = new HashMap<>();
    }

    @Override
    public Long getFrequency(String word) throws Exception {
        Long frequency = this.frequencyMap.get(word);

        if (frequency == null) {
            throw new SearchWordInvalidException();
        }

        return frequency;
    }

    @Override
    public void countEveryWords(DataBatch dataBatch) {
        this.hay.forEach((dataLine) -> super.calculateFrequency(dataLine).forEach((word, frequency) -> this.frequencyMap.put(word, this.frequencyMap.getOrDefault(word, 0L) + frequency)));
    }

    public int getSize() {
        return this.hay.size();
    }

    public void storeLine(String newLine) {
        this.hay.add(newLine);
    }

    public void processFile(BufferedReader fileScanner) {
        ProcessExecutor processExecutor = new ProcessExecutor();
        processExecutor.sequentialProcess(fileScanner, this);
    }
}

package com.cognitree.sangeet.fork_join;

import com.cognitree.sangeet.DataBatch;
import com.cognitree.sangeet.exceptions.SearchWordInvalidException;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class WordFrequencyForkJoin extends RecursiveTask<HashMap<String, Long>> {
    private final DataBatch dataBatch;
    private Map<String, Long> wordCountMap;
    private final int lowerBound;
    private int upperBound;

    public WordFrequencyForkJoin() {
        this.dataBatch = new DataBatch();
        this.wordCountMap = new HashMap<>();
        this.lowerBound = 0;
        this.upperBound = 0;
    }

    public WordFrequencyForkJoin(DataBatch dataBatch, int lowerBound, int upperBound) {
        this.dataBatch = dataBatch;
        this.wordCountMap = new HashMap<>();
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public Long getFrequency(String word) throws Exception {
        Long frequency = this.get(word);

        if (frequency == null) {
            throw new SearchWordInvalidException();
        }

        return frequency;
    }

    protected Long get(String word) {
        return wordCountMap.get(word);
    }

    @Override
    protected HashMap<String, Long> compute() {
        if (upperBound - lowerBound <= 100000) {
            HashMap<String, Long> tempMap = new HashMap<>();

            for (int index = this.lowerBound; index < this.upperBound; index++) {
                String dataLine = dataBatch.take(index);
                Arrays.stream(dataLine.split("\\s+")).collect(groupingBy(Function.identity(), counting())).forEach(tempMap::put);
            }

            return tempMap;
        } else {
            int mid = (upperBound + lowerBound) / 2;
            WordFrequencyForkJoin tempWFFJOne = new WordFrequencyForkJoin(this.dataBatch, 0, mid);
            WordFrequencyForkJoin tempWFFJTwo = new WordFrequencyForkJoin(this.dataBatch, mid, this.dataBatch.getSize());
            tempWFFJOne.fork();

            HashMap<String, Long> firstMap = tempWFFJOne.join();
            HashMap<String, Long> secondMap = tempWFFJTwo.compute();

            secondMap.forEach((word, frequency) -> firstMap.put(word, firstMap.getOrDefault(word, 0L) + frequency));

            return firstMap;
        }
    }

    public void processFile(BufferedReader fileScanner) throws Exception {
        String line;
        while ((line = fileScanner.readLine()) != null) {
            storeLine(line);
        }

        this.wordCountMap = this.invoke();
    }

    private void storeLine(String line) {
        this.dataBatch.add(line);
        this.upperBound++;
    }
}

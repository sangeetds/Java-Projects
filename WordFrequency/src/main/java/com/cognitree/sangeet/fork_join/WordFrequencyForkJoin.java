package com.cognitree.sangeet.fork_join;

import com.cognitree.sangeet.exceptions.SearchWordInvalidException;
import com.cognitree.sangeet.processExecutor.ProcessExecutor;

import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class WordFrequencyForkJoin extends RecursiveTask<HashMap<String, Long>> {
    private final List<String> hay;
    private final Map<String, Long> wordCountMap;
    private final int lowerBound;
    private int upperBound;

    public WordFrequencyForkJoin() {
        this.hay = new ArrayList<>();
        this.wordCountMap = new HashMap<>();
        this.lowerBound = 0;
        this.upperBound = 0;
    }

    public WordFrequencyForkJoin(List<String> hay, int lowerBound, int upperBound) {
        this.hay = hay;
        this.wordCountMap = new HashMap<>();
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public Long getFrequency(String word) throws Exception {
        Long frequency = this.wordCountMap.get(word);

        if (frequency == null) {
            throw new SearchWordInvalidException();
        }

        return frequency;
    }

    public void put(String word, Long frequency) {
        this.wordCountMap.put(word, this.wordCountMap.getOrDefault(word, 0L) + frequency);
    }

    public void storeLine(String line) {
        this.hay.add(line);
        this.upperBound++;
    }

    public int getUpperBound() {
        return this.upperBound;
    }

    public int getLowerBound() {
        return this.lowerBound;
    }

    public void processFile(BufferedReader fileScanner) {
        ProcessExecutor processExecutor = new ProcessExecutor();

        processExecutor.forkJoinProcess(fileScanner, this);
    }

    @Override
    protected HashMap<String, Long> compute() {
        if (this.upperBound - this.lowerBound <= 50000) {
            HashMap<String, Long> tempMap = new HashMap<>();
            long currTime = System.nanoTime();
            long time = 0;

            for (int index = this.lowerBound; index < this.upperBound; index++) {
                String dataLine = this.hay.get(index);

                long tempTime = System.nanoTime();
                Map<String, Long> map = Arrays
                        .stream(dataLine.split("\\s+"))
                        .collect(groupingBy(Function.identity(), counting()));
                time += System.nanoTime() - tempTime;

                map.forEach((word, frequency) ->
                        tempMap.put(word, tempMap.getOrDefault(word, 0L) + frequency));
            }

            System.out.println("Time taken for just calculating and storing the frequency for " + (this.upperBound - this.lowerBound) + " lines: " + (System.nanoTime() - currTime) / 1_000_000_000d);
            System.out.println("Time taken for calculating the data: " + time / 1_000_000_000d);

            return tempMap;
        }
        else {
            int mid = (this.upperBound + this.lowerBound) / 2;
            WordFrequencyForkJoin tempWFFJOne = new WordFrequencyForkJoin(this.hay, this.lowerBound, mid);
            WordFrequencyForkJoin tempWFFJTwo = new WordFrequencyForkJoin(this.hay, mid + 1, this.upperBound);
            tempWFFJOne.fork();

            HashMap<String, Long> secondMap = tempWFFJTwo.compute();
            HashMap<String, Long> firstMap = tempWFFJOne.join();

            secondMap.forEach((word, frequency) -> firstMap.put(word, firstMap.getOrDefault(word, 0L) + frequency));

            return firstMap;
        }
    }
}

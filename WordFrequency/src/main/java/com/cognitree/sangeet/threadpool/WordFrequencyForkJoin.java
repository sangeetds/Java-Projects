package com.cognitree.sangeet.threadpool;

import com.cognitree.sangeet.exceptions.EmptyWordException;
import com.cognitree.sangeet.exceptions.LineException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.cognitree.sangeet.exceptions.EmptyWordException.SearchWordInvalidException;

public class WordFrequencyForkJoin extends RecursiveTask<Long> {
    private final List<String> hay;
    private long lines;
    private String needle;

    public WordFrequencyForkJoin() {
        this.hay = new ArrayList<>();
        this.lines = 0;
    }

    public WordFrequencyForkJoin(List<String> newHay) {
        this.hay = newHay;
        this.lines = 0;
    }

    public void storeLine(String newLine) throws Exception {
        if (newLine == null) {
            throw LineException.InvalidLineException();
        }

        synchronized (this.hay) {
            this.hay.add(newLine);
            this.hay.notifyAll();
        }
    }

    public void setWord(String needle) {
        this.needle = needle;
    }

    @Override
    protected Long compute() {
        if (this.needle == null) {
            try {
                throw SearchWordInvalidException();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        if (this.hay.size() <= 100) {
            long frequency = 0;

            for (String line: this.hay) {
                for (String splitLine : line.split("\\s+")) {
                    if (splitLine.equalsIgnoreCase(needle)) {
                        frequency++;
                    }
                }
            }

            return frequency;
        }
        else {
            int mid = this.hay.size() / 2;
            WordFrequencyForkJoin tempWFFJOne = new WordFrequencyForkJoin(this.hay.subList(0, mid));
            WordFrequencyForkJoin tempWFFJTwo = new WordFrequencyForkJoin(this.hay.subList(mid, this.hay.size()));
            tempWFFJOne.setWord(this.needle);
            tempWFFJTwo.setWord(this.needle);
            tempWFFJOne.fork();

            return tempWFFJTwo.compute() + tempWFFJOne.join();
        }
    }
}

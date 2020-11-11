package com.cognitree.sangeet.sequential;

import com.cognitree.sangeet.WordFrequency;
import com.cognitree.sangeet.exceptions.LineException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WordFrequencySequential extends WordFrequency {
    private final List<String> hay;

    public WordFrequencySequential() {
        this.hay = new ArrayList<>();
    }

    @Override
    public void storeLine(String newLine) throws Exception {
        if (newLine == null) {
            throw LineException.InvalidLineException();
        }

        this.hay.add(newLine);
    }

    public long reportCaseInsensitiveWordCount(String needle) {
        long frequency = 0;

        for (String line: this.hay) {
            frequency += super.getFrequency(needle, line);
        }

        return frequency;
    }

    public long reportCaseSensitiveWordCount(String needle) {
        long frequency = 0;

        for (String currentLine : this.hay) {
            for (String splitLine : currentLine.split("\\s+")) {
                if (splitLine.equals(needle)) {
                    frequency++;
                }
            }
        }

        return frequency;
    }

    public long reportStreamCaseInsensitiveWordCount(String needle) {
        return this.hay.stream().flatMap(line -> Stream.of(line.split("\\s+"))).filter(str -> str.equalsIgnoreCase(needle)).count();
    }

    public long reportStreamCaseSensitiveWordCount(String needle) {
        return this.hay.stream().flatMap(line -> Stream.of(line.split("\\s+"))).filter(str -> str.equals(needle)).count();
    }
}

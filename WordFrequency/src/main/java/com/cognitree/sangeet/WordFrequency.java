package com.cognitree.sangeet;

import java.util.*;
import java.util.stream.Stream;

public class WordFrequency {
    private final List<String> hay;
    private long lines;
    private boolean linesOver;

    public WordFrequency() {
        this.hay = Collections.synchronizedList(new ArrayList<>());
        this.lines = 0;
        this.linesOver = false;
    }

    public void setLinesOver() {
        this.linesOver = true;
    }

    public void storeLine(String newLines) {
        synchronized (this.hay) {
            this.hay.add(newLines);

            if (this.lines < (long) this.hay.size()) {
                this.hay.notifyAll();
            }
        }
    }

    public long reportCaseInsensitiveWordCount(String needle) {
        long frequency = 0;

        while (true) {
            String currentLine = getCurrentLine(this.lines);

            if (currentLine == null) break;

            for (String word: currentLine.split("\\s+")) {
                if (word.equalsIgnoreCase(needle)) {
                    frequency++;
                }
            }

            this.lines++;
        }

        System.out.println("Lines: " + this.lines);

        return frequency;
    }

    private String getCurrentLine(long line) {
        synchronized (this.hay) {
            if (this.linesOver) {
                return null;
            }

            if (line >= this.hay.size()) {
                try {
                    System.out.println("Wait");
                    this.hay.wait();
                    System.out.println("Producing");
                }
                catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                }
            }

            return this.hay.get((int) line);
        }
    }

    public long reportCaseSensitiveWordCount(String needle) {
        long frequency = 0;

        for (String currentLine: this.hay) {
            for (String splitLine: currentLine.split("\\s+")) {
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

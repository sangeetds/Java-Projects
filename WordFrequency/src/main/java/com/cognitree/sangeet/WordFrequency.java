package com.cognitree.sangeet;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordFrequency {
    final List<String> hay;
    HashMap<String, Long> hayMap;
    long lines;

    public WordFrequency() {
        this.hay = Collections.synchronizedList(new ArrayList<>());
        this.hayMap = new HashMap<>();
        this.lines = 0;
    }

    public void storeLine(String newLines) {
        synchronized (hay) {
            hay.add(newLines);

            if (lines < (long) hay.size()) {
                hay.notifyAll();
            }
        }
    }

    public long reportCaseInsensitiveWordCount(String needle) {
        synchronized (hay) {
            long frequency = 0;

            if (lines >= hay.size()) {
                try {
                    System.out.println("No lines to process");
                    hay.wait();
                    System.out.println("Now processing");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (String currentLine : hay) {
                lines++;

                for (String splitLine : currentLine.split("\\s+")) {
                    if (splitLine.equalsIgnoreCase(needle)) {
                        frequency++;
                    }
                }

                if (lines >= hay.size()) {
                    try {
                        System.out.println("No lines to process");
                        hay.wait();
                        System.out.println("Now processing");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("lines:" + lines);

            return frequency;
        }
    }

    public long reportCaseSensitiveWordCount(String needle) {
        long frequency = 0;

        for (String currentLine: hay) {
            for (String splitLine: currentLine.split("\\s+")) {
                if (splitLine.equals(needle)) {
                    frequency++;
                }
            }
        }

        return frequency;
    }

    public long reportStreamCaseInsensitiveWordCount(String needle) {
        return hay.stream().flatMap(line -> Stream.of(line.split("\\s+"))).filter(str -> str.equalsIgnoreCase(needle)).count();
    }

    public long reportStreamCaseSensitiveWordCount(String needle) {
        return hay.stream().flatMap(line -> Stream.of(line.split("\\s+"))).filter(str -> str.equals(needle)).count();
    }

    public long reportDirectWordCount(String needle) {
        return hayMap.getOrDefault(needle, 0L);
    }
}

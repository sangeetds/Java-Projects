package com.cognitree.sangeet;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordFrequency {
    List<String> hay;
    HashMap<String, Long> hayMap;

    public WordFrequency() {
        this.hay = new ArrayList<>();
        this.hayMap = new HashMap<>();
    }

    public void storeLine(List<String> lines) {
        hay.addAll(lines);
    }

    public void storeFrequency(List<String> lines) {
        lines.forEach( line -> {
            Map<String, Long> temp = Arrays.stream(line.split("\\s+")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            temp.forEach((key, value) -> hayMap.put(key, hayMap.getOrDefault(key, 0L) + value));
        });
    }

    public long reportCaseInsensitiveWordCount(String needle) {
        long frequency = 0;

        long l = 0L;

        for (String currentLine: hay) {
            for (String splitLine: currentLine.split("\\s+")) {
                l++;
                if (splitLine.equalsIgnoreCase(needle)) {
                    frequency++;
                }
            }
        }

        System.out.println("words:" + l);

        return frequency;
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

package com.cognitree.sangeet;

import com.cognitree.sangeet.exceptions.ProcessNotStartedException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public abstract class WordFrequency {

    public long ind = 0;
//    abstract public void storeLine(String line);

    abstract public Long getFrequency(String word) throws Exception;

    abstract public void countEveryWords(DataBatch dataBatch) throws ProcessNotStartedException;

    public Map<String, Long> calculateFrequency(String dataLine) {
        HashMap<String, Long> a = new HashMap<>();
        StringBuilder s = new StringBuilder();

        long time = System.nanoTime();
        for (int index = 0; index < dataLine.length(); index++) {
            char c = dataLine.charAt(index);
            if (c == ' ' && !s.isEmpty()) {
                a.put(s.toString(), a.getOrDefault(s.toString(), 0L) + 1);
                s = new StringBuilder();
            } else {
                s.append(c);
            }
        }
        ind += System.nanoTime() - time;

        return a;
    }
}

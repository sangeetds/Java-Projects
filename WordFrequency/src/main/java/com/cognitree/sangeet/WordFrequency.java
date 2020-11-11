package com.cognitree.sangeet;

import java.util.List;

public abstract class WordFrequency {
    abstract public void storeLine(String newLine) throws Exception;
    public long getFrequency(String needle, String smallHay) {
        long frequency = 0;

        for (String splitLine : smallHay.split("\\s+")) {
            if (splitLine.equalsIgnoreCase(needle)) {
                frequency++;
            }
        }

        return frequency;
    }
}

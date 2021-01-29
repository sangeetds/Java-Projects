package com.cognitree.sangeet;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public abstract class WordFrequency {

    abstract public Long getFrequency(String word) throws Exception;

    abstract public void countEveryWords(DataBatch<String> dataBatch);

    public Map<String, Long> calculateFrequency(String dataLine) {
        return Arrays
                .stream(dataLine.split("\\s+"))
                .collect(groupingBy(Function.identity(), counting()));
    }
}

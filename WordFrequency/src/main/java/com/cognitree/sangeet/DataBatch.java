package com.cognitree.sangeet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

final public class DataBatch {
    private final List<String> hay;
    private final HashMap<String, Long> wordCountMap;

    public DataBatch() {
        this.hay = new ArrayList<>();
        this.wordCountMap = new HashMap<>();
    }

    public void add(String line) {
        this.hay.add(line);
    }

    public int getSize() {
        return this.hay.size();
    }

    public boolean isEmpty() {
        return this.getSize() == 0;
    }

    public String take(int index) {
        return this.hay.get(index);
    }

    public void clear() {
        this.hay.clear();
    }

    public void put(String word, Long frequency) {
        this.wordCountMap.put(word, this.wordCountMap.getOrDefault(word, 0L) + frequency);
    }

    public Long getWord(String word) {
        return this.wordCountMap.get(word);
    }

    public HashMap<String, Long> getMap() {
        return this.wordCountMap;
    }

    public DataBatch subBatch(int mid, int size) {
        DataBatch tempBatch = new DataBatch();

        for (int index = mid; index < size; index++) {
            tempBatch.add((this.take(index)));
        }

        return tempBatch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataBatch dataBatch = (DataBatch) o;
        return hay.equals(dataBatch.hay) &&
                wordCountMap.equals(dataBatch.wordCountMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hay, wordCountMap);
    }

    @Override
    public String toString() {
        return "DataBatch{" +
                "hay=" + hay +
                ", wordCountMap=" + wordCountMap +
                '}';
    }
}

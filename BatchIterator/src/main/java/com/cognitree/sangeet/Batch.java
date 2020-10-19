package com.cognitree.sangeet;

import java.util.ArrayList;
import java.util.List;

public class Batch<T> {
    private final List<T> iterableData;

    public <E> Batch(int size, int index, List<E> data) {
        iterableData = new ArrayList<>(size);
        getBatch(index, size, data);
    }


    private <E> void getBatch(int index, int size, List<E> dataStore) {
        int endPoint = index + size;

        while (index < endPoint) {
            this.iterableData.add((T) dataStore.get(index));
            index++;
        }
    }

    @Override
    public String toString() {
        return iterableData.toString();
    }
}

package com.cognitree.sangeet;

import main.com.cognitree.sangeet.Batch;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class StreamingStore<T> {
    private final List<T> data;
    private int modification;

    public StreamingStore() {
        this.data = new ArrayList<>();
        this.modification = 0;
    }

    public void storeData(T dataPoint) {
        this.data.add(dataPoint);
        this.modification++;
    }

    public T getData(int point) {
        return this.data.get(point);
    }

    public void removeData(T dataPoint) {
        this.data.remove(dataPoint);
        this.modification++;
    }

    public Iterator<Batch<T>> getIterator(int batchSize) {
        return new BatchIterator<>(batchSize);
    }

    private class BatchIterator<E> implements Iterator<Batch<E>>, Iterable<Batch<E>> {
        int index;
        int size;
        int currentModification;

        public BatchIterator(int len) {
            this.size = len;
            this.index = 0;
            this.currentModification = modification;
        }

        @Override
        public Iterator<Batch<E>> iterator() {
            return this;
        }

        @Override
        public boolean hasNext() {
            return this.index + this.size <= data.size();
        }

        @Override
        public Batch<E> next() {
            if (this.currentModification != modification) {
                throw new ConcurrentModificationException();
            }
            Batch<E> currBatch = new Batch<>(size, index, data);

            this.index ++;

            return currBatch;
        }
    }
}

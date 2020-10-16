package com.cognitree.sangeet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataStore<T> {
    private final List<T> data;

    public DataStore() {
        this.data = new ArrayList<>();
    }

    public void storeData(T dataPoint) {
        this.data.add(dataPoint);
    }

    public T getData(int point) {
        return this.data.get(point);
    }

    public Iterator<Batch<T>> getIterator(int batchSize) {
        return new BatchIterator<>(batchSize);
    }

    private class BatchIterator<E> implements Iterator<Batch<E>>, Iterable<Batch<E>> {
        int index;
        int size;

        public BatchIterator(int len) {
            this.size = len;
            this.index = 0;
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
//            System.out.println(index + " " + size + " " + data.get(0));
//            if (!hasNext()) {
//                throw
//            }

            Batch<E> currBatch = new Batch<E>(size, index, data);

            index += size;

            return currBatch;
        }
    }
}

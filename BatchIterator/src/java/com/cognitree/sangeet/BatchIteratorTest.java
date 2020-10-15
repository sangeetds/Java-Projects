package com.cognitree.sangeet;

import java.util.Iterator;

public class BatchIteratorTest {
    public static void main(String[] args) {
        DataStore<Integer> d = new DataStore<>();
        d.storeData(1);
        d.storeData(1);
        d.storeData(1);

        Iterator<Batch<Integer>> dataIterator = d.getIterator(3);

        while (dataIterator.hasNext()) {
            System.out.println(dataIterator.next());
        }
    }
}

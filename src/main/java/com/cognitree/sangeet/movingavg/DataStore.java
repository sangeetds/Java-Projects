package com.cognitree.sangeet.movingavg;

import java.util.ArrayList;
import java.util.List;

// Naming might not be perfect
public class DataStore {
    public final List<Double> inputList;
    private final int maxSize;
    private int head; // Head index to keep constant size.
    private int tail; // Tail index to keep constant size

    public DataStore(int windowSize) {
        this.maxSize = windowSize;
        this.inputList = new ArrayList<>(windowSize);
        this.head = 0;
        this.tail = 0;
    }

    // To get data at the tail.
    public double getInitialData() {
        double data = this.inputList.get(this.tail);
        this.tail = (this.tail + 1) % this.maxSize;

        return data;
    }

    public int getSize() {
        return inputList.size();
    }

    // Could have went with resizing the input list
    // by constantly adding and removing data.
    // Went with a constant size as adding and removing
    // data might result in resizing list.
    public void addData(double num) {
        if (this.inputList.size() < this.maxSize) {
            this.inputList.add(num);
        }
        else {
            this.inputList.set(this.head, num);
        }

        this.head = (this.head + 1) % this.maxSize;
    }
}

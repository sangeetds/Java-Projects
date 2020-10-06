package com.cognitree.sangeet.movingavg;

public class MovingAverage<N> {
    private double runningSum; // to keep the sum at a particular point
    private final int windowSize;
    private final DataStore<N> inputStore; // to hold all the input data

    public MovingAverage(int windowSize) {
        this.runningSum = 0;
        this.windowSize = windowSize;
        this.inputStore = new DataStore<>(windowSize);
    }

    public void calculateMovingAverage(N num) {
        inputStore.addData(num);
        this.runningSum += (double) num;
    }

    // Not storing the average to save space and only
    // calculating when asked.
    public double getMovingAverage() {
        double ret = this.runningSum / this.windowSize;
        this.runningSum -= (double) this.inputStore.getInitialData();

        return ret;
    }

    // To check whether `windowSize` amount of input has been
    // given or not.
    public boolean ready() {
        return inputStore.getSize() >= this.windowSize;
    }
}



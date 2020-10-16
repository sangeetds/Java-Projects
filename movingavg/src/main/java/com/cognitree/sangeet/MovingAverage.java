package com.cognitree.sangeet;

public class MovingAverage<N extends Number>  {
    private double runningSum; // to keep the sum at a particular point
    private final int windowSize;
    private final FixedCircularList<N> inputStore; // to hold all the input data

    public MovingAverage(int windowSize) {
        this.runningSum = 0;
        this.windowSize = windowSize;
        this.inputStore = new FixedCircularList<>(windowSize);
    }

    public void calculateMovingAverage(N num) {
        inputStore.addData(num);
        this.runningSum += (double) num;
    }

    // Not storing the average to save space and only
    // calculating when asked.
    public double getMovingAverage() {
        double movingAverage = this.runningSum / this.windowSize;
        this.runningSum -= (double) this.inputStore.getInitialData();

        return movingAverage;
    }

    // To check whether `windowSize` amount of input has been
    // given or not.
    public boolean ready() {
        return inputStore.getSize() >= this.windowSize;
    }
}



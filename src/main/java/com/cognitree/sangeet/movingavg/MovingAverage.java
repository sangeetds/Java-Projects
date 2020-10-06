package com.cognitree.sangeet.movingavg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovingAverage {
    int i;
    int runningSum;
    int windowSize;
    List<Integer> input;

    public MovingAverage(int windowSize) {
        this.i = 0;
        this.runningSum = 0;
        this.windowSize = windowSize;
        this.input = new ArrayList<>();
    }

    public void getMovingAverage() {
        this.runningSum += this.input.get(this.i);
        this.i++;

        if (this.i >= this.windowSize) {
            printMovingAverage();
            this.runningSum -= this.input.get(this.i - this.windowSize);
        }
    }

    private void printMovingAverage() {
        double average = (double) this.runningSum / this.windowSize;
        System.out.println(average);
    }
}



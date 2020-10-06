package com.cognitree.sangeet.movingavg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovingAverage {
    int i;
    int runningSum;
    int windowSize;

    public MovingAverage(int windowSize) {
        this.i = 0;
        this.runningSum = 0;
        this.windowSize = windowSize;
    }

    public void getMovingAverage(List<Integer> input) {
        this.runningSum += input.get(this.i);
        this.i++;

        if (this.i >= this.windowSize) {
            printMovingAverage();
            this.runningSum -= input.get(this.i - this.windowSize);
        }
    }

    private void printMovingAverage() {
        double average = (double) this.runningSum / this.windowSize;
        System.out.println(average);
    }
}



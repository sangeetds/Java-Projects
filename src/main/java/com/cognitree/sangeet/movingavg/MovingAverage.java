package com.cognitree.sangeet.movingavg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovingAverage {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int windowSize = sc.nextInt();
        List<Integer> input = new ArrayList<>();

        getMovingAverage(sc, windowSize, input);
    }

    private static void getMovingAverage(Scanner sc, int windowSize, List<Integer> input) {
        int i = 0;
        int runningSum = 0;

        // exits when not a number
        while (true) {
            if (getInput(sc, input)) {
                runningSum += input.get(i);
                i++;

                if (i >= windowSize) {
                    printMovingAverage(i, input, runningSum, windowSize);
                    runningSum -= input.get(i - windowSize);
                }
            }
            else break;
        }
    }

    private static void printMovingAverage(
            int i, List<Integer> input, int runningSum, int windowSize
    ) {
        double average = (double) runningSum / windowSize;
        System.out.println(average);
    }

    private static boolean getInput(Scanner sc, List<Integer> input) {
        try {
            int num = Integer.parseInt(sc.next());
            input.add(num);
        } catch (NumberFormatException e) {
            System.out.println("Exiting");
            return false;
        }
        return true;
    }
}

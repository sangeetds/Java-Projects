package com.cognitree.sangeet.movingavg;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MovingAverageApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the window size:");
        int windowSize = sc.nextInt();
        MovingAverage<Double> moveAvg = new MovingAverage<>(windowSize);

        while (true) {
            Double num = getInput(sc);
            if (num != null) {
                moveAvg.calculateMovingAverage(num);

                // Driver checks whether enough data has been fed
                // to input moving average
                if (moveAvg.ready()) {
                    System.out.println(moveAvg.getMovingAverage());
                }
            }
            else break;
        }
    }

    // Take inputs from user. Exits when proper number is not give as input
    private static Double getInput(Scanner sc) {
        try {
            return sc.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Input needs to be a number. Exiting...");
            return null;
        }
    }
}

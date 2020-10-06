package com.cognitree.sangeet.movingavg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int windowSize = sc.nextInt();
        MovingAverage moveAvg = new MovingAverage(windowSize);

        while (true) {
            if (getInput(sc) != null) {
                moveAvg.getMovingAverage();
            }
            else break;
        }
    }

    private static Integer getInput(Scanner sc) {
        try {
            return sc.nextInt();
        } catch (NumberFormatException e) {
            System.out.println("Exiting");
            return null;
        }
    }
}

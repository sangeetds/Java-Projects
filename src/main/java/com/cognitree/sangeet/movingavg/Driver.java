package com.cognitree.sangeet.movingavg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int windowSize = sc.nextInt();
        List<Integer> input = new ArrayList<>();
        MovingAverage moveAvg = new MovingAverage(windowSize);

        while (true) {
            if (getInput(sc, input)) {
                moveAvg.getMovingAverage(input);
            }
            else break;
        }
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

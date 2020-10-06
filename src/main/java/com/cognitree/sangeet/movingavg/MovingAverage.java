package com.cognitree.sangeet.movingavg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovingAverage {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int windowSize = sc.nextInt();
        List<Integer> input = new ArrayList<>();

        int i = 0;
        int j = 0;
        int runningSum = 0;

        // exits when not a number
        while (true) {
            try {
                int num = Integer.parseInt(sc.next());
                input.add(num);
            }
            catch (NumberFormatException e) {
                System.out.println("Exiting");
                break;
            }

            runningSum += input.get(i);
            i++;

            if (i >= windowSize) {
                double average = (double) runningSum / windowSize;
                System.out.println(average);

                runningSum -= input.get(j);
                j++;
            }
        }
    }
}

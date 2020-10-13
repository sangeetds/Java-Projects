package com.cognitree.sangeet.interview;

import java.util.Arrays;

public class FirstWarmup {
    public static void main(String[] args) {
        int pivot = 5;
        int[] arr = { -1, -23, 10, 2, 15, 2, 35, 38, 12, 80 };
        int[] firstArr = new int[arr.length - pivot];
        int[] secondArr = new int[pivot];

        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        int l = 0;

        for (int k = 0; k < arr.length; k++) {
            if (k < arr.length - pivot) {
                firstArr[k] = arr[k];
            }
            else {
                secondArr[l] = arr[k];
                l++;
            }
        }

        System.out.println(Arrays.toString(firstArr));
        System.out.println(Arrays.toString(secondArr));
    }
}

package com.cognitree.sangeet;

public class SecondWarmup {
    public static void main(String[] args) {
        double minprice = Double.MAX_VALUE;
        double ans = 0;
        double start = 0.0;
        double end = 0.0;

        double[][] arr = { { 2.0, 17.5 }, { 3.0, 7.9 },
                { 4.0, 8.0 }, { 5.0, 1.5 } , { 6.0, 7.0 } };

        for (double[] timeValue: arr) {
            if (timeValue[1] < minprice)
                minprice = timeValue[1];
            else if (timeValue[1] - minprice > ans)
                ans = timeValue[1] - minprice;
        }

        System.out.println(ans);
    }

//    public static double recursion(double[] arr, int i, int hasStock) {
//        if (i > arr.length) {
//            return 0;
//        }
//
//        double value;
//
//        if (hasStock == 1) {
//            value = Math.max(recursion(arr, i + 1, 1), recursion(arr, i + 1, 0) + arr[i]);
//        }
//        else {
//            value = Math.max(recursion(arr, i + 1, 0), recursion(arr, i + 1, 1) - arr[i]);
//        }
//
//        return value;
//    }
}

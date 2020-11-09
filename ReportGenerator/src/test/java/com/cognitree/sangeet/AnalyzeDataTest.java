package com.cognitree.sangeet;

import com.cognitree.sangeet.AnalyzeData;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class AnalyzeDataTest {
    public static void main(String[] args) throws IOException {
        BufferedReader fileScanner;

        try {
            fileScanner = new BufferedReader(new InputStreamReader(new FileInputStream("yoochoose-buys.txt"), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

//        System.out.println(System.nanoTime() / 1_000_000);
        AnalyzeData analyzeData = new AnalyzeData(fileScanner);
//        System.out.println(System.nanoTime() / 1_000_000);
        analyzeData.generateAllReports();
//        System.out.println(System.nanoTime() / 1_000_000);
    }
}
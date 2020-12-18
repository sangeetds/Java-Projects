package com.cognitree.sangeet.processExecutor;

import com.cognitree.sangeet.DataBatch;
import com.cognitree.sangeet.fork_join.WordFrequencyForkJoin;
import com.cognitree.sangeet.sequential.WordFrequencySequential;
import com.cognitree.sangeet.threads.WordFrequencyThread;
import com.cognitree.sangeet.threads.WordFrequencyThreadPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ProcessExecutor {
    public void sequentialProcess(BufferedReader fileScanner, WordFrequencySequential wordFrequencySequential) {
        String currentLine = null;
        long time = System.nanoTime();

        while (true) {
            try {
                if ((currentLine = fileScanner.readLine()) == null) break;
            } catch (IOException e) {
                System.out.println("Problem with the file input at " + wordFrequencySequential.getSize());
            }

            wordFrequencySequential.storeLine(currentLine);
        }

        System.out.println("Time taken for processing the file: " + (System.nanoTime() - time) / 1_000_000_000d);

        time = System.nanoTime();
        wordFrequencySequential.countEveryWords(null);
        System.out.println("Time taken for getting the frequency(whole): " + (System.nanoTime() - time) / 1_000_000_000d);
    }

    public void startThreadProcess(BufferedReader fileScanner, WordFrequencyThread wordFrequencyThread) throws InterruptedException {
        List<Thread> workerThreads = new ArrayList<>();
        List<DataBatch> batches = new ArrayList<>();
        int threadIndex = 0;
        batches.add(new DataBatch());

        long time = System.nanoTime();

        while (true) {
            String currentLine = null;
            try {
                if ((currentLine = fileScanner.readLine()) == null) break;
            } catch (IOException e) {
                System.out.println("Problem with the file input at " + batches.get(threadIndex).getSize() * (threadIndex + 1));
            }

            batches.get(threadIndex).add(currentLine);

            if (batches.get(threadIndex).getSize() >= 75000) {
                DataBatch currBatch = batches.get(threadIndex);
                workerThreads.add(new Thread(() -> wordFrequencyThread.countEveryWords(currBatch)));
                workerThreads.get(threadIndex++).start();
                batches.add(new DataBatch());
            }
        }

        DataBatch currBatch = batches.get(threadIndex);
        workerThreads.add(new Thread(() -> wordFrequencyThread.countEveryWords(currBatch)));
        workerThreads.get(threadIndex).start();

        System.out.println("Time taken for processing the file: " + (System.nanoTime() - time) / 1_000_000_000d);

        time = System.nanoTime();
        for (Thread thread : workerThreads) {
            thread.join();
        }

        pollData(wordFrequencyThread, batches, time);
    }

    public void startThreadPoolProcess(BufferedReader fileScanner, WordFrequencyThreadPool wordFrequencyThreadPool) throws InterruptedException {
        ExecutorService exService = Executors.newFixedThreadPool(4);
        List<DataBatch> batches = new ArrayList<>();
        int threadIndex = 0;
        batches.add(new DataBatch());

        long time = System.nanoTime();

        while (true) {
            String currentLine = null;
            try {
                if ((currentLine = fileScanner.readLine()) == null) break;
            } catch (IOException e) {
                System.out.println("Problem with the file input at " + batches.get(threadIndex).getSize() * (threadIndex + 1));
            }

            batches.get(threadIndex).add(currentLine);

            if (batches.get(threadIndex).getSize() >= 75000) {
                DataBatch currBatch = batches.get(threadIndex);
                exService.submit(() -> wordFrequencyThreadPool.countEveryWords(currBatch));
                batches.add(new DataBatch());
                threadIndex++;
            }
        }

        DataBatch currBatch = batches.get(threadIndex);
        exService.submit(() -> wordFrequencyThreadPool.countEveryWords(currBatch));

        System.out.println("Time taken for processing the file : " + (System.nanoTime() - time) / 1_000_000_000d);

        time = System.nanoTime();
        exService.shutdown();
        exService.awaitTermination(5000, TimeUnit.MILLISECONDS);

        pollData(wordFrequencyThreadPool, batches, time);
    }

    private void pollData(WordFrequencyThread wordFrequencyThread, List<DataBatch> batches, long timeB) {
        System.out.println("Time taken for getting the frequency(whole): " + (System.nanoTime() - timeB) / 1_000_000_000d);

        long time = System.nanoTime();
        batches.forEach(batch -> batch
                .getMap()
                .forEach(wordFrequencyThread::put));

        System.out.println("Time taken for polling the data: " + (System.nanoTime() - time) / 1_000_000_000d);
    }

    public void forkJoinProcess(BufferedReader fileScanner, WordFrequencyForkJoin wordFrequencyForkJoin) {
        String line = null;
        ForkJoinPool forkJoinPool = new ForkJoinPool(8);

        long time = System.nanoTime();
        while (true) {
            try {
                if ((line = fileScanner.readLine()) == null) break;
            } catch (IOException e) {
                System.out.println("Problem with the file input at " + wordFrequencyForkJoin.getUpperBound());
            }
            wordFrequencyForkJoin.storeLine(line);
        }
        System.out.println("Time taken to read the file: " + (System.nanoTime() - time) / 1_000_000_000d);

        time = System.nanoTime();
        HashMap<String, Long> frequencyResult = forkJoinPool.invoke(wordFrequencyForkJoin);
        System.out.println("Time taken for the fork join action: " + (System.nanoTime() - time) / 1_000_000_000d);

        time = System.nanoTime();
        frequencyResult.forEach(wordFrequencyForkJoin::put);
        System.out.println("Time taken for polling the data: " + (System.nanoTime() - time) / 1_000_000_000d);
    }
}

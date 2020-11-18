package com.cognitree.sangeet.threadpool;

import com.cognitree.sangeet.threads.WordFrequencyThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.*;

public class WordFrequencyThreadPool extends WordFrequencyThread {

    public WordFrequencyThreadPool() {
        super();
    }

    public Long reportThreadPoolCountFuture(BufferedReader fileScanner, String needle) {
        ExecutorService exService = Executors.newFixedThreadPool(32);
        System.out.println("Thread with pool and future test starting at: " + (new Date()).toString().split("\\s+")[3]);

        Callable<Void> c1 = () -> {
            while (true) {
                final String currLine;
                try {
                    currLine = fileScanner.readLine();
                } catch (IOException e) {
                    break;
                }

                if (currLine == null) break;

                try {
                    super.storeLine(currLine);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            setLinesOver();
            return null;
        };

        Callable<Long> c2 = () -> super.reportCaseInsensitiveWordCount(needle);

        exService.submit(c1);
        Future<Long> freq = exService.submit(c2);

        try {
            return freq.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return 0L;
        }
        finally {
            exService.shutdown();
            try {
                if (!exService.awaitTermination(10000, TimeUnit.MILLISECONDS)) {
                    exService.shutdownNow();
                }
            } catch (InterruptedException e) {
                exService.shutdownNow();
            }
        }
    }

    public void reportThreadPoolCount(BufferedReader fileScanner, String needle) {
        ExecutorService exService = Executors.newFixedThreadPool(32);
        System.out.println("Thread with pool test starting at: " + (new Date()).toString().split("\\s+")[3]);

        exService.execute(() -> {
            while (true) {
                final String currLine;
                try {
                    currLine = fileScanner.readLine();
                } catch (IOException e) {
                    break;
                }

                if (currLine == null) break;

                try {
                    super.storeLine(currLine);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            setLinesOver();
        });

        exService.submit(() -> System.out.println("Words counted with pool and without future " + super.reportCaseInsensitiveWordCount(needle) + " finished at: " + (new Date()).toString().split("\\s+")[3]));

        exService.shutdown();
        try {
            if (!exService.awaitTermination(10000, TimeUnit.MILLISECONDS)) {
                exService.shutdownNow();
            }
        } catch (InterruptedException e) {
            exService.shutdownNow();
        }
    }

    @Override
    public void storeLine(String newLine) throws Exception {
        super.storeLine(newLine);
    }

    public long reportCaseInsensitiveWordCount(String needle) {
        return super.reportCaseInsensitiveWordCount(needle);
    }
}

package com.cognitree.sangeet.threadpool;

import com.cognitree.sangeet.threads.WordFrequencyThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.*;

public class WordFrequencyThreadPool extends WordFrequencyThread {
    private final ExecutorService exService;

    public WordFrequencyThreadPool() {
        super();
        this.exService = Executors.newFixedThreadPool(2);
    }

    public Long reportThreadPoolCountFuture(BufferedReader fileScanner, String needle) {
        Callable<Void> c1 = () -> {
            System.out.println("Storing lines: " + System.nanoTime() / 1_000_000);
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
        exService.execute(() -> {
            System.out.println("Storing lines: " + System.nanoTime() / 1_000_000);
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

        exService.submit(() -> System.out.println(super.reportCaseInsensitiveWordCount(needle)));

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

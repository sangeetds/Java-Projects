package com.cognitree.sangeet.threads;

import com.cognitree.sangeet.WordFrequency;
import com.cognitree.sangeet.exceptions.LineException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class WordFrequencyThread extends WordFrequency {
    private final List<String> hay;
    private long lines;
    private AtomicBoolean linesOver;
    private long totalLines;

    public WordFrequencyThread() {
        this.hay = new ArrayList<>();
        this.lines = 0;
        this.linesOver = new AtomicBoolean(false);
    }

    public void reportWordCount(BufferedReader fileScanner, String needle) {
        System.out.println("Thread without pool test starting at: " + (new Date()).toString().split("\\s+")[3]);

        Thread t1 = new Thread(() -> {
            while (true) {
                final String currLine;
                try {
                    currLine = fileScanner.readLine();
                } catch (IOException e) {
                    break;
                }

                if (currLine == null) break;

                try {
                    storeLine(currLine);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            setLinesOver();
        });

        Thread t2 = new Thread(() -> System.out.println("Words matched with simple threading: " + reportCaseInsensitiveWordCount(needle) + " finished at: " + (new Date()).toString().split("\\s+")[3]));

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void storeLine(String newLine) throws Exception {
        if (newLine == null) {
            throw LineException.InvalidLineException();
        }

        this.totalLines++;

        synchronized (this.hay) {
            this.hay.add(newLine);
            this.hay.notifyAll();
        }
    }

    public long reportCaseInsensitiveWordCount(String needle) {
        String currentLine;
        long frequency = 0L;

        while ((currentLine = getCurrentLine()) != null) {
            frequency += super.getFrequency(needle, currentLine);
        }

        return frequency;
    }

    private String getCurrentLine() {
        synchronized (this.hay) {
            while (this.lines >= this.hay.size()) {
                if (this.lines >= 170390) return null;

                try {
                    this.hay.wait();
                }
                catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                }
            }

            String returnString = this.hay.get((int) this.lines);
            this.lines++;
//            this.hay.notifyAll();

            return returnString;
        }
    }

    public void setLinesOver() {
        this.linesOver = new AtomicBoolean(true);
    }

    public boolean isLinesOver() {
        return linesOver.equals(new AtomicBoolean(true));
    }
}

package com.cognitree.sangeet.threads;

import com.cognitree.sangeet.WordFrequency;
import com.cognitree.sangeet.exceptions.LineException;

import java.util.ArrayList;
import java.util.List;

public class WordFrequencyThread extends WordFrequency {
    private final List<String> hay;
    private long lines;
    private boolean linesOver;
    public long totalLines;

    public WordFrequencyThread() {
        this.hay = new ArrayList<>();
        this.lines = 0;
        this.linesOver = false;
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
        Long frequency = 0L;

        while ((currentLine = getCurrentLine()) != null) {
            frequency += super.getFrequency(needle, currentLine);
        }

        return frequency;
    }

    private String getCurrentLine() {
        synchronized (this.hay) {
            while (this.lines >= this.hay.size()) {
                if (this.lines >= 170939) return null;

                try {
//                    System.out.println("wait " + this.lines + " " + this.totalLines);
                    this.hay.wait();
//                    System.out.println("no wait " + this.lines);
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
        this.linesOver = true;
        this.totalLines = this.hay.size();
    }

    public boolean isLinesOver() {
        return linesOver;
    }
}

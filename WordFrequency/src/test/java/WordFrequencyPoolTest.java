import com.cognitree.sangeet.threads.WordFrequencyThreadPool;

import java.io.BufferedReader;

public class WordFrequencyPoolTest {
    public static void main(String[] args) {
        WordFrequencyThreadPool wordFrequencyThread = new WordFrequencyThreadPool();
        BufferedReader fileScanner = WordFrequencyThreadTest.getBufferedReader();

        long a = System.nanoTime();
        wordFrequencyThread.processFile(fileScanner);
        System.out.println("The time for the whole threaded process: " + (System.nanoTime() - a) / 1_000_000_000d);
    }
}

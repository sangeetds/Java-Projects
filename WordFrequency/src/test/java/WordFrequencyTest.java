import com.cognitree.sangeet.sequential.WordFrequencySequential;
import com.cognitree.sangeet.threadpool.WordFrequencyForkJoin;
import com.cognitree.sangeet.threadpool.WordFrequencyThreadPool;
import com.cognitree.sangeet.threads.WordFrequencyThread;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

public class WordFrequencyTest {
    public static void main(String[] args) throws Exception {

        WordFrequencySequential wordFrequencySequential = new WordFrequencySequential();
        WordFrequencyThread wordFrequencyThread = new WordFrequencyThread();
        WordFrequencyThreadPool wordFreqTPool = new WordFrequencyThreadPool();
        WordFrequencyForkJoin wordFreqFork = new WordFrequencyForkJoin();
        wordFrequencySequential.sequentialTest(Objects.requireNonNull(getBufferedReader()));
        wordFrequencyThread.reportWordCount(Objects.requireNonNull(getBufferedReader()), "lorem");
        wordFreqTPool.reportThreadPoolCount(Objects.requireNonNull(getBufferedReader()), "lorem");
        System.out.println("Thread pooling with future: " + wordFreqTPool.reportThreadPoolCountFuture(Objects.requireNonNull(getBufferedReader()), "lorem") + " finished at: " + (new Date()).toString().split("\\s+")[3]);
        wordFreqFork.setWord("lorem");
        wordFreqFork.reportWordCount(Objects.requireNonNull(getBufferedReader()));
    }

    private static BufferedReader getBufferedReader() {
        BufferedReader fileScanner;

        try {
            fileScanner = new BufferedReader(new InputStreamReader(new FileInputStream("sample-2mb-text-file.txt"), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return null;
        }

        return fileScanner;
    }
}

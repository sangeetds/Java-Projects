import com.cognitree.sangeet.sequential.WordFrequencySequential;
import com.cognitree.sangeet.threadpool.WordFrequencyThreadPool;
import com.cognitree.sangeet.threads.WordFrequencyThread;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class WordFrequencyTest {
    public static void main(String[] args) throws Exception {
        BufferedReader fileScanner = getBufferedReader();
        if (fileScanner == null) return;

//        WordFrequencySequential wordFrequencySequential = new WordFrequencySequential();
//        WordFrequencyThread wordFrequencyThread = new WordFrequencyThread();
        WordFrequencyThreadPool wordFreqTPool = new WordFrequencyThreadPool();


//        sequentialTest(fileScanner, wordFrequencySequential);
        System.out.println("Threading test: ");
//        wordFrequencyThread.reportThreadCount(fileScanner, "lorem");

//        wordFreqTPool.reportThreadPoolCount(fileScanner, "lorem");
        System.out.println(wordFreqTPool.reportThreadPoolCountFuture(fileScanner, "lorem"));
        System.out.println("Test over: " + System.nanoTime() / 1_000_000);
    }



    private static void sequentialTest(BufferedReader fileScanner, WordFrequencySequential wordFrequencySequential) throws Exception {
        System.out.println("Sequential test: ");
        String line;
        System.out.println("Storing lines: " + System.nanoTime() / 1_000_000);
        while ((line = fileScanner.readLine()) != null) {
            wordFrequencySequential.storeLine(line);
        }

        System.out.println("Counting frequencies: " + System.nanoTime() / 1_000_000);
        System.out.println("Insensitive sans stream: " + wordFrequencySequential.reportCaseInsensitiveWordCount("lorem"));
        System.out.println(System.nanoTime() / 1_000_000);
        System.out.println("Sensitive sans stream: " + wordFrequencySequential.reportCaseSensitiveWordCount("lorem"));
        System.out.println(System.nanoTime() / 1_000_000);
        System.out.println("Insensitive w stream: " + wordFrequencySequential.reportStreamCaseInsensitiveWordCount("lorem"));
        System.out.println(System.nanoTime() / 1_000_000);
        System.out.println("Sensitive w stream: " + wordFrequencySequential.reportStreamCaseSensitiveWordCount("Lorem"));
        System.out.println(System.nanoTime() / 1_000_000);
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

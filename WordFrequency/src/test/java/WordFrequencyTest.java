import com.cognitree.sangeet.WordFrequency;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordFrequencyTest {

    public static void main(String[] args) throws InterruptedException, IOException {
        BufferedReader fileScanner = getBufferedReader();
        if (fileScanner == null) return;

        WordFrequency wordFrequency = new WordFrequency();

        Thread t1 = new Thread(() -> {
            try {
                process(wordFrequency, fileScanner);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> System.out.println("Matched " + wordFrequency.reportCaseInsensitiveWordCount("Lorem") + " " + System.nanoTime() / 1_000_000));
        System.out.println("Running threads " + System.nanoTime() / 1_000_000);
        t1.start();
//        process(wordFrequency, fileScanner);
//        wordFrequency.reportCaseInsensitiveWordCount("Lorem");
//        process(wordFrequency, fileScanner);
//        t1.join();
//        Thread.sleep(1000);
        t2.start();
//        t1.start();
        System.out.println("Running threads " + System.nanoTime() / 1_000_000);
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

    private static void process(WordFrequency wordFrequency, BufferedReader fileScanner) throws IOException {
        int batchSize = 0;
//        List<String> batch = new ArrayList<>();
        String line;

        do {
//            batchSize++;
            line = fileScanner.readLine();
            wordFrequency.storeLine(line);

            //            if (batchSize > 100000) {
//                batchSize = 0;
//                wordFrequency.storeLine(batch);
//                batch.clear();
        } while (line != null);

    }
}

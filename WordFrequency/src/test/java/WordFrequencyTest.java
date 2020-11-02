import com.cognitree.sangeet.ProcessType;
import com.cognitree.sangeet.WordFrequency;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordFrequencyTest {

    public static void main(String[] args) throws InterruptedException {
        BufferedReader fileScanner = getBufferedReader();
        if (fileScanner == null) return;

        WordFrequency wordFrequency = new WordFrequency();

        Thread t1 = new Thread(() -> {
            try {
                process(wordFrequency, fileScanner, ProcessType.LineByLine);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> System.out.println("Matched " + wordFrequency.reportCaseInsensitiveWordCount("Lorem") + " " + System.nanoTime() / 1_000_000));
        System.out.println("Running thread" + System.nanoTime() / 1_000_000);
        t1.start();
//        process(wordFrequency, fileScanner, ProcessType.AtLast);
//        System.out.println("Running thread 2 " + System.nanoTime() / 1_000_000);
        t1.join();
        t2.start();

//        while (t1.isAlive()) {
//            continue;
//        }
//        System.out.println("Matched at " + System.nanoTime() / 1_000_000 + " " + wordFrequency.reportDirectWordCount("Lorem"));
////        System.out.println(System.nanoTime() / 1_000_000);
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

    private static void process(WordFrequency wordFrequency, BufferedReader fileScanner, ProcessType process) throws IOException, InterruptedException {
        int batchSize = 0;
        List<String> batch = new ArrayList<>();
        String line;
        Thread t2 = null;

        System.out.println(System.nanoTime());
        while (true) {
            batchSize++;
            line = fileScanner.readLine();

            if (line == null) {
                break;
            }

            if (batchSize > 100000) {
                batchSize = 0;
                if (process.equals(ProcessType.LineByLine)) {
                    wordFrequency.storeLine(batch);
                }
                else {
                    final List<String> currBatch = new ArrayList<>(batch);
                    t2 = new Thread(() ->
                      wordFrequency.storeFrequency(currBatch)
                    );
                    t2.start();
                }
                batch.clear();
            }

            batch.add(line);
        }

        t2.join();
    }
}

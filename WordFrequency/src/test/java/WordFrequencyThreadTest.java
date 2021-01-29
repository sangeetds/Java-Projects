import com.cognitree.sangeet.threads.WordFrequencyThread;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class WordFrequencyThreadTest {
    public static void main(String[] args) throws Exception {
        WordFrequencyThread wordFrequencyThread = new WordFrequencyThread();
        BufferedReader fileScanner = getBufferedReader();

        long a = System.nanoTime();
        wordFrequencyThread.processFile(fileScanner);
        System.out.println("The time for the whole threaded process: " + (System.nanoTime() - a) / 1_000_000_000d);
    }

    static BufferedReader getBufferedReader() {
        BufferedReader fileScanner;

        try {
            fileScanner = new BufferedReader(new InputStreamReader(new FileInputStream("sample 2.txt"), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return null;
        }

        return fileScanner;
    }
}

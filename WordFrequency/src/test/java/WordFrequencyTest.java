import com.cognitree.sangeet.sequential.WordFrequencySequential;

import java.io.BufferedReader;

public class WordFrequencyTest {
    public static void main(String[] args)  {
        WordFrequencySequential wordFrequency = new WordFrequencySequential();
        BufferedReader fileScanner = WordFrequencyThreadTest.getBufferedReader();

        long a = System.nanoTime();
        wordFrequency.processFile(fileScanner);
        try {
            wordFrequency.getFrequency("lorem");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("The time for the whole sequential process: " + (System.nanoTime() - a) / 1_000_000_000d);
    }
}

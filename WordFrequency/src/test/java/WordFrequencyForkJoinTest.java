import com.cognitree.sangeet.fork_join.WordFrequencyForkJoin;

import java.io.IOException;

public class WordFrequencyForkJoinTest {
    public static void main(String[] args) throws IOException {
        WordFrequencyForkJoin wordFrequencyForkJoin = new WordFrequencyForkJoin();
        long a = System.nanoTime();
        wordFrequencyForkJoin.processFile(WordFrequencyThreadTest.getBufferedReader());
        try {
            System.out.println(wordFrequencyForkJoin.getFrequency("lorem"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("The time for the whole sequential process: " + (System.nanoTime() - a) / 1_000_000_000d);
    }
}

import com.cognitree.sangeet.Consumer;
import com.cognitree.sangeet.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ProducerConsumerTest {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> tempList = new ArrayList<>();
        Producer p = new Producer(tempList);
        Consumer c = new Consumer(tempList);

        Thread t1 = new Thread(p::produce);
        t1.start();
        Thread t2 = new Thread(c::consume);

        t2.start();
    }
}

import com.cognitree.sangeet.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class ProducerConsumerTest {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> tempList = new ArrayBlockingQueue<>(10);
        Producer p = new Producer(tempList, "p1");
        Producer pTwo = new Producer(tempList, "p2");
        Producer pThree = new Producer(tempList, "p3");
        Consumer c = new Consumer(tempList, "c1");
        Consumer cTwo = new Consumer(tempList, "c2");

        Thread t1 = new Thread(pTwo::produce);
        Thread t3 = new Thread(pThree::produce);
        Thread t4 = new Thread(p::produce);
        t1.start();
        t3.start();
        t4.start();
        Thread t2 = new Thread(c::consume);
        Thread t5 = new Thread(cTwo::consume);

        t2.start();
        t5.start();
    }
}

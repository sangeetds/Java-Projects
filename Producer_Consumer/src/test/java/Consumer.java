import com.cognitree.sangeet.Producer;

import java.util.stream.Stream;

public class Consumer {
    public static void main(String[] args) throws InterruptedException {
        Producer p = new Producer();

//        while (i < 10) {
//            p.produce();
//            i++;
//        }

        Thread t1 = new Thread(() -> {
            int i = 0;
            while (i < 30) {
                p.produce();
                i++;
            }
        });
//        t1.start();
        Thread t2 = new Thread(() -> {
            int i = 0;
            while (i < 30) {
                System.out.println(p.consume());
                i++;
            }
        });
        t2.start();
        t1.start();
    }
}

import com.cognitree.sangeet.ThreadPool;

import java.util.concurrent.Callable;

public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(10);

//        threadPool.submit(() -> "Callable Object");
        threadPool.submit(() -> System.out.println("New Task"));
    }
}

package com.cognitree.sangeet;

import com.cognitree.sangeet.ThreadPool;

import java.util.concurrent.*;


public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool();

        for (int i = 0; i < 100; i++) {
            final int a = i;
            threadPool.submit(() -> System.out.println("Hello " + a));
        }
//        Callable<String> one = () -> "hello one";
//        Callable<String> two = () -> "Hello two";
//        Runnable three = () -> System.out.println("Hello three");
//        Runnable four = () -> System.out.println("Hello four");
//
//        threadPool.execute(three);
//        threadPool.submit(four);

//        FutureTask<String> fOne = threadPool.execute(one);
//        FutureTask<String> fTwo = threadPool.execute(two);
//        try {
//            fOne.get(100, TimeUnit.MILLISECONDS);
//            fTwo.get(100, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException | ExecutionException | TimeoutException e) {
//            e.printStackTrace();
//        }
    }
}

package com.cognitree.sangeet;

import com.cognitree.sangeet.ThreadPool;

import java.util.concurrent.*;


public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPool threadPool = null;
        try {
            threadPool = new ThreadPool();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Callable<String> one = () -> {
            System.out.println("Hello One 1");
            return "true 2";
        };
        Callable<String> two = () -> {
            System.out.println("Hello Two 3");
            return "true 4";
        };
        Runnable three = () -> System.out.println("Hello three 5");
        Runnable four = () -> System.out.println("Hello four 6");

        threadPool.execute(three);
        threadPool.submit(four);
        Future<String> s = threadPool.execute(one);
        Future<String> sOne = threadPool.execute(two);

        try {
            System.out.println(s.get());
            System.out.println(sOne.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

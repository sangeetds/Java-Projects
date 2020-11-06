package com.cognitree.sangeet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPool {
    private final int maxThread;
    private final List<Thread> threads;
    private final BlockingQueue queue;

    public ThreadPool() {
        this(10);
    }

    public ThreadPool(int maxThread) {
        this.maxThread = maxThread;
        this.queue = new LinkedBlockingQueue();
        this.threads = new ArrayList<>();

        for (int i = 0; i < maxThread; i++) {
            Thread newThread = new Thread(() -> {
                while (true) {
                    synchronized (queue) {
                        if (queue.isEmpty()) {
                            try {
//                                System.out.println("ThreadPool is waiting for the task");
                                queue.wait();
                            } catch (InterruptedException e) {
                                System.out.println("Thread interrupted");
                            }
                        }
                    }
                }
            });
            this.threads.add(newThread);
            newThread.start();
        }

//        start();
    }

//    private void start() {
//        Runnable task;
//
//
//    }

//    public <T> Future<T> submit(Callable<T> task) {
//        return null;
//    }

    public void submit(Runnable task) {
        synchronized (queue) {
            queue.add(task);
            queue.notifyAll();
        }
    }
}

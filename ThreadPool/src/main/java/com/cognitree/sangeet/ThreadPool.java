package com.cognitree.sangeet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPool {
    private final List<Thread> threads;
    private final BlockingQueue<Runnable> runnableQueue;
    private final BlockingQueue<RunnableFuture<?>> callableQueue;

    public ThreadPool() {
        this(4);
    }

    public ThreadPool(int maxThread) {
        this.callableQueue = new LinkedBlockingQueue<>();
        this.runnableQueue = new LinkedBlockingQueue<>();
        this.threads = new ArrayList<>();

        for (int i = 0; i < maxThread; i++) {
            Thread newThread = new Thread(() -> {
                while (true) {
                    synchronized (this.runnableQueue){
                        if (this.runnableQueue.isEmpty() && this.callableQueue.isEmpty()) {
                            try {
                                this.runnableQueue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (runnableQueue.isEmpty()) {
                        try {
                            RunnableFuture<?> future = this.callableQueue.take();
                            future.run();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {
                            Runnable runnable = this.runnableQueue.take();
                            runnable.run();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            this.threads.add(newThread);
            newThread.start();
        }

    }

    public <T> Future<T> execute(Callable<T> task) {
        synchronized (this.callableQueue) {
            RunnableFuture<T> newTask = new FutureTask<>(task);
            this.callableQueue.add(newTask);
            this.callableQueue.notifyAll();

            return newTask;
        }
    }

    public Future<?> execute(Runnable task) {
        synchronized (this.runnableQueue) {
            RunnableFuture<Void> newTask = new FutureTask<Void>(task, null);
            this.runnableQueue.add(newTask);
            this.runnableQueue.notifyAll();

            return newTask;
        }
    }

    public void submit(Runnable task) {
        synchronized (this.runnableQueue) {
            this.runnableQueue.add(task);
            this.runnableQueue.notifyAll();
        }
    }
}

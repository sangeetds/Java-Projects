package com.cognitree.sangeet;

import java.util.concurrent.*;

public class ThreadPool {
    private final Thread[] threads;
    private final BlockingQueue queue;

    public ThreadPool() {
        this(10);
    }

    public ThreadPool(int maxThread) {
        this.queue = new LinkedBlockingQueue();
        this.threads = new Thread[maxThread];

        for (int i = 0; i < maxThread; i++) {
            Thread newThread = new Thread(() -> {
                while (true) {
                    synchronized (this.queue){
                        if (this.queue.isEmpty()) {
                            try {
                                this.queue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    Object task = this.queue.remove();
                    Runnable runnable = null;
                    Callable callable = null;

                    try {
                        runnable = (Runnable) task;
                    }
                    catch (ClassCastException ignored) {}

                    if (runnable == null) {
                        try {
                            callable = (Callable) task;
                        } catch (ClassCastException ignored) {}

                        new FutureTask(callable).run();
                    }
                    else runnable.run();
                }
            });

            this.threads[i] = newThread;
            newThread.start();
        }

    }

    public <T> FutureTask<T> execute(Callable<T> task) {
        synchronized (this.queue) {
            this.queue.add(task);
            this.queue.notify();
        }
        return new FutureTask<>(task);
    }

    public FutureTask<?> execute(Runnable task) {
        synchronized (this.queue) {
            this.queue.add(task);
            this.queue.notify();
        }

        return new FutureTask<Void>(task, null);
    }

    public void submit(Runnable task) {
        synchronized (this.queue) {
            this.queue.add(task);
            this.queue.notify();
        }
    }
}

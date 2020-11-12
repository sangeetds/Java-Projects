package com.cognitree.sangeet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPool {
    private final List<Thread> threads;
    private final BlockingQueue<Object> runnableQueue;

    public ThreadPool() {
        this(4);
    }

    public ThreadPool(int maxThread) {
        this.runnableQueue = new LinkedBlockingQueue<>();
        this.threads = new ArrayList<>();

        for (int i = 0; i < maxThread; i++) {
            Thread newThread = new Thread(() -> {
                while (true) {
                    synchronized (this.runnableQueue) {
                        while (this.runnableQueue.isEmpty()) {
                            try {
                                this.runnableQueue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    Object task = null;
                    try {
                        task = this.runnableQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (task == null) continue;

                    try {
                        RunnableFuture<?> future = (RunnableFuture<?>) task;
                        future.run();
                    } catch (ClassCastException ignored) {
                    }

                    try {
                        Runnable runnable = (Runnable) task;
                        runnable.run();
                    } catch (ClassCastException ignored) {
                    }
                }
            });

            this.threads.add(newThread);
            newThread.start();
        }

    }

    public <T> Future<T> execute(Callable<T> task) throws NumberFormatException {
        if (task == null) throw new NullPointerException();

        synchronized (this.runnableQueue) {
            RunnableFuture<T> newTask = new FutureTask<>(task);
            this.runnableQueue.add(newTask);
            this.runnableQueue.notifyAll();

            return newTask;
        }
    }

    public Future<?> execute(Runnable task) throws NumberFormatException {
        if (task == null) throw new NullPointerException();

        synchronized (this.runnableQueue) {
            RunnableFuture<Void> newTask = new FutureTask<>(task, null);
            this.runnableQueue.add(newTask);
            this.runnableQueue.notifyAll();

            return newTask;
        }
    }

    public void submit(Runnable task) throws NumberFormatException {
        if (task == null) throw new NullPointerException();

        synchronized (this.runnableQueue) {
            this.runnableQueue.add(task);
            this.runnableQueue.notifyAll();
        }
    }
}

package com.cognitree.sangeet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPool {
    private final BlockingQueue<Object> runnableQueue;
    private final int MaxThreads = Runtime.getRuntime().availableProcessors();

    public ThreadPool() throws Exception {
        this(Runtime.getRuntime().availableProcessors() / 4);
    }

    public ThreadPool(int threadsRequested) throws Exception {
        if (threadsRequested > MaxThreads) {
            throw requestExceededException();
        }
        this.runnableQueue = new LinkedBlockingQueue<>();
        List<Thread> threads = new ArrayList<>();

        for (int index = 0; index < threadsRequested; index++) {
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

            threads.add(newThread);
            newThread.start();
        }

    }

    private Exception requestExceededException() {
        return new Exception("Threads requested can not be more than " + MaxThreads);
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

package com.cognitree.sangeet;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

public class Consumer {
    final List<Integer> producedGoods;
    final String name;
    final StampedLock consumerLock;

    public Consumer(List<Integer> producedGoods, String name) {
        this.name = name;
        this.producedGoods = producedGoods;
        this.consumerLock = new StampedLock();
    }

    public void consume() {
        while (true) {
//            synchronized (producedGoods) {
            long stamp = consumerLock.writeLock();
            if (getSize() != 0) {
                try {
//                if (producedGoods.isEmpty()) {
//                    System.out.println("No goods right now. Wait");
//                    producedGoods.wait();
//                }

                    System.out.println("Removed: " + this.name + " " + this.producedGoods.remove(0));

//                producedGoods.notifyAll();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
                } finally {
                    consumerLock.unlockWrite(stamp);
                }
            }
        }
//        }
    }

    private int getSize() {
        synchronized (this.producedGoods) {
            return this.producedGoods.size();
        }
    }
}

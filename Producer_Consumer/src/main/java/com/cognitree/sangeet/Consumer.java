package com.cognitree.sangeet;

import java.util.List;

import static com.cognitree.sangeet.Locks.*;

public class Consumer {
    final List<Integer> producedGoods;
    final String name;

    public Consumer(List<Integer> producedGoods, String name) {
        this.name = name;
        this.producedGoods = producedGoods;
    }

    public void consume() {
        while (true) {
//            System.out.println("Hello");
//            System.out.println("H");
            if (getSize() != 0) {
                long stamp = consumerLock.writeLock();
                try {
                    System.out.println("Removed: " + this.name + " " + this.producedGoods.remove(0));
                } finally {
                    consumerLock.unlockWrite(stamp);
                }
            }
        }
    }

    private int getSize() {
        synchronized (this.producedGoods) {
            while (this.producedGoods.size() == 0) {
                try {
                    this.producedGoods.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            this.producedGoods.notifyAll();
            return this.producedGoods.size();
        }
    }
}
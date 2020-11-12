package com.cognitree.sangeet;

import java.util.List;

import static com.cognitree.sangeet.Locks.producerLock;

public class Producer {
    final List<Integer> producedGoods;
    final String name;

    public Producer(List<Integer> producedGoods, String name) {
        this.name = name;
        this.producedGoods = producedGoods;
    }

    public void produce() {
        while (true) {
            if (getSize() <= 10) {
                long stamp = producerLock.writeLock();
                try {
                    System.out.println("Added: " + this.name + " " + getSize());
                    this.producedGoods.add(getSize());
                } finally {
                    producerLock.unlockWrite(stamp);
                }
            }
        }
    }

    private int getSize() {
        synchronized (this.producedGoods) {
            while (this.producedGoods.size() >= 10) {
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

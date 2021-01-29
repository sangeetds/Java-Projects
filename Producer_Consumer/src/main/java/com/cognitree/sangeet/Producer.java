package com.cognitree.sangeet;

import java.util.List;

import static com.cognitree.sangeet.Locks.producerLock;

public class Producer {
    private final List<Integer> producedGoods;
    private final String name;

    public Producer(List<Integer> producedGoods, String name) {
        this.name = name;
        this.producedGoods = producedGoods;
    }

    public void produce() {
        while (true) {
            long stamp = producerLock.writeLock();
            try {
                synchronized (this.producedGoods) {
                    if (producedGoods.size() <= 10) {
                        System.out.println("Added: " + this.name + " " + producedGoods.size());
                        this.producedGoods.add(producedGoods.size());
                    }
                }
            } finally {
                producerLock.unlockWrite(stamp);
            }
        }
    }
}

package com.cognitree.sangeet;

import java.util.List;

import static com.cognitree.sangeet.Locks.consumerLock;

public class Consumer {
    private final List<Integer> producedGoods;
    private final String name;

    public Consumer(List<Integer> producedGoods, String name) {
        this.name = name;
        this.producedGoods = producedGoods;
    }

    public void consume() {
        while (true) {
            long stamp = consumerLock.writeLock();
            try {
                synchronized (this.producedGoods) {
                    if (this.producedGoods.size() != 0) {
                        System.out.println("Removed: " + this.name + " " + this.producedGoods.remove(0));
                    }
                }
            } finally {
                consumerLock.unlockWrite(stamp);
            }
        }
    }
}
package com.cognitree.sangeet;


import java.util.List;
import java.util.concurrent.locks.StampedLock;

public class Producer {
    final List<Integer> producedGoods;
    final String name;
    final StampedLock producerLock;

    public Producer(List<Integer> producedGoods, String name) {
        this.name = name;
        this.producedGoods = producedGoods;
        this.producerLock = new StampedLock();
    }

    public void produce() {
        while (true) {
//            synchronized (producedGoods) {
            long stamp = producerLock.writeLock();
            if (getSize() <= 10) {
                try {
//                    if (producedGoods.size() >= 10) {
//                        System.out.println("Too many goods in the factory. Current Size: " + producedGoods.size());
//                    producedGoods.wait();
//                        System.out.println("Now producing");
//                    }
                    System.out.println("Added: " + this.name + " " + getSize());
                    this.producedGoods.add(getSize());

//                producedGoods.notifyAll();
                } finally {
                    producerLock.unlockWrite(stamp);
                }
            }
        }


    }

    private int getSize() {
        synchronized (this.producedGoods) {
            return this.producedGoods.size();
        }
    }
//        }
}

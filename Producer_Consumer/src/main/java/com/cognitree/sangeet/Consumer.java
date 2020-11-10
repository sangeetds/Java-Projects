package com.cognitree.sangeet;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Consumer {
    final BlockingQueue<Integer> producedGoods;
    final String name;

    public Consumer(BlockingQueue<Integer> producedGoods, String name) {
        this.name = name;
        this.producedGoods = producedGoods;
    }

    public void consume() {
        while (true) {
            synchronized (producedGoods) {
                if (producedGoods.isEmpty()) {
                    System.out.println("No goods right now. Wait");
                    try {
                        producedGoods.wait();

                    } catch (InterruptedException e) {
                        System.out.println("Production interrupted");
                    }
                }

                if (producedGoods.size() > 0) {
                    try {
                        System.out.println("Removed: " + this.name + " " + this.producedGoods.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                producedGoods.notify();
                try {
                    producedGoods.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

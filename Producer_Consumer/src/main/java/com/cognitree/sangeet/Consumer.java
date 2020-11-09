package com.cognitree.sangeet;

import java.util.List;

public class Consumer {
    final List<Integer> producedGoods;
    final String name;

    public Consumer(List<Integer> producedGoods, String name) {
        this.name = name;
        this.producedGoods = producedGoods;
    }

    public void consume() {
        while (true) {
            synchronized (producedGoods) {
                if (producedGoods.size() == 0) {
                    System.out.println("No goods right now. Wait");
                    try {
                        producedGoods.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Production interrupted");
                    }
                }

                System.out.println("Removed: " + this.name + this.producedGoods.remove(0));

                producedGoods.notify();
            }
        }
    }
}

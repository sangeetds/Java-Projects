package com.cognitree.sangeet;


import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Producer {
    final BlockingQueue<Integer> producedGoods;
    final String name;

    public Producer(BlockingQueue<Integer> producedGoods, String name) {
        this.name = name;
        this.producedGoods = producedGoods;
    }

    public void produce() {
        while (true) {
            synchronized (producedGoods) {
                if (producedGoods.size() >= 10) {
                    try {
                        System.out.println("Too many goods in the factory. Current Size: " + producedGoods.size());
                        producedGoods.wait();
                        System.out.println("Now producing");
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted");
                    }
                }

                if (producedGoods.size() < 10) {
                    System.out.println("Added: " + this.name + " " + this.producedGoods.size());
                    try {
                        this.producedGoods.put(this.producedGoods.size());
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

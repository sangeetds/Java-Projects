package com.cognitree.sangeet;


import java.util.List;

public class Producer {
    final List<Integer> producedGoods;

    public Producer(List<Integer> producedGoods) {
        this.producedGoods = producedGoods;
    }

    public void produce() {
        while (true) {
            synchronized (producedGoods) {
                while (producedGoods.size() >= 10) {
                    try {
                        System.out.println("Too many goods in the factory. Current Size: " + producedGoods.size());
                        producedGoods.wait();
                        System.out.println("Now producing");
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted");
                    }
                }

                System.out.println("Added: " + this.producedGoods.size());
                this.producedGoods.add(this.producedGoods.size());

                producedGoods.notify();
            }
        }
    }
}

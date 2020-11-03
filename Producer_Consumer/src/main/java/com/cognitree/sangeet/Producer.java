package com.cognitree.sangeet;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Producer {
    final List<Integer> producedGoods;

    public Producer() {
        this.producedGoods = Collections.synchronizedList(new ArrayList<>());
    }

    public int getProducedGoods() {
        return producedGoods.size();
    }

    public int consume() {
        synchronized (producedGoods) {
            if (getProducedGoods() >= 10) {
                producedGoods.notifyAll();
            }

            if (getProducedGoods() == 0) {
                System.out.println("No goods right now. Wait");
                try {
                    producedGoods.wait();
                } catch (InterruptedException e) {
                    System.out.println("Production interrupted");
                }
                System.out.println("Goods ready to dispatched");
            }

            return this.producedGoods.remove(this.producedGoods.size() - 1);
        }
    }

    public void produce() {
        synchronized (producedGoods)  {
            while (getProducedGoods() >= 10) {
                try {
                    System.out.println("Too many goods in the factory. Current Size: " + getProducedGoods());
                    producedGoods.wait();
                    System.out.println("Now producing");
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                }
            }

            if (getProducedGoods() == 0) {
                producedGoods.notifyAll();
            }

            this.producedGoods.add(this.producedGoods.size());
        }
    }
}

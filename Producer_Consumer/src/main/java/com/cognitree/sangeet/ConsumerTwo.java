package com.cognitree.sangeet;

import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class ConsumerTwo extends Consumer {
    public ConsumerTwo(SynchronousQueue<Integer> producedGoods, String name) {
        super(producedGoods, name);
    }
}

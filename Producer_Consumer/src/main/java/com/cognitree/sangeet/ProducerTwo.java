package com.cognitree.sangeet;

import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class ProducerTwo extends Producer {
    public ProducerTwo(SynchronousQueue<Integer> producedGoods, String name) {
        super(producedGoods, name);
    }
}

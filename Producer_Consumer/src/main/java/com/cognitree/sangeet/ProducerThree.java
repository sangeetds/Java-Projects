package com.cognitree.sangeet;

import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class ProducerThree extends Producer {
    public ProducerThree(SynchronousQueue<Integer> producedGoods, String name) {
        super(producedGoods, name);
    }
}

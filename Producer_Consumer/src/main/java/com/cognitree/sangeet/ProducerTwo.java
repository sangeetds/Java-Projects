package com.cognitree.sangeet;

import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class ProducerTwo extends Producer {
    public ProducerTwo(List<Integer> producedGoods, String name) {
        super(producedGoods, name);
    }
}

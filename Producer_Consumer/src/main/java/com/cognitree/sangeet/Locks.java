package com.cognitree.sangeet;

import java.util.concurrent.locks.StampedLock;

public class Locks {
    public static StampedLock consumerLock = new StampedLock();
    public static StampedLock producerLock = new StampedLock();
}

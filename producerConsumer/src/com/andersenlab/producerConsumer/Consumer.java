package com.andersenlab.producerConsumer;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Consumer implements Runnable {

    private static final AtomicLong consumedCounter = new AtomicLong(0);

    private BlockingQueue<int[]> blockingQueue;

    public Consumer(BlockingQueue<int[]> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            Arrays.sort(blockingQueue.take());
            System.out.println("Consumed: " + consumedCounter.incrementAndGet());
        }
    }



}

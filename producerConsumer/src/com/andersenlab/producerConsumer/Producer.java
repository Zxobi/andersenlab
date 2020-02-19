package com.andersenlab.producerConsumer;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Producer implements Runnable {

    private static final int SIZE_MIN = 1000;
    private static final int SIZE_MAX = 100000;

    private static final AtomicLong producedCounter = new AtomicLong(0);

    private BlockingQueue<int[]> blockingQueue;
    private Random random;

    public Producer(BlockingQueue<int[]> blockingQueue) {
        this.blockingQueue = blockingQueue;
        random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            blockingQueue.put(generateArray());
            System.out.println("Produced: " + producedCounter.incrementAndGet());
        }
    }

    private int[] generateArray() {
        int arrSize = SIZE_MIN + random.nextInt(SIZE_MAX - SIZE_MIN);
        int[] arr = new int[arrSize];

        for (int i = 0; i < arrSize; i++) {
            arr[i] = random.nextInt(Integer.MAX_VALUE);
        }

        return arr;
    }
}

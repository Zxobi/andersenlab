package com.andersenlab.producerConsumer;

public class Main {

    public static void main(String[] args) {
        BlockingQueue<int[]> queue = new BlockingQueue<>(10);

        new Thread(new Consumer(queue)).start();
        new Thread(new Producer(queue)).start();
    }
}

package com.andersenlab.producerConsumer;

import java.util.LinkedList;

public class BlockingQueue<E> {

    LinkedList<E> buffer;
    int capacity;

    public BlockingQueue(int capacity) {
        buffer = new LinkedList<>();
        this.capacity = capacity;
    }

    public synchronized void put(E element) {
        while (buffer.size() == capacity) {
            try {
                wait();
            } catch (InterruptedException ex) {}
        }

        buffer.add(element);
        notifyAll();
    }

    public synchronized E take() {
        while (buffer.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ex) {}
        }

        notifyAll();
        return buffer.remove();
    }

}

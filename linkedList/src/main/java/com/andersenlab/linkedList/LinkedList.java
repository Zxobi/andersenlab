package com.andersenlab.linkedList;

public class LinkedList<E> {

    private int size = 0;

    private Node<E> head = null;
    private Node<E> last = null;

    public void add(E element) {
        Node<E> newNode = new Node<>(element);
        if (head == null) head = newNode;
        if (last != null) last.next = newNode;
        last = newNode;
        size++;
    }

    public boolean remove(E element) {
        if (size == 0) return false;

        Node<E> prevNode = null;
        Node<E> curNode = head;

        do {
            if (curNode.val == element) {
                if (prevNode == null) {
                    head = curNode.next;
                } else {
                    prevNode.next = curNode.next;
                }
                if (curNode == last) last = prevNode;
                size--;
                return true;
            }
            prevNode = curNode;
        } while ((curNode = curNode.next) != null);

        return false;
    }

    public E get(int index) {
        if (index >= size) throw new IndexOutOfBoundsException();

        Node<E> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }

        return node.val;
    }

    public void clear() {
        head = null;
        last = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public void reverse() {
        if (size == 0) return;

        Node<E> cur = head;
        Node<E> next = head.next;
        cur.next = null;

        Node<E> swap;
        while (next != null) {
            swap = next.next;
            next.next = cur;
            cur = next;
            next = swap;
        }

        swap = head;
        head = last;
        last = swap;
    }

    private static class Node<E> {
        E val;
        Node<E> next;

        public Node(E val) {
            this.val = val;
        }
    }

}

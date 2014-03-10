package com.geeklife.cs315.linkedlist.single;

/**
 * Created by andysong on 13-12-25.
 */
public class SingleLinkedNode<T> {

    private T value;

    private SingleLinkedNode<T> next;

    public SingleLinkedNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public SingleLinkedNode<T> getNext() {
        return next;
    }

    public void setNext(SingleLinkedNode<T> next) {
        this.next = next;
    }
}

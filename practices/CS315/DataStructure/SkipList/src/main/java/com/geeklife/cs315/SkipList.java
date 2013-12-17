package com.geeklife.cs315;

public class SkipList<T> {

    private SkipListNode<T> head, tail;

    private long length;

    private int level;

    private int maxLevel;

    public SkipListNode<T> getHead() {
        return head;
    }

    public void setHead(SkipListNode<T> head) {
        this.head = head;
    }

    public SkipListNode<T> getTail() {
        return tail;
    }

    public void setTail(SkipListNode<T> tail) {
        this.tail = tail;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }
}

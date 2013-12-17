package com.geeklife.cs315;

/**
 * Created with GeekLife.
 * User: andysong
 * Date: 13-12-2
 * Time: PM1:39
 * To change this template use File | Settings | File Templates.
 */
public class SkipListNode<T> {

    //The previous node only exists in the lowest level
    private SkipListNode<T> prevNode;

    //The forward node exists in all the level
    private SkipListNode<T>[] nextNode;

    private double score;

    private T value;

    public SkipListNode<T> getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(SkipListNode<T> prevNode) {
        this.prevNode = prevNode;
    }

    public SkipListNode<T>[] getNextNode() {
        return nextNode;
    }

    public SkipListNode<T> getNextNode(int level) {
        return nextNode[level];
    }

    public void setNextNode(int level, SkipListNode<T> next) {
        this.nextNode[level] = next;
    }

    public void setNextNode(SkipListNode<T>[] nextNode) {
        this.nextNode = nextNode;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void levelCreate(int level) {
        this.nextNode = new SkipListNode[level];
    }

    @Override
    public String toString() {
        return "Value=" + value + ",Score=" + score;
    }
}

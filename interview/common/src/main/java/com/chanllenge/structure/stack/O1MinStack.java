package com.chanllenge.structure.stack;

/**
 * Created by andysong on 14-11-20.
 */
public class O1MinStack {




}

class Stack {
    private int[] values;
    private int top;

    public Stack(int length) {
        values = new int[length];
        top = 0;
    }

    public void push(int value) {
        values[top] = value;
        top++;
    }

    public int pop() {
        return values[top--];
    }
}

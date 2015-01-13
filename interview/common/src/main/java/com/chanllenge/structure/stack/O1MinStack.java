package com.chanllenge.structure.stack;

/**
 * Created by andysong on 14-11-20.
 */
public class O1MinStack {

    Stack stack = new Stack(10);
    Stack minStack = new Stack(10);
    int min = Integer.MIN_VALUE;

    public void push(int value) {
        stack.push(value);


        if (min == Integer.MIN_VALUE ) {
            min=value;
        } else if (value <= min) {
            minStack.push(min);
            min=value;
        }
    }

    public int pop() {
        int value = stack.pop();
        if (value == min) {
            min = minStack.pop();
        }

        return value;
    }

    public int min() {
        return min;
    }

    public static void main(String[] args) {
        O1MinStack minStack = new O1MinStack();



        minStack.push(10);
        minStack.push(5);
        minStack.push(3);
        minStack.push(9);
        minStack.push(12);
        minStack.push(1);

        System.out.println(minStack.min());
        System.out.println(minStack.pop());

        System.out.println(minStack.min());
        System.out.println(minStack.pop());

        System.out.println(minStack.min());
        System.out.println(minStack.pop());
    }


}

class Stack {
    private int[] values;
    private int top;

    public Stack(int length) {
        values = new int[length];
        top = 0;
    }

    public void push(int value) {
        values[top++] = value;
    }

    public int pop() {
        return values[--top];
    }
}

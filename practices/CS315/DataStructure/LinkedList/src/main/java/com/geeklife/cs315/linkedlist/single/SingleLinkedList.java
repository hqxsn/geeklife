package com.geeklife.cs315.linkedlist.single;


public class SingleLinkedList<T> {

    private SingleLinkedNode<T> head;

    private SingleLinkedNode current;

    private int count;

    public SingleLinkedList() {
        head = null;
    }

    public void add(SingleLinkedNode<T> node) {
        if (head == null || current == null) {
            head = node;
            current = node;
        } else if (head != null && current != null) {
            current.setNext(node);

            SingleLinkedNode<T> tmpNode = node;
            while (tmpNode.getNext() != null) {
                tmpNode = tmpNode.getNext();
            }

            current = tmpNode;
        }
    }

    public void reverse() {
        SingleLinkedNode<T> prev = null;
        SingleLinkedNode<T> nodeA = head;

        //#1  The whole list is empty, we won't handle anything
        if (nodeA == null) {
            return;
        }

        //#2  The whole list only contains one node, we won't handle anything
        if (nodeA.getNext() == null) {
            return;
        }

        //#3  Moved the whole list each one step do the reverse
        while (nodeA.getNext() != null) {
            SingleLinkedNode<T> nodeB = nodeA.getNext();

            nodeA.setNext(prev);

            prev = nodeA;
            nodeA = nodeB;

        }

        nodeA.setNext(prev);
        current = head;
        head = nodeA;
    }

    public void reverse2() {
        SingleLinkedNode<T> prev = null;
        SingleLinkedNode<T> nodeA = head;

        //#1  The whole list is empty, we won't handle anything
        if (nodeA == null) {
            return;
        }

        //#2  The whole list only contains one node, we won't handle anything
        if (nodeA.getNext() == null) {
            return;
        }

        //#3  Moved the whole list each two step do the reverse
        SingleLinkedNode<T> nodeB = nodeA.getNext();
        while (nodeB != null && nodeB.getNext() != null) {

            SingleLinkedNode<T> tmpNode = nodeB.getNext();

            nodeA.setNext(prev);

            nodeB.setNext(nodeA);

            prev = nodeB;
            nodeA = tmpNode;
            nodeB = nodeA.getNext();
        }

        nodeA.setNext(prev);

        if (nodeB == null) {
            current = head;
            head = nodeA;
        } else if (nodeB.getNext() == null) {
            current = head;
            nodeB.setNext(nodeA);
            head = nodeB;
        }
    }

    public static void main(String[] args) {
        SingleLinkedList<Integer> tmpLinkedList = new SingleLinkedList<Integer>();

        tmpLinkedList.add(new SingleLinkedNode<Integer>(10));
        tmpLinkedList.add(new SingleLinkedNode<Integer>(11));
        tmpLinkedList.add(new SingleLinkedNode<Integer>(12));
        tmpLinkedList.add(new SingleLinkedNode<Integer>(14));

        tmpLinkedList.reverse();

        System.gc();
        System.gc();

        long[] nanoSecArray = new long[15];
        for (int i = 0; i < 15; ++i) {
            SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<Integer>();

            for (int j = 0; j < 1000; ++j) {
                singleLinkedList.add(new SingleLinkedNode<Integer>(10));
                singleLinkedList.add(new SingleLinkedNode<Integer>(11));
                singleLinkedList.add(new SingleLinkedNode<Integer>(12));
                singleLinkedList.add(new SingleLinkedNode<Integer>(14));
                singleLinkedList.add(new SingleLinkedNode<Integer>(15));
                singleLinkedList.add(new SingleLinkedNode<Integer>(16));
                singleLinkedList.add(new SingleLinkedNode<Integer>(18));
                singleLinkedList.add(new SingleLinkedNode<Integer>(19));
            }

            long nanoSeconds = System.nanoTime();
            singleLinkedList.reverse();
            nanoSecArray[i] = System.nanoTime() - nanoSeconds;
        }

        for(long nanoSec: nanoSecArray) {
            System.out.println("Execution 1 : " + nanoSec);
        }

        System.out.println();

        System.gc();
        System.gc();


        nanoSecArray = new long[15];
        for (int i = 0; i < 15; ++i) {
            SingleLinkedList<Integer> singleLinkedList2 = new SingleLinkedList<Integer>();

            for (int j = 0; j < 1000; j++) {
                singleLinkedList2.add(new SingleLinkedNode<Integer>(10));
                singleLinkedList2.add(new SingleLinkedNode<Integer>(11));
                singleLinkedList2.add(new SingleLinkedNode<Integer>(12));
                singleLinkedList2.add(new SingleLinkedNode<Integer>(14));
                singleLinkedList2.add(new SingleLinkedNode<Integer>(15));
                singleLinkedList2.add(new SingleLinkedNode<Integer>(16));
                singleLinkedList2.add(new SingleLinkedNode<Integer>(18));
                singleLinkedList2.add(new SingleLinkedNode<Integer>(19));
            }

            long nanoSeconds = System.nanoTime();
            singleLinkedList2.reverse2();
            nanoSecArray[i] = System.nanoTime() - nanoSeconds;
        }

        for(long nanoSec: nanoSecArray) {
            System.out.println("Execution 2 : " + nanoSec);
        }
        System.out.println();
    }

}

package com.chanllenge.numbers.topk;

/**
 * Created by andysong on 14-11-19.
 */
public class TopK {

    public static int[] topMostK(int[] values, int k) {

        if (values.length <= k) return values;

        Heap minHeap = new Heap(k);

        for(int inx=0; inx<values.length; ++inx) {
            minHeap.add(values[inx]);
            print(minHeap.nodes);
        }


        return minHeap.nodes;
    }

    private static void print(int[] nodes) {
        for (int node: nodes) {
            System.out.print(node + " ");
        }
        System.out.println();
    }

    static class Heap {
        int[] nodes;
        boolean minHeap;
        int tail;
        int count;

        public Heap(int k) {
            nodes=new int[k];
            tail = 0;
            count = 0;
        }

        public Heap(int k, boolean minHeap) {
            nodes=new int[k];
            this.minHeap = minHeap;
            tail = 0;
            count = 0;
        }

        public int[] nodes() {
            return nodes;
        }

        public int getTop() {
            return nodes.length > 0 ? nodes[0] : Integer.MIN_VALUE;
        }

        public void add(int value) {
            if (count == 0) {
                nodes[0]=value;
                count++;
            } else {

                if (tail + 1 < nodes.length) {
                    nodes[tail + 1] = value;
                    tail = tail + 1;
                    bubbleUp(tail);
                } else if (nodes[0] < value) {
                    nodes[0] = value;
                    bubbleDown(tail);
                }
            }
        }

        private void bubbleDown(int tail) {
            int start = 0;
            while ((2*start) + 1 < tail) {
                int inx = 2 * start + 1;


                if (2*start + 2 <= tail) {
                    if (nodes[2*start + 1] > nodes[2*start +2]) {
                       inx = 2 * start + 2;
                    }
                }

                if (nodes[start] > nodes[inx]) {
                    int tmp = nodes[inx];
                    nodes[inx] = nodes[start];
                    nodes[start] = tmp;

                    start = inx;
                }  else {
                    break;
                }


            }
        }

        private void bubbleUp(int tail) {
            int last = tail;
            while(nodes[last] < nodes[(last -1)/2]) {
                int tmp = nodes[last];
                nodes[last] = nodes[(last - 1)/2];
                nodes[(last -1)/2] = tmp;
                last = (last-1)/2;
            }
        }
    }

    public static void main(String[] args) {

        int[] values = {10, 20, 5, 600, 7, 80, 32, 18, 4, 58};

        int[] topK = topMostK(values, 6);

        System.out.println(topK[0] + " " + topK[1] + " " + topK[2]);


    }

}

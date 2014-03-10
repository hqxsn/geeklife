package com.geeklife.cs315.skiplist;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: andysong
 * Date: 13-12-2
 * Time: PM2:23
 * To change this template use File | Settings | File Templates.
 */
public class SkipListOp {


    public static <T> SkipListNode<T> createSkipListNode(int level, double score, T value) {
        SkipListNode<T> skipListNode = new SkipListNode<T>();
        skipListNode.setScore(score);
        skipListNode.levelCreate(level);
        skipListNode.setValue(value);

        return skipListNode;
    }

    public static <T> SkipList<T> createSkipList(int maxLevel) {
        SkipList<T> skipList = new SkipList<T>();
        skipList.setMaxLevel(maxLevel);
        skipList.setLevel(1);

        SkipListNode<T> skipListNode = createSkipListNode(maxLevel, 0.0, null);
        skipList.setHead(skipListNode);

        //In java, we definitely no need set all the class variable to NULL,
        //But in general it's good practice to do so for the good code reading.
        /*for (int j=0; j<maxLevel; ++j) {
            skipList.getHead().setNextNode(j, null);
        }

        skipList.getHead().setPrevNode(null);
        skipList.setTail(null); */
        return skipList;
    }

    static int randomLevel(int maxLevel) {
        Random random = new Random(System.nanoTime());

        int level = 1;

        while((random.nextInt() & 0xFFFF) < (0.5 * 0xFFFF))
            level += 1;

        return level < maxLevel ? level : maxLevel;


    }

    public static <T> SkipListNode<T> insertNode(SkipList<T> skipList, T value, double score) {
        int maxLevel = skipList.getMaxLevel();
        //int level = skipList.getLevel();

        SkipListNode<T>[] updates = new SkipListNode[maxLevel];

        SkipListNode<T> node = skipList.getHead();
        for(int j = skipList.getLevel() - 1; j >= 0 ; --j) {

            while(node.getNextNode(j) != null && node.getNextNode(j).getScore() < score) {
                node = node.getNextNode(j);
            }

            updates[j] = node;
        }

        int level = randomLevel(maxLevel);

        if (level > skipList.getLevel()) {
            for (int j=skipList.getLevel(); j<level; ++j) {
                updates[j] = skipList.getHead();
            }

            skipList.setLevel(level);
        }

        node = createSkipListNode(level, score, value);

        for (int i = 0; i < level; ++i) {
            node.setNextNode(i, updates[i].getNextNode(i));
            updates[i].setNextNode(i, node);
        }

        node.setPrevNode(updates[0].getPrevNode()==skipList.getHead() ? null : updates[0]);

        if (node.getNextNode(0) != null) {
            node.getNextNode(0).setPrevNode(node);
        } else {
            skipList.setTail(node);
        }

        skipList.setLength(skipList.getLength() + 1);

        return node;
    }


    public static <T> SkipListNode<T> deleteNode(SkipList<T> skipList, T value, double score) {

        int level = skipList.getLevel();
        SkipListNode<T>[] updates = new SkipListNode[level];

        SkipListNode<T> node = skipList.getHead();
        for(int j=level -1; j >=0; --j) {

            while(node.getNextNode(j) != null && node.getNextNode(j).getScore() < score) {
                node = node.getNextNode(j);
            }

            updates[j] = node;
        }

        node = node.getNextNode(0);

        if (node != null && node.getScore() == score) {
            internalDeleteNode(skipList, node, updates);
            return node;
        } else {
            return null;
        }
    }

    static <T> void internalDeleteNode(SkipList<T> skipList, SkipListNode<T> node, SkipListNode<T>[] updates) {
        for (int i=0; i < skipList.getLevel(); ++i) {
            if(updates[i] != null && updates[i].getNextNode(i) != null && updates[i].getNextNode(i).getScore()==node.getScore()) {
                updates[i].setNextNode(i, node.getNextNode(i));
            }
        }

        if (node.getNextNode(0) != null && node.getNextNode(0).getNextNode() != null) {
            node.getNextNode(0).getNextNode(0).setPrevNode(node.getPrevNode());
        } else {
            skipList.setTail(node.getPrevNode());
        }

        while(skipList.getLevel() > 1 && skipList.getHead().getNextNode(skipList.getLevel()-1) == null) {
            skipList.setLevel(skipList.getLevel() - 1);
        }

        skipList.setLength(skipList.getLength() - 1);
    }

    public static <T> SkipListNode<T> search(SkipList<T> skipList, T value, double score) {

        int level = skipList.getLevel();

        SkipListNode<T> node = skipList.getHead();

        for (int j = level - 1; j >=0 ; --j) {
            if (node.getNextNode(j) != null && node.getNextNode(j).getScore() < score) {
                node = node.getNextNode(j);
            }
        }

        node = node.getNextNode(0) != null ? node.getNextNode(0) : null;

        if (node != null && score == node.getScore()) {
            return node;
        } else {
            return null;
        }
    }


    public static <T> String debugInfo(SkipList<T> skipList) {

        StringBuilder stringBuilder = new StringBuilder();
        int level = skipList.getLevel();
        stringBuilder.append("#############Skip List Debug Begin##############").append(System.getProperty("line.separator"));
        stringBuilder.append("Level:").append(level).append(" ,MaxLevel:").
                append(skipList.getMaxLevel()).append(System.getProperty("line.separator"));
        stringBuilder.append("Element Counts:").append(skipList.getLength()).append(System.getProperty("line.separator"));

        stringBuilder.append("Data section:").append(System.getProperty("line.separator"));

        SkipListNode<T> head = skipList.getHead();
        for (int j = 0; j< level; ++j) {

            stringBuilder.append(String.format("Level[%d]", j));
            SkipListNode<T> node = head.getNextNode(j);
            while(node != null) {
                stringBuilder.append(node).append("->");
                node = node.getNextNode(j);
            }

            stringBuilder.append("NULL").append(System.getProperty("line.separator"));

        }

        stringBuilder.append("#############Skip List Debug End##############").append(System.getProperty("line.separator"));

        return stringBuilder.toString();
    }

    public static void main(String[] args) {

        SkipList<Integer> skipList = createSkipList(8);

        System.out.println(debugInfo(skipList));

        insertNode(skipList, 10, 5.0d);
        /*insertNode(skipList, 11, 5.1d);
        insertNode(skipList, 14, 5.2d);*/
        //insertNode(skipList, 8, 4.9d);

        System.out.println();
        System.out.println(debugInfo(skipList));

        insertNode(skipList, 8, 4.9d);
        System.out.println();
        System.out.println(debugInfo(skipList));

        /*insertNode(skipList, 8, 4.9d);
        System.out.println();
        System.out.println(debugInfo(skipList));

        insertNode(skipList, 15, 5.6d);

        System.out.println();
        System.out.println(debugInfo(skipList));

        deleteNode(skipList, 8, 4.9d);

        System.out.println();
        System.out.println(debugInfo(skipList));*/
        while(true);
    }

}

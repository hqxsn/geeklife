package com.challenge;

import java.util.*;

public class Graph {

    private Set<Shape> nodes;

    public static final int UNDEFINED_POWER = -10;

    public void addShapes(Collection<Shape> shapes) {
        getNodes().addAll(shapes);
    }

    public Set<Shape> getNodes() {
        if (nodes == null) {
            nodes = new HashSet<Shape>();
        }

        return nodes;
    }

    public void addEdge(Shape a, Shape b) {
        a.getConnections().add(b);
        b.getConnections().add(a);
    }

    public int calcPower(Shape shape) {
        int power = UNDEFINED_POWER;
        if (shape != null && getNodes().size() > 0) {
            power = internalCalcPower(shape);
        }

        return power;
    }

    int internalCalcPower(Shape shape) {

        Set<Shape> shapes = getNodes();

        //If shapes.size equals 0 and shapes doesn't contain any the input shape node will return UNDEFINED_POWER
        if (shapes.size() == 0 || !shapes.contains(shape)) {
            return UNDEFINED_POWER;
        }

        //If only contains 1 node just return shape's area as power
        if (shapes.size() == 1) {
            return shape.getArea();
        }

        /*
            If only contains 2 nodes
            1. if shape connected shapes size not equals to 1, return UNDEFINED_POWER
            2. if shape connected shape not in the nodes collection, return UNDEFINED_POWER
            3. then compare the area values of two nodes, return the bigger one
         */
        if (shapes.size() == 2) {
            if (shape.getConnections().size() == 0 || shape.getConnections().size() > 1) return UNDEFINED_POWER;

            Shape nextShape = shape.getConnections().get(0);

            if (!getNodes().contains(nextShape)) return UNDEFINED_POWER;

            return shape.getArea() > nextShape.getArea() ? shape.getArea() : nextShape.getArea();
        }


        //Here begin using the BFS algorithm to traverse the map nodes.
        //Here use bitset to save the if the node was visited.
        BitSet bitSet = new BitSet();

        LinkedList<Shape> queue = new LinkedList<Shape>();

        queue.add(shape);
        bitSet.set(IdUtils.getInstance().distance(shape.getId()), true);

        int power = shape.getArea();

        while(!queue.isEmpty()) {

            Shape tmpShape = queue.peek();

            List<Shape> connections = tmpShape.getConnections();

            for(Shape nextShape:connections) {

                int distance = IdUtils.getInstance().distance(nextShape.getId());

                if (!nodes.contains(nextShape))  return UNDEFINED_POWER;

                if (bitSet.get(distance)) {
                    continue;
                }



                power = power > nextShape.getArea() ? power : nextShape.getArea();
                bitSet.set(distance, true);
                queue.add(nextShape);
            }

            queue.pop();

        }

        return power;
    }

}
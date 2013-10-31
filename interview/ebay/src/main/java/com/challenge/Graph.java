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

        if (shapes.size() == 0 || !shapes.contains(shape)) {
            return UNDEFINED_POWER;
        }

        if (shapes.size() == 1) {
            return shape.getArea();
        }

        if (shapes.size() == 2) {
            Shape nextShape = shape.getConnections().get(0);
            return shape.getArea() > nextShape.getArea() ? shape.getArea() : nextShape.getArea();
        }

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
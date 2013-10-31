package com.challenge.test;

import com.challenge.Graph;
import com.challenge.IdUtils;
import com.challenge.Shape;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class GraphTest {

    @Test
    public void testCase1() {
        long id1 = IdUtils.getInstance().generate(1l);
        long id2 = IdUtils.getInstance().generate(1l);
        long id3 = IdUtils.getInstance().generate(1l);
        long id4 = IdUtils.getInstance().generate(1l);
        long id5 = IdUtils.getInstance().generate(1l);
        long id6 = IdUtils.getInstance().generate(1l);
        long id7 = IdUtils.getInstance().generate(1l);
        long id8 = IdUtils.getInstance().generate(1l);
        long id9 = IdUtils.getInstance().generate(1l);
        long id10 = IdUtils.getInstance().generate(1l);


        List<Shape> shapes = new ArrayList<Shape>();

        Shape shapeA = build(id1, "A", 10);
        shapes.add(shapeA);

        Shape shapeB = build(id2, "B", 15);
        shapes.add(shapeB);

        Shape shapeC = build(id3, "C", 30);
        shapes.add(shapeC);

        Shape shapeD = build(id4, "D", 5);
        shapes.add(shapeD);

        Graph graph = new Graph();
        graph.addShapes(shapes);

        graph.addEdge(shapeA, shapeB);
        graph.addEdge(shapeB, shapeC);
        graph.addEdge(shapeC, shapeA);
        graph.addEdge(shapeD, shapeC);


        assertTrue(graph.calcPower(shapeA) == 30);
        assertTrue(graph.calcPower(shapeB) == 30);
        assertTrue(graph.calcPower(shapeC) == 30);
        assertTrue(graph.calcPower(shapeD) == 30);

    }

    static Shape build(long id, String name, int area) {
        Shape shape = new Shape();

        shape.setId(id);
        shape.setName(name);
        shape.setArea(area);

        return shape;
    }

}

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
        IdUtils.getInstance().reset();

        long id1 = IdUtils.getInstance().generate(1l);
        long id2 = IdUtils.getInstance().generate(1l);
        long id3 = IdUtils.getInstance().generate(1l);
        long id4 = IdUtils.getInstance().generate(1l);
        /*long id5 = IdUtils.getInstance().generate(1l);
        long id6 = IdUtils.getInstance().generate(1l);
        long id7 = IdUtils.getInstance().generate(1l);
        long id8 = IdUtils.getInstance().generate(1l);
        long id9 = IdUtils.getInstance().generate(1l);
        long id10 = IdUtils.getInstance().generate(1l);*/


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

    @Test
    public void testCase2() {
        IdUtils.getInstance().reset();

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

        Shape shapeD = build(id4, "D", 25);
        shapes.add(shapeD);

        Shape shapeE = build(id5, "E", 15);
        shapes.add(shapeE);

        Shape shapeF = build(id6, "F", 55);
        shapes.add(shapeF);

        Shape shapeG = build(id7, "G", 45);
        shapes.add(shapeG);

        Shape shapeH = build(id8, "H", 16);
        shapes.add(shapeH);

        Shape shapeI = build(id9, "I", 5);
        shapes.add(shapeI);

        Shape shapeJ = build(id10, "J", 30);
        shapes.add(shapeJ);

        Graph graph = new Graph();
        graph.addShapes(shapes);

        graph.addEdge(shapeA, shapeB);
        graph.addEdge(shapeB, shapeC);
        graph.addEdge(shapeC, shapeA);
        graph.addEdge(shapeD, shapeC);
        graph.addEdge(shapeE, shapeD);
        graph.addEdge(shapeF, shapeA);
        graph.addEdge(shapeF, shapeE);
        graph.addEdge(shapeF, shapeD);
        graph.addEdge(shapeH, shapeC);
        graph.addEdge(shapeH, shapeG);
        graph.addEdge(shapeH, shapeA);
        graph.addEdge(shapeI, shapeB);
        graph.addEdge(shapeI, shapeE);
        graph.addEdge(shapeJ, shapeI);
        graph.addEdge(shapeJ, shapeF);


        assertTrue(graph.calcPower(shapeE) == 55);
        assertTrue(graph.calcPower(shapeI) == 55);
        assertTrue(graph.calcPower(shapeB) == 55);
        assertTrue(graph.calcPower(shapeA) == 55);
        assertTrue(graph.calcPower(shapeJ) == 55);

    }

    @Test
    public void testCase3() {
        IdUtils.getInstance().reset();

        long id1 = IdUtils.getInstance().generate(1l);
        long id2 = IdUtils.getInstance().generate(1l);

        List<Shape> shapes = new ArrayList<Shape>();

        Shape shapeA = build(id1, "A", 10);
        shapes.add(shapeA);

        Shape shapeB = build(id2, "B", 20);



        Graph graph = new Graph();
        graph.addShapes(shapes);

        assertTrue(graph.calcPower(shapeA) == 10);
        assertTrue(graph.calcPower(shapeB) == Graph.UNDEFINED_POWER);

    }

    @Test
    public void testCase4() {
        IdUtils.getInstance().reset();

        long id1 = IdUtils.getInstance().generate(1l);
        long id2 = IdUtils.getInstance().generate(1l);

        List<Shape> shapes = new ArrayList<Shape>();

        Shape shapeA = build(id1, "A", 10);
        shapes.add(shapeA);

        Shape shapeB = build(id2, "B", 20);
        shapes.add(shapeB);


        Graph graph = new Graph();
        graph.addShapes(shapes);

        graph.addEdge(shapeA, shapeB);

        assertTrue(graph.calcPower(shapeA) == 20);
        assertTrue(graph.calcPower(shapeB) == 20);

    }


    static Shape build(long id, String name, int area) {
        Shape shape = new Shape();

        shape.setId(id);
        shape.setName(name);
        shape.setArea(area);

        return shape;
    }

}

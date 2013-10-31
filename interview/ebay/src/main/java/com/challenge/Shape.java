package com.challenge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with Andy Song.
 * User: andysong
 * Date: 13-10-31
 * Time: AM10:11
 * To change this template use File | Settings | File Templates.
 */
public class Shape implements Serializable {

    private long id;

    private String name;

    private int area;

    private List<Shape> connections;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Shape> getConnections() {
        if (connections == null) {
            connections = new ArrayList<Shape>();
        }
        return connections;
    }

    public void setConnections(List<Shape> connections) {
        this.connections = connections;
    }

    @Override
    public int hashCode() {

        //using jdk 7 new string hashing algorithm, it's faster than less collision
        //Ref:http://vaskoz.wordpress.com/2013/06/03/java-678-hashmap-collisions/
        return sun.misc.Hashing.stringHash32(getName() + id);

        //If using jdk 6 should working with
        //return (getName() + id).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Shape shape1 = (Shape)obj;

        return shape1.name.equals(this.name) && (shape1.id == this.id);

    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }
}

package com.banzneri.AStarTest;

import com.banzneri.geometry.Vector2d;

import java.util.ArrayList;

public class Node {

    public Node parent = null;
    public ArrayList<Node> neighbours = new ArrayList<>();

    public double gValue; //points from start
    public double hValue; //distance from target
    public boolean isWall = false;
    public Vector2d location;
    public double size;

    private final int MOVEMENT_COST = 10;

    public Node(Vector2d point, double size) {
        this.location = point;
        this.size = size;
    }

    /**
     * Used for setting the starting node value to 0
     */
    public void setGValue(int amount) {
        this.gValue = amount;
    }

    public void calculateHValue(Node destPoint) {
        this.hValue = (Math.abs(location.x - destPoint.location.x) + Math.abs(location.y - destPoint.location.y)) * this.MOVEMENT_COST;
    }

    public void calculateGValue(Node point) {
        this.gValue = point.gValue + this.MOVEMENT_COST;
    }

    public double getFValue() {
        return this.gValue + this.hValue;
    }

    @Override
    public String toString() {
        return "[X: " + location.x + ", Y: " + location.y + "]";
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    @Override
    public boolean equals(Object object) {
        Node node = (Node) object;
        return node.location.x == location.x && node.location.y == location.y;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setNeighbours(ArrayList<Node> neighbours) {
        this.neighbours = neighbours;
    }

    public double getgValue() {
        return gValue;
    }

    public void setgValue(double gValue) {
        this.gValue = gValue;
    }

    public double gethValue() {
        return hValue;
    }

    public void sethValue(double hValue) {
        this.hValue = hValue;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public Vector2d getLocation() {
        return location;
    }

    public void setLocation(Vector2d location) {
        this.location = location;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getMOVEMENT_COST() {
        return MOVEMENT_COST;
    }
}
package com.banzneri.geometry;

public class Vector2d {
    public double x;
    public double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2d vector2d) {
        x += vector2d.x;
        y += vector2d.y;
    }

    public void multiply(double x) {
        this.x *= x;
    }

    public void substract(Vector2d vector2d) {
        x -= vector2d.x;
        y -= vector2d.y;
    }

    @Override
    public String toString() {
        return "[X: " + x + ", Y: " + y + "]";
    }

    @Override
    public boolean equals(Object vector2d) {
        Vector2d v = (Vector2d) vector2d;
        return x == v.x && y == v.y;
    }
}

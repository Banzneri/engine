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
}

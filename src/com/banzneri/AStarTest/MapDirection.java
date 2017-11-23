package com.banzneri.AStarTest;

import com.banzneri.geometry.Vector2d;

import java.util.ArrayList;

public class MapDirection {
    private double x;
    private double y;

    public MapDirection(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static ArrayList<MapDirection> values() {
        ArrayList<MapDirection> values = new ArrayList<>();
        values.add(new MapDirection(-32, 0));
        values.add(new MapDirection(32, 0));
        values.add(new MapDirection(0,32));
        values.add(new MapDirection(0, -32));

        return values;
    }

    public Vector2d getVectorForDirection(Vector2d vector2d) {
        Vector2d v = new Vector2d(this.x, this.y);
        v.add(vector2d);
        return v;
    }
}

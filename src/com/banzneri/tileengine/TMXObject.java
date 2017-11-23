package com.banzneri.tileengine;

/**
 * Class for the Tiled map object from the object layer.
 */
public class TMXObject {
    private double x;
    private double y;
    private double width;
    private double height;
    private String name;

    /**
     * Constructor for the TMXObject.
     *
     * @param x The x coordinate of the object
     * @param y The y coordinate of the object
     * @param width The width of the object
     * @param height The height of the object
     * @param name The name of the object
     */
    public TMXObject(double x, double y, double width, double height, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
    }

    // GETTERS & SETTERS

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.banzneri.graphics;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Rect extends Rectangle implements GameObject {
    private double velocityX = 0;
    private double velocityY = 0;
    private double accelerationX;
    private double accelerationY;

    private Color color;

    public Rect(double width, double height) {
        setWidth(width);
        setHeight(height);
        setX(0);
        setY(0);
        color = Color.WHITE;
    }

    public Rect(double x, double y, double width, double height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        //setVisible(false);
    }

    public boolean collidesWith(Rect rect) {

        return intersects(rect.getBoundsInLocal());
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void move() {
        setX(getX() + getVelocityX());
        setY(getY() + getVelocityY());
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(getColor());
        gc.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}

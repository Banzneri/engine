package com.banzneri.graphics;

import com.banzneri.geometry.Rect;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

public abstract class GameObject {
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean collides;
    private double rotation;
    private Rectangle rectangle;
    private Point2D speed = Point2D.ZERO;
    private Point2D acceleration = Point2D.ZERO;

    abstract public void draw(GraphicsContext gc);

    public void move() {
        setX(getX() + speed.getX());
        setY(getY() + speed.getY());
        rectangle.setX(getX());
        rectangle.setY(getY());
    }

    public boolean collidesWith(GameObject o) {
        Shape intersect = Shape.intersect(getRectangle(), o.getRectangle());

        return intersect.getBoundsInLocal().getWidth() != -1;
    }

    public boolean isCollides() {
        return collides;
    }

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

    public void setCollides(boolean collides) {
        this.collides = collides;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
        rectangle = createRectangle(this);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Point2D getSpeed() {
        return speed;
    }

    public void setSpeed(double x, double y) {
        speed = new Point2D(x, y);
    }

    public double getSpeedX() {
        return speed.getX();
    }

    public void setSpeedX(double x) {
        speed = new Point2D(x, speed.getY());
    }

    public double getSpeedY() {
        return speed.getY();
    }

    public void setSpeedY(double y) {
        speed = new Point2D(speed.getX(), y);
    }

    public Point2D getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Point2D acceleration) {
        this.acceleration = acceleration;
    }

    public static Rectangle createRectangle(GameObject gameObject) {
        Rectangle rectangle = new Rectangle(gameObject.getX(), gameObject.getY(),
                                            gameObject.getWidth(), gameObject.getHeight());
        rectangle.setRotate(gameObject.getRotation());
        return rectangle;
    }
}

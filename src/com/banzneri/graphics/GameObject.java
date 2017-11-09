package com.banzneri.graphics;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public abstract class GameObject {
    private double x;
    private double y;
    private double width;
    private double height;
    private boolean collides = true;
    private double rotation = 0;
    private Rectangle rectangle;
    private Point2D location;
    private Point2D speed = Point2D.ZERO;
    private Point2D acceleration = Point2D.ZERO;
    private ArrayList<Point2D> forces = new ArrayList<>();
    private Node node;

    public GameObject(double x, double y, double width, double height) {
        setRectangle(new Rectangle(x, y, width, height));
        setX(x);
        setY(y);
        location = new Point2D(x, y);
        setWidth(width);
        setHeight(height);
    }

    public void move() {
        setX(getX() + getSpeedX());
        setY(getY() + getSpeedY());
        setLocation(new Point2D(getX(), getY()));
    }

    abstract public void draw(GraphicsContext gc);

    public void moveAlternative() {
        rectangle.setTranslateX(getX());
        rectangle.setTranslateY(getY());
        setSpeed(getSpeed().add(getAcceleration()));
        setLocation(getLocation().add(getSpeed()));
        setAcceleration(getAcceleration().multiply(0));
    }

    public boolean collidesWith(GameObject o) {
        if(!isCollides() || !o.isCollides())
            return false;

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
        rectangle.setX(x);
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        rectangle.setY(y);
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        rectangle.setWidth(width);
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        rectangle.setHeight(height);
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
        Rotate rotate = new Rotate();
        rotate.setAngle(getRotation());
        rectangle.getTransforms().add(new Affine(rotate));
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

    public void setSpeed(Point2D speed) {
        this.speed = speed;
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

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public Point2D getCenterLocation() {
        return new Point2D(getX() + (getWidth() / 2), getY() + (getWidth() / 2));
    }
}

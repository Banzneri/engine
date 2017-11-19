package com.banzneri.graphics;

import com.banzneri.geometry.Vector2d;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

/**
 * An abstract representation of the base game object which the user can add to the screen.
 *
 * Keeps info about the objects location, size, rotation, collision, velocity, acceleration and
 * forces acting on the object. Handles the movement according to velocity in move(), which is
 * called each frame in the game loop.
 *
 * Uses a javafx Rectangle object to determine collisions. The javafx Rectangle is updated according to the
 * attributes of the GameObject, but it is not added as a javafx Node onto the scene, the drawing is
 * done on the canvas instead.
 */

public abstract class GameObject {
    private double width;
    private double height;
    private boolean collides = true;
    private double rotation = 0;
    private Rectangle rectangle = new Rectangle();
    private Vector2d location;
    private Vector2d speed = new Vector2d(0, 0);
    private Vector2d acceleration = new Vector2d(0, 0);
    private ArrayList<Vector2d> forces = new ArrayList<>();
    private Node node;
    private double pivotX;
    private double pivotY;
    private boolean isVisible = true;

    /**
     * The base constructor for the GameObject. All game objects should have at least a location and size.
     * Defaults pivotX and pivotY to 0, which is the upper left corner of the object.
     *
     * @param x The object location in x direction
     * @param y The object location in y direction
     * @param width The width of the object
     * @param height The height of the object
     */
    public GameObject(double x, double y, double width, double height) {
        setLocation(new Vector2d(x, y));
        setWidth(width);
        setHeight(height);
        setPivotX(0);
        setPivotY(0);
    }

    /**
     * Sets the location of the object according to velocity.
     *
     * @param delta The time between frames
     */
    public void move(double delta) {
        setX(getX() + getSpeedX());
        setY(getY() + getSpeedY());
    }

    /**
     * An abstract draw method, which handles the drawing on canvas.
     *
     * @param gc The graphicsContext of the canvas
     */
    abstract public void draw(GraphicsContext gc);

    /**
     * Sets the players velocity according to acceleration and location according to velocity.
     */
    public void moveAlternative() {
        forces.forEach(e -> {
            getAcceleration().add(e);
        });
        getSpeed().add(getAcceleration());
        getLocation().add(getSpeed());
        setX(getLocation().x);
        setY(getLocation().y);
        getAcceleration().multiply(0);
    }

    /**
     * Checks if two game objects collide with each other.
     *
     * Creates a Shape out of the intersection of the game objects' javafx Rectangles to
     * determine whether they collide. This takes into account the rotation of the Rectangles.
     *
     * @param o The game object to test collision with
     * @return True if collision happened, false otherwise
     */
    public boolean collidesWith(GameObject o) {
        if(!isCollides() || !o.isCollides())
            return false;

        Shape intersect = Shape.intersect(getRectangle(), o.getRectangle());

        return intersect.getBoundsInLocal().getWidth() != -1;
    }

    /**
     * Checks if a point is contained within the bounding box of the javafx Rectangle.
     *
     * @param x The x location of the point to test
     * @param y The y location of the point to test
     * @return True if a game object contains a point inside it, false otherwise
     */
    public boolean contains(double x, double y) {
        return x > getMinX() && x < getMaxX() && y > getMinY() && y < getMaxY();
    }

    /**
     * Returns the center location of the bounding box of the game object
     *
     * @return The center of the bounding box
     */
    public Vector2d getCenterLocation() {
        return new Vector2d(getMinX() + (getWidth() / 2), getMinY() + (getWidth() / 2));
    }

    /**
     * Checks if the game object should collide
     *
     * @return True if it collides, false otherwise
     */
    public boolean isCollides() {
        return collides;
    }

    public double getMinX() {
        return rectangle.getBoundsInParent().getMinX();
    }

    public double getMaxX() {
        return rectangle.getBoundsInParent().getMaxX();
    }

    public double getMinY() {
        return rectangle.getBoundsInParent().getMinY();
    }

    public double getMaxY() {
        return rectangle.getBoundsInParent().getMaxY();
    }

    public double getX() {
        return location.x;
    }

    public void setX(double x) {
        rectangle.setTranslateX(x);
        location.x = x;

    }

    // Getters and setters. The only special one is setRotation.

    /**
     * Setter for the rotation, in degrees. Gives the javafx Rectangle
     * a new Rotate transform. Sets the angle and pivot point according
     * to the game objects attributes.
     *
     * @param rotation The rotation of the object in degrees.
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
        Rotate rotate = new Rotate();
        rotate.setAngle(getRotation());
        rotate.setPivotX(getPivotX());
        rotate.setPivotY(getPivotY());
        rectangle.getTransforms().clear(); // clears the Rectangle's transforms so the rotation won't add up
        rectangle.getTransforms().add(new Affine(rotate));
    }

    public double getY() {
        return location.y;
    }

    public void setY(double y) {
        rectangle.setTranslateY(y);
        location.y = y;
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

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Vector2d getSpeed() {
        return speed;
    }

    public void setSpeed(double x, double y) {
        speed = new Vector2d(x, y);
    }

    public void setSpeed(Vector2d speed) {
        this.speed = speed;
    }

    public double getSpeedX() {
        return speed.x;
    }

    public void setSpeedX(double x) {
        speed = new Vector2d(x, speed.y);
    }

    public double getSpeedY() {
        return speed.y;
    }

    public void setSpeedY(double y) {
        speed = new Vector2d(speed.x, y);
    }

    public Vector2d getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2d acceleration) {
        this.acceleration = acceleration;
    }

    public void setAcceleration(double x, double y) {
        this.acceleration = new Vector2d(x, y);
    }

    public double getAccelerationY() {
        return acceleration.y;
    }

    public void setAccelerationY(double accelerationY) {
        acceleration.y = accelerationY;
    }

    public double getAccelerationX() {
        return acceleration.x;
    }

    public void setAccelerationX(double accelerationX) {
        acceleration.x = accelerationX;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Vector2d getLocation() {
        return location;
    }

    public void setLocation(Vector2d location) {
        getRectangle().setTranslateX(location.x);
        getRectangle().setTranslateY(location.y);
        this.location = location;
    }

    public double getPivotX() {
        return pivotX;
    }

    public void setPivotX(double pivotX) {
        this.pivotX = pivotX;
    }

    public double getPivotY() {
        return pivotY;
    }

    public void setPivotY(double pivotY) {
        this.pivotY = pivotY;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public ArrayList<Vector2d> getForces() {
        return forces;
    }

    public void setForces(ArrayList<Vector2d> forces) {
        this.forces = forces;
    }
}

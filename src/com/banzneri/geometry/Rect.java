package com.banzneri.geometry;

import com.banzneri.graphics.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

public class Rect extends GameObject {
    private Color color;
    private Line sideUp;
    private Line sideRight;
    private Line sideBottom;
    private Line sideLeft;
    private Point2D topLeft;
    private Point2D topRight;
    private Point2D bottomLeft;
    private Point2D bottomRight;

    public Rect(double width, double height) {
        setWidth(width);
        setHeight(height);
        setX(0);
        setY(0);
        setRotation(0);

        topLeft = new Point2D(getX(), getY());
        topRight = new Point2D(getX() + getWidth(), getY());
        bottomRight = new Point2D(getX() + width, getY() + getHeight());
        bottomLeft = new Point2D(getX(), getY() + getHeight());

        sideUp = new Line(topLeft.getX(), topLeft.getY(), topRight.getX(), topRight.getY());
        sideRight = new Line(topRight.getX(), topRight.getY(), bottomRight.getX(), bottomRight.getY());
        sideBottom = new Line(bottomRight.getX(), bottomRight.getY(), bottomLeft.getX(), bottomLeft.getY());
        sideLeft = new Line(bottomLeft.getX(), bottomLeft.getY(), topLeft.getX(), topLeft.getY());
        color = Color.WHITE;
    }

    public Rect(double x, double y, double width, double height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.transform(new Affine(new Rotate(getRotation(), getX(), getY())));
        gc.setFill(getColor());
        gc.fillRect(getX(), getY(), getWidth(), getHeight());
        gc.restore();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Line getSideUp() {
        return sideUp;
    }

    public Line getSideRight() {
        return sideRight;
    }

    public Line getSideBottom() {
        return sideBottom;
    }

    public Line getSideLeft() {
        return sideLeft;
    }

    public Point2D getTopLeft() {
        return topLeft;
    }

    public Point2D getTopRight() {
        return topRight;
    }

    public Point2D getBottomLeft() {
        return bottomLeft;
    }

    public Point2D getBottomRight() {
        return bottomRight;
    }
}

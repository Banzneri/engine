package com.banzneri.particles;

import com.banzneri.graphics.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import java.util.concurrent.TimeUnit;

public class Particle extends GameObject {
    private double lifeSpan;
    private double size;

    public Particle(Point2D location, Point2D velocity, Point2D acceleration, double size, double lifeSpanInSeconds) {
        super(location.getX(), location.getY(), size, size);
        setSpeedX(velocity.getX());
        setSpeedY(velocity.getY());
        setAcceleration(acceleration);
        setLifeSpan(lifeSpanInSeconds);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.transform(new Affine(new Rotate(getRotation(), getX(), getY())));
        gc.setFill(Color.RED);
        gc.fillRect(getX(), getY(), getWidth(), getHeight());
        gc.restore();
    }

    public double getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(double lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    @Override
    public Node getNode() {
        return getRectangle();
    }

    public void applyForce(Point2D force) {
        setAcceleration(getAcceleration().add(force));
    }

    public void reduceLifeSpan(long time) {
        lifeSpan -= (float) (time / 1E9);
    }

    public boolean isAlive() {
        return lifeSpan > 0;
    }
}

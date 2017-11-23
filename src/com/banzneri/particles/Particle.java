package com.banzneri.particles;

import com.banzneri.geometry.Vector2d;
import com.banzneri.graphics.GameObject;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import java.util.concurrent.TimeUnit;

public class Particle extends GameObject {
    private long lifeSpan;
    private double maxLifeSpan;
    private Emitter host;

    public Particle(Vector2d location, Vector2d velocity, Vector2d acceleration, double size, long lifeSpanInSeconds) {
        super(location.x, location.y, size, size);
        setSpeedX(velocity.x);
        setSpeedY(velocity.y);
        setAcceleration(acceleration);
        setLifeSpan(lifeSpanInSeconds);
        setMaxLifeSpan(lifeSpanInSeconds);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.setGlobalAlpha(0.5);
        gc.transform(new Affine(new Rotate(getRotation(), getX(), getY())));
        gc.setFill(Color.RED);
        gc.fillRect(getX(), getY(), getWidth(), getHeight());
        gc.restore();
    }

    public double getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(long lifeSpan) {
        this.lifeSpan = TimeUnit.NANOSECONDS.convert(lifeSpan, TimeUnit.SECONDS);
    }

    @Override
    public Node getNode() {
        return getRectangle();
    }

    public void applyForce(Vector2d force) {
        getAcceleration().add(force);
    }

    public void reduceLifeSpan(long time) {
        lifeSpan -= time;
    }

    public boolean isAlive() {
        return lifeSpan > 0;
    }

    public double getMaxLifeSpan() {
        return maxLifeSpan;
    }

    public void setMaxLifeSpan(double maxLifeSpan) {
        this.maxLifeSpan = maxLifeSpan;
    }

    public Emitter getHost() {
        return host;
    }

    public void setHost(Emitter host) {
        this.host = host;
    }
}

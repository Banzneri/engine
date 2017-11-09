package com.banzneri.particles;

import com.banzneri.Screen;
import com.banzneri.Util;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Emitter {
    private Point2D location;
    private double width;
    private ArrayList<Particle> particles = new ArrayList<>();
    private AnimationTimer timer;
    private Screen host;

    public Emitter(Point2D location, double width, Screen host) {
        setLocation(location);
        setWidth(width);
        this.host = host;
    }

    public Point2D getLocation() {
        return location;
    }

    public Particle addParticle() {
        Random r = new Random();
        double x = getLocation().getX();
        double y = getLocation().getY();
        Point2D location = new Point2D(Util.getRandomInRange(x, x + getWidth()), y);
        double vx = r.nextGaussian() * 0.3;
        double vy = r.nextGaussian() * 0.3 - 1.0;
        Point2D velocity = new Point2D(vx, vy);
        Point2D acceleration = Point2D.ZERO;
        Particle particle = new Particle(location, velocity, acceleration, 2, 2);
        particle.getRectangle().setFill(Color.RED);
        particles.add(particle);
        return particle;
    }

    public void emit() {
        timer = new AnimationTimer() {
            long start = System.nanoTime();
            @Override
            public void handle(long now) {
                long elapsed = now - start;
                Point2D forceGravity = new Point2D(0, 0.01);
                particles.forEach(e -> {
                    e.applyForce(forceGravity);
                    e.moveAlternative();
                    e.reduceLifeSpan(elapsed);
                });
                start = now;
                for (int i = 0; i < 100; i++) {
                    host.addGameObject(addParticle());
                }
                killParticles();
            }
        };
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void killParticles() {
        particles.forEach(e -> {
            if (!e.isAlive()) host.removeGameObject(e); });
        particles.removeIf(particle -> !particle.isAlive());
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void setParticles(ArrayList<Particle> particles) {
        this.particles = particles;
    }
}

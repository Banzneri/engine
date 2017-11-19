package com.banzneri.particles;

import com.banzneri.Screen;
import com.banzneri.Util;
import com.banzneri.geometry.Vector2d;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Emitter {
    private Particle[] particles;
    private Vector2d location;
    private double width;
    private AnimationTimer timer;
    private double particleSize;
    private long particleLifeSpan;
    private int maxParticles;
    private Screen host;
    private ArrayList<Particle> spawnPool = new ArrayList<>();
    private Random random = new Random();
    private double rateInSeconds = 1000;
    private long duration;

    public Emitter(Vector2d location, double width, Screen host) {
        setHost(host);
        setLocation(location);
        setWidth(width);
        setParticleSize(5);
        setParticleLifeSpan(1);
        setMaxParticles(100000);
        host.setParticles(spawnPool);
        setDuration(TimeUnit.NANOSECONDS.convert(500, TimeUnit.MILLISECONDS));
        loadParticles();
    }

    public Vector2d getLocation() {
        return location;
    }

    private Particle createParticle() {
        double x = getLocation().x;
        double y = getLocation().y;
        Vector2d location = new Vector2d(Util.getRandomInRange(x, x + getWidth()), y);
        double vx = random.nextGaussian() * 0.3;
        double vy = random.nextGaussian() * 0.3 - 1.0;
        Vector2d velocity = new Vector2d(vx, vy);
        Vector2d acceleration = new Vector2d(0, 0);
        Particle particle = new Particle(location, velocity, acceleration, particleSize, particleLifeSpan);
        particle.getRectangle().setFill(Color.RED);
        particle.setHost(this);
        return particle;
    }

    private void loadParticles() {
        particles = new Particle[getMaxParticles()];
        for(int i = 0; i < getMaxParticles(); i++) {
            particles[i] = createParticle();
        }
    }

    public void emit() {
        timer = new AnimationTimer() {
            long start = System.nanoTime();
            long beginning = System.nanoTime();

            Vector2d forceGravity = new Vector2d(0, 0.01);
            int idx = 0;

            @Override
            public void handle(long now) {
                long elapsed = now - start;
                spawnPool.removeIf(e -> !e.isAlive());
                for (int i = 0; i < rateInSeconds * host.getDelta(); i++) {
                    respawn(particles[idx]);
                    spawnPool.add(particles[idx]);
                    idx++;
                    if(idx >= particles.length - 1) {
                        idx = 0;
                    }
                }
                spawnPool.stream().parallel().forEach(e ->  {
                    e.reduceLifeSpan(elapsed);
                    e.applyForce(forceGravity);
                    e.moveAlternative();

                });
                start = now;
                if (now - beginning > TimeUnit.NANOSECONDS.convert(duration, TimeUnit.MILLISECONDS)) {
                    spawnPool.clear();
                    this.stop();
                }
            }
        };
        timer.start();
    }

    private void respawn(Particle particle) {
        double x = getLocation().x;
        double y = getLocation().y;
        double vx = random.nextGaussian() * 0.3;
        double vy = random.nextGaussian() * 0.3 - 1.0;
        Vector2d location = new Vector2d(Util.getRandomInRange(x, x + getWidth()), y);

        particle.setLocation(location);
        particle.setSpeed(new Vector2d(vx, vy));
        particle.setAcceleration(new Vector2d(0, 0));
        particle.setLifeSpan(particleLifeSpan);
    }

    public void setLocation(Vector2d location) {
        this.location = location;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getParticleSize() {
        return particleSize;
    }

    public void setParticleSize(double particleSize) {
        this.particleSize = particleSize;
        loadParticles();
    }

    public long getParticleLifeSpan() {
        return particleLifeSpan;
    }

    public void setParticleLifeSpan(long particleLifeSpan) {
        this.particleLifeSpan = particleLifeSpan;
    }

    public int getMaxParticles() {
        return maxParticles;
    }

    public void setMaxParticles(int maxParticles) {
        this.maxParticles = maxParticles;
        loadParticles();
    }

    public Screen getHost() {
        return host;
    }

    public void setHost(Screen host) {
        this.host = host;
    }

    public ArrayList<Particle> getSpawnPool() {
        return spawnPool;
    }

    public void setSpawnPool(ArrayList<Particle> spawnPool) {
        this.spawnPool = spawnPool;
    }

    public double getRateInSeconds() {
        return rateInSeconds;
    }

    public void setRateInSeconds(double rateInSeconds) {
        this.rateInSeconds = rateInSeconds;
    }

    public boolean isOutOfBounds(Particle particle) {
        return particle.getX() > getHost().getWidth() || particle.getX() < 0 || particle.getY() > getHost().getHeight() || particle.getY() < 0;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}

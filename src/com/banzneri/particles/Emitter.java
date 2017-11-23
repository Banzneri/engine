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

/**
 * The class for the particle emitter. You can adjust different parameters to make the particles behave differently:
 * their size, their max life span, their spawn rate, the maximum number of particles on the screen at the same time
 * and the duration the emitter keeps emitting.
 */
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

    /**
     * The constructor for the Emitter.
     *
     * @param location The location of the emitter
     * @param width The width of the emitter base
     * @param host The host Screen object
     */
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

    /**
     * Creates a new particle according to the emitter attributes.
     *
     * @return A new particle object
     */
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

    /**
     * Loads particles into the particles array, according to the maxParticles attribute
     */
    private void loadParticles() {
        particles = new Particle[getMaxParticles()];
        for(int i = 0; i < getMaxParticles(); i++) {
            particles[i] = createParticle();
        }
    }

    /**
     * An AnimationTimer, which emits the particles, handles respawning of the particles,
     * applies forces to the particles and moves the particles. Keeps doing this according to the
     * duration attribute.
     */
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

    /**
     * Respawns a particle. Makes it spawn again from the base of the emitter.
     *
     * @param particle The particle to respawn
     */
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

    /**
     * Checks if a particle is out of the screen.
     *
     * @param particle The particle to check
     * @return True if out of screen, false otherwise
     */
    public boolean isOutOfBounds(Particle particle) {
        return particle.getX() > getHost().getWidth() || particle.getX() < 0 || particle.getY() > getHost().getHeight() || particle.getY() < 0;
    }

    // GETTERS & SETTERS

    public Vector2d getLocation() {
        return location;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
